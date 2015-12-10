package com.kenzan.msl.server.mock;

import io.swagger.model.LoginSuccessResponse;
import io.swagger.model.StatusResponse;

import java.util.Date;

public class LogInMockData {

    LoginSuccessResponse loginMockData = new LoginSuccessResponse();

    /**
     * @param email the email address associated with the user account
     * @param password the clear text, case-sensitive password for the user account
     */
    public LoginSuccessResponse getAuthenticatedFlag(String email, String password) {
        loginMockData.setAuthenticated(Long.toString(new Date().getTime()));
        return loginMockData;
    }

    public StatusResponse logOut() {
        StatusResponse response = new StatusResponse();
        response.setMessage("Successfully logged out");
        return response;
    }

}
