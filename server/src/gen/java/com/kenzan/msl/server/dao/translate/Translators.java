/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.dao.translate;

import com.kenzan.msl.server.bo.AlbumBo;
import com.kenzan.msl.server.bo.AlbumListBo;
import com.kenzan.msl.server.bo.ArtistBo;
import com.kenzan.msl.server.bo.ArtistListBo;
import com.kenzan.msl.server.bo.SongBo;
import com.kenzan.msl.server.bo.SongListBo;
import com.kenzan.msl.server.dao.FacetDao;
import com.kenzan.msl.server.dao.FacetWithChildrenDao;

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
 *
 *
 * @author billschwanitz
 */
public class Translators {
	
    // ========================================================================================================== ALBUMS
    // =================================================================================================================

	public static AlbumList translate(AlbumListBo listBo) {
		AlbumList model = new AlbumList();
		
		PagingState pagingState = new PagingState();
		pagingState.setPagingState(null == listBo.getPagingState() ? null : listBo.getPagingState().toString()); 
		model.setPagingState(pagingState);
		
		for (AlbumBo albumBo : listBo.getBoList()) {
			model.getAlbums().add(Translators.translate(albumBo));
		}
		
		return model;
	}
	
	public static AlbumInfo translate(AlbumBo bo) {
		AlbumInfo model = new AlbumInfo();
		
		model.setAlbumId(bo.getAlbumId().toString());
		model.setAlbumName(bo.getAlbumName());
		model.setArtistId(bo.getArtistId().toString());
		model.setArtistName(bo.getArtistName());
		model.setGenre(bo.getGenre());
		model.setYear(bo.getYear());
		model.setAverageRating(bo.getAverageRating());
		model.setPersonalRating(bo.getPersonalRating());
		model.setImageLink(bo.getImageLink());
		model.getSongsList().addAll(bo.getSongsList());
		
		return model;
	}
	

    // ========================================================================================================= ARTISTS
    // =================================================================================================================

	public static ArtistList translate(ArtistListBo listBo) {
		ArtistList model = new ArtistList();
		
		PagingState pagingState = new PagingState();
		pagingState.setPagingState(null == listBo.getPagingState() ? null : listBo.getPagingState().toString()); 
		model.setPagingState(pagingState);
		
		
		for (ArtistBo artistBo : listBo.getBoList()) {
			model.getArtists().add(Translators.translate(artistBo));
		}
		
		return model;
	}

	public static ArtistInfo translate(ArtistBo bo) {
		ArtistInfo model = new ArtistInfo();
		
		model.setArtistId(bo.getArtistId().toString());
		model.setArtistName(bo.getArtistName());
		model.setGenre(StringUtils.isEmpty(bo.getGenre()) ? null : bo.getGenre());
		model.setAverageRating(bo.getAverageRating());
		model.setPersonalRating(bo.getPersonalRating());
		model.setImageLink(StringUtils.isEmpty(bo.getImageLink()) ? null : bo.getImageLink());
		model.setAlbumsList((null == bo.getAlbumsList() || bo.getAlbumsList().isEmpty()) ? null : bo.getAlbumsList());
		model.setSongsList((null == bo.getSongsList() || bo.getSongsList().isEmpty()) ? null : bo.getSongsList());
		model.setSimilarArtistsList((null == bo.getSimilarArtistsList() || bo.getSimilarArtistsList().isEmpty()) ? null : bo.getSimilarArtistsList());
		
		return model;
	}
 

    // =========================================================================================================== SONGS
    // =================================================================================================================
	
	public static SongList translate(SongListBo listBo) {
		SongList model = new SongList();
		
		PagingState pagingState = new PagingState();
		pagingState.setPagingState(null == listBo.getPagingState() ? null : listBo.getPagingState().toString()); 
		model.setPagingState(pagingState);
		
		for (SongBo songBo : listBo.getBoList()) {
			model.getSongs().add(Translators.translate(songBo));
		}
		
		return model;
	}
	
	public static SongInfo translate(SongBo dao) {
		SongInfo model = new SongInfo();
		
		model.setSongId(dao.getSongId().toString());
		model.setSongName(dao.getSongName());
		model.setGenre(dao.getGenre());
		model.setDuration(dao.getDuration());
		model.setDanceability(dao.getDanceability());
		model.setSongHotttnesss(dao.getSongHotttnesss());
		model.setYear(dao.getYear());
		model.setAverageRating(dao.getAverageRating());
		model.setPersonalRating(dao.getPersonalRating());
		model.setImageLink(dao.getImageLink());
		model.setArtistId(dao.getArtistId().toString());
		model.setArtistName(dao.getArtistName());
		model.setAlbumId(dao.getAlbumId().toString());
		model.setAlbumName(dao.getAlbumName());
		
		return model;
	}

	
    // ========================================================================================================== FACETS
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

	/*
	 * +-----------------+
	 * | Private Methods |
	 * +-----------------+
	 */

	/*
	private static List<AlbumInfo> translateAlbumList(List<AlbumBo> daoList) {
		List<AlbumInfo> modelList = new ArrayList<>(daoList.size());
		
		for (AlbumBo dao : daoList) {
			modelList.add(translate(dao));
		}
		
		return modelList;
	}
	
	private static List<ArtistInfo> translateArtistList(List<ArtistBo> artistBoList) {
		List<ArtistInfo> modelList = new ArrayList<>(artistBoList.size());
		
		for (ArtistBo artistBo : artistBoList) {
			modelList.add(translate(artistBo));
		}
		
		return modelList;
	}
	*/
	public static List<FacetInfo> translateFacetList(List<FacetDao> daoList) {
		List<FacetInfo> modelList = new ArrayList<FacetInfo>(daoList.size());
		
		for (FacetDao dao : daoList) {
			modelList.add(translate(dao));
		}
		
		return modelList;
	}
	/*
	private static List<SongInfo> translateSongList(List<SongBo> daoList) {
		List<SongInfo> modelList = new ArrayList<>(daoList.size());
		
		for (SongBo dao : daoList) {
			modelList.add(translate(dao));
		}
		
		return modelList;
	}
	*/
}
