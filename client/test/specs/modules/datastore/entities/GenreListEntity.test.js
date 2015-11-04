import datastoreModule from 'modules/datastore/module';

describe('GenreListEntity', () => {
  let entity, schema;

  beforeEach(() => {
    angular.mock.module(datastoreModule);

    inject((GenreListEntity, GenreInfoEntity) => {
      entity = new GenreListEntity();

      schema = {
        facetId: Number,
        name: String,
        children: [GenreInfoEntity],
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
