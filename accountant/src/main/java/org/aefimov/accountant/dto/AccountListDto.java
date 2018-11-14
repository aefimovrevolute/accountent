package org.aefimov.accountant.dto;

import org.aefimov.accountant.bean.Account;

import java.util.List;

public class AccountListDto {

    private List<Account> accounts;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
