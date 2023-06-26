package exceptions.myExceptions;

/**
 * Неверный ввод адресов Input/Output папок/файлов или их остсутствие
 * Создан, чтобы можно было отследить ошибки возникающие из-за написанных методов или при неправильном использовании программы
 */
//То есть, в случаях когда ошибка появляется из-за генерации некорректного пути благодаря кривым методам или ручкам, которые их используют, мы получаем MyFileIOException
//В остальных случаях IOException - когда используются методы, в которые непосредственно передается полный путь файла
public class MyFileIOException extends Exception {

    public MyFileIOException() {
    }

    public MyFileIOException(String message) {
        super(message);
    }

    public MyFileIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyFileIOException(Throwable cause) {
        super(cause);
    }

    public MyFileIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
