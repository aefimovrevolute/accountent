package org.aefimov.accountant.db;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Singleton
public class InMemoryDBInitializer implements DBInitializer {
    private static final String CREATE_TABLE_ACCOUNTS_SQL = "CREATE TABLE accounts(" +
            "id BIGINT auto_increment, " +
            "acc_number varchar(20) NOT NULL, " +
            "balance BIGINT NOT NULL" +
            ")";

    private static final String CREATE_TABLE_TRANSACTIONS_SQL = "CREATE TABLE transactions(" +
            "id BIGINT auto_increment, " +
            "from_account_id BIGINT NOT NULL, " +
            "to_account_id BIGINT NOT NULL, " +
            "amount BIGINT NOT NULL, " +
            "status varchar(20), " +
            "foreign key (from_account_id) references accounts(id), " +
            "foreign key (to_account_id) references accounts(id)" +
            ")";

    private final ConnectionOpts connectionOpts;

    @Inject
    public InMemoryDBInitializer(ConnectionOpts connectionOpts) {
        this.connectionOpts = connectionOpts;
    }

    @Override
    public void initDB() {
        Connection connection = connectionOpts.getConnection();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE_ACCOUNTS_SQL);
            st.execute(CREATE_TABLE_TRANSACTIONS_SQL);
            st.execute("" +
                    "INSERT INTO accounts(acc_number, balance) " +
                    "VALUES ('42307234567895265453', 15000)");

            st.execute("" +
                    "INSERT INTO accounts(acc_number, balance) " +
                    "VALUES ('42307234567895265454', 7000)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
