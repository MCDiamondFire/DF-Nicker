package com.diamondfire.dfnicker.database;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetManager {

    void run(ResultSet set) throws SQLException;
}
