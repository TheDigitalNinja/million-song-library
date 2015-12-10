package com.kenzan.msl.server.mock;

import java.util.ArrayList;
import java.util.List;

import io.swagger.model.*;

public class FacetMockData {

    public List<FacetInfoWithChildren> mockFacets;

    public FacetMockData() {

        mockFacets = new ArrayList<>();
        List<FacetInfo> genreFacets = getGenreFacetList();
        List<FacetInfo> ratingFacets = getRatingFacetList();

        for ( FacetInfo facet : genreFacets ) {
            FacetInfoWithChildren result = new FacetInfoWithChildren();
            result.setFacetId(facet.getFacetId());
            result.setName(facet.getName());
            mockFacets.add(result);
        }

        for ( FacetInfo facet : ratingFacets ) {
            FacetInfoWithChildren result = new FacetInfoWithChildren();
            result.setFacetId(facet.getFacetId());
            result.setName(facet.getName());
            mockFacets.add(result);
        }

        FacetInfoWithChildren genreFacet = new FacetInfoWithChildren();
        genreFacet.setFacetId("0");
        genreFacet.setName("genres");
        genreFacet.setChildren(genreFacets);
        mockFacets.add(genreFacet);

        FacetInfoWithChildren ratingFacet = new FacetInfoWithChildren();
        ratingFacet.setFacetId("1");
        ratingFacet.setName("ratings");
        ratingFacet.setChildren(ratingFacets);
        mockFacets.add(ratingFacet);

    }

    public List<FacetInfoWithChildren> getRatingFacets() {
        List<FacetInfoWithChildren> result = new ArrayList<>();
        for ( FacetInfo facet : getRatingFacetList() ) {
            FacetInfoWithChildren _facet = new FacetInfoWithChildren();
            _facet.setFacetId(facet.getFacetId());
            _facet.setName(facet.getName());
            result.add(_facet);
        }
        return result;
    }

    public FacetInfoWithChildren getFacet(String facet_id) {
        // ~ should return root facet
        if ( facet_id.equals("~") ) {
            return getRootFacet();
        }

        for ( FacetInfoWithChildren facet : mockFacets ) {
            if ( facet.getFacetId().equals(facet_id) ) {
                return facet;
            }
        }

        return new FacetInfoWithChildren();
    }

    private FacetInfoWithChildren getRootFacet() {
        List<FacetInfo> mainFacets = new ArrayList<>();
        FacetInfo _genreFacets = new FacetInfo();
        _genreFacets.setFacetId("0");
        _genreFacets.setName("genres");
        mainFacets.add(_genreFacets);

        FacetInfo _ratingFacets = new FacetInfo();
        _ratingFacets.setFacetId("1");
        _ratingFacets.setName("rating");
        mainFacets.add(_ratingFacets);

        FacetInfoWithChildren result = new FacetInfoWithChildren();
        result.setFacetId("99");
        result.setName("root");
        result.setChildren(mainFacets);
        return result;
    }

    private List<FacetInfo> getGenreFacetList() {
        List<FacetInfo> genreFacetList = new ArrayList<>();

        FacetInfo facetInfoMock2 = new FacetInfo();
        facetInfoMock2.setFacetId("3");
        facetInfoMock2.setName("Rock and Roll");
        genreFacetList.add(facetInfoMock2);

        FacetInfo facetInfoMock6 = new FacetInfo();
        facetInfoMock6.setFacetId("4");
        facetInfoMock6.setName("Progressive Rock");
        genreFacetList.add(facetInfoMock6);

        FacetInfo facetInfoMock7 = new FacetInfo();
        facetInfoMock7.setFacetId("5");
        facetInfoMock7.setName("Alternative Rock");
        genreFacetList.add(facetInfoMock7);

        FacetInfo facetInfoMock1 = new FacetInfo();
        facetInfoMock1.setFacetId("2");
        facetInfoMock1.setName("Rock");
        genreFacetList.add(facetInfoMock1);

        FacetInfo facetInfoMock3 = new FacetInfo();
        facetInfoMock3.setFacetId("6");
        facetInfoMock3.setName("Pop");
        genreFacetList.add(facetInfoMock3);

        FacetInfo facetInfoMock4 = new FacetInfo();
        facetInfoMock4.setFacetId("7");
        facetInfoMock4.setName("Country");
        genreFacetList.add(facetInfoMock4);

        FacetInfo facetInfoMock5 = new FacetInfo();
        facetInfoMock5.setFacetId("8");
        facetInfoMock5.setName("Funk");
        genreFacetList.add(facetInfoMock5);

        return genreFacetList;
    }

    private List<FacetInfo> getRatingFacetList() {
        List<FacetInfo> ratingFacetList = new ArrayList<>();

        FacetInfo facetInfoMock1 = new FacetInfo();
        facetInfoMock1.setFacetId("14");
        facetInfoMock1.setName("4 stars and up");
        ratingFacetList.add(facetInfoMock1);

        FacetInfo facetInfoMock2 = new FacetInfo();
        facetInfoMock2.setFacetId("13");
        facetInfoMock2.setName("3 stars and up");
        ratingFacetList.add(facetInfoMock2);

        FacetInfo facetInfoMock3 = new FacetInfo();
        facetInfoMock3.setFacetId("12");
        facetInfoMock3.setName("2 stars and up");
        ratingFacetList.add(facetInfoMock3);

        FacetInfo facetInfoMock4 = new FacetInfo();
        facetInfoMock4.setFacetId("11");
        facetInfoMock4.setName("1 star and up");
        ratingFacetList.add(facetInfoMock4);

        return ratingFacetList;
    }
}
