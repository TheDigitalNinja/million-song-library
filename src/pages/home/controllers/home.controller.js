/**
 * Home page controller
 */
export default class homeCtrl {
  /*@ngInject*/

  constructor($scope, catalogStore, genreStore) {
    this.getSongs($scope, catalogStore);

    this.categories = [];
    // Mockup some data
    // TODO: Remove mock data when we start getting list of genres from backend
    this.categories.push({
      title: 'Artists',
      items: [{name: 'Artist Name'}, {name: 'Artist Name 2'}, {name: 'Artist Name 3'}],
    });
  }

  getNumber(num) {
    return new Array(num);
  }

  getSongs($scope, catalogStore) {
    (async () => {
      try {
        const songList = await catalogStore.fetch();
        this.songs = songList.songs;
        $scope.$evalAsync();
      }
      catch(err) { }
    })();
  }
}

