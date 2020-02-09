package ru.mail.romanov1234567890987.controller.listener;

import ru.mail.romanov1234567890987.service.TableService;
import ru.mail.romanov1234567890987.service.impl.TableServiceImpl;
import ru.mail.romanov1234567890987.service.model.AccountDTO;
import ru.mail.romanov1234567890987.util.PropertyUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

public class Listener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";
    private static final String STARTUP_ADMIN_NAME = "StartupAdminName";
    private static final String STARTUP_ADMIN_PASSWORD = "StartupAdminPassword";
    private static final String STARTUP_USER_NAME = "StartupUserName";
    private static final String STARTUP_USER_PASSWORD = "StartupUserPassword";
    private TableService tableService = TableServiceImpl.getInstance();
    private PropertyUtil propertyUtil = new PropertyUtil();


    public Listener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        List<AccountDTO> accountDTOList = new ArrayList<>();
        addTwoUsersIntoList(accountDTOList);
        tableService.dropTablesIfExists();
        tableService.createTables();
        tableService.insertTwoUsers(accountDTOList);
    }

    private void addTwoUsersIntoList(List<AccountDTO> accountDTOList) {
        AccountDTO adminToDTO = new AccountDTO();
        adminToDTO.setPassword(propertyUtil.getProperty(STARTUP_ADMIN_NAME));
        adminToDTO.setUserName(propertyUtil.getProperty(STARTUP_ADMIN_PASSWORD));
        adminToDTO.setRole(ROLE_ADMIN);
        accountDTOList.add(adminToDTO);
        AccountDTO userToDTO = new AccountDTO();
        userToDTO.setPassword(propertyUtil.getProperty(STARTUP_USER_NAME));
        userToDTO.setUserName(propertyUtil.getProperty(STARTUP_USER_PASSWORD));
        userToDTO.setRole(ROLE_USER);
        accountDTOList.add(userToDTO);
    }
}
