package org.aefimov.accountant.service;

import org.aefimov.accountant.dto.TransferDto;

public interface TransferService {

    boolean transfer(TransferDto transfer);

}
