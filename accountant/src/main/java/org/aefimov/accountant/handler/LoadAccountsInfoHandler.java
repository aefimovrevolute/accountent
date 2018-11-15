package org.aefimov.accountant.handler;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.aefimov.accountant.dao.entity.Account;
import org.aefimov.accountant.dto.AccountListDto;
import org.aefimov.accountant.service.AccountService;
import org.aefimov.accountant.util.AppObjecMapper;
import org.aefimov.accountant.util.converter.AccountToDtoConverter;
import org.aefimov.http_server.server.http.HttpResponder;
import org.aefimov.http_server.server.http.request.Request;
import org.aefimov.http_server.server.http.request.RequestHandler;
import org.aefimov.http_server.server.http.util.ContentType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class LoadAccountsInfoHandler implements RequestHandler {

    private final AppObjecMapper mapper;
    private final AccountService accountService;
    private final AccountToDtoConverter toDtoConverter;

    @Inject
    public LoadAccountsInfoHandler(AppObjecMapper mapper, AccountService accountService,
                                   AccountToDtoConverter toDtoConverter) {
        this.mapper = mapper;
        this.accountService = accountService;
        this.toDtoConverter = toDtoConverter;
    }

    @Override
    public FullHttpResponse handle(Request request) {
        try {

            List<Account> foundAccounts = accountService.findAll();
            if (foundAccounts == null) {
                return makeNotFoundResponse();
            }

            String json = mapper.instance()
                    .writerFor(AccountListDto.class)
                    .writeValueAsString(toDtoConverter.converList(foundAccounts));

            return HttpResponder.createResponse(
                    HttpResponseStatus.FOUND,
                    json,
                    ContentType.APPLICATION_JSON
            );
        } catch (Exception e) {
            throw new RuntimeException("An error occurred when try to find the accounts info.", e);
        }
    }

    private FullHttpResponse makeNotFoundResponse() {
        return HttpResponder.createResponse(
                HttpResponseStatus.NOT_FOUND,
                "{}",
                ContentType.APPLICATION_JSON);
    }
}
