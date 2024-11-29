package tobyspring.hellospring.exrate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import tobyspring.hellospring.payment.ExRateProvider;

public class CachedExRateProvider implements ExRateProvider {

    private final ExRateProvider target;

    private BigDecimal cachedExRate;

    private LocalDateTime cacheExpiredTime;

    public CachedExRateProvider(ExRateProvider target) {
        this.target = target;
    }

    @Override
    public BigDecimal getExRate(String currency) {
        if(cachedExRate == null || cacheExpiredTime.isBefore(LocalDateTime.now())) {
            cachedExRate = this.target.getExRate(currency);
            cacheExpiredTime = LocalDateTime.now().plusSeconds(3);

            System.out.println("Cache Updated");
        }
        return cachedExRate;
    }
}
