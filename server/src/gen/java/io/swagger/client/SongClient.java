package io.swagger.client;

import io.swagger.model.SongInfo;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.ResponseBuilder;

/**
* Created by anram88 on 11/16/15.
*/
public class SongClient {

    private String baseUrl = "http://localhost:9000/msl";
    private ResteasyClient client;

    public SongClient() {
        client = new ResteasyClientBuilder().build();
    }

    public Response get (String id) {
        ResteasyWebTarget target = client.target(baseUrl + "/v1/catalogedge/");
        Response response = target
                .path("song/" + id)
                .request()
                .get();
        return response;
    }

    public Response browse() {
        ResteasyWebTarget target = client.target(baseUrl + "/v1/catalogedge/browse/song");
        Response response = target.request().get();
        return response;
    }
}
