package tobyspring.hellospring.payment;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import net.bytebuddy.asm.Advice.Local;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tobyspring.hellospring.exrate.WebApiExRateProvider;

class PaymentServiceTest{

    @Test
    @DisplayName("prepare 메소드가 요구사항 3가지를 잘 충족했는지 검증")
    void convertedAmount() throws Exception{
        testAmount(BigDecimal.valueOf(500), BigDecimal.valueOf(5_000));
        testAmount((BigDecimal.valueOf(1_000)), BigDecimal.valueOf(10_000));
        testAmount((BigDecimal.valueOf(3_000)), BigDecimal.valueOf(30_000));
//        Assertions.assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
//        Assertions.assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }

    private static void testAmount(BigDecimal exRate, BigDecimal convertedAmount) throws Exception {
        //준비
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate));
        //실행
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);
        //검증
        Assertions.assertThat(payment.getExRate()).isEqualByComparingTo(exRate);
        Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
//        return payment;
    }
}