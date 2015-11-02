package io.swagger.api.factories;

import io.swagger.model.AlbumInfo;
import io.swagger.model.ArtistInfo;
import io.swagger.model.FacetInfoWithChildren;
import io.swagger.model.SongInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anram88 on 10/29/15.
 */
public class FacetServiceFactory {

    /**
     * Retrieves a facet from list by id
     * @param facet_id
     * @param mockFacets
     * @return
     */
    public static FacetInfoWithChildren getFacet (String facet_id, List<FacetInfoWithChildren> mockFacets) {
        FacetInfoWithChildren searchResults = FacetServiceFactory.searchForFacet(mockFacets, facet_id);
        if (searchResults != null && searchResults.getFacetId().equals(facet_id)) {
            return searchResults;
        }
        return null;
    }

    /**
     * Searches recursively for a facet under the children
     * @param facets
     * @param facet_id
     * @return
     */
    public static FacetInfoWithChildren searchForFacet(List<FacetInfoWithChildren> facets, String facet_id) {
        for (FacetInfoWithChildren facet : facets) {
            if (facet.getFacetId().equals(facet_id)) {
                return facet;
            } else if (facet.getChildren().size() > 0) {
                FacetInfoWithChildren searchResults = searchForFacet(facet.getChildren(), facet_id);
                if (searchResults != null) {
                    return searchResults;
                }
            }
        }
        return null;
    }

    /**
     * Checks if facet is type rating facet
     * @param facet_id
     * @param ratingFacets
     * @return boolean
     */
    public static boolean isRatingFacet(String facet_id, List<FacetInfoWithChildren> ratingFacets) {
        ratingFacets = ratingFacets.get(1).getChildren();
        for (FacetInfoWithChildren ratingFacet : ratingFacets) {
            if (ratingFacet.getFacetId().equals(facet_id)) {
                return true;
            }
        }
        return false;
    }

    public static List<AlbumInfo> filterAlbumByRatingFacet(List<AlbumInfo> albumList, String facet_id) {
        List<AlbumInfo> result = new ArrayList<AlbumInfo>();
        for (AlbumInfo album : albumList) {
            if (fitsInRating(album.getAverageRating(), facet_id)) {
                result.add(album);
            }
        }
        return result;
    }

    public static List<SongInfo> filterSongsByRatingFacet(List<SongInfo> songList, String facet_id) {
        List<SongInfo> result = new ArrayList<SongInfo>();

        for (SongInfo song : songList) {
            if (fitsInRating(song.getAverageRating(), facet_id)) {
                result.add(song);
            }
        }
        return result;
    }

    public static List<ArtistInfo> filterArtistByRatingFacet(List<ArtistInfo> artistList, String facet_id) {
        List<ArtistInfo> result = new ArrayList<ArtistInfo>();

        for (ArtistInfo artist : artistList) {
            if (fitsInRating(artist.getAverageRating(), facet_id)) {
                result.add(artist);
            }
        }
        return result;
    }

    /**
     * Checks if rating fits in a specified facet category
     * @param rating
     * @param facet_id
     * @return
     */
    private static boolean fitsInRating(BigDecimal rating, String facet_id) {
        switch (Integer.parseInt(facet_id)) {
            case 14:
                if (rating.compareTo(new BigDecimal("4")) >= 0) {
                    return true;
                }
                break;
            case 13:
                if (rating.compareTo(new BigDecimal("3")) >= 0) {
                    return true;
                }
                break;
            case 12:
                if (rating.compareTo(new BigDecimal("2")) >= 0) {
                    return true;
                }
                break;
            case 11:
                if (rating.compareTo(new BigDecimal("1")) >= 0) {
                    return true;
                }
                break;
        }
        return false;
    }

}
