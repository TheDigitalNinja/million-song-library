/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.translate;

import com.datastax.driver.mapping.Result;
import com.kenzan.msl.server.bo.AlbumBo;
import com.kenzan.msl.server.bo.AlbumListBo;
import com.kenzan.msl.server.bo.ArtistBo;
import com.kenzan.msl.server.bo.ArtistListBo;
import com.kenzan.msl.server.bo.SongBo;
import com.kenzan.msl.server.bo.SongListBo;
import com.kenzan.msl.server.dao.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import io.swagger.model.AlbumInfo;
import io.swagger.model.AlbumList;
import io.swagger.model.ArtistInfo;
import io.swagger.model.ArtistList;
import io.swagger.model.FacetInfo;
import io.swagger.model.FacetInfoWithChildren;
import io.swagger.model.PagingState;
import io.swagger.model.SongInfo;
import io.swagger.model.SongList;

/**
 * @author billschwanitz
 */
public class Translators {

    // ==========================================================================================================
    // ALBUMS
    // =================================================================================================================

    public static AlbumList translate(AlbumListBo listBo) {
        AlbumList model = new AlbumList();

        PagingState pagingState = new PagingState();
        pagingState.setPagingState(null == listBo.getPagingState() ? null : listBo.getPagingState().toString());
        model.setPagingState(pagingState);

        for ( AlbumBo albumBo : listBo.getBoList() ) {
            model.getAlbums().add(Translators.translate(albumBo));
        }

        return model;
    }

    public static AlbumInfo translate(AlbumBo bo) {
        AlbumInfo model = new AlbumInfo();

        model.setAlbumId(null == bo.getAlbumId() ? null : bo.getAlbumId().toString());
        model.setAlbumName(bo.getAlbumName());
        model.setArtistId(null == bo.getArtistId() ? null : bo.getArtistId().toString());
        model.setArtistMbid(null == bo.getArtistMbid() ? null : bo.getArtistMbid().toString());
        model.setArtistName(bo.getArtistName());
        model.setGenre(bo.getGenre());
        model.setYear(bo.getYear());
        model.setAverageRating(bo.getAverageRating());
        model.setPersonalRating(bo.getPersonalRating());
        model.setImageLink(bo.getImageLink());
        model.setInMyLibrary(bo.isInMyLibrary());
        model.setFavoritesTimestamp(bo.getFavoritesTimestamp());
        if ( null == bo.getSongsList() || bo.getSongsList().isEmpty() ) {
            model.setSongsList(null);
        }
        else {
            model.getSongsList().addAll(bo.getSongsList());
        }

        return model;
    }

    public static List<AlbumInfo> translateAlbumsByUserDao(Result<AlbumsByUserDao> input) {
        List<AlbumInfo> output = new ArrayList<>();
        for ( AlbumsByUserDao dao : input ) {
            AlbumInfo albumInfo = new AlbumInfo();
            albumInfo.setYear(dao.getAlbumYear());
            albumInfo.setArtistName(dao.getArtistName());
            albumInfo.setArtistName(dao.getAlbumName());
            albumInfo.setAlbumId(dao.getAlbumId() == null ? null : dao.getAlbumId().toString());
            albumInfo.setArtistId(dao.getArtistId() == null ? null : dao.getArtistId().toString());
            albumInfo.setArtistMbid(dao.getArtistMbid() == null ? null : dao.getArtistMbid().toString());
            albumInfo.setFavoritesTimestamp(dao.getFavoritesTimestamp() == null ? null : String.valueOf(dao
                .getFavoritesTimestamp().getTime()));
            output.add(albumInfo);
        }
        return output;
    }

    // =========================================================================================================
    // ARTISTS
    // =================================================================================================================

    public static ArtistList translate(ArtistListBo listBo) {
        ArtistList model = new ArtistList();

        PagingState pagingState = new PagingState();
        pagingState.setPagingState(null == listBo.getPagingState() ? null : listBo.getPagingState().toString());
        model.setPagingState(pagingState);

        if ( null == listBo.getBoList() || listBo.getBoList().isEmpty() ) {
            model.setArtists(null);
        }
        else {
            for ( ArtistBo artistBo : listBo.getBoList() ) {
                model.getArtists().add(Translators.translate(artistBo));
            }
        }

        return model;
    }

    public static ArtistInfo translate(ArtistBo bo) {
        ArtistInfo model = new ArtistInfo();

        model.setArtistId(null == bo.getArtistId() ? null : bo.getArtistId().toString());
        model.setArtistMbid(null == bo.getArtistMbid() ? null : bo.getArtistMbid().toString());
        model.setArtistName(bo.getArtistName());
        model.setGenre(StringUtils.isEmpty(bo.getGenre()) ? null : bo.getGenre());
        model.setAverageRating(bo.getAverageRating());
        model.setPersonalRating(bo.getPersonalRating());
        model.setImageLink(bo.getImageLink());
        model.setAlbumsList((null == bo.getAlbumsList() || bo.getAlbumsList().isEmpty()) ? null : bo.getAlbumsList());
        model.setSongsList((null == bo.getSongsList() || bo.getSongsList().isEmpty()) ? null : bo.getSongsList());
        model.setInMyLibrary(bo.isInMyLibrary());
        model.setFavoritesTimestamp(bo.getFavoritesTimestamp());
        model
            .setSimilarArtistsList((null == bo.getSimilarArtistsList() || bo.getSimilarArtistsList().isEmpty())
                                                                                                               ? null
                                                                                                               : bo.getSimilarArtistsList());

        return model;
    }

    public static List<ArtistInfo> translateArtistByUserDao(Result<ArtistsByUserDao> input) {
        List<ArtistInfo> output = new ArrayList<>();

        for ( ArtistsByUserDao dao : input ) {
            ArtistInfo artistInfo = new ArtistInfo();
            artistInfo.setArtistName(dao.getArtistName());
            artistInfo.setArtistId(dao.getArtistId() == null ? null : dao.getArtistId().toString());
            artistInfo.setArtistMbid(dao.getArtistMbid() == null ? null : dao.getArtistMbid().toString());
            artistInfo.setFavoritesTimestamp(dao.getFavoritesTimestamp() == null ? null : String.valueOf(dao
                .getFavoritesTimestamp().getTime()));
            output.add(artistInfo);
        }
        return output;
    }

    // ===========================================================================================================
    // SONGS
    // =================================================================================================================

    public static SongList translate(SongListBo listBo) {
        SongList model = new SongList();

        PagingState pagingState = new PagingState();
        pagingState.setPagingState(null == listBo.getPagingState() ? null : listBo.getPagingState().toString());
        model.setPagingState(pagingState);

        if ( null == listBo.getBoList() || listBo.getBoList().isEmpty() ) {
            model.setSongs(null);
        }
        else {
            for ( SongBo songBo : listBo.getBoList() ) {
                model.getSongs().add(Translators.translate(songBo));
            }
        }

        return model;
    }

    public static SongInfo translate(SongBo bo) {
        SongInfo model = new SongInfo();

        model.setSongId(null == bo.getSongId() ? null : bo.getSongId().toString());
        model.setSongName(bo.getSongName());
        model.setGenre(bo.getGenre());
        model.setDuration(bo.getDuration());
        model.setDanceability(bo.getDanceability());
        model.setSongHotttnesss(bo.getSongHotttnesss());
        model.setYear(bo.getYear());
        model.setAverageRating(bo.getAverageRating());
        model.setPersonalRating(bo.getPersonalRating());
        model.setImageLink(bo.getImageLink());
        model.setArtistId(null == bo.getArtistId() ? null : bo.getArtistId().toString());
        model.setArtistMbid(null == bo.getArtistMbid() ? null : bo.getArtistMbid().toString());
        model.setArtistName(bo.getArtistName());
        model.setAlbumId(null == bo.getAlbumId() ? null : bo.getAlbumId().toString());
        model.setAlbumName(bo.getAlbumName());
        model.setInMyLibrary(bo.isInMyLibrary());
        model.setFavoritesTimestamp(bo.getFavoritesTimestamp());

        return model;
    }

    public static List<SongInfo> translateSongsByUserDao(Result<SongsByUserDao> input) {
        List<SongInfo> output = new ArrayList<>();
        for ( SongsByUserDao dao : input ) {
            SongInfo songInfo = new SongInfo();
            songInfo.setArtistName(dao.getArtistName());
            songInfo.setAlbumName(dao.getAlbumName());
            songInfo.setSongName(dao.getSongName());
            songInfo.setDuration(dao.getSongDuration());
            songInfo.setYear(dao.getAlbumYear());
            songInfo.setSongId(dao.getSongId() == null ? null : dao.getSongId().toString());
            songInfo.setAlbumId(dao.getAlbumId() == null ? null : dao.getAlbumId().toString());
            songInfo.setArtistId(dao.getArtistId() == null ? null : dao.getArtistId().toString());
            songInfo.setArtistMbid(dao.getArtistMbid() == null ? null : dao.getArtistMbid().toString());
            songInfo.setFavoritesTimestamp(dao.getFavoritesTimestamp() == null ? null : String.valueOf(dao
                .getFavoritesTimestamp().getTime()));
            output.add(songInfo);
        }
        return output;
    }

    // ==========================================================================================================
    // FACETS
    // =================================================================================================================

    public static FacetInfoWithChildren translate(FacetWithChildrenDao dao) {
        FacetInfoWithChildren model = new FacetInfoWithChildren();

        model.setFacetId(dao.getFacetId());
        model.setName(dao.getFacetName());
        model.setChildren(translateFacetList(dao.getChildren()));

        return model;
    }

    public static FacetInfo translate(FacetDao dao) {
        FacetInfo model = new FacetInfo();

        model.setFacetId(dao.getFacetId());
        model.setName(dao.getFacetName());

        return model;
    }

    public static List<FacetInfo> translateFacetList(List<FacetDao> daoList) {
        List<FacetInfo> modelList = new ArrayList<>(daoList.size());

        for ( FacetDao dao : daoList ) {
            modelList.add(translate(dao));
        }

        return modelList;
    }
}
