package converters;

import enums.ConstInt;
import enums.PostgreData;
import exceptions.myExceptions.MyFileIOException;
import experiments.ExObjects;

import java.sql.*;
import java.util.Objects;

/**
 * Класс для работы с PostgreSql
 */
public class ExSql{

    /**
     * Получение финального проверочного массива из SQL
     */
    public static String[][] toFinalArray() throws MyFileIOException {

        String queryCodes = "SELECT * from prodcode_promo";
        String queryProms = "SELECT * from active_promo";

        //Массив с кодами товаров и отображаемыми акциями
        String [][] codes;
        //Массив с списком проверяемых акций
        String [][] proms;

        //Заполняем массивы данными из SQL
        try {
            codes = to2dString(PostgreData.LocalProdcode1, queryCodes);
            proms = to2dString(PostgreData.LocalProdcode1, queryProms);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при генерации массивов из SQL");
        }

        //Номер строки, с которой начинается перечисление кодов в codes
        int codeStartRow = ConstInt.startRow.getValue();
        //Номер ячейки, с которой начинается перечисление акций в codes
        int promStartCell = 1;
        // Счетчик удаленных акций
        int deleted = 0;
        //Проверочный символ
        String promSymbol = "*";

        //Убираем текст из первого ряда в скидках, которые не нужно проверять + считает количество удаленных ячеек
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
        // Переносим данные из отредактированного массива - забираются только столбцы с непустой 0-й строкой
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
     * Преобразует результат запроса в двумерный String массив
     */
    public static String [][] to2dString(PostgreData databaseEnum, String query) throws SQLException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection connection = DriverManager.getConnection(databaseEnum.getUrl(), databaseEnum.getUser(),
                databaseEnum.getPassword());

        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet result = statement.executeQuery(query);

        String [][] info = ExSql.getTemplateString2d(result);
        ResultSetMetaData data = result.getMetaData();

        //Добавляем шапку с именами ячеек
        for (int i = 1; true; i++) {
            try {
                info[0][i - 1] = data.getColumnName(i);
            } catch (SQLException e) {
                break;
            }
        }

        //Переписываем значения
        for (int o = 1; result.next(); o++) {
            for (int i = 1; true; i++) {
                try {
                    info [o][i-1] = ExObjects.toString(result.getString(i));
                } catch (SQLException e) {
                    break;
                }
            }
        }

        //Перемещаем курсор в изначальное положение и возвращаем массив
        result.beforeFirst();
        return info;
    }

    /**
     * Возвращает пустой двумерный массив с размером соответствующим результату запроса
     */
    private static String [][] getTemplateString2d (ResultSet result) throws SQLException {

        //Считаем количество строк и ячеек
        int rows = 0;
        int cells = 0;

        while (result.next()) {
            rows++;
            for (int i = 1; true; i++) {
                try {
                    result.getString(i);
                    cells=i;
                } catch (SQLException e) {
                    break;
                }
            }
        }

        //Перемещаем курсор в изначальное положение и возвращаем пустой массив
        result.beforeFirst();
        return new String[rows+1][cells];
    }

}
