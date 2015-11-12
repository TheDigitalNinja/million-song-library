/* global describe, beforeEach, afterEach, inject, it, expect, jasmine */
import _ from 'lodash';
import $ from 'jquery';
import angular from 'angular';
import starRatingModule from 'modules/star-rating/module';

describe('starRating controller', () => {
  const error = new Error('an error');
  let $scope, rateStore, compileElement, $log;

  beforeEach(() => {
    angular.mock.module(starRatingModule, ($provide) => {
      rateStore = jasmine.createSpyObj('rateStore', ['push']);
      $log = jasmine.createSpyObj('$log', ['warn']);
      $provide.value('rateStore', rateStore);
      $provide.value('$log', $log);
    });

    inject(($rootScope, $compile) => {
      $scope = $rootScope.$new();
      $scope.songId = 'songId';
      $scope.rating = 1;
      $scope.readOnly = false;

      compileElement = function () {
        const element = `<div star-rating='rating' song-id='songId' read-only='readOnly'></div>`;
        const compile = $compile(element)($scope);
        $scope.$digest();
        return compile;
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

    _.forEach($(element).find('li'), (el) => {
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

    $(element).find('button.fa-star-o').first().click();
    const isolatedScope = element.isolateScope();
    expect(isolatedScope.starRating).toBe(1);
  });

  it('should change rating when user clicks on a star', () => {
    const element = compileElement();

    $(element).find('button.fa-star-o').first().click();
    expect(rateStore.push).toHaveBeenCalledWith($scope.songId, $scope.rating + 1);
  });

  it('', () => {
    const element = compileElement();
    const isolatedScope = element.isolateScope();
    const newRating = 2;
    isolatedScope.rating._setStarRating(newRating);
    expect(isolatedScope.starRating).toBe(newRating);
  });

  it('should log a warn if an error is thrown', (done) => {
    (async () => {
      rateStore.push.and.throwError(error);
      const element = compileElement();

      $(element).find('.btn').click();
      expect($log.warn).toHaveBeenCalledWith(error);
      done();
    })();
  });
});
