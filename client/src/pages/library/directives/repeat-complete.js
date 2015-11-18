/**
 * Repeat complete directive to be used with ng-repeat
 * fires off the evaluation of an expression once ng-repeat has completed.
 * example: <div ng-repeat="i in [0,1,2,3,4]" repeat-complete="vm.repeatComplete()">Item</div>
 * @returns {{restrict: string, link: Function}}
 */
export default function repeatComplete() {
  'ngInject';

  function link(scope, element, attributes) {
    let completeExpression = attributes.repeatComplete;
    if(scope.$last) {
      scope.$evalAsync(completeExpression);
    }
  }

  return {
    restrict: 'A',
    link: link,
  };
}
