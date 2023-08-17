package enums;

/**
 * Сборник общих int констант/переменных
 */
public enum ConstInt {
    //Ряд, с которого начинаются записи кодов товаров в Excel
    startRow (2-1)
    ;



    public int getValue() {
        return value;
    }

    private final int value;

    ConstInt(int value) {
        this.value = value;
    }
}
