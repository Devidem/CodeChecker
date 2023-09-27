package tests.citilink.finalTest.supportClasses.pojos;

/**
 * Pojo класс для сохранения имен промо-акций {@link #title}
 */
public class PojoPromoName {

    public String getTitle() {
        return title;
    }

    public PojoPromoName(String title) {
        this.title = title;
    }

    private final String title;

}
