import ratingFilterCtrl from './rating_filter.controller.js';

class RatingFilter {
  /*@ngInject*/

  constructor() {
    this.restrict = 'E';
    this.template = require('./rating_filter.html');
    this.scope = {
      activeMinRating: '@',
    };
    this.controller = ratingFilterCtrl;
    this.controllerAs = 'vm';
  }

  static directiveFactory() {
    RatingFilter.instance = new RatingFilter();
    return RatingFilter.instance;
  }
}

export default RatingFilter;
