import datastoreModule from 'modules/datastore/module';

describe('GenreInfoEntity', () => {
  const schema = {
    facetId: String,
    name: String,
  };

  let entity;

  beforeEach(() => {
    angular.mock.module(datastoreModule);

    inject((GenreInfoEntity) => {
      entity = new GenreInfoEntity();
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
