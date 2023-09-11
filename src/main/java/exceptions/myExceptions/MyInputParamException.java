package exceptions.myExceptions;

/**
 * Исключение для ошибок, связанных с неправильным вводом входных параметров.
 */
public class MyInputParamException extends Exception{
    public MyInputParamException() {
    }

    public MyInputParamException(String message) {
        super(message);
    }

    public MyInputParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyInputParamException(Throwable cause) {
        super(cause);
    }

    public MyInputParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
