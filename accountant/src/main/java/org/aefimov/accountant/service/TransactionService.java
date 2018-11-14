package org.aefimov.accountant.service;

import org.aefimov.accountant.dto.TransactionDto;

import java.util.List;

public interface TransactionService {

    List<TransactionDto> findAll();

}
