export default function match($parse) {
  return {
    require: 'ngModel',
    link,
  };

  function link(scope, element, attributes, controller) {
    const password = $parse(attributes.match);

    scope.$watch(() => password(scope), () => {
      controller.$$parseAndValidate();
    });

    controller.$validators.match = (modelValue, viewValue) => {
      const isValid = password(scope).$viewValue === viewValue;
      return isValid;
    };
  }
}
