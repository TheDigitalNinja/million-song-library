/**
 * Remove from library directive
 * @param libraryModel
 * @returns {{restrict: string, scope: {type: string, id: string}, link: Function}}
 */
export default function removeFromLibrary (libraryModel) {

  'ngInject';

  return {
    restrict: 'CA',
    scope: {
      type: '@',
      id: '=',
    },
    link: function(scope, elem) {
      elem.bind('click', () => {
        switch(scope.type) {
          case 'album':
            libraryModel.removeAlbumFromLibrary(scope.id);
            break;
          case 'artist':
            libraryModel.removeArtistFromLibrary(scope.id);
            break;
          case 'song':
            libraryModel.removeSongFromLibrary(scope.id);
            break;
        }
      });
    },
  };
}
