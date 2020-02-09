package ru.mail.romanov1234567890987.service;

import ru.mail.romanov1234567890987.service.model.AccountDTO;

import java.util.List;

public interface TableService {
    void createTables();
    void dropTablesIfExists();
    void insertTwoUsers(List<AccountDTO> accountDTOList);
}
