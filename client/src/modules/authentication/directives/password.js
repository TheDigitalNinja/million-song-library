export default function password() {
  return {
    require: 'ngModel',
    link,
  };

  function link(scope, element, attributes, controller) {
    const PASSWORD_REGEXP = /(?=.*\d.*)(?=.*[A-Z].*)(?=.*[!#$%&*+,./:;<=>?@\^_~-].*)(?=.{10,})/;

    controller.$validators.password = (modelValue, viewValue) => {
      const isValid = controller.$isEmpty(modelValue) || PASSWORD_REGEXP.test(viewValue);
      return isValid;
    };
  }
}
