package Selectors;

/**
 * Абстрактный класс для селекторов
 */
public abstract class Selectors {
    String input;
    String result;

    public Selectors(String input) {
        this.input = input;
    }

    /**
     * Передает значение выбора в {@link #result} в зависимости от {@link #input}
     */
    public abstract void selector();


    // Геттеры и Сеттеры
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}


