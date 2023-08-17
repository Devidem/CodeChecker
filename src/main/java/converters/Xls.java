package converters;

import enums.ConstInt;
import exceptions.myExceptions.MyFileIOException;
import experiments.ExObjects;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Обработка Xls файлов
 */
public class Xls {

    /**
     * Преобразование .xls файла в двумерный массив с кодами товаров и проверяемыми акциями.
     * .xls имеет 2 листа - лист с кодами товаров и имеющимися у них акциями
     * + лист с указанием акций, которые будут проверяться.
     * @param filePath путь к .xls файлу
     * @return Двумерный массив с кодами товаров и проверяемыми акциями ( * - если должна отображаться, пустая строка если не должна)
     */
    public  String[][] toFinalArray(String filePath) throws IOException, MyFileIOException {
        //Массив с кодами товаров и отображаемыми акциями
        String [][] codes = fileToArray(filePath, 0);
        //Массив с списком проверяемых акций
        String [][] proms = fileToArray(filePath, 1);

        //Номер строки, с которой начинается перечисление кодов в codes
        int codeStartRow = ConstInt.startRow.getValue();
        //Номер ячейки, с которой начинается перечисление акций в codes
        int promStartCell = 1;
        // Счетчик удаленных акций
        int deleted = 0;
        //Проверочный символ
        String promSymbol = "*";

        //Убирает текст из первого ряда в скидках, которые не нужно проверять + считает количество удаленных ячеек
        for (int i = 0; i< (proms.length-ConstInt.startRow.getValue()); i++) {
            String starCheck = proms [codeStartRow+i] [1];

            if (!Objects.equals(starCheck, promSymbol)) {
                String promName = proms [codeStartRow+i] [0];
                for (int a = promStartCell; a <(codes[0].length); a++) {
                    if (Objects.equals(codes[0][a], promName)) {
                        codes [0][a] = "";
                        deleted++;
                        break;
                    }
                }
            }
        }

        //Массив без лишних ячеек (с учетом удаленных ячеек/столбцов)
        String [][] finalArray = new String[codes.length][codes[0].length-deleted];
        // Счетчик(указатель) столбцов для конечного массива finalArray
        int oFinal = 0;
        // Переносит данные из отредактированного массива - забираются только столбцы с непустой 0-й строкой
        for (int o=0; o<codes[0].length; o++ ) {
            try {
                if (!Objects.equals(codes[0][o], "")) {
                    for (int p=0; p< codes.length; p++ ) {
                        finalArray [p][oFinal] = codes [p][o];

                    }
                    //Не увеличивается только при столбцах с потертой скидкой
                    oFinal++;
                }
            //Возникает в случае несоотвествия файла используемому образцу(не тот Template)
            } catch (NullPointerException e) {
                throw new MyFileIOException("Первый и второй лист не соответствуют требованиям", e);
            }

        }
        return finalArray;
    }

    /**
     * Преобразует указанный лист .xls файла в двумерный массив.
     * @param filePath Путь к .xls файлу
     * @param sheet Номер листа (начинается с 0)
     */
    public String[][] fileToArray (String filePath, int sheet) throws IOException {
        FileInputStream direct = new FileInputStream(filePath);
        Workbook proms = new HSSFWorkbook(direct);
        //Создается массив исходя из количества рядов и стобцов в файле
        int rows = calc_R(filePath, sheet);
        int cells = calc_C(filePath, sheet);
        String [][] finArr = new String[rows][cells];
        //Переписываеся значения ячеек
        for (int i = 0; i < rows; i++) {
            for (int o = 0; o < cells; o++) {
                try {
                    finArr [i][o] = ExObjects.toString(proms.getSheetAt(sheet).getRow(i).getCell(o));
                }
                //Если будет пустой или несуществующий лист
                catch (Exception NullPointerException) {
                    finArr [i][o] = "";
                }

            }
        }
        return finArr;
    }

    /**
     * Считает количество рядов .xls файла в первом столбце.
     * @param filePath Путь к .xls файлу
     * @param sheet Номер листа (начинается с 0)
     */
    public int calc_R(String filePath, int sheet) throws IOException{

        FileInputStream direct = new FileInputStream(filePath);
        Workbook toArray = new HSSFWorkbook(direct);

        //Счетчик рядов (для наглядности) + номер ряда с которой с которого начинается перечисление кодов товаров (начинается 0)
        int cycleRow = 2;
        //Шаг для проверки следующего ряда
        int step = 1;


        // Цикл подсчета рядов
        // Apache Poi может возвращать 3 варианта на пустую ячейку в excel в зависимости от условий - пустая строка, строковое null и NullPointerException
        // Поэтому, чтобы упростить описание условий и привести таблицу к единому виду, используется созданный
        // метод ObjectsEx.toString - аналог метода Objects, только превращает строковое null в пустую строку
        while (step!=0) {
            try {
                // Если ячейка не пустая, то увеличиваем шаг в 2 раза
                if (!Objects.equals(ExObjects.toString(toArray.getSheetAt(sheet).getRow(cycleRow).getCell(0)), "")) {
                    step = step * 2;
                    cycleRow = cycleRow + step;
                // Если ячейка пустая и шаг не 1, то уменьшаем шаг в 2 раза
                } else if (Objects.equals(ExObjects.toString(toArray.getSheetAt(sheet).getRow(cycleRow).getCell(0)), "") & step!=1) {
                    step = step / 2;
                    cycleRow = cycleRow - step;
                // Если ячейка пустая и шаг 1, то завершаем подсчет, поскольку находимся в первой пустой ячейке
                // Из итогового значения не вычитается единица, поскольку счет ведется с 0
                } else if (Objects.equals(ExObjects.toString(toArray.getSheetAt(sheet).getRow(cycleRow).getCell(0)), "") & step==1) {
                    step = 0;
                }
                else {
                    System.out.println("Здесь невозможно оказаться");
                }
            }
            // Аналог последних двух else if, но для NullPointerException
            catch (NullPointerException e) {
                if (step!=1) {
                    step = step / 2;
                    cycleRow = cycleRow - step;
                } else {
                    step = 0;
                }
            }
        }
        return cycleRow;
    }

    /**
     * Считает количество ячеек в .xls файла в 1(0) ряду.
     * @param filePath Путь к .xls файлу
     * @param sheet Номер листа (начинается с 0)
     */
    //Работает аналогично calc_R, но упрощен в цикле для кейса с NullPointerException
    public int calc_C(String filePath, int sheet) throws IOException{

        FileInputStream Direct = new FileInputStream(filePath); //C:/Users/SHH/IdeaProjects/PreFinalExcel/Base.xls
        Workbook toArray = new HSSFWorkbook(Direct);

        int cycleCell = 0;
        int step = 1;

        //Работает аналогично calc_R, но упрощен в NullPointerException
        while (step!=0) {
            try {
                if (!Objects.equals(ExObjects.toString(toArray.getSheetAt(sheet).getRow(0).getCell(cycleCell)), "")) {
                    step = step * 2;
                    cycleCell = cycleCell + step;

                } else if (Objects.equals(ExObjects.toString(toArray.getSheetAt(sheet).getRow(0).getCell(cycleCell)), "") & step!=1) {
                    step = step / 2;
                    cycleCell = cycleCell - step;

                } else if (Objects.equals(ExObjects.toString(toArray.getSheetAt(sheet).getRow(0).getCell(cycleCell)), "") & step==1) {
                    step = 0;

                }
                else {
                    System.out.println("Здесь невозможно оказаться");
                }
            //Здесь можно оказаться только если в нулевой ячейке будет пусто или указании несуществующего листа
            } catch (NullPointerException e) {
                step = 0;
            }

        }
        return cycleCell;
    }

}
