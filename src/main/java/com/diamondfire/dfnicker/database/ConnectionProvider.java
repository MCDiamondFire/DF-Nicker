package com.diamondfire.dfnicker.database;

import com.diamondfire.dfnicker.bot.NickBotInstance;
import com.diamondfire.dfnicker.bot.config.Config;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;

public class ConnectionProvider {

    private static final MysqlDataSource source = new MysqlDataSource();

    static {
        Config config = NickBotInstance.getConfig();
        source.setUrl(config.getDBUrl());
        source.setUser(config.getDBUser());
        source.setPassword(config.getDBPassword());
    }

    public static Connection getConnection() throws SQLException {
        return source.getConnection();
    }

}
