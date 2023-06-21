package Selectors;

import Converts.ArrayEx;

public class Sites extends Selectors{
    public void selector() {

        if((input.toLowerCase()).contains("citilink")) {
            result = "https://www.citilink.ru/";
        } else {
            System.out.println("Unknown Site");
            String [] siteList = {"https://www.citilink.ru/"};

            ArrayEx arrayEx = new ArrayEx();
            arrayEx.setInput1D(siteList);
            arrayEx.selector1D();
            result = arrayEx.getResult1D();


        }

    }

}
