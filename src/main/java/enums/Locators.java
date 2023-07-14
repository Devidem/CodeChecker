package enums;

/**
 * Набор локаторов
 * Динамические локаторы начинаются с Var и должны вызываться методом getXpathVariable
 */
public enum Locators {
    SearchButton("//*[@class=\"css-1d9cswg e15krpzo1\"]/*[@type=\"submit\"]"),
    Catalog ("//*[@class=\"css-3nmxdw eyoh4ac0\"]/*[@href=\"/catalog/\"]"),
    SearchField ("//input[@type=\"search\"]"),
    SearchWatchedBefore ("//*[contains(text(),'просмотренные')]"),
    VarSearchResult("//*[contains(@href,\"" + "XPATH_VARIABLE" + "\")]//*[@data-meta-name=\"InstantSearchMainResult\"]"),
    PopularCategory ("//*[@class=\"edhylph0 app-catalog-1ljlt6q e3tyxgd0\"]"),
    ProductAbout ("//*[@data-meta-value = \"about\"]"),
    ProductPageProdContainer("//*[@class=\"app-catalog-1xdhyk6 e19nkc9p0\"]"),
    VarProductPromoMain("//*[@data-meta-name=\"ProductHeaderContentLayout\"]//*[contains(text(),'" + "XPATH_VARIABLE" + "')]")
    ;





    public String getXpath() {
        return xPath;
    }
    public String getXpathVariable (String xpathVariable) {
        return getXpath().replace("XPATH_VARIABLE", xpathVariable);
    }

    private final String xPath;
    Locators(String xPath) {
        this.xPath = xPath;
    }

}
