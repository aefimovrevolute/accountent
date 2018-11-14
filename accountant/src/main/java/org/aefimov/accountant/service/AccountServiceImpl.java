package org.aefimov.accountant.service;

import org.aefimov.accountant.bean.Account;
import org.aefimov.accountant.dao.AccountDao;
import org.aefimov.accountant.db.ConnectionOpts;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Singleton
public class AccountServiceImpl implements AccountService {

    private final ConnectionOpts connectionOpts;
    private final AccountDao accountDao;

    @Inject
    public AccountServiceImpl(ConnectionOpts connectionOpts, AccountDao accountDao) {
        this.connectionOpts = connectionOpts;
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAll() {
        Connection connection = connectionOpts.getConnection();
        try {
            connection.setAutoCommit(false);
            return accountDao.getAll(connection);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Account findByNumber(String accNumber) {
        Connection connection = connectionOpts.getConnection();
        try {
            connection.setAutoCommit(false);
            return accountDao.findByAccountNumber(accNumber, connection);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
}
