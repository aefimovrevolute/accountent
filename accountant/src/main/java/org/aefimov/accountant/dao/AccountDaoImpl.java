package org.aefimov.accountant.dao;

import org.aefimov.accountant.dao.entity.Account;

import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class AccountDaoImpl implements AccountDao {

    @Override
    public Account get(Long id, Connection conn) {

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM accounts WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            List<Account> result = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("ID"));
                account.setAccNumber(rs.getString("ACC_NUMBER"));
                account.setBalance(rs.getLong("BALANCE"));
                result.add(account);
            }
            return result.size() > 0 ? result.get(0) : null;
        } catch (Exception e) {
            throw new RuntimeException(String.format("An exception occurred when " +
                    "try to find an account by ID [%s].", id), e);
        }
    }

    @Override
    public List<Account> getAll(Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM accounts");
            ResultSet rs = ps.executeQuery();
            List<Account> result = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("ID"));
                account.setAccNumber(rs.getString("ACC_NUMBER"));
                account.setBalance(rs.getLong("BALANCE"));
                result.add(account);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("An exception occurred when " +
                    "try to get all accounts.", e);
        }
    }

    @Override
    public Account findByAccountNumber(String accNumber, Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM accounts WHERE acc_number = ?");
            ps.setString(1, accNumber);
            ResultSet rs = ps.executeQuery();
            List<Account> result = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("ID"));
                account.setAccNumber(rs.getString("ACC_NUMBER"));
                account.setBalance(rs.getLong("BALANCE"));
                result.add(account);
            }
            conn.commit();
            return result.size() > 0 ? result.get(0) : null;
        } catch (Exception e) {
            throw new RuntimeException(String.format("An exception occurred when try to find " +
                    "an account by number [%s].", accNumber), e);
        }
    }

    @Override
    public boolean save(Account account, Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO accounts VALUES (1, ?, ?)");
            ps.setString(1, account.getAccNumber());
            ps.setLong(2, account.getBalance());
            int i = ps.executeUpdate();
            if(i == 1) {
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("An exception occurred when try to insert " +
                    "an account information [%s].", account), e);
        }
        return false;
    }

    @Override
    public boolean update(Account account, Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE accounts SET acc_number=?, balance=? WHERE id=?");
            ps.setString(1, account.getAccNumber());
            ps.setLong(2, account.getBalance());
            ps.setLong(3, account.getId());
            int i = ps.executeUpdate();

            if(i == 1) {
                return true;
            }

        } catch (Exception e) {
            throw new RuntimeException(String.format("An exception occurred when try to update " +
                    "an account information [%s].", account), e);
        }
        return false;
    }

    @Override
    public void delete(Long id, Connection conn) {
        throw new UnsupportedOperationException();
    }
}
