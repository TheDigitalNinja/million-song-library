/* global describe, beforeEach, afterEach, inject, it, expect, jasmine */
import _ from "lodash";
import $ from "jquery";
import angular from "angular";
import starRatingModule from "modules/star-rating/module";

describe("starRating directive", function () {
  var $scope, rateStore, compileElement;

  beforeEach(angular.mock.module(starRatingModule, function ($provide) {
    rateStore = jasmine.createSpyObj("rateStore", ["push"]);
    $provide.value("rateStore", rateStore);
  }));

  beforeEach(inject(function ($rootScope, $compile) {
    $scope = $rootScope.$new();
    $scope.songId = "songId";
    $scope.rating = 1;
    $scope.readOnly = false;

    compileElement = function () {
      return $($compile("<div star-rating='rating' song-id='songId' read-only='readOnly'></div>")($scope));
    };
  }));

  afterEach(function () {
    $scope.$destroy();
  });

  it("should fill one star if (rating = 1)", function () {
    var emptyStars = 0;
    var filledStars = 0;
    var element = compileElement();
    $scope.$digest();
    _.forEach(element.find("li"), function (el) {
      if ($(el).children("button").hasClass("fa-star")) {
        emptyStars++;
      }
      if ($(el).children("button").hasClass("fa-star-o")) {
        filledStars++;
      }
    });
    expect(emptyStars).toBe(1);
    expect(filledStars).toBe(4);
  });

  it("should not change rating if readOnly is true", function () {
    $scope.readOnly = true;
    var element = compileElement();
    $scope.$digest();
    element.find("button.fa-star-o").first().click();
    $scope.$digest();
    expect($scope.rating).toBe(1);
  });

  it("should change rating when user clicks on a star", function () {
    var element = compileElement();
    $scope.$digest();
    element.find("button.fa-star-o").first().click();
    $scope.$digest();
    expect(rateStore.push).toHaveBeenCalledWith($scope.songId, $scope.rating);
    expect($scope.rating).toBe(2);
  });
});
