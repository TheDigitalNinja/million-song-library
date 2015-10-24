import GenreInfoEntity from './GenreInfoEntity';

class GenreListEntity {
  constructor() {
    this.facetId = Number;
    this.name = String;
    this.children = [GenreInfoEntity];
  }
}

export default GenreListEntity;
