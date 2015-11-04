import datastoreModule from 'modules/datastore/module';

describe('SongInfoEntity', () => {
  const schema = {
    songId: String,
    songName: String,
    imageLink: String,
    artistId: String,
    artistName: String,
    albumId: String,
    albumName: String,
    duration: Number,
    genre: String,
    danceability: Number,
    averageRating: Number,
    personalRating: Number,
    songHotttnesss: Number,
    year: Number,
  };

  let entity;

  beforeEach(() => {
    angular.mock.module(datastoreModule);

    inject((SongInfoEntity) => {
      entity = new SongInfoEntity();
    });
  });

  describe('entity attributes', () => {
    it('should match the attribute types with the schema', () => {
      for(let attribute in schema) {
        expect(entity[attribute]).toEqual(schema[attribute]);
      }
    });
  });
});
