package io.swagger.client;

import io.swagger.api.impl.MslApiResponseMessage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by anram88 on 11/23/15.
 */
public class AuthenticationClientTest {

    private final String MOCK_EMAIL = "username12@kenzan.com";
    private final String MOCK_PASSWORD = "password12";

    private AuthenticationClient authenticationClient;
    static Logger logger = Logger.getLogger(AuthenticationClientTest.class);

    @Before
    public void init() {
        authenticationClient = new AuthenticationClient();
        logger.setLevel(Level.DEBUG);
    }

    @Test
    public void testLogin() {
        logger.debug("AuthenticationClient.testLogin");
        MslApiResponseMessage response = authenticationClient.login(MOCK_EMAIL, MOCK_PASSWORD);
        assertNotNull(response);
        assertEquals("login call is successful", "success", response.getMessage());
    }

    @Test
    public void testLogOut() {
        logger.debug("AuthenticationClient.testLogOut");
        MslApiResponseMessage response = authenticationClient.logOut();
        logger.info(response);
        assertNotNull(response);
        assertEquals("logout call is successful", "success", response.getMessage());
    }

}
