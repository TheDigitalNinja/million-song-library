import datastoreModule from 'modules/datastore/module';

describe('FacetListEntity', () => {
  let entity, schema;

  beforeEach(() => {
    angular.mock.module(datastoreModule);

    inject((FacetListEntity, FacetInfoEntity) => {
      entity = new FacetListEntity();

      schema = {
        facetId: Number,
        name: String,
        children: [FacetInfoEntity],
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
