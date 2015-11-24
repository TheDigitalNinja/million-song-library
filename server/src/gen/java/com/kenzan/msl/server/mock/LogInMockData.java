package com.kenzan.msl.server.mock;

import io.swagger.model.LoginSuccessResponse;
import io.swagger.model.StatusResponse;

import java.util.Date;

public class LogInMockData {

    LoginSuccessResponse loginMockData = new LoginSuccessResponse();

    public LoginSuccessResponse getAuthenticatedFlag(String email, String password) {
        loginMockData.setAuthenticated(Long.toString(new Date().getTime()));
        return loginMockData;
    }

    public StatusResponse logOut () {
        StatusResponse response = new StatusResponse();
        response.setMessage("Successfully logged out");
        return response;
    }

}
