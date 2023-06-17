package Selectors;

public class Sites {
    public static String selector(String site) {
        site = site.toLowerCase();
        if(site.contains("citilink")) {
            site = "https://www.citilink.ru/";
        }
        System.out.println("Такого сайта пока нет!");
        String [] siteList = {"https://www.citilink.ru/"};
        System.out.println("Выберите из имеющихся");


        return site;
    }

}
