package ru.mail.romanov1234567890987.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mail.romanov1234567890987.repository.ConnectionRepository;
import ru.mail.romanov1234567890987.repository.RolesRepository;
import ru.mail.romanov1234567890987.repository.TableRepository;
import ru.mail.romanov1234567890987.repository.UserRepository;
import ru.mail.romanov1234567890987.repository.enums.CreateActionEnum;
import ru.mail.romanov1234567890987.repository.enums.DropActionEnum;
import ru.mail.romanov1234567890987.repository.impl.ConnectionRepositoryImpl;
import ru.mail.romanov1234567890987.repository.impl.RolesRepositoryImpl;
import ru.mail.romanov1234567890987.repository.impl.TableRepositoryImpl;
import ru.mail.romanov1234567890987.repository.impl.UserRepositoryImpl;
import ru.mail.romanov1234567890987.repository.model.Roles;
import ru.mail.romanov1234567890987.repository.model.User;
import ru.mail.romanov1234567890987.service.TableService;
import ru.mail.romanov1234567890987.service.model.AccountDTO;

import javax.management.relation.Role;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class TableServiceImpl implements TableService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static TableService instance;
    private ConnectionRepository connectionRepository;
    private TableRepository tableRepository;
    private UserRepository userRepository = UserRepositoryImpl.getInstance();
    private RolesRepository rolesRepository = RolesRepositoryImpl.getInstance();


    private TableServiceImpl(ConnectionRepository connectionRepository, TableRepository tableRepository) {
        this.connectionRepository = connectionRepository;
        this.tableRepository = tableRepository;
    }

    public static TableService getInstance() {
        if (instance == null) {
            instance = new TableServiceImpl(
                    ConnectionRepositoryImpl.getInstance(),
                    TableRepositoryImpl.getInstance()
            );
        }
        return instance;
    }

    @Override
    public void createTables() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (CreateActionEnum action : CreateActionEnum.values()) {
                    tableRepository.executeQuery(connection, action.getQuery());
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void dropTablesIfExists() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (DropActionEnum action : DropActionEnum.values()) {
                    tableRepository.executeQuery(connection, action.getQuery());
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void insertTwoUsers(List<AccountDTO> accountDTOList) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (AccountDTO accountDTO : accountDTOList) {
                    User userToInsert = getUserFromDTO(accountDTO);
                    Roles roleToInsert = getRoleFromDTO(accountDTO);
                    Roles addedRole = rolesRepository.add(connection, roleToInsert);
                    userToInsert.setRole(addedRole);
                    userRepository.add(connection, userToInsert);
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private User getUserFromDTO(AccountDTO accountDTO) {
        User user = new User();
        user.setUserName(accountDTO.getUserName());
        user.setPassword(accountDTO.getPassword());
        user.setCreatedBy(new Date().toString());
        return user;
    }

    private Roles getRoleFromDTO(AccountDTO accountDTO) {
        Roles role = new Roles();
        role.setRole(accountDTO.getRole());
        role.setDescription("Name: " + accountDTO.getUserName());
        return role;
    }
}
