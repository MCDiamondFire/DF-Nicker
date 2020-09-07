package com.diamondfire.dfnicker.database;

import java.sql.*;

@FunctionalInterface
public interface ResultSetManager {

    void run(ResultSet set) throws SQLException;
}
