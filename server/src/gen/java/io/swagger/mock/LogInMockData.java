package io.swagger.mock;

import io.swagger.model.LoginSuccessResponse;

/**
 * Created by anram88 on 11/10/15.
 */
public class LogInMockData {

    LoginSuccessResponse loginMockData = new LoginSuccessResponse();

    public LoginSuccessResponse getSessionToken(String email, String password) {
        loginMockData.setSessionToken(email);
        return loginMockData;
    }

}
