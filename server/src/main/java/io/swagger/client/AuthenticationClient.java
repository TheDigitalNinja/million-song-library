package io.swagger.client;

import io.swagger.api.impl.MslApiResponseMessage;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AuthenticationClient {

    private String baseUrl = "http://local.msl.dev:9000/msl";
    private ResteasyClient client;

    public AuthenticationClient() {
        client = new ResteasyClientBuilder().build();
    }

    public MslApiResponseMessage login(String email, String password) {
        ResteasyWebTarget target = client.target(baseUrl + "/v1/loginedge/login");

        Form form = new Form();
        form.param("email", email);
        form.param("password", password);

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
            .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        if ( response.getStatus() != 200 ) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        return response.readEntity(MslApiResponseMessage.class);
    }

    public MslApiResponseMessage logOut() {
        ResteasyWebTarget target = client.target(baseUrl + "/v1/loginedge/logout");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
            .post(Entity.entity("", MediaType.APPLICATION_JSON));
        if ( response.getStatus() != 200 ) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        return response.readEntity(MslApiResponseMessage.class);
    }

}
