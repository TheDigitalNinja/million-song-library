package io.swagger.client;

import io.swagger.api.impl.MslApiResponseMessage;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

public class ArtistClient {

    private String baseUrl = "http://local.msl.dev:9000/msl";
    private ResteasyClient client;

    public ArtistClient() {
        client = new ResteasyClientBuilder().build();
    }

    public MslApiResponseMessage get (String id) {

        ResteasyWebTarget target = client.target(baseUrl + "/v1/catalogedge/");
        Response response = target
                .path("artist/" + id)
                .request()
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        return response.readEntity(MslApiResponseMessage.class);
    }

    public MslApiResponseMessage browse(String items) {
        ResteasyWebTarget target;
        target = client.target(baseUrl + "/v1/catalogedge/browse/artist?items=" + items);
        Response response = target
                .request()
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        return response.readEntity(MslApiResponseMessage.class);
    }

    public MslApiResponseMessage addArtist (String artistId, String sessionToken) {
        ResteasyWebTarget target = client.target(baseUrl + "/v1/accountedge/users/mylibrary/addartist/" + artistId);

        Response response = target
                .request()
                .header("Cookie", sessionToken)
                .put(Entity.entity(artistId, MediaType.APPLICATION_JSON));

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        return response.readEntity(MslApiResponseMessage.class);
    }

    public MslApiResponseMessage rateArtist (String artistId, Integer rating, String sessionToken) {
        ResteasyWebTarget target = client.target(baseUrl + "/v1/ratingsedge/rateartist/" + artistId);

        Form form = new Form();
        form.param("rating", rating.toString());

        Response response = target
                .request()
                .header("Cookie", sessionToken)
                .put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        return response.readEntity(MslApiResponseMessage.class);

    }
}
