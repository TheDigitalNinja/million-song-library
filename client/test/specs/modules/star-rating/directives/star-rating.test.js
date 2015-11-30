/* global describe, beforeEach, afterEach, inject, it, expect, jasmine */
import _ from 'lodash';
import $ from 'jquery';
import angular from 'angular';
import starRatingModule from 'modules/star-rating/module';

describe('starRating directive', () => {
  let $scope, compileElement;
  let element;

  beforeEach(() => {
    angular.mock.module(starRatingModule, ($provide) => {
      const loginModal = jasmine.createSpyObj('loginModal', ['show']);
      const Permission = jasmine.createSpyObj('Permission', ['authorize']);

      $provide.value('loginModal', loginModal);
      $provide.value('Permission', Permission);
    });

    inject(($rootScope, $compile) => {
      $scope = $rootScope.$new();
      $scope.entityId = 'entityId';
      $scope.readOnly = false;

      const elementHTML = `<div star-rating='rating' entity-id='entityId' entity-type='song' read-only='readOnly'></div>`;
      element = $compile(elementHTML)($scope);
      $scope.$digest();
    });
  });

  afterEach(() => {
    $scope.$destroy();
  });

  describe('scope', () => {
    it('should set the entityId on the scope', () => {
      const isolatedScope = element.isolateScope();
      expect(isolatedScope.entityId).toEqual('entityId');
    });

    it('should set the readOnly on the scope', () => {
      const isolatedScope = element.isolateScope();
      expect(isolatedScope.readOnly).toEqual(false);
    });
  });
});
