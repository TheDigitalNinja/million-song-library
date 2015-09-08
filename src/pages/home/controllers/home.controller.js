/**
 * Home page controller
 */
export default class homeCtrl {

  constructor() {
    var vm = this;
    // TODO: Remove after getting list from backend
    vm.itemsCount = 5;
    vm.categories = [];
    // Mockup some data
    // TODO: Remove mock data when we start getting list of genres from backend
    vm.categories.push({
      title: 'Artists',
      items: [{name: 'Artist Name'}, {name: 'Artist Name 2'}, {name: 'Artist Name 3'}]
    });
    vm.categories.push({title: 'Genres', items: [{name: 'Pop'}, {name: 'Rock'}, {name: 'Techno'}]});
  }

  getNumber(num) {
    return new Array(num);
  }

}


