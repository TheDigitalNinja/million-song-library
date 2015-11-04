import datastoreModule from 'modules/datastore/module';

describe('AlbumInfoEntity', () => {
  const schema = {
    albumId: String,
    albumName: String,
    artistId: String,
    genre: String,
    year: Number,
    averageRating: Number,
    personalRating: Number,
    imageLink: String,
    songsList: [String],
  };

  let entity;

  beforeEach(() => {
    angular.mock.module(datastoreModule);

    inject((AlbumInfoEntity) => {
      entity = new AlbumInfoEntity();
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
