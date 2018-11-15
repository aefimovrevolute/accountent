package org.aefimov.accountant.util.converter;

import org.aefimov.accountant.dao.entity.Account;
import org.aefimov.accountant.dto.AccountDto;
import org.aefimov.accountant.dto.AccountListDto;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class AccountToDtoConverter {

    public AccountDto convertOne(Account account) {
        if (account == null)
            throw new IllegalArgumentException("An account to convert cannot be null.");

        AccountDto dto = new AccountDto();
        dto.setAccNumber(account.getAccNumber());
        dto.setBalance(account.getBalance());
        return dto;
    }

    public AccountListDto converList(List<Account> accounts) {
        if (accounts == null)
            throw new IllegalArgumentException("A list of accounts to convert cannot be null.");

        List<AccountDto> accountDtos = new ArrayList<>();
        accounts.forEach(item -> {
            AccountDto dto = convertOne(item);
            accountDtos.add(dto);
        });
        AccountListDto result = new AccountListDto();
        result.setAccounts(accountDtos);
        return result;
    }

}
