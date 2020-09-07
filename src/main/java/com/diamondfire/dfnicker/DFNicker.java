package com.diamondfire.dfnicker;

import com.diamondfire.dfnicker.bot.NickBotInstance;

import javax.security.auth.login.LoginException;

public class DFNicker {

    public static void main(String[] args) throws LoginException {
        NickBotInstance.initialize();
    }
}
