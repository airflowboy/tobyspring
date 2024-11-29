package tobyspring.hellospring.payment;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaymentServiceTest{
    Clock clock;

    @BeforeEach
    void beforeEach() {
        this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족했는지 검증")
    void convertedAmount(){
        testAmount(BigDecimal.valueOf(500), BigDecimal.valueOf(5_000), clock);
        testAmount((BigDecimal.valueOf(1_000)), BigDecimal.valueOf(10_000), clock);
        testAmount((BigDecimal.valueOf(3_000)), BigDecimal.valueOf(30_000), clock);
    }

    @Test
    void validUntil() {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(BigDecimal.valueOf(1_000)), clock);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }

    private static void testAmount(BigDecimal exRate, BigDecimal convertedAmount, Clock clock) {
        //준비
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);
        //실행
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);
        //검증
        Assertions.assertThat(payment.getExRate()).isEqualByComparingTo(exRate);
        Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
//        return payment;
    }
}