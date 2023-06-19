package Selectors;

public abstract class Selectors {
    String input;
    String result;

    public abstract void selector();


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


