import angular from 'angular';
import request from './request';
import entityMapper from './entityMapper';
import myLibraryStore from './services/myLibraryStore';
import recentSongsStore from './services/recentSongsStore';
import songStore from './services/songStore';
import artistStore from './services/artistStore';
import albumStore from './services/albumStore';
import registrationStore from './services/registrationStore';
import loginStore from './services/loginStore';
import logoutStore from './services/logoutStore';
import rateSongStore from './services/rateSongStore';
import rateAlbumStore from './services/rateAlbumStore';
import rateArtistStore from './services/rateArtistStore';
import facetStore from './services/facetStore';
import ArtistInfoEntity from './entities/ArtistInfoEntity';
import ArtistListEntity from './entities/ArtistListEntity';
import FacetInfoEntity from './entities/FacetInfoEntity';
import FacetListEntity from './entities/FacetListEntity';
import AlbumInfoEntity from './entities/AlbumInfoEntity';
import AlbumListEntity from './entities/AlbumListEntity';
import SongListEntity from './entities/SongListEntity';
import StatusResponseEntity from './entities/StatusResponseEntity';
import LoginSuccessResponseEntity from './entities/LoginSuccessResponseEntity';
import SongInfoEntity from './entities/SongInfoEntity';
import MyLibraryEntity from './entities/MyLibraryEntity';

export default angular.module('msl.datastore', [])
  .factory('request', request)
  .factory('entityMapper', entityMapper)
  .factory('myLibraryStore', myLibraryStore)
  .factory('recentSongsStore', recentSongsStore)
  .factory('songStore', songStore)
  .factory('artistStore', artistStore)
  .factory('albumStore', albumStore)
  .factory('registrationStore', registrationStore)
  .factory('loginStore', loginStore)
  .factory('logoutStore', logoutStore)
  .factory('rateSongStore', rateSongStore)
  .factory('rateAlbumStore', rateAlbumStore)
  .factory('rateArtistStore', rateArtistStore)
  .factory('facetStore', facetStore)
  .value('ArtistInfoEntity', ArtistInfoEntity)
  .value('ArtistListEntity', ArtistListEntity)
  .value('FacetInfoEntity', FacetInfoEntity)
  .value('FacetListEntity', FacetListEntity)
  .value('AlbumInfoEntity', AlbumInfoEntity)
  .value('AlbumListEntity', AlbumListEntity)
  .value('SongListEntity', SongListEntity)
  .value('StatusResponseEntity', StatusResponseEntity)
  .value('LoginSuccessResponseEntity', LoginSuccessResponseEntity)
  .value('SongInfoEntity', SongInfoEntity)
  .value('MyLibraryEntity', MyLibraryEntity)
  .name;
