import FacetInfoEntity from './FacetInfoEntity';
/**
 * @name FacetListEntity
 * @property {string} facetId
 * @property {string} name
 * @property {FacetInfoEntity[]} children
 */
export default class FacetListEntity {
  constructor() {
    this.facetId = Number;
    this.name = String;
    this.children = [FacetInfoEntity];
  }
}
