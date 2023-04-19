package woowacourse.movie.domain

import org.junit.Assert
import org.junit.Test
import woowacourse.movie.domain.discount.EarlyTimeDiscount
import java.time.LocalDateTime

class EarlyTimeDiscountTest {
    @Test
    fun `기존 금액이 26000원일 때 시간이 이른 경우 (11시 이전) 예매 금액은 24000원이다`() {
        val actual = EarlyTimeDiscount(
            LocalDateTime.of(2023, 8, 7, 10, 0)
        ).getDiscountPrice(26000)

        Assert.assertEquals(24000, actual)
    }

    @Test
    fun `기존 금액이 26000원일 때 시간이 이르지 않은 경우 예매 금액은 26000원이다`() {
        val actual = EarlyTimeDiscount(
            LocalDateTime.of(2023, 8, 7, 15, 0)
        ).getDiscountPrice(26000)

        Assert.assertEquals(26000, actual)
    }
}
