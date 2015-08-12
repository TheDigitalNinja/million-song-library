import _ from "lodash";

function entityMapperFactory () {
  "ngInject";

  return function entityMapper (response, Entity) {
    var entity = new Entity();
    var methods = _.keys(entity);
    var Instance;
    _.forEach(_.keys(response), function (key) {
      var method = _.camelCase(key);
      if (_.isFunction(entity[method])) {
        Instance = entity[method];
        entity[method] = new Instance(response[key]).valueOf();
        methods = _.without(methods, method);
      } else if (_.isArray(entity[method])) {
        Instance = entity[method][0];
        entity[method] = _.map(response[key], item => entityMapper(item, Instance));
        methods = _.without(methods, method);
      }
    });
    _.forEach(methods, method => delete entity[method]);
    return entity;
  };
}

export default entityMapperFactory;
