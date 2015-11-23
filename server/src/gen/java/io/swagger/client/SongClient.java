package io.swagger.client;

import io.swagger.api.impl.MslApiResponseMessage;
import io.swagger.model.SongInfo;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import javax.ws.rs.client.Client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

/**
* Created by anram88 on 11/16/15.
*/
public class SongClient {

    private String baseUrl = "http://localhost:9000/msl";
    private ResteasyClient client;

    public SongClient() {
        client = new ResteasyClientBuilder().build();
    }

    public MslApiResponseMessage get (String id) {
        WebTarget target = client.target(baseUrl + "/v1/catalogedge/");
        Response response = target
                .path("song/" + id)
                .request()
                .get();
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        MslApiResponseMessage song = response.readEntity(MslApiResponseMessage.class);
        return song;
    }

    public MslApiResponseMessage browse(String facets) {
        WebTarget target;
        if (!facets.isEmpty()){
            target = client.target(baseUrl + "/v1/catalogedge/browse/song?facets=" + facets);
        }else {
            target = client.target(baseUrl + "/v1/catalogedge/browse/song");
        }
        Response response = target.request().get();
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        MslApiResponseMessage responseWrapper = response.readEntity(MslApiResponseMessage.class);
        return responseWrapper;
    }

    public MslApiResponseMessage addSong (String songId, String sessionToken) {
        WebTarget target = client.target(baseUrl + "/v1/accountedge/users/mylibrary/addsong/" + songId);

        Response response = target
                .request()
                .header("sessionToken", sessionToken)
                .put(Entity.entity(songId, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        MslApiResponseMessage responseWrapper = response.readEntity(MslApiResponseMessage.class);
        return responseWrapper;
    }
}
