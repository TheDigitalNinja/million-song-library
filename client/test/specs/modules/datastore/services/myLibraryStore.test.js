import datastoreModule from 'modules/datastore/module';

describe('myLibraryStore', () => {
  const TIMESTAMP = 'Thu Feb 05 00:31:49 CST 2015';
  const API_PATH = `:${process.env.ACCOUNT_PORT}/msl/v1/accountedge/users/mylibrary`;

  let myLibraryStore, request, entityMapper, MyLibraryEntity, StatusResponseEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['get', 'put', 'delete']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_myLibraryStore_, _MyLibraryEntity_, _StatusResponseEntity_) => {
      myLibraryStore = _myLibraryStore_;
      MyLibraryEntity = _MyLibraryEntity_;
      StatusResponseEntity = _StatusResponseEntity_;
    });
  });

  describe('fetch', () => {
    const response = 'a_response';
    beforeEach(() => {
      request.get.and.returnValue({ data: response });
    });

    it('should request the list of songs', (done) => {
      (async () => {
        await myLibraryStore.fetch();
        expect(request.get).toHaveBeenCalledWith(API_PATH);
        done();
      })();
    });

    it('should map the response into a SongListEntity', (done) => {
      (async () => {
        await myLibraryStore.fetch();
        expect(entityMapper).toHaveBeenCalledWith(response, MyLibraryEntity);
        done();
      })();
    });
  });

  describe('addSong', () => {
    const SONG_ID = '2';
    const response = { message: 'success' };

    beforeEach(() => {
      request.put.and.returnValue(response);
    });

    it('should make a put request to add the song to the library endpoint', (done) => {
      (async () => {
        await myLibraryStore.addSong(SONG_ID);
        expect(request.put).toHaveBeenCalledWith(`${API_PATH}/addsong/${ SONG_ID }`);
        done();
      })();
    });

    it('should map the response into a StatusResponseEntity', (done) => {
      (async () => {
        await myLibraryStore.addSong(SONG_ID);
        expect(entityMapper).toHaveBeenCalledWith(response, StatusResponseEntity);
        done();
      })();
    });
  });

  describe('addAlbum', () => {
    const ALBUM_ID = '2';
    const response = { status: 0, message: 'success' };

    beforeEach(() => {
      request.put.and.returnValue(response);
    });

    it('should make a put request to add the album to the library endpoint', (done) => {
      (async () => {
        await myLibraryStore.addAlbum(ALBUM_ID);
        expect(request.put).toHaveBeenCalledWith(`${API_PATH}/addalbum/${ ALBUM_ID }`);
        done();
      })();
    });

    it('should map the response into a StatusResponnseEntity', (done) => {
      (async () => {
        await myLibraryStore.addAlbum(ALBUM_ID);
        expect(entityMapper).toHaveBeenCalledWith(response, StatusResponseEntity);
        done();
      })();
    });
  });

  describe('addArtist', () => {
    const ARTIST_ID = '2';
    const response = { status: 0, message: 'success' };

    beforeEach(() => {
      request.put.and.returnValue(response);
    });

    it('should make a put request to add the artist to the library endpoint', (done) => {
      (async () => {
        await myLibraryStore.addArtist(ARTIST_ID);
        expect(request.put).toHaveBeenCalledWith(`${API_PATH}/addartist/${ ARTIST_ID }`);
        done();
      })();
    });

    it('should map the response into a StatusResponseEntity', (done) => {
      (async () => {
        await myLibraryStore.addArtist(ARTIST_ID);
        expect(entityMapper).toHaveBeenCalledWith(response, StatusResponseEntity);
        done();
      })();
    });
  });

  describe('removeSong', () => {
    const SONG_ID = '2';
    const response = { status: 0, message: 'success' };

    beforeEach(() => {
      request.delete.and.returnValue(response);
    });

    it('should make a put request to remove the song from the library endpoint', (done) => {
      (async () => {
        await myLibraryStore.removeSong(SONG_ID, TIMESTAMP);
        expect(request.delete).toHaveBeenCalledWith(`${API_PATH}/removesong/${ SONG_ID }/${ TIMESTAMP }`);
        done();
      })();
    });

    it('should map the response into a StatusResponseEntity', (done) => {
      (async () => {
        await myLibraryStore.removeSong(SONG_ID, TIMESTAMP);
        expect(entityMapper).toHaveBeenCalledWith(response, StatusResponseEntity);
        done();
      })();
    });
  });

  describe('removeAlbum', () => {
    const ALBUM_ID = '2';
    const response = { status: 0, message: 'success' };

    beforeEach(() => {
      request.delete.and.returnValue(response);
    });

    it('should make a put request to remove the album from the library endpoint', (done) => {
      (async () => {
        await myLibraryStore.removeAlbum(ALBUM_ID, TIMESTAMP);
        expect(request.delete).toHaveBeenCalledWith(`${API_PATH}/removealbum/${ ALBUM_ID }/${ TIMESTAMP }`);
        done();
      })();
    });

    it('should map the response into a StatusResponnseEntity', (done) => {
      (async () => {
        await myLibraryStore.removeAlbum(ALBUM_ID, TIMESTAMP);
        expect(entityMapper).toHaveBeenCalledWith(response, StatusResponseEntity);
        done();
      })();
    });
  });

  describe('removeArtist', () => {
    const ARTIST_ID = '2';
    const response = { status: 0, message: 'success' };

    beforeEach(() => {
      request.delete.and.returnValue(response);
    });

    it('should make a put request to remove the artist from the library endpoint', (done) => {
      (async () => {
        await myLibraryStore.removeArtist(ARTIST_ID, TIMESTAMP);
        expect(request.delete).toHaveBeenCalledWith(`${API_PATH}/removeartist/${ ARTIST_ID }/${ TIMESTAMP }`);
        done();
      })();
    });

    it('should map the response into a StatusResponseEntity', (done) => {
      (async () => {
        await myLibraryStore.removeArtist(ARTIST_ID, TIMESTAMP);
        expect(entityMapper).toHaveBeenCalledWith(response, StatusResponseEntity);
        done();
      })();
    });
  });
});
