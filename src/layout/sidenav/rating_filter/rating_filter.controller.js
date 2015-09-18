export default class ratingFilterCtrl {
  /*@ngInject*/

  constructor($scope) {
    this.rates = [];

    this._updateRatings();
  }

  _updateRatings() {
    const maxRates = 4;

    for(let i = maxRates; i > 0; i--) {
      this.rates.push({ rate: i, stars: this._updateStars(i) });
    }
  }

  _updateStars(starRating) {
    const max = 5;
    let stars = [];

    for (let i = 0; i < max; i++) {
      stars.push({
        filled: i < starRating,
      });
    }
    return stars;
  }
}
