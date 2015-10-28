import angular from 'angular';
import request from './request';
import entityMapper from './entityMapper';
import myLibraryStore from './services/myLibraryStore';
import recentSongsStore from './services/recentSongsStore';
import songStore from './services/songStore';
import sessionInfoStore from './services/sessionInfoStore';
import artistStore from './services/artistStore';
import albumStore from './services/albumStore';
import catalogStore from './services/catalogStore';
import loginStore from './services/loginStore';
import logoutStore from './services/logoutStore';
import rateStore from './services/rateStore';
import genreStore from './services/genreStore';
import ArtistInfoEntity from './entities/ArtistInfoEntity';
import ArtistListEntity from './entities/ArtistListEntity';
import GenreListEntity from './entities/GenreListEntity';
import AlbumInfoEntity from './entities/AlbumInfoEntity';
import AlbumListEntity from './entities/AlbumListEntity';
import SongListEntity from './entities/SongListEntity';
import StatusResponseEntity from './entities/StatusResponseEntity';
import LoginSuccessResponseEntity from './entities/LoginSuccessResponseEntity';

export default angular.module('msl.datastore', [])
  .factory('request', request)
  .factory('entityMapper', entityMapper)
  .factory('myLibraryStore', myLibraryStore)
  .factory('recentSongsStore', recentSongsStore)
  .factory('songStore', songStore)
  .factory('sessionInfoStore', sessionInfoStore)
  .factory('artistStore', artistStore)
  .factory('albumStore', albumStore)
  .factory('catalogStore', catalogStore)
  .factory('loginStore', loginStore)
  .factory('logoutStore', logoutStore)
  .factory('rateStore', rateStore)
  .factory('genreStore', genreStore)
  .value('ArtistInfoEntity', ArtistInfoEntity)
  .value('ArtistListEntity', ArtistListEntity)
  .value('GenreListEntity', GenreListEntity)
  .value('AlbumInfoEntity', AlbumInfoEntity)
  .value('AlbumListEntity', AlbumListEntity)
  .value('SongListEntity', SongListEntity)
  .value('StatusResponseEntity', StatusResponseEntity)
  .value('LoginSuccessResponseEntity', LoginSuccessResponseEntity)
  .name;
