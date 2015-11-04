import datastoreModule from 'modules/datastore/module';

describe('SessionInfoEntity', () => {
  const schema = {
    userEmail: String,
    userId: String,
  };

  let entity;

  beforeEach(() => {
    angular.mock.module(datastoreModule);

    inject((SessionInfoEntity) => {
      entity = new SessionInfoEntity();
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
