package net.vorium.currencies.storarge.adapter;

import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;
import net.vorium.currencies.entities.Account;

public class AccountAdapter implements SQLResultAdapter<Account> {
    @Override
    public Account adaptResult(SimpleResultSet resultSet) {
        Account account = new Account(resultSet.get("username"));
        account.setBalance(resultSet.get("coins"));
        return account;
    }
}
