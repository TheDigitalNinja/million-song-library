export default function navbar() {

  'ngInject';

  return {
    restrict: 'E',
    template: require('./navbar.html'),
    controller: 'navbarCtrl',
    controllerAs: 'vm',
  };
}

