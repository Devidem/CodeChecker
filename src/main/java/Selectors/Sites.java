package Selectors;

import Converts.Array;

import java.io.IOException;

public class Sites {
    public static String selector(String site) throws IOException {

        if((site.toLowerCase()).contains("citilink")) {
            site = "https://www.citilink.ru/";
            return site;
        }

        System.out.println("Такого сайта пока нет!");

        String [] siteList = {"https://www.citilink.ru/"};

        site = Array.picker(siteList);
        return site;
    }

}
