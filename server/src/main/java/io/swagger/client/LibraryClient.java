package io.swagger.client;

import io.swagger.api.impl.MslApiResponseMessage;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

public class LibraryClient {

    private ResteasyClient client;

    public LibraryClient() {
        client = new ResteasyClientBuilder().build();
    }

    public MslApiResponseMessage getLibrary(String sessionToken) {

        ResteasyWebTarget target = client.target(String.format("%s/v1/accountedge/users/mylibrary",
                                                               ClientConstants.BASE_URL));
        Response response = target.request().cookie(new NewCookie("sessionToken", sessionToken)).get();

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %s", response.getStatus()));
        }

        return response.readEntity(MslApiResponseMessage.class);
    }

    // ============================================================================== SONG

    public MslApiResponseMessage addSongToLibrary(String songId, String sessionToken) {
        ResteasyWebTarget target = client.target(String.format("%s/v1/accountedge/users/mylibrary/addsong/%s",
                                                               ClientConstants.BASE_URL, songId));

        Response response = target.request().cookie(new NewCookie("sessionToken", sessionToken))
            .put(Entity.entity(songId, MediaType.APPLICATION_JSON));

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %s", response.getStatus()));
        }
        return response.readEntity(MslApiResponseMessage.class);
    }

    public MslApiResponseMessage removeSongFromLibrary(String songId, String timestamp, String sessionToken) {
        ResteasyWebTarget target = client.target(String.format("%s/v1/accountedge/users/mylibrary/removesong/%s/%s",
                                                               ClientConstants.BASE_URL, songId, timestamp));

        Response response = target.request().cookie(new NewCookie("sessionToken", sessionToken)).delete();

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %s", response.getStatus()));
        }
        return response.readEntity(MslApiResponseMessage.class);
    }

    // =============================================================================== ALBUM

    public MslApiResponseMessage addAlbumToLibrary(String albumId, String sessionToken) {
        ResteasyWebTarget target = client.target(String.format("%s/v1/accountedge/users/mylibrary/addalbum/%s",
                                                               ClientConstants.BASE_URL, albumId));

        Response response = target.request().cookie(new NewCookie("sessionToken", sessionToken))
            .put(Entity.entity(albumId, MediaType.APPLICATION_JSON));

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %s", response.getStatus()));
        }
        return response.readEntity(MslApiResponseMessage.class);
    }

    public MslApiResponseMessage removeAlbumFromLibrary(String albumId, String timestamp, String sessionToken) {
        ResteasyWebTarget target = client.target(String.format("%s/v1/accountedge/users/mylibrary/removealbum/%s/%s",
                                                               ClientConstants.BASE_URL, albumId, timestamp));

        Response response = target.request().cookie(new NewCookie("sessionToken", sessionToken)).delete();

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %s", response.getStatus()));
        }
        return response.readEntity(MslApiResponseMessage.class);
    }

    // =============================================================================== ARTIST

    public MslApiResponseMessage addArtistToLibrary(String artistId, String sessionToken) {
        ResteasyWebTarget target = client.target(String.format("%s/v1/accountedge/users/mylibrary/addartist/%s",
                                                               ClientConstants.BASE_URL, artistId));

        Response response = target.request().cookie(new NewCookie("sessionToken", sessionToken))
            .put(Entity.entity(artistId, MediaType.APPLICATION_JSON));

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %s", response.getStatus()));
        }
        return response.readEntity(MslApiResponseMessage.class);
    }

    public MslApiResponseMessage removeArtistFromLibrary(String artistId, String timestamp, String sessionToken) {
        ResteasyWebTarget target = client.target(String.format("%s/v1/accountedge/users/mylibrary/removeartist/%s/%s",
                                                               ClientConstants.BASE_URL, artistId, timestamp));

        Response response = target.request().cookie(new NewCookie("sessionToken", sessionToken)).delete();

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %s", response.getStatus()));
        }
        return response.readEntity(MslApiResponseMessage.class);
    }
}
