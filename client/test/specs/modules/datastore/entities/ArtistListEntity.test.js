import datastoreModule from 'modules/datastore/module';

describe('ArtistListEntity', () => {
  let entity, schema;

  beforeEach(() => {
    angular.mock.module(datastoreModule);

    inject((ArtistListEntity, ArtistInfoEntity) => {
      entity = new ArtistListEntity();

      schema = { artists: [ArtistInfoEntity] };
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
