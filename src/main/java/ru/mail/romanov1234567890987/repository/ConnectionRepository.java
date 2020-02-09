package ru.mail.romanov1234567890987.perository;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionRepository {

    Connection getConnection() throws SQLException;

}
