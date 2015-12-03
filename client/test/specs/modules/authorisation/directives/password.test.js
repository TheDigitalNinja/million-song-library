import authenticationModule from 'modules/authentication/module';

describe('password directive', () => {
  let $scope, element;

  beforeEach(() => {
    angular.mock.module(authenticationModule);

    inject(($compile, $rootScope) => {
      $scope = $rootScope.$new();

      const elementHTML = `<form name="credentials"><input type="password"
             name="password"
             ng-model="password"
             password></form>`;
      element = $compile(elementHTML)($scope);
      $scope.$digest();
    });
  });

  it('should check an invalid password', () => {
    $scope.password = 'd';
    $scope.$digest();

    const isolatedScope = element.isolateScope();
    expect($scope.credentials.password.$error.password).toBeTruthy();
  });

  it('should check a valid password', () => {
    $scope.password = 'abcde12345FGHI@!#$';
    $scope.$digest();

    const isolatedScope = element.isolateScope();
    expect($scope.credentials.password.$error.password).toBeFalsy();
  });
});
