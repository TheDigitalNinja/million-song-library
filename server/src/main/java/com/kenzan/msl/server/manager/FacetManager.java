/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.manager;

import com.google.common.base.Optional;
import com.kenzan.msl.server.dao.FacetDao;
import com.kenzan.msl.server.translate.Translators;

import io.swagger.model.FacetInfo;
import io.swagger.model.FacetInfoWithChildren;

import java.util.ArrayList;
import java.util.List;

public class FacetManager {

    private static FacetManager instance = new FacetManager();

    private FacetManager() {
    }

    public static FacetManager getInstance() {
        return instance;
    }

    public Optional<FacetDao> getFacet(String id) {

        ArrayList<FacetDao> genreFacets = getGenreFacets();
        ArrayList<FacetDao> ratingFacets = getRatingFacets();

        for ( FacetDao genreFacet : genreFacets ) {
            if ( genreFacet.getFacetId().equals(id) ) {
                return Optional.of(genreFacet);
            }
        }

        for ( FacetDao ratingFacet : ratingFacets ) {
            if ( ratingFacet.getFacetId().equals(id) ) {
                return Optional.of(ratingFacet);
            }
        }

        return Optional.absent();
    }

    private ArrayList<FacetDao> getGenreFacets() {
        String[] genres = {
            "Classical",
            "Blues",
            "Dubstep",
            "Jazz",
            "Electronica",
            "Latin",
            "Soul",
            "Funk",
            "Cajun",
            "Celtic",
            "Folk",
            "Big Band",
            "Alternative",
            "Reggae",
            "Bluegrass",
            "Punk",
            "Rap",
            "Rock",
            "Hip Hop",
            "Gospel",
            "Heavy Metal",
            "Country",
            "Salsa",
            "Opera",
            "Pop" };

        ArrayList<FacetDao> result = new ArrayList<>();
        for ( int i = 0; i < genres.length; i++ ) {
            result.add(new FacetDao(Integer.toString(i + 5), genres[i]));
        }
        return result;
    }

    private ArrayList<FacetDao> getRatingFacets() {
        String[] ratings = new String[4];
        for ( int i = 1; i < 5; i++ ) {
            ratings[i - 1] = i + " & UP";
        }
        ArrayList<FacetDao> result = new ArrayList<>();
        for ( int i = 0; i < ratings.length; i++ ) {
            result.add(new FacetDao(Integer.toString(i + 1), ratings[i]));
        }
        return result;
    }

    public FacetInfoWithChildren getRestFacets(String facet_id) {
        if ( facet_id.equals("~") ) {
            return getRootFacet();
        }

        // genreFacet or ratingFacet
        if ( facet_id.contains("A") ) {
            if ( facet_id.equals("A1") ) { // - genre facet case
                FacetInfoWithChildren genreFacet = new FacetInfoWithChildren();
                genreFacet.setFacetId("A1");
                genreFacet.setName("genres");
                genreFacet.setChildren((Translators.translateFacetList(getGenreFacets())));
                return genreFacet;

            }
            FacetInfoWithChildren ratingFacet = new FacetInfoWithChildren();
            ratingFacet.setFacetId("1");
            ratingFacet.setName("ratings");
            ratingFacet.setChildren(Translators.translateFacetList(getRatingFacets()));
            return ratingFacet;
        }

        Optional<FacetDao> optResponse = getFacet(facet_id);
        if ( optResponse.isPresent() ) {
            FacetInfoWithChildren responseFacet = new FacetInfoWithChildren();
            responseFacet.setFacetId(optResponse.get().getFacetId());
            responseFacet.setName(optResponse.get().getFacetName());
            return responseFacet;
        }

        return new FacetInfoWithChildren();
    }

    private FacetInfoWithChildren getRootFacet() {
        List<FacetInfo> mainFacets = new ArrayList<>();
        FacetInfo genreFacets = new FacetInfo();
        genreFacets.setFacetId("A1");
        genreFacets.setName("genres");
        mainFacets.add(genreFacets);

        FacetInfo ratingFacets = new FacetInfo();
        ratingFacets.setFacetId("A2");
        ratingFacets.setName("rating");
        mainFacets.add(ratingFacets);

        FacetInfoWithChildren result = new FacetInfoWithChildren();
        result.setFacetId("00");
        result.setName("root");
        result.setChildren(mainFacets);
        return result;
    }

}
