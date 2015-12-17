/**
 * Remove from library directive
 * @param {object} libraryModel
 * @returns {{restrict: string, scope: {type: string, id: string}, link: Function}}
 */
export default function removeFromLibrary (libraryModel) {

  'ngInject';

  return {
    restrict: 'CA',
    scope: {
      type: '@',
      id: '=',
      timestamp: '=',
    },
    link: function(scope, elem) {
      elem.bind('click', () => {
        switch(scope.type) {
          case 'album':
            libraryModel.removeAlbumFromLibrary(scope.id, scope.timestamp);
            break;
          case 'artist':
            libraryModel.removeArtistFromLibrary(scope.id, scope.timestamp);
            break;
          case 'song':
            libraryModel.removeSongFromLibrary(scope.id, scope.timestamp);
            break;
        }
      });
    },
  };
}
