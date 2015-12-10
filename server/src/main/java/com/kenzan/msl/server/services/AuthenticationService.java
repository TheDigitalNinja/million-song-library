package com.kenzan.msl.server.services;

import com.google.common.base.Optional;

import io.swagger.api.impl.MslApiResponseMessage;
import io.swagger.api.impl.MslSessionToken;
import io.swagger.model.ErrorResponse;
import io.swagger.model.LoginSuccessResponse;
import io.swagger.model.StatusResponse;

import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Response;

import java.util.Date;
import java.util.UUID;

public class AuthenticationService {

    /**
     * Login processing and handling
     * 
     * @param catalogService instance of CatalogService
     * @param email user email
     * @param password user password
     * @return Response
     */
    public static Response logIn(final CatalogService catalogService, final String email, final String password) {
        // Validate required parameters
        if ( StringUtils.isEmpty(email) ) {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR,
                                                  "Required parameter 'email' is null or empty.")).build();
        }
        if ( StringUtils.isEmpty(password) ) {
            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR,
                                                  "Required parameter 'password' is null or empty.")).build();
        }

        Optional<UUID> optSessionToken;
        try {
            optSessionToken = catalogService.logIn(email, password).toBlocking().first();
        }
        catch ( Exception e ) {
            e.printStackTrace();

            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Server error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }

        if ( !optSessionToken.isPresent() ) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Invalid credentials");
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorResponse).build();
        }

        LoginSuccessResponse loginSuccessResponse = new LoginSuccessResponse();
        loginSuccessResponse.setAuthenticated(new Date().toString());

        return Response.ok().cookie(MslSessionToken.getInstance().getSessionCookie(optSessionToken.get()))
            .entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", loginSuccessResponse)).build();
    }

    /**
     * LogOut processing and handling
     * 
     * @return Response
     */
    public static Response logOut() {
        StatusResponse response = new StatusResponse();
        response.setMessage("Successfully logged out");

        return Response.ok().cookie(MslSessionToken.getInstance().getSessionCookie(null))
            .entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", response)).build();
    }

    /**
     * Checks if current sessionToken is valid
     * 
     * @return Boolean
     */
    public static Boolean hasValidToken() {
        return MslSessionToken.getInstance().isValidToken();
    }
}
