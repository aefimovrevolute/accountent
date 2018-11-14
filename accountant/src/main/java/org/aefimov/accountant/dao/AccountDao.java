package org.aefimov.accountant.dao;

import org.aefimov.accountant.bean.Account;

import java.sql.Connection;
import java.util.List;

public interface AccountDao {

    Account get(Long id, Connection conn);

    List<Account> getAll(Connection conn);

    Account findByAccountNumber(String accNumber, Connection conn);

    boolean save(Account account, Connection conn);

    boolean update(Account account, Connection conn);

    void delete(Long id, Connection conn);

}
