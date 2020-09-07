package com.diamondfire.dfnicker.bot.config;


import com.diamondfire.dfnicker.externalfile.ExternalFile;
import com.google.gson.*;

import java.io.*;
import java.util.stream.Collectors;

public class Config {

    private final JsonObject config;

    public Config() throws IllegalStateException {
        try (BufferedReader txtReader2 = new BufferedReader(new FileReader(ExternalFile.CONFIG.getFile().getPath()))) {
            String config = txtReader2.lines().collect(Collectors.joining());
            this.config = JsonParser.parseString(config).getAsJsonObject();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new IllegalStateException("Config not correctly structured! Please check the readme file for a config template.");
        }
    }

    // Bot Stuff
    public String getToken() {
        return getProperty("token");
    }

    public String getPrefix() {
        return getProperty("prefix");
    }

    // Database
    public String getDBUrl() {
        return getProperty("db_link");
    }

    public String getDBUser() {
        return getProperty("db_user");
    }

    public String getDBPassword() {
        return getProperty("db_password");
    }

    private String getProperty(String property) {
        return config.get(property).getAsString();
    }

}
