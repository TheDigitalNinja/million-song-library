import registrationModule from 'pages/registration/registration.module.js';

describe('match validator directive', () => {
  let $scope, element;

  beforeEach(() => {
    angular.mock.module(registrationModule);

    inject(($compile, $rootScope) => {
      $scope = $rootScope.$new();
      const elementHTML = `<form name='registration'>
        <input name='password' ng-model='password' />
        <input name='confirmationPassword' ng-model='confirmationPassword' match='registration.password' />
      </form>`;

      element = $compile(elementHTML)($scope);
      $scope.$digest();
    });
  });

  describe('validate matching inputs', () => {
    it('should set the input as valid when the values are equal', () => {
      $scope.password = 'password';
      $scope.confirmationPassword = 'password';
      $scope.$digest();

      const isolatedScope = element.isolateScope();
      expect($scope.registration.confirmationPassword.$valid).toBeTruthy();
    });

    it('should set the input as invalid when the values are not equal', () => {
      $scope.password = 'password';
      $scope.confirmationPassword = 'different_password';
      $scope.$digest();

      const isolatedScope = element.isolateScope();
      expect($scope.registration.confirmationPassword.$invalid).toBeTruthy();
    });

    it('should include the match error', () => {
      $scope.password = 'password';
      $scope.confirmationPassword = 'different_password';
      $scope.$digest();

      const isolatedScope = element.isolateScope();
      expect($scope.registration.confirmationPassword.$error.match).toBeTruthy();
    });
  });
});
