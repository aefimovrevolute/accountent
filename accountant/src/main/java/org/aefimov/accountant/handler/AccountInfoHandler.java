package org.aefimov.accountant.handler;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.aefimov.accountant.dao.entity.Account;
import org.aefimov.accountant.dto.AccountDto;
import org.aefimov.accountant.service.AccountService;
import org.aefimov.accountant.util.AppObjecMapper;
import org.aefimov.accountant.util.converter.AccountToDtoConverter;
import org.aefimov.http_server.server.http.HttpResponder;
import org.aefimov.http_server.server.http.request.Request;
import org.aefimov.http_server.server.http.request.RequestHandler;
import org.aefimov.http_server.server.http.util.ContentType;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

/**
 * The handler to retrieve the accounts info from the storage.
 */
@Singleton
public class AccountInfoHandler implements RequestHandler {

    private static final String ACCOUNT_NUMBER_ATTRIBUTE_NAME = "number";
    private final AppObjecMapper mapper;
    private final AccountService accountService;
    private final AccountToDtoConverter toDtoConverter;

    /**
     * Default constructor.
     *
     * @param mapper         dependency injection type of {@link AppObjecMapper}.
     * @param accountService dependency injection type of {@link AccountService}.
     * @param toDtoConverter dependency injection type of {@link AccountToDtoConverter}.
     */
    @Inject
    public AccountInfoHandler(AppObjecMapper mapper, AccountService accountService,
                              AccountToDtoConverter toDtoConverter) {
        this.mapper = mapper;
        this.accountService = accountService;
        this.toDtoConverter = toDtoConverter;
    }

    /**
     * Finds the information of an account
     * and return them if found.
     *
     * @param request the request for handling.
     * @return {@code 404} if information of account is not found by number
     * else return the response with the requested account information.
     */
    @Override
    public FullHttpResponse handle(Request request) {
        try {
            Map<String, String> parameters = request.getParameters();
            if (parameters == null)
                return makeNotFoundResponse();

            String accNumber = parameters.get(ACCOUNT_NUMBER_ATTRIBUTE_NAME);
            if (StringUtils.isBlank(accNumber))
                return makeNotFoundResponse();

            Account foundAccount = accountService.findByNumber(accNumber);
            if (foundAccount == null) {
                return makeNotFoundResponse();
            }

            String json = mapper.instance().writerFor(AccountDto.class)
                    .writeValueAsString(toDtoConverter.convertOne(foundAccount));

            return HttpResponder.createResponse(
                    HttpResponseStatus.FOUND,
                    json,
                    ContentType.APPLICATION_JSON
            );
        } catch (Exception e) {
            throw new RuntimeException("An error occurred when try to find the account info.", e);
        }
    }

    private FullHttpResponse makeNotFoundResponse() {
        return HttpResponder.createResponse(
                HttpResponseStatus.NOT_FOUND,
                "{}",
                ContentType.APPLICATION_JSON);
    }
}
