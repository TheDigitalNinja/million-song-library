function browseCtrl () {
  this.categories = [];

  // Mockup some data for Browse page
  this.categories.push({title: "Artists", items: [{name: "Artist Name"}, {name: "Artist Name 2"}, {name: "Artist Name 3"}]});
  this.categories.push({title: "Genres", items: [{name: "Pop"}, {name: "Rock"}, {name: "Techno"}]});
}

export default browseCtrl;
