import genreFilterCtrl from './genre-filter.controller.js';

/**
 * Genre filter directive
 */
export default class GenreFilter {
  /*@ngInject*/

  constructor($q) {
    this.restrict = 'E';
    this.template = require('./genre-filter.html');
    this.scope = {
      listener: '=',
    };
    this.controller = genreFilterCtrl;
    this.controllerAs = 'vm';
  }

  static directiveFactory($q) {
    GenreFilter.instance = new GenreFilter();
    return GenreFilter.instance;
  }
}
