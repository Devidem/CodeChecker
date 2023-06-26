package interfaces;

import exceptions.myExceptions.MyFileIOException;

/**
 * Преобразование элемента в итоговый массив проверки акций у товаров
 */
public interface ToPromsArray {
    String [][] toFinalArray() throws MyFileIOException;

}
