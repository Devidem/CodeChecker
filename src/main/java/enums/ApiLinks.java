package enums;

public enum ApiLinks {
    SearchProdLink ("https://autocomplete.diginetica.net/autocomplete?st=STRING_VARIABLE&apiKey=UCC76J09GL"),
    SearchProdPromo ("https://www.citilink.ru/graphql/")
    ;


    public String getLink() {
        return link;
    }
    public String getLinkVariable (String stringVariable) {
        return getLink().replace("STRING_VARIABLE", stringVariable);
    }

    private final String link;
    ApiLinks(String link) {
        this.link = link;
    }

}