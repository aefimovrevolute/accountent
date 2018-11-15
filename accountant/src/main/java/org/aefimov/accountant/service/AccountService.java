package org.aefimov.accountant.service;

import org.aefimov.accountant.dao.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account findByNumber(String accNumber);

}
