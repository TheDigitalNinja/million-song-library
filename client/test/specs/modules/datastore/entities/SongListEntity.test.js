import datastoreModule from 'modules/datastore/module';

describe('SongListEntity', () => {
  let entity, schema;

  beforeEach(() => {
    angular.mock.module(datastoreModule);

    inject((SongListEntity, SongInfoEntity) => {
      entity = new SongListEntity();

      schema = {
        genre: String,
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
