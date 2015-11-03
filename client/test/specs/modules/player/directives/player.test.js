import playerModule from 'modules/player/module';
import $ from 'jquery';

describe('playerDirective', () => {
  let element, $scope, player;

  beforeEach(() => {
    angular.mock.module(playerModule, ($provide) => {
      player = jasmine.createSpyObj('player', ['isActive', 'getSongEntity', 'stop', 'addStateChangeListener', 'removeStateChangeListener']);

      $provide.value('player', player);
    });

    inject(($compile, $rootScope) => {
      $scope = $rootScope.$new();

      element = () => {
        const elementHTML = `<player></player>`;
        const compiledElement = $compile(elementHTML)($scope);
        $scope.$digest();
        return compiledElement;
      };
    });
  });

  afterEach(() => {
    $scope.$destroy();
  });

  describe('onPlayerStateChange', () => {
    const isActive = true;
    const song = 'a_song';

    beforeEach(() => {
      player.isActive.and.returnValue(isActive);
      player.getSongEntity.and.returnValue(song);
      player.addStateChangeListener.and.callFake((cb) => cb());
    });

    it('should get the isActive from player', () => {
      element();
      expect($scope.player.active).toBe(isActive);
    });

    it('should get the song entity', () => {
      element();
      expect($scope.player.songEntity).toBe(song);
    });
  });

  describe('close', () => {
    it('should stop the player', () => {
      element();
      $scope.player.close();
      expect(player.stop).toHaveBeenCalled();
    });
  });

  describe('when scope destroys', () => {
    it('should call removeStateChangeListener', () => {
      element();
      $scope.$destroy();
      expect(player.removeStateChangeListener).toHaveBeenCalled();
    });
  });

  describe('template', () => {
    describe('when player is not active', () => {
      beforeEach(() => {
        player.isActive.and.returnValue(false);
        player.addStateChangeListener.and.callFake((cb) => cb());
      });

      it('should not include the player footer if player is not active', () => {
        const template = $(element());
        expect(template.find('footer').length).toBe(0);
      });
    });

    describe('when player is active', () => {
      beforeEach(() => {
        player.isActive.and.returnValue(true);
        player.addStateChangeListener.and.callFake((cb) => cb());
      });

      it('should not include the player footer if player is not active', () => {
        const template = $(element());
        expect(template.find('footer').length).toBe(1);
      });
    });
  });
});
