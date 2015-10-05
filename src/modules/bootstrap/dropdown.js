import $ from 'jquery';
import 'bootstrap/dist/js/umd/dropdown';

/**
 * dropdown directive - this uses bootstrap dropdown
 * @name dropdown
 * @returns {{restrict: string, compile}}
 */
export default function dropdown () {
  'ngInject';
  return {
    restrict: 'A',
    compile(element) {
      element = $(element);
      element.attr('data-toggle', 'dropdown');
      element.dropdown();
    },
  };
}
