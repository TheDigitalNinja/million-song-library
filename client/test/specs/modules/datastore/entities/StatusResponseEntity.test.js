import datastoreModule from 'modules/datastore/module';

describe('StatusResponseEntity', () => {
  const schema = {
    message: String,
  };

  let entity;

  beforeEach(() => {
    angular.mock.module(datastoreModule);

    inject((StatusResponseEntity) => {
      entity = new StatusResponseEntity();
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
