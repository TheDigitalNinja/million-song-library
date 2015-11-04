import datastoreModule from 'modules/datastore/module';

describe('AlbumListEntity', () => {

  let entity, schema;

  beforeEach(() => {
    angular.mock.module(datastoreModule);

    inject((AlbumListEntity, AlbumInfoEntity) => {
      entity = new AlbumListEntity();

      schema = { albums: [AlbumInfoEntity] };
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
