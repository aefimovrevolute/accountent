package org.aefimov.accountant;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.aefimov.accountant.db.DBInitializer;
import org.aefimov.accountant.handler.AccountInfoHandler;
import org.aefimov.accountant.handler.ListTransactionsHandler;
import org.aefimov.accountant.handler.LoadAccountsInfoHandler;
import org.aefimov.accountant.handler.TransferAmountHandler;
import org.aefimov.http_server.server.ServerBuilder;
import org.aefimov.http_server.server.http.request.Key;

public class Server {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        DBInitializer dbInitializer = injector.getInstance(DBInitializer.class);
        dbInitializer.initDB();

        TransferAmountHandler transferAmountHandler = injector.getInstance(TransferAmountHandler.class);
        AccountInfoHandler accountInfoHandler = injector.getInstance(AccountInfoHandler.class);
        ListTransactionsHandler listTransactionsHandler = injector.getInstance(ListTransactionsHandler.class);
        LoadAccountsInfoHandler loadAccountsInfoHandler = injector.getInstance(LoadAccountsInfoHandler.class);

        ServerBuilder sb = new ServerBuilder();
        sb.appendRequestHandler(new Key("/api/transfer", "POST"), transferAmountHandler);
        sb.appendRequestHandler(new Key("/api/account/info", "GET"), accountInfoHandler);
        sb.appendRequestHandler(new Key("/api/transaction/list", "GET"), listTransactionsHandler);
        sb.appendRequestHandler(new Key("/api/account/list", "GET"), loadAccountsInfoHandler);
        sb.build().start();
    }

}
