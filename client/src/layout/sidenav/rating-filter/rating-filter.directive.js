import ratingFilterCtrl from './rating-filter.controller.js';

export default class RatingFilter {
  /*@ngInject*/

  constructor() {
    this.restrict = 'E';
    this.template = require('./rating-filter.html');
    this.scope = {
      listener: '=',
    };
    this.controller = ratingFilterCtrl;
    this.controllerAs = 'vm';
  }

  static directiveFactory() {
    RatingFilter.instance = new RatingFilter();
    return RatingFilter.instance;
  }
}
