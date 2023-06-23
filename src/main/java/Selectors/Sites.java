package Selectors;

import Converts.ArrayEx;

/**
 * Работа с адресами сайтов
 */
public class Sites extends Selectors{
    public Sites(String input) {
        super(input);
    }

    /**
     * Выбирает сайт {@link #input} и помещает в {@link #result}
     * Предлагает сделать ручной выбор при неправильном вводе (Если в списке сайтов больше одного).
     */
    public void selector() {

        if((input.toLowerCase()).contains("citilink")) {
            result = "https://www.citilink.ru/";
        } else {
            System.out.println("Unknown Site");
            String [] siteList = {"https://www.citilink.ru/"};

            // Здесь можно посмотреть как селектор отрабатывает массив с одним элементом
            result = ArrayEx.selector1D(siteList);

        }

    }

}
