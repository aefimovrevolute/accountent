package org.aefimov.accountant.handler;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.aefimov.accountant.dto.TransactionDto;
import org.aefimov.accountant.dto.TransactionListDto;
import org.aefimov.accountant.service.TransactionService;
import org.aefimov.accountant.util.AppObjecMapper;
import org.aefimov.http_server.server.http.HttpResponder;
import org.aefimov.http_server.server.http.request.Request;
import org.aefimov.http_server.server.http.request.RequestHandler;
import org.aefimov.http_server.server.http.util.ContentType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;

@Singleton
public class ListTransactionsHandler implements RequestHandler {

    private final AppObjecMapper mapper;
    private final TransactionService transactionService;

    @Inject
    public ListTransactionsHandler(AppObjecMapper mapper, TransactionService transactionService) {
        this.mapper = mapper;
        this.transactionService = transactionService;
    }

    @Override
    public FullHttpResponse handle(Request request) {
        try {
            List<TransactionDto> transactions = transactionService.findAll();
            TransactionListDto transactionList = new TransactionListDto();
            transactionList.setTransactions(transactions);

            String json = mapper.instance().writerFor(TransactionListDto.class).writeValueAsString(transactionList);

            return HttpResponder.createResponse(
                    HttpResponseStatus.OK,
                    json,
                    ContentType.APPLICATION_JSON
            );
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }
}
