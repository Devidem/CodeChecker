package tests.citilink.finalTest.supportClasses.pojos;

/**
 * Pojo класс для сохранения ссылки на страницу товара {@link #link_url}
 */
public class PojoProductLink {
    public String link_url;

    public PojoProductLink(String link_url) {
        this.link_url = link_url;
    }

    public String getLink_url() {
        return link_url;
    }
}
