package seven.belog.loans.domain;

import lombok.NonNull;

public class PaymentCode implements Comparable<PaymentCode> {

    private final char value;
    private final int badness;
    private final String localization;

    private PaymentCode(char value, int badness, String localization) {
        this.value = value;
        this.badness = badness;
        this.localization = localization;
    }

    public char getValue() {
        return value;
    }

    public String getLocalization() {
        return localization;
    }

    @Override
    public int compareTo(@NonNull PaymentCode other) {
        if (this.badness == other.badness) {
            return 0;
        }

        return this.badness > other.badness ? 1 : -1;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof PaymentCode)) {
            return false;
        }

        return this.value == ((PaymentCode) other).value;
    }

    @Override
    public int hashCode() {
        return badness;
    }

    public static PaymentCode of(char value) {
        switch (value) {
            case '1':
                return new PaymentCode('1', 0, "платеж вовремя");
            case '0':
                return new PaymentCode('0', 1, "данные не поступили");
            case 'A':
                return new PaymentCode('A', 2, "просрочка 7 дней");
            case '2':
                return new PaymentCode('2', 3, "просрочка от 29 до 39 дней");
            case '3':
                return new PaymentCode('3', 4, "просрочка свыше 39 дней");
            case 'X':
                return new PaymentCode('X', 5, "отсутствие данных");
            default:
                throw new IllegalStateException("Unknown payment code char: " + value);
        }
    }

    public static PaymentCode noData() {
        return new PaymentCode('X', 5, "отсутствие данных");
    }
}