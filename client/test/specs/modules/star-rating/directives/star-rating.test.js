/* global describe, beforeEach, afterEach, inject, it, expect, jasmine */
import _ from 'lodash';
import $ from 'jquery';
import angular from 'angular';
import starRatingModule from 'modules/star-rating/module';

describe('starRating directive', () => {
  let $scope, rateStore, compileElement;
  let element;

  beforeEach(() => {
    angular.mock.module(starRatingModule);

    inject(($rootScope, $compile) => {
      $scope = $rootScope.$new();
      $scope.songId = 'songId';
      $scope.readOnly = false;

      const elementHTML = `<div star-rating='rating' song-id='songId' read-only='readOnly'></div>`;
      element = $compile(elementHTML)($scope);
      $scope.$digest();
    });
  });

  afterEach(() => {
    $scope.$destroy();
  });

  describe('scope', () => {
    it('should set the songId on the scope', () => {
      const isolatedScope = element.isolateScope();
      expect(isolatedScope.songId).toEqual('songId');
    });

    it('should set the readOnly on the scope', () => {
      const isolatedScope = element.isolateScope();
      expect(isolatedScope.readOnly).toEqual(false);
    });
  });
});
