package com.diamondfire.dfnicker.database;


import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SingleQueryBuilder {

    String query;
    PreparedStatementManager preparedStatement;
    ResultSetManager onQuery;
    Runnable onFail;

    public SingleQueryBuilder query(@Language("SQL") String query, PreparedStatementManager statement) {
        this.query = query;
        this.preparedStatement = statement;
        return this;
    }
    public SingleQueryBuilder query(@Language("SQL") String query) {
        this.query = query;
        return this;
    }

    public SingleQueryBuilder onQuery(ResultSetManager onQuery) {
        this.onQuery = onQuery;
        return this;
    }

    public SingleQueryBuilder onNotFound(Runnable onFail) {
        this.onFail = onFail;
        return this;
    }


    public void execute() {
        try (Connection connection = ConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            if (preparedStatement != null) {
                preparedStatement.run(statement);
            }

            ResultSet set = statement.executeQuery();
            if (set.next()) {
                onQuery.run(set);
            } else {
                onFail.run();
            }
            set.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
