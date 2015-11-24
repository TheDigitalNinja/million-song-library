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
import java.math.BigDecimal;

public class SongClient {

    private String baseUrl = "http://localhost:9000/msl";
    private ResteasyClient client;

    public SongClient() {
        client = new ResteasyClientBuilder().build();
    }

    public MslApiResponseMessage get(String id) {

        WebTarget target = client.target(baseUrl + "/v1/catalogedge/");
        Response response = target
                .path("song/" + id)
                .request()
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        return response.readEntity(MslApiResponseMessage.class);
    }

    public MslApiResponseMessage browse(String facets) {
        WebTarget target;

        if (!facets.isEmpty()) {
            target = client.target(baseUrl + "/v1/catalogedge/browse/song?facets=" + facets);
        } else {
            target = client.target(baseUrl + "/v1/catalogedge/browse/song");
        }

        Response response = target.request().get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        return response.readEntity(MslApiResponseMessage.class);
    }

    public MslApiResponseMessage addSong(String songId, String sessionToken) {
        WebTarget target = client.target(baseUrl + "/v1/accountedge/users/mylibrary/addsong/" + songId);

        Response response = target
                .request()
                .header("sessionToken", sessionToken)
                .put(Entity.entity(songId, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        return response.readEntity(MslApiResponseMessage.class);
    }

    public MslApiResponseMessage rateSong(String songId, BigDecimal rating, String sessionToken) {
        ResteasyWebTarget target = client.target(baseUrl + "/v1/ratingsedge/ratesong/" + songId);

        Form form = new Form();
        form.param("rating", rating.toString());

        Response response = target
                .request()
                .header("sessionToken", sessionToken)
                .put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        return response.readEntity(MslApiResponseMessage.class);
    }
}
