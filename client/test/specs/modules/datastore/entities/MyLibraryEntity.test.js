import datastoreModule from 'modules/datastore/module';

describe('MyLibraryEntity', () => {
  let entity, schema;
  beforeEach(() => {
    angular.mock.module(datastoreModule);

    inject((MyLibraryEntity, AlbumInfoEntity, ArtistInfoEntity, SongInfoEntity) => {
      entity = new MyLibraryEntity();

      schema = {
        albums: [AlbumInfoEntity],
        artists: [ArtistInfoEntity],
        songs: [SongInfoEntity],
      };
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
