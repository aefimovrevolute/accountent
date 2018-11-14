package org.aefimov.accountant;

import com.google.inject.AbstractModule;
import org.aefimov.accountant.dao.AccountDao;
import org.aefimov.accountant.dao.AccountDaoImpl;
import org.aefimov.accountant.dao.TransactionDao;
import org.aefimov.accountant.dao.TransactionDaoImpl;
import org.aefimov.accountant.db.ConnectionOpts;
import org.aefimov.accountant.db.ConnectionOptsDefault;
import org.aefimov.accountant.db.DBInitializer;
import org.aefimov.accountant.db.InMemoryDBInitializer;
import org.aefimov.accountant.service.*;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ConnectionOpts.class).to(ConnectionOptsDefault.class);
        bind(DBInitializer.class).to(InMemoryDBInitializer.class);
        bind(AccountDao.class).to(AccountDaoImpl.class);
        bind(TransactionDao.class).to(TransactionDaoImpl.class);
        bind(TransferService.class).to(TransferServiceImpl.class);
        bind(AccountService.class).to(AccountServiceImpl.class);
        bind(TransactionService.class).to(TransactionServiceImpl.class);
    }
}
