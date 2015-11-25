describe('nav', () => {

  beforeEach(() => {
    browser.driver.get(`${ browser.baseUrl }/`);
  });

  describe('Songs', () => {
    it('checks song name', () => {
      var songName = browser.driver.findElement(By.css('songs-list song-box div.media-box-meta a')).getText();
      expect(songName).toMatch('Liga Campeon');
    });

    it('redirects to song page', () => {
      browser.driver.findElement(By.css('songs-list song-box div.media-box-meta a')).click();
      browser.getCurrentUrl().then((url) => {
        expect(url).toBe(`${ browser.baseUrl }/song/1`);
        debugger;
      });
    });
  });

  describe('Albums', () => {
    it('checks album name', () => {
      browser.driver.findElement(By.css('md-tab-item:nth-child(2) span')).click();
      browser.driver.sleep(600);
      const albumName = browser.driver.findElement(By.css('albums-list album-box div.media-box-meta a')).getText();
      expect(albumName).toMatch('Some album');
    });

    it('redirects to album page', () => {
      browser.driver.findElement(By.css('md-tab-item:nth-child(2) span')).click();
      browser.driver.sleep(600);
      browser.driver.findElement(By.css('albums-list album-box div.media-box-meta a')).click();
      browser.getCurrentUrl().then((url) => {
        expect(url).toBe(`${ browser.baseUrl }/album/1`);
        debugger;
      });
    });
  });

  describe('Artists', () => {
    it('checks artists name', () => {
      browser.driver.findElement(By.css('md-tab-item:nth-child(3) span')).click();
      browser.driver.sleep(600);
      const artistName = browser.driver.findElement(By.css('artists-list artist-box div.media-box-meta a')).getText();
      expect(artistName).toMatch('La 12');
    });

    it('redirects to artist page', () => {
      browser.driver.findElement(By.css('md-tab-item:nth-child(3) span')).click();
      browser.driver.sleep(600);
      browser.driver.findElement(By.css('artists-list artist-box div.media-box-meta a')).click();
      browser.getCurrentUrl().then((url) => {
        expect(url).toBe(`${ browser.baseUrl }/artists/1`);
        debugger;
      });
    });
  });
});
