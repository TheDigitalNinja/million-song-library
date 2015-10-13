/**
 * Add to library directive
 * @author anram88
 * @param libraryModel
 * @returns {{restrict: string, scope: {type: string, id: string}, link: Function}}
 */
export default function addToLibrary (libraryModel) {

  'ngInject';

  return {
    restrict: 'CA',
    scope: {
      type: '@',
      id: '=',
    },
    link: function(scope, elem, attrs) {
      elem.bind('click', ()=> {
        switch(scope.type) {
          case 'album':
            libraryModel.addAlbumToLibrary(scope, scope.id);
            break;
          case 'artist':
            libraryModel.addArtistToLibrary(scope, scope.id);
            break;
          case 'song':
            libraryModel.addSongToLibrary(scope, scope.id);
            break;
        }
      });
    },
  };
}
