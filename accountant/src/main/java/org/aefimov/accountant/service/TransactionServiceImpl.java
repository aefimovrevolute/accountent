package org.aefimov.accountant.service;

import org.aefimov.accountant.bean.Transaction;
import org.aefimov.accountant.dao.AccountDao;
import org.aefimov.accountant.dao.TransactionDao;
import org.aefimov.accountant.db.ConnectionOpts;
import org.aefimov.accountant.dto.TransactionDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public class TransactionServiceImpl implements TransactionService {

    private final ConnectionOpts connectionOpts;
    private final TransactionDao transactionDao;
    private final AccountDao accountDao;

    @Inject
    public TransactionServiceImpl(ConnectionOpts connectionOpts, TransactionDao transactionDao,
                                  AccountDao accountDao) {
        this.connectionOpts = connectionOpts;
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
    }

    @Override
    public List<TransactionDto> findAll() {
        Connection connection = connectionOpts.getConnection();
        try {
            connection.setAutoCommit(false);
            List<Transaction> transactions = transactionDao.getAll(connection);
            List<TransactionDto> result = new ArrayList<>();
            transactions.forEach(tr -> {
                TransactionDto trDto = new TransactionDto();
                trDto.setAmount(tr.getAmount());
                trDto.setFromAccount(accountDao.get(tr.getFromAccountId(), connection).getAccNumber());
                trDto.setToAccount(accountDao.get(tr.getToAccountId(), connection).getAccNumber());
                result.add(trDto);
            });

            return result;
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
}
