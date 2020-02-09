package ru.mail.romanov1234567890987.repository.enums;

public enum DropActionEnum {

    DROP_USER_TABLE("DROP TABLE IF EXISTS user"),
    DROP_ROLES_TABLE("DROP TABLE IF EXISTS roles");

    private final String query;

    DropActionEnum(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
