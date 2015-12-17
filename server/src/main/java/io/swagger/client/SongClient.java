package io.swagger.client;

import io.swagger.api.impl.MslApiResponseMessage;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SongClient {

    private ResteasyClient client;

    public SongClient() {
        client = new ResteasyClientBuilder().build();
    }

    public MslApiResponseMessage get(String id) {

        ResteasyWebTarget target = client.target(ClientConstants.BASE_URL + "/v1/catalogedge/");
        Response response = target.path("song/" + id).request().get();

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        return response.readEntity(MslApiResponseMessage.class);
    }

    public MslApiResponseMessage browse(String items) {
        WebTarget target;
        target = client.target(ClientConstants.BASE_URL + "/v1/catalogedge/browse/song?items=" + items);
        Response response = target.request().get();

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        return response.readEntity(MslApiResponseMessage.class);
    }

    public MslApiResponseMessage rateSong(String songId, Integer rating, String sessionToken) {
        ResteasyWebTarget target = client.target(ClientConstants.BASE_URL + "/v1/ratingsedge/ratesong/" + songId);

        Form form = new Form();
        form.param("rating", rating.toString());

        Response response = target.request().cookie("sessionToken", sessionToken)
            .put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        return response.readEntity(MslApiResponseMessage.class);
    }
}
