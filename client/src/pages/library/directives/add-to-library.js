import { ROLE_USER } from '../../../constants.js';

/**
 * Add to library directive
 * @param {object} libraryModel
 * @param {object} loginModal
 * @param {object} Permission
 * @returns {{restrict: string, scope: {type: string, id: string}, link: Function}}
 */
export default function addToLibrary(libraryModel, loginModal, Permission) {
  'ngInject';

  return {
    restrict: 'CA',
    scope: {
      type: '@',
      id: '=',
    },

    link,
  };

  function link(scope, elem) {
    async function clickEvent() {
      try {
        await Permission.authorize({ only: [ROLE_USER] });
        addToLibrary(scope.type, scope.id);
      }
      catch(error) {
        loginModal.show();
      }
    }

    elem.bind('click', clickEvent);
  }

  async function addToLibrary(type, id) {
    switch(type) {
      case 'album':
        await libraryModel.addAlbumToLibrary(id);
        break;
      case 'artist':
        await libraryModel.addArtistToLibrary(id);
        break;
      case 'song':
        await libraryModel.addSongToLibrary(id);
        break;
    }
  }
}
