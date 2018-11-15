package org.aefimov.accountant.service;

import org.aefimov.accountant.bean.Account;
import org.aefimov.accountant.dao.AccountDao;
import org.aefimov.accountant.db.ConnectionOpts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Singleton
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

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
            logger.error("An error occurred when try to load accounts.", e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error(e1.getMessage(), e1);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("An error occurred when try to close DB connection.", e);
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
            logger.error("An error occurred when try to load account by number [{}].", accNumber, e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error(e1.getMessage(), e1);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("An error occurred when try to close DB connection.", e);
            }
        }
        return null;
    }
}
