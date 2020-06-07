package com.diamondfire.dfnicker.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementManager {

    void run(PreparedStatement statement) throws SQLException;
}
