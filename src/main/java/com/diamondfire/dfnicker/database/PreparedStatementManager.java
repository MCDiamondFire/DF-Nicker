package com.diamondfire.dfnicker.database;

import java.sql.*;

@FunctionalInterface
public interface PreparedStatementManager {

    void run(PreparedStatement statement) throws SQLException;
}
