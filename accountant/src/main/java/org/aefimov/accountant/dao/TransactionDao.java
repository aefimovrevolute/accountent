package org.aefimov.accountant.dao;

import org.aefimov.accountant.dao.entity.Transaction;

import java.sql.Connection;
import java.util.List;

public interface TransactionDao {

    Transaction get(Long id, Connection conn);

    List<Transaction> getAll(Connection conn);

    boolean save(Transaction account, Connection conn);

    boolean update(Transaction account, Connection conn);

    void delete(Long id, Connection conn);

}
