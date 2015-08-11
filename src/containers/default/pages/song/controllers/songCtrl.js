function songCtrl () {
  // Randomize song rating
  // Remove this when we start getting real data from back-end
  var min = 0;
  var max = 5;
  this.songRating = Math.floor(Math.random() * (max - min + 1)) + min;
}

export default songCtrl;
