/* global describe, beforeEach, afterEach, inject, it, jasmine, expect */
import _ from 'lodash';
import $ from 'jquery';
import angular from 'angular';
import playerModule from 'modules/player/module';
import angularMaterial from 'angular-material';

describe('play button directive', () => {
  /** @type {player} */
  let player;
  let $scope;
  let $compile;

  beforeEach(() => {
    angular.mock.module(angularMaterial);
    angular.mock.module(playerModule, ($provide) => {
      player = jasmine.createSpyObj('player', [
        'stop',
        'play',
        'getSongEntity',
        'addStateChangeListener',
        'removeStateChangeListener',
      ]);
      $provide.value('player', player);
    });

    inject((_$compile_, $rootScope) => {
      $scope = $rootScope.$new();
      $compile = _$compile_;
    });
  });

  afterEach(() => {
    $scope.$destroy();
  });

  it('should change to stop button when playing current track', () => {
    let onPlayerStateChange;
    player.getSongEntity.and.returnValue({ songId: 'id' });
    player.addStateChangeListener.and.callFake((fn) => { onPlayerStateChange = fn; });

    const template = $($compile(`<play-button song-id="'id'"></play-button>`)($scope));
    $scope.$digest();
    expect(_.trim(template.find('.icon-label').text())).toBe('Play');
    onPlayerStateChange();
    $scope.$digest();
    expect(_.trim(template.find('.icon-label').text())).toBe('Stop');
    expect(player.getSongEntity).toHaveBeenCalled();
  });

  it('should not change to stop button when playing other track', () => {
    let onPlayerStateChange;
    player.getSongEntity.and.returnValue({ songId: 'id2' });
    player.addStateChangeListener.and.callFake((fn) => { onPlayerStateChange = fn; });

    const template = $($compile(`<play-button song-id="'id'"></play-button>`)($scope));
    $scope.$digest();
    expect(_.trim(template.find('.icon-label').text())).toBe('Play');
    onPlayerStateChange();
    $scope.$digest();
    expect(_.trim(template.find('.icon-label').text())).toBe('Play');
    expect(player.getSongEntity).toHaveBeenCalled();
  });

  it('should change to stop button when clicking on the button', () => {
    player.getSongEntity.and.returnValue({ songId: 'id' });
    const template = $($compile(`<play-button song-id="'id'"></play-button>`)($scope));
    $scope.$digest();
    template.find('button').click();
    $scope.$digest();
    expect(player.play).toHaveBeenCalledWith('id');
  });

  it('should stop playing track', () => {
    let onPlayerStateChange;
    player.getSongEntity.and.returnValue({ songId: 'id' });
    player.addStateChangeListener.and.callFake((fn) => { onPlayerStateChange = fn; });

    const template = $($compile(`<play-button song-id="'id'"></play-button>`)($scope));
    $scope.$digest();
    expect(_.trim(template.find('.icon-label').text())).toBe('Play');
    onPlayerStateChange();
    $scope.$digest();
    template.find('button').click();
    $scope.$digest();
    expect(player.stop).toHaveBeenCalled();
  });

  it('should not change to stop when the the songEntity is undefined', () => {
    let onPlayerStateChange;
    player.addStateChangeListener.and.callFake((fn) => { onPlayerStateChange = fn; });

    const template = $($compile(`<play-button song-id="'id'"></play-button>`)($scope));
    $scope.$digest();
    expect(_.trim(template.find('.icon-label').text())).toBe('Play');
    onPlayerStateChange();
    $scope.$digest();
    expect(_.trim(template.find('.icon-label').text())).toBe('Play');
    expect(player.getSongEntity).toHaveBeenCalled();
  });
});
