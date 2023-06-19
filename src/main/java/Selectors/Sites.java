package Selectors;

import Converts.Array;

import java.io.IOException;

public class Sites extends Selectors{
    public void selector() {

        if((input.toLowerCase()).contains("citilink")) {
            result = "https://www.citilink.ru/";
        } else {
            System.out.println("Такого сайта пока нет!");
            String [] siteList = {"https://www.citilink.ru/"};


                Array array = new Array();
                array.setInput1D(siteList);
                array.selector1D();
                result = array.getResult1D();


        }

    }

}
