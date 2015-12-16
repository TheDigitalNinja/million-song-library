/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.bo;

import com.kenzan.msl.server.dao.AbstractAlbumDao;
import com.kenzan.msl.server.dao.AbstractDao;

/**
 * @author billschwanitz
 */
public class AlbumListBo extends AbstractListBo<AlbumBo> {

	@Override
	public AlbumBo convertDaoToBo(AbstractDao abstractDao) {
		AbstractAlbumDao abstractAlbumDao = (AbstractAlbumDao)abstractDao;
		
		AlbumBo albumBo = new AlbumBo();
		albumBo.setAlbumId(abstractAlbumDao.getAlbumId());
		albumBo.setAlbumName(abstractAlbumDao.getAlbumName());
		albumBo.setYear(abstractAlbumDao.getAlbumYear());
		albumBo.setArtistId(abstractAlbumDao.getArtistId());
		albumBo.setArtistName(abstractAlbumDao.getArtistName());
		albumBo.setImageLink(abstractAlbumDao.getImageLink());
			
		return albumBo;
	}

}