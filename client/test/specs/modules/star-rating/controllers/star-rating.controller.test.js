/* global describe, beforeEach, afterEach, inject, it, expect, jasmine */
import _ from 'lodash';
import $ from 'jquery';
import angular from 'angular';
import starRatingModule from 'modules/star-rating/module';

describe('starRating controller', () => {
  const error = new Error('an error');
  const entityType = 'song';
  let $scope, ratingModel, compileElement, $log;

  beforeEach(() => {
    angular.mock.module(starRatingModule, ($provide) => {
      ratingModel = jasmine.createSpyObj('ratingModel', ['rate']);
      $log = jasmine.createSpyObj('$log', ['warn']);

      $provide.value('ratingModel', ratingModel);
      $provide.value('$log', $log);
    });

    inject(($rootScope, $compile) => {
      $scope = $rootScope.$new();
      $scope.entityId = 'entityId';
      $scope.rating = 1;
      $scope.readOnly = false;

      compileElement = function () {
        const element = `<div star-rating='rating' entity-id='entityId' entity-type='song' read-only='readOnly'></div>`;
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
      if ($(el).children('button').text() == 'star') {
        emptyStars++;
      }
      if ($(el).children('button').text() == 'star_outline') {
        filledStars++;
      }
    });
    expect(emptyStars).toBe(1);
    expect(filledStars).toBe(4);
  });

  it('should not change rating if readOnly is true', () => {
    $scope.readOnly = true;
    const element = compileElement();

    $(element).find('button').first().click();
    const isolatedScope = element.isolateScope();
    expect(isolatedScope.starRating).toBe(1);
  });

  it('should change rating when user clicks on a star', () => {
    const element = compileElement();

    $(element).find('button:contains("star")').click();
    expect(ratingModel.rate).toHaveBeenCalledWith($scope.entityId, entityType, $scope.rating + 1);
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
      ratingModel.rate.and.throwError(error);
      const element = compileElement();

      $(element).find('.material-icons').click();
      expect($log.warn).toHaveBeenCalledWith(error);
      done();
    })();
  });
});
