package org.aefimov.accountant.dto;

import java.util.List;

public class TransactionListDto {

    private List<TransactionDto> transactions;

    public List<TransactionDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDto> transactions) {
        this.transactions = transactions;
    }
}
