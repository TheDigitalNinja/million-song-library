/* global describe, beforeEach, afterEach, inject, it, expect, jasmine */
import _ from 'lodash';
import $ from 'jquery';
import angular from 'angular';
import starRatingModule from 'modules/star-rating/module';

describe('starRating directive', () => {
  let $scope, rateStore, compileElement;

  beforeEach(() => {
    angular.mock.module(starRatingModule, ($provide) => {
      rateStore = jasmine.createSpyObj('rateStore', ['push']);
      $provide.value('rateStore', rateStore);
    });

    inject(($rootScope, $compile) => {
      $scope = $rootScope.$new();
      $scope.songId = 'songId';
      $scope.rating = 1;
      $scope.readOnly = false;

      compileElement = function () {
        const element = `<div star-rating='rating' song-id='songId' read-only='readOnly'></div>`;
        return $($compile(element)($scope));
      };
    });
  });

  afterEach(() => {
    $scope.$destroy();
  });

  it('should fill one star if (rating = 1)', () => {
    const element = compileElement();
    let emptyStars = 0;
    let filledStars = 0;

    $scope.$digest();
    _.forEach(element.find('li'), (el) => {
      if ($(el).children('button').hasClass('fa-star')) {
        emptyStars++;
      }
      if ($(el).children('button').hasClass('fa-star-o')) {
        filledStars++;
      }
    });
    expect(emptyStars).toBe(1);
    expect(filledStars).toBe(4);
  });

  it('should not change rating if readOnly is true', () => {
    $scope.readOnly = true;
    const element = compileElement();

    $scope.$digest();
    element.find('button.fa-star-o').first().click();
    $scope.$digest();
    expect($scope.rating).toBe(1);
  });

  it('should change rating when user clicks on a star', () => {
    const element = compileElement();

    $scope.$digest();
    element.find('button.fa-star-o').first().click();
    $scope.$digest();
    expect(rateStore.push).toHaveBeenCalledWith($scope.songId, $scope.rating);
    expect($scope.rating).toBe(2);
  });
});
