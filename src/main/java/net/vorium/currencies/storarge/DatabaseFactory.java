package net.vorium.currencies.storarge;

import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.connector.type.impl.MySQLDatabaseType;
import org.bukkit.configuration.ConfigurationSection;

public class DatabaseFactory {

    public static SQLConnector createConnection(ConfigurationSection section) {
        return MySQLDatabaseType.builder()
                .address(section.getString("address"))
                .database(section.getString("database"))
                .username(section.getString("username"))
                .password(section.getString("password"))
                .build()
                .connect();
    }

}
