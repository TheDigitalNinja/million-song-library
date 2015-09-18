import artistFilterCtrl from './artist_filter.controller.js';

class ArtistFilter {
  /*@ngInject*/

  constructor() {
    this.restrict = 'E';
    this.template = require('./artist_filter.html');
    this.scope = {
      activeArtist: '@',
    };
    this.controller = artistFilterCtrl;
    this.controllerAs = 'vm';
  }

  static directiveFactory() {
    ArtistFilter.instance = new ArtistFilter();
    return ArtistFilter.instance;
  }
}

export default ArtistFilter;
