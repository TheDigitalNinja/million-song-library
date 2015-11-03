import loadingModule from 'modules/loading/module';
import $ from 'jquery';

describe('loadind Directive', () => {
  let element;

  beforeEach(() => {
    angular.mock.module(loadingModule);

    inject(($compile, $rootScope) => {
      const $scope = $rootScope.$new();

      const elementHTML = `<loading></loading>`;
      element = $compile(elementHTML)($scope);
      $scope.$digest();
    });
  });

  describe('template', () => {
    it('should have the spin icon', () => {
      const template = $(element);
      expect(template.find('.fa-refresh.fa-spin').length).toBe(1);
    });
  });
});
