package org.aefimov.accountant.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.aefimov.accountant.bean.Account;
import org.aefimov.accountant.service.AccountService;
import org.aefimov.accountant.util.AppObjecMapper;
import org.aefimov.async_http.server.http.HttpResponder;
import org.aefimov.async_http.server.http.request.Request;
import org.aefimov.async_http.server.http.request.RequestHandler;
import org.aefimov.async_http.server.http.util.ContentType;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class AccountInfoHandler implements RequestHandler {

    private final AppObjecMapper mapper;
    private final AccountService accountService;

    @Inject
    public AccountInfoHandler(AppObjecMapper mapper, AccountService accountService) {
        this.mapper = mapper;
        this.accountService = accountService;
    }

    @Override
    public FullHttpResponse handle(Request request) {
        try {
            Map<String, String> parameters = request.getParameters();
            if (parameters == null)
                return makeNotFoundResponse();

            String accNumber = parameters.get("number");
            if (StringUtils.isBlank(accNumber))
                return makeNotFoundResponse();

            Account foundAccount = accountService.findByNumber(accNumber);
            if (foundAccount == null) {
                return makeNotFoundResponse();
            }

            String json = mapper.instance().writerFor(Account.class).writeValueAsString(foundAccount);

            return HttpResponder.createResponse(
                    HttpResponseStatus.FOUND,
                    json,
                    ContentType.APPLICATION_JSON
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private FullHttpResponse makeNotFoundResponse() {
        return HttpResponder.createResponse(
                HttpResponseStatus.NOT_FOUND,
                "{}",
                ContentType.APPLICATION_JSON);
    }
}
