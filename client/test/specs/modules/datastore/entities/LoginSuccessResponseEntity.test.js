import datastoreModule from 'modules/datastore/module';

describe('LoginSuccessResponseEntity', () => {
  const schema = {
    authenticated: String,
  };

  let entity;

  beforeEach(() => {
    angular.mock.module(datastoreModule);

    inject((LoginSuccessResponseEntity) => {
      entity = new LoginSuccessResponseEntity();
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
