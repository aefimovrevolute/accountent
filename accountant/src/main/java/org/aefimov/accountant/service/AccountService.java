package org.aefimov.accountant.service;

import org.aefimov.accountant.bean.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account findByNumber(String accNumber);

}
