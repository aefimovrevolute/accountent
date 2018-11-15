package org.aefimov.accountant.service;

import org.aefimov.accountant.dao.entity.Account;
import org.aefimov.accountant.dao.entity.Transaction;
import org.aefimov.accountant.dto.TransferDto;
import org.aefimov.accountant.db.ConnectionOpts;
import org.aefimov.accountant.dao.AccountDao;
import org.aefimov.accountant.dao.TransactionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;

@Singleton
public class TransferServiceImpl implements TransferService {

    private static final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);
    private static final String SUCCESS_TRANSFER_RESULT_VALUE = "COMPLETED";

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
            if (fromAccBalance >= amountToTransfer && !fromAcc.getAccNumber().equals(toAcc.getAccNumber())) {

                toAcc.setBalance(toAccBalance + amountToTransfer);
                fromAcc.setBalance(fromAccBalance - amountToTransfer);

                accountDao.update(fromAcc, connection);
                accountDao.update(toAcc, connection);
                Transaction tr = new Transaction();
                tr.setFromAccountId(fromAcc.getId());
                tr.setToAccountId(toAcc.getId());
                tr.setAmount(amountToTransfer);
                tr.setStatus(SUCCESS_TRANSFER_RESULT_VALUE);
                transactionDao.save(tr, connection);
                connection.commit();
                operationCompleted = true;
            } else {
                connection.rollback();
            }

        } catch (Exception e) {
            logger.error("An error occurred when try to transfer money. The transfer object is [{}]", transfer, e);
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
        return operationCompleted;
    }
}
