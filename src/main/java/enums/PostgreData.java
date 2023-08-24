package enums;

/**
 * Место для хранения данных доступа к базам SQL
 */
public enum PostgreData {
    LocalProdcode ("jdbc:postgresql://localhost:5432/test_prodcode", "postgres", "admin"),
    LocalProdcode1 ("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin")

    ;

//--------------------------------------------------------------------------------------------------------------------//

    /**
     * Получить имя пользователя
     */
    public String getUser() {
        return user;
    }

    /**
     * Получить пароль
     */
    public String getPassword() {
        return password;
    }

    /**
     * Получить адрес базы данных
     */
    public String getUrl() {
        return url;
    }

//--------------------------------------------------------------------------------------------------------------------//
    private final String url;
    private final String user;
    private final String password;

    PostgreData(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
}
