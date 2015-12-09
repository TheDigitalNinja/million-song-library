/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.bo;

import com.kenzan.msl.server.dao.AbstractDao;
import com.kenzan.msl.server.dao.AbstractSongDao;

/**
 *
 *
 * @author billschwanitz
 */
public class SongListBo extends AbstractListBo<SongBo> {
    @Override
    public SongBo convertDaoToBo(AbstractDao abstractDao) {
        AbstractSongDao abstractSongDao = (AbstractSongDao) abstractDao;

        SongBo songBo = new SongBo();
        songBo.setSongId(abstractSongDao.getSongId());
        songBo.setSongName(abstractSongDao.getSongName());
        songBo.setDuration(abstractSongDao.getSongDuration());
        songBo.setYear(abstractSongDao.getAlbumYear());
        songBo.setArtistId(abstractSongDao.getArtistId());
        songBo.setArtistMbid(abstractSongDao.getArtistMbid());
        songBo.setArtistName(abstractSongDao.getArtistName());
        songBo.setAlbumId(abstractSongDao.getAlbumId());
        songBo.setAlbumName(abstractSongDao.getAlbumName());

        return songBo;
    }
}
