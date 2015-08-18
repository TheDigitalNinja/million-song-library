function homeCtrl () {
  this.itemsCount = 5;
  this.getNumber = (num) => {
    return new Array(num);
  };

  this.categories = [];

  // Mockup some data
  this.categories.push({title: "Artists", items: [{name: "Artist Name"}, {name: "Artist Name 2"}, {name: "Artist Name 3"}]});
  this.categories.push({title: "Genres", items: [{name: "Pop"}, {name: "Rock"}, {name: "Techno"}]});
}

export default homeCtrl;
