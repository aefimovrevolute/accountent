package org.aefimov.accountant.service;

import org.aefimov.accountant.bean.Account;
import org.aefimov.accountant.bean.Transaction;
import org.aefimov.accountant.dto.TransferDto;
import org.aefimov.accountant.db.ConnectionOpts;
import org.aefimov.accountant.dao.AccountDao;
import org.aefimov.accountant.dao.TransactionDao;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;

@Singleton
public class TransferServiceImpl implements TransferService {

    private final ConnectionOpts connectionOpts;
    private final AccountDao accountDao;
    private final TransactionDao transactionDao;

    @Inject
    public TransferServiceImpl(ConnectionOpts connectionOpts, AccountDao accountDao, TransactionDao transactionDao) {
        this.connectionOpts = connectionOpts;
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

    @Override
    public boolean transfer(TransferDto transfer) {
        Connection connection = connectionOpts.getConnection();
        boolean operationCompleted = false;
        try {
            connection.setAutoCommit(false);
            Account fromAcc = accountDao.findByAccountNumber(transfer.getFrom(), connection);
            Account toAcc = accountDao.findByAccountNumber(transfer.getTo(), connection);

            Long amountToTransfer = transfer.getAmount();
            Long toAccBalance = toAcc.getBalance();
            Long fromAccBalance = fromAcc.getBalance();
            if (fromAccBalance >= amountToTransfer) {

                toAcc.setBalance(toAccBalance + amountToTransfer);
                fromAcc.setBalance(fromAccBalance - amountToTransfer);

                accountDao.update(fromAcc, connection);
                accountDao.update(toAcc, connection);
                Transaction tr = new Transaction();
                tr.setFromAccountId(fromAcc.getId());
                tr.setToAccountId(toAcc.getId());
                tr.setAmount(amountToTransfer);
                tr.setStatus("COMPLETED");
                transactionDao.save(tr, connection);
                connection.commit();
                operationCompleted = true;
            } else {
                connection.rollback();
            }

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
        return operationCompleted;
    }
}
