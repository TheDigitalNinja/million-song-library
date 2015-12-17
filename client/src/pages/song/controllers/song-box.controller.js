/**
 * Song box controller
 */
export default class songBoxCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @this {vm}
   * @param {ui.router.state.$state} $state
   */
  constructor($state) {
    this.$state = $state;
  }

  isLibraryPage () {
    return this.$state.is('msl.library');
  }
}
