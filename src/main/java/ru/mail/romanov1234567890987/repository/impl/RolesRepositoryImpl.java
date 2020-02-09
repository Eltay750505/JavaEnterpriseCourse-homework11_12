package ru.mail.romanov1234567890987.repository.impl;

import ru.mail.romanov1234567890987.repository.RolesRepository;
import ru.mail.romanov1234567890987.repository.model.Roles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RolesRepositoryImpl implements RolesRepository {
    private static RolesRepository instance;

    private RolesRepositoryImpl() {

    }

    public static RolesRepository getInstance() {
        if (instance == null) {
            instance = new RolesRepositoryImpl();
        }
        return instance;
    }
    @Override
    public Roles add(Connection connection, Roles role) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO roles(name,description) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, role.getRole());
            statement.setString(2,role.getDescription());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating role failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    role.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating role failed, no ID obtained.");
                }
            }
            return role;
        }
    }

}
