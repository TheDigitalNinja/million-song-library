import $ from "jquery";
import "bootstrap/js/dropdown";

/**
 * dropdown directive - this uses bootstrap dropdown
 * @name dropdown
 * @returns {{restrict: string, compile}}
 */
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
