package seven.belog.loans.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PaymentCodeTest {

    @Test
    void createFromChar() {
        assertThat(PaymentCode.of('1').getLocalization()).isEqualTo("платеж вовремя");
        assertThat(PaymentCode.of('0').getLocalization()).isEqualTo("данные не поступили");
        assertThat(PaymentCode.of('A').getLocalization()).isEqualTo("просрочка 7 дней");
        assertThat(PaymentCode.of('2').getLocalization()).isEqualTo("просрочка от 29 до 39 дней");
        assertThat(PaymentCode.of('3').getLocalization()).isEqualTo("просрочка свыше 39 дней");
        assertThat(PaymentCode.of('X').getLocalization()).isEqualTo("отсутствие данных");
    }

    @Test
    void createNoData() {
        assertThat(PaymentCode.noData().getLocalization()).isEqualTo("отсутствие данных");
    }

    @Test
    void compareByBadness() {
        var payment1 = PaymentCode.of('1');
        var payment0 = PaymentCode.of('0');
        var paymentA = PaymentCode.of('A');
        var payment2 = PaymentCode.of('2');
        var payment3 = PaymentCode.of('3');
        var paymentX = PaymentCode.of('X');

        assertThat(paymentX.compareTo(payment3)).isPositive();
        assertThat(payment3.compareTo(payment2)).isPositive();
        assertThat(payment2.compareTo(paymentA)).isPositive();
        assertThat(paymentA.compareTo(payment0)).isPositive();
        assertThat(payment0.compareTo(payment1)).isPositive();
    }
}
