import _ from 'lodash';

/**
 * response raw object mapper to typed entity
 * @name entityMapper
 * @type {Function}
 */
export default function entityMapper () {
  'ngInject';

  /**
   * @param {Object} response
   * @param {Function} Entity
   * @returns {*}
   */
  function mapper (response, Entity) {
    // create new instance of entity
    const entity = new Entity();
    // get entity map keys
    let keys = _.keys(entity);

    // if the entity have no keys try it as a native object
    if(_.isEmpty(keys)) {
      return new Entity(response);
    }
    else {
      // loop per response keys
      _.forEach(_.keys(response), (key) => {
        // camel case response map key
        const method = _.camelCase(key);
        // check if we can create instance of key type
        if(_.isFunction(entity[method])) {
          // create new key type instance
          const Instance = entity[method];
          // assign it response value and get its instance
          entity[method] = new Instance(response[key]).valueOf();
          // remove key from needed to map keys list
          keys = _.without(keys, method);
        }
        // check if map value is a array
        else if(_.isArray(entity[method])) {
          // map response items to type instance
          entity[method] = _.map(response[key], (item) => mapper(item, entity[method][0]));
          // remove key from needed to map keys list
          keys = _.without(keys, method);
        }
      });
      // remove keys that had not mapped to response keys
      _.forEach(keys, (method) => delete entity[method]);
    }

    return entity;
  }

  return mapper;
}
