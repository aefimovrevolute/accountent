package org.aefimov.accountant.dao;

import org.aefimov.accountant.dao.entity.Transaction;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class TransactionDaoImpl implements TransactionDao {

    @Override
    public Transaction get(Long id, Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM transactions WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            List<Transaction> result = new ArrayList<>();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getLong("ID"));
                transaction.setFromAccountId(rs.getLong("FROM_ACCOUNT_ID"));
                transaction.setToAccountId(rs.getLong("TO_ACCOUNT_ID"));
                transaction.setStatus(rs.getString("STATUS"));
                result.add(transaction);
            }
            return result.size() > 0 ? result.get(0) : null;
        } catch (Exception e) {
            throw new RuntimeException(String.format("An exception occurred when try to find " +
                    "a transaction by ID [%s].", id), e);
        }
    }

    @Override
    public List<Transaction> getAll(Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM transactions");
            ResultSet rs = ps.executeQuery();
            List<Transaction> result = new ArrayList<>();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getLong("ID"));
                transaction.setAmount(rs.getLong("AMOUNT"));
                transaction.setFromAccountId(rs.getLong("FROM_ACCOUNT_ID"));
                transaction.setToAccountId(rs.getLong("TO_ACCOUNT_ID"));
                transaction.setStatus(rs.getString("STATUS"));
                result.add(transaction);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("An exception occurred when try to get " +
                    "all transactions.", e);
        }
    }

    @Override
    public boolean save(Transaction transaction, Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO transactions VALUES (1, ?, ?, ?, ?)");
            ps.setLong(1, transaction.getFromAccountId());
            ps.setLong(2, transaction.getToAccountId());
            ps.setLong(3, transaction.getAmount());
            ps.setString(4, transaction.getStatus());
            int i = ps.executeUpdate();
            if (i == 1) {
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("An exception occurred when try to " +
                    "insert transaction. [%s].", transaction), e);
        }
        return false;
    }

    @Override
    public boolean update(Transaction transaction, Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("" +
                    "UPDATE accounts " +
                    "SET from_account_id=?, to_account_id=?, amount=?, status=? " +
                    "WHERE id=?");
            ps.setLong(1, transaction.getFromAccountId());
            ps.setLong(2, transaction.getToAccountId());
            ps.setLong(3, transaction.getAmount());
            ps.setString(4, transaction.getStatus());
            ps.setLong(5, transaction.getId());
            int i = ps.executeUpdate();

            if (i == 1) {
                return true;
            }

        } catch (Exception e) {
            throw new RuntimeException(String.format("An exception occurred when try to " +
                    "update transaction. [%s].", transaction), e);
        }
        return false;
    }

    @Override
    public void delete(Long id, Connection conn) {
        throw new UnsupportedOperationException();
    }
}
