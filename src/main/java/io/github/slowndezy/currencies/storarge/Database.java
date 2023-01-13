package io.github.slowndezy.currencies.storarge;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Database {

    private HikariDataSource dataSource;

    private final String address, database, username, password, table;


    public void initialize() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + address + ":3306/" + database);
        config.setUsername(username);
        config.setPassword(password);

        dataSource = new HikariDataSource(config);
        dataSource.setDataSourceClassName("com.mysql.jdbc.Driver");
    }

    public void close() {
        dataSource.close();
    }

}
