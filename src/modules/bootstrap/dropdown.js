import $ from "jquery";
import "bootstrap/js/dropdown";

function dropdown () {
  "ngInject";
  return {
    restrict: "A",
    compile(element) {
      element = $(element);
      element.attr("data-toggle", "dropdown");
      element.dropdown();
    }
  };
}

export default dropdown;
