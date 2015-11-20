package io.swagger.client;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by anram88 on 11/19/15.
 */
public class AlbumClient {

    private String baseUrl = "http://localhost:9000/msl";
    private ResteasyClient client;

    public AlbumClient() {
        client = new ResteasyClientBuilder().build();
    }

    public Response get (String id) {
        ResteasyWebTarget target = client.target(baseUrl + "/v1/catalogedge/");
        Response response = target
                .path("album/" + id)
                .request()
                .get();
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        return response;
    }

    public Response browse(String facets) {
        ResteasyWebTarget target;
        if (!facets.isEmpty()){
            target = client.target(baseUrl + "/v1/catalogedge/browse/album?facets=" + facets);
        }else {
            target = client.target(baseUrl + "/v1/catalogedge/browse/album");
        }
        Response response = target
                .request()
                .get();
        return response;
    }

    public Response addAlbum (String albumId, String sessionToken) {
        ResteasyWebTarget target = client.target(baseUrl + "/v1/accountedge/users/mylibrary/addalbum/" + albumId);

        Response response = target
                .request()
                .header("sessionToken", sessionToken)
                .put(Entity.entity(albumId, MediaType.APPLICATION_JSON));
        return response;
    }
}
