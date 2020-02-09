package ru.mail.romanov1234567890987.repository.enums;

public enum CreateActionEnum {
    CREATE_ROLES_TABLE("CREATE TABLE roles\n" +
            "(\n" +
            "    id  INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    name VARCHAR(60) NOT NULL,\n" +
            "    description VARCHAR(60) NOT NULL\n" +
            ");"),
    CREATE_USER_TABLE("CREATE TABLE user\n" +
            "(\n" +
            "    id     INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    user_name   VARCHAR(60) NOT NULL,\n" +
            "    password    VARCHAR(60) NOT NULL,\n" +
            "    created_by  VARCHAR(60) NOT NULL,\n" +
            "    roles_id int  NOT NULL,\n" +
            "    FOREIGN KEY (roles_id) REFERENCES roles (id)\n" +
            ");");

    private final String query;

    CreateActionEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
