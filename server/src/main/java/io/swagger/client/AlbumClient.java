package io.swagger.client;

import io.swagger.api.impl.MslApiResponseMessage;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AlbumClient {

    private ResteasyClient client;

    public AlbumClient() {
        client = new ResteasyClientBuilder().build();
    }

    public MslApiResponseMessage get(String id) {
        ResteasyWebTarget target = client.target(ClientConstants.BASE_URL + "/v1/catalogedge/");
        Response response = target.path("album/" + id).request().get();
        if ( response.getStatus() != 200 ) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        MslApiResponseMessage responseWrapper = response.readEntity(MslApiResponseMessage.class);
        return responseWrapper;
    }

    public MslApiResponseMessage browse(String items) {
        ResteasyWebTarget target;
        target = client.target(ClientConstants.BASE_URL + "/v1/catalogedge/browse/album?items=" + items);
        Response response = target.request().get();

        MslApiResponseMessage responseWrapper = response.readEntity(MslApiResponseMessage.class);
        if ( response.getStatus() != 200 ) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        return responseWrapper;
    }

    public MslApiResponseMessage rateAlbum(String albumId, Integer rating, String sessionToken) {
        ResteasyWebTarget target = client.target(ClientConstants.BASE_URL + "/v1/ratingsedge/ratealbum/" + albumId);

        Form form = new Form();
        form.param("rating", rating.toString());

        Response response = target.request().header("Cookie", sessionToken)
            .put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        if ( response.getStatus() != 200 ) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        MslApiResponseMessage responseWrapper = response.readEntity(MslApiResponseMessage.class);
        return responseWrapper;
    }
}
