import datastoreModule from 'modules/datastore/module';

describe('ArtistInfoEntity', () => {
  const schema = {
    artistId: String,
    artistName: String,
    albumsList: [String],
    averageRating: Number,
    personalRating: Number,
    imageLink: String,
    songsList: [String],
    similarArtistsList: [String],
  };

  let entity;

  beforeEach(() => {
    angular.mock.module(datastoreModule);

    inject((ArtistInfoEntity) => {
      entity = new ArtistInfoEntity();
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
