package io.swagger.mock;

import java.util.ArrayList;
import java.util.List;

import io.swagger.api.factories.FacetServiceFactory;
import io.swagger.model.*;

/**
 * Created by anram88 on 10/27/15.
 */
public class FacetMockData {

    public List<FacetInfoWithChildren> mockFacets;

    public FacetMockData() {

        FacetInfoWithChildren genreFacets = new FacetInfoWithChildren();
        genreFacets.setFacetId("0");
        genreFacets.setName("genres");
        genreFacets.setChildren(getGenreFacetList());

        FacetInfoWithChildren ratingFacets = new FacetInfoWithChildren();
        ratingFacets.setFacetId("1");
        ratingFacets.setName("rating");
        ratingFacets.setChildren(getRatingFacetList());

        mockFacets = new ArrayList<FacetInfoWithChildren>();
        mockFacets.add(genreFacets);
        mockFacets.add(ratingFacets);

    }

    private List<FacetInfoWithChildren> getGenreFacetList() {
        List<FacetInfoWithChildren> genreFacetList = new ArrayList<FacetInfoWithChildren>();

        List<FacetInfoWithChildren> rockSubFacetList = new ArrayList<FacetInfoWithChildren>();

        FacetInfoWithChildren facetInfoMock2 = new FacetInfoWithChildren();
        facetInfoMock2.setFacetId("3");
        facetInfoMock2.setName("Rock and Roll");
        rockSubFacetList.add(facetInfoMock2);

        FacetInfoWithChildren facetInfoMock6 = new FacetInfoWithChildren();
        facetInfoMock6.setFacetId("4");
        facetInfoMock6.setName("Progressive Rock");
        rockSubFacetList.add(facetInfoMock6);

        FacetInfoWithChildren facetInfoMock7 = new FacetInfoWithChildren();
        facetInfoMock7.setFacetId("5");
        facetInfoMock7.setName("Alternative Rock");
        rockSubFacetList.add(facetInfoMock7);

        FacetInfoWithChildren facetInfoMock1 = new FacetInfoWithChildren();
        facetInfoMock1.setFacetId("2");
        facetInfoMock1.setName("Rock");
        facetInfoMock1.setChildren(rockSubFacetList);
        genreFacetList.add(facetInfoMock1);

        FacetInfoWithChildren facetInfoMock3 = new FacetInfoWithChildren();
        facetInfoMock3.setFacetId("6");
        facetInfoMock3.setName("Pop");
        genreFacetList.add(facetInfoMock3);

        FacetInfoWithChildren facetInfoMock4 = new FacetInfoWithChildren();
        facetInfoMock4.setFacetId("7");
        facetInfoMock4.setName("Country");
        genreFacetList.add(facetInfoMock4);

        FacetInfoWithChildren facetInfoMock5 = new FacetInfoWithChildren();
        facetInfoMock5.setFacetId("8");
        facetInfoMock5.setName("Funk");
        genreFacetList.add(facetInfoMock5);

        return genreFacetList;
    }

    private List<FacetInfoWithChildren> getRatingFacetList() {
        List<FacetInfoWithChildren> ratingFacetList = new ArrayList<FacetInfoWithChildren>();

        FacetInfoWithChildren facetInfoMock1 = new FacetInfoWithChildren();
        facetInfoMock1.setFacetId("14");
        facetInfoMock1.setName("4 stars and up");
        ratingFacetList.add(facetInfoMock1);

        FacetInfoWithChildren facetInfoMock2 = new FacetInfoWithChildren();
        facetInfoMock2.setFacetId("13");
        facetInfoMock2.setName("3 stars and up");
        ratingFacetList.add(facetInfoMock2);

        FacetInfoWithChildren facetInfoMock3 = new FacetInfoWithChildren();
        facetInfoMock3.setFacetId("12");
        facetInfoMock3.setName("2 stars and up");
        ratingFacetList.add(facetInfoMock3);

        FacetInfoWithChildren facetInfoMock4 = new FacetInfoWithChildren();
        facetInfoMock4.setFacetId("11");
        facetInfoMock4.setName("1 star and up");
        ratingFacetList.add(facetInfoMock4);

        return ratingFacetList;
    }

    public FacetInfoWithChildren getFacet(String facet_id) {
        // ~ should return root facet
        if (facet_id.equals("~")) {
            FacetInfoWithChildren allFacets = new FacetInfoWithChildren();
            allFacets.setName("All");
            List<FacetInfoWithChildren> facets = new ArrayList<FacetInfoWithChildren>();
            facets.add(mockFacets.get(0));
            facets.add(mockFacets.get(1));
            allFacets.setChildren(facets);
            return allFacets;
        } else {
            FacetInfoWithChildren result = FacetServiceFactory.getFacet(facet_id, mockFacets);
            if (result != null) {
                return result;
            }
        }
        return new FacetInfoWithChildren();
    }

}
