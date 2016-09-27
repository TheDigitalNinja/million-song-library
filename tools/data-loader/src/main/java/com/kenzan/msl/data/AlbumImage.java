package com.kenzan.msl.data;

import java.util.List;
import java.util.UUID;

import org.musicbrainz.MBWS2Exception;
import org.musicbrainz.controller.Artist;
import org.musicbrainz.includes.ArtistIncludesWs2;
import org.musicbrainz.model.entity.ArtistWs2;
import org.musicbrainz.model.entity.ReleaseWs2;

import fm.last.musicbrainz.coverart.CoverArt;
import fm.last.musicbrainz.coverart.CoverArtArchiveClient;
import fm.last.musicbrainz.coverart.CoverArtImage;
import fm.last.musicbrainz.coverart.impl.DefaultCoverArtArchiveClient;

public class AlbumImage {
    private String albumName;
    private UUID artistMbid;
    private String url;

    public AlbumImage(String albumName, UUID artistMbid) {
        this.albumName = albumName;
        this.artistMbid = artistMbid;
    }

    public String getUrl() {
        if (this.url == null) {
            this.url = getImageUrl();
        }
        return url;
    }

    private String getAlbumMbid() {
        String albumMbid = null;
        Artist artist = new Artist();

        ArtistIncludesWs2 includes = artist.getIncludes();
        includes.setReleases(true);
        artist.setIncludes(includes);
        try {
            ArtistWs2 artistWs = artist.lookUp(this.artistMbid.toString());
            List<ReleaseWs2> list = artistWs.getReleases();
            for (ReleaseWs2 release : list) {
                if (release.getTitle().toLowerCase().equals(this.albumName.toLowerCase())) {
                    albumMbid = release.getId();
                    break;
                }
            }

        } catch (MBWS2Exception e) {
            e.printStackTrace();
        }
        return albumMbid;
    }

    private String getImageUrl() {
        String imageUrl = "";
        CoverArtArchiveClient client = new DefaultCoverArtArchiveClient();
        String albumMbid = this.getAlbumMbid();
        if (albumMbid == null) {
            return imageUrl;
        }
        UUID releaseMbid = UUID.fromString(albumMbid);
        CoverArt coverArt = null;
        try {
            coverArt = client.getByMbid(releaseMbid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (coverArt != null) {
            for (CoverArtImage coverArtImage : coverArt.getImages()) {
                if (coverArtImage.isFront()) {
                    imageUrl = coverArtImage.getSmallThumbnailUrl();
                    if (null == imageUrl) {
                        imageUrl = coverArtImage.getLargeThumbnailUrl();
                    }
                    if (null == imageUrl) {
                        imageUrl = coverArtImage.getImageUrl();
                    }
                }
            }
        }
        return imageUrl;
    }
}
