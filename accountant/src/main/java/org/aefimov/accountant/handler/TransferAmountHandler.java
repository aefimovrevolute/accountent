package org.aefimov.accountant.handler;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.aefimov.accountant.dao.entity.Account;
import org.aefimov.accountant.dto.AccountListDto;
import org.aefimov.accountant.dto.TransferDto;
import org.aefimov.accountant.dto.TransferResultDto;
import org.aefimov.accountant.service.AccountService;
import org.aefimov.accountant.service.TransferService;
import org.aefimov.accountant.util.AppObjecMapper;
import org.aefimov.http_server.server.http.HttpResponder;
import org.aefimov.http_server.server.http.request.Request;
import org.aefimov.http_server.server.http.request.RequestHandler;
import org.aefimov.http_server.server.http.util.ContentType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class TransferAmountHandler implements RequestHandler {

    private final AppObjecMapper mapper;
    private final TransferService transferService;

    @Inject
    public TransferAmountHandler(AppObjecMapper mapper, TransferService transferService) {
        this.mapper = mapper;
        this.transferService = transferService;
    }

    @Override
    public FullHttpResponse handle(Request request) {
        try {
            String body = request.getBody();
            TransferDto transfer = mapper.instance().readerFor(TransferDto.class).readValue(body);
            boolean isTransfered = transferService.transfer(transfer);
            TransferResultDto transferResultDto = new TransferResultDto();
            transferResultDto.setSuccessful(isTransfered);

            String json = mapper.instance().writerFor(TransferResultDto.class)
                    .writeValueAsString(transferResultDto);

            return HttpResponder.createResponse(
                    HttpResponseStatus.CREATED,
                    json,
                    ContentType.APPLICATION_JSON
            );
        } catch (Exception e) {
            throw new RuntimeException("An error occurred when try to transfer amount.", e);
        }
    }
}
