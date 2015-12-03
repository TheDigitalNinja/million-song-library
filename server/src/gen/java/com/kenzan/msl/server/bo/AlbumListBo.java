/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.bo;

import com.kenzan.msl.server.dao.AbstractAlbumDao;
import com.kenzan.msl.server.dao.AbstractDao;


/**
 *
 *
 * @author billschwanitz
 */
public class AlbumListBo extends AbstractListBo<AlbumBo> {
	@Override
	public void convertDaosToBos() {
		for (AbstractDao abstractDao : getDaoList()) {
			AbstractAlbumDao abstractAlbumDao = (AbstractAlbumDao)abstractDao;
			
			AlbumBo albumBo = new AlbumBo();
			albumBo.setAlbumId(abstractAlbumDao.getAlbumId());
			albumBo.setAlbumName(abstractAlbumDao.getArtistName());
			albumBo.setYear(abstractAlbumDao.getAlbumYear());
			albumBo.setArtistId(abstractAlbumDao.getArtistId());
			albumBo.setArtistName(abstractAlbumDao.getArtistName());
			
			addBo(albumBo);
		}
	}

}