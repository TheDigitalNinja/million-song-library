package io.swagger.api.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class MslSessionToken {

    public static String value;

    public static void updateSessionToken(HttpServletRequest req) {
        String sessionTokenValue = "";
        if (req.getCookies() != null) {
            Cookie[] cookies = req.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("sessionToken")) {
                    sessionTokenValue = cookies[i].getValue();
                }
            }
        }
        MslSessionToken.value = sessionTokenValue;
    }

    public static boolean isValidToken (){
        return MslSessionToken.value != null && !MslSessionToken.value.isEmpty();
    }
}
