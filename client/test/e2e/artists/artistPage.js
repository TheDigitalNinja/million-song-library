describe('artistPage', () => {

  beforeEach(() => {
    browser.driver.get(`${ browser.baseUrl }/artists/1`);
    browser.driver.sleep(600);
  });

  it('checks artist name', () => {
    const artistName = browser.driver.findElement(By.css('[data-pt-id=artist-name]')).getText();
    expect(artistName).toBe('La 12');
  });

  describe('nav in artist page', () => {
    describe('Songs', () => {
      it('checks song name', () => {
        const songName = browser.driver.findElement(By.css('songs-table td:nth-child(3) > a')).getText();
        expect(songName).toBe('Liga Campeon');
      });

      it('redirects to song page', () => {
        browser.driver.findElement(By.css('songs-table td:nth-child(3) > a')).click();
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
        const albumName = browser.driver.findElement(By.css('albums-list album-box a')).getText();
        expect(albumName).toBe('Some album');
      });

      it('redirects to album page', () => {
        browser.driver.findElement(By.css('md-tab-item:nth-child(2) span')).click();
        browser.driver.sleep(600);
        browser.driver.findElement(By.css('albums-list album-box a')).click();
        browser.driver.sleep(600);
        browser.getCurrentUrl().then((url) => {
          expect(url).toBe(`${ browser.baseUrl }/album/1`);
          debugger;
        });
      });
    });

    describe('Similar Artists', () => {
      it('checks artist name', () => {
        browser.driver.findElement(By.css('md-tab-item:nth-child(3) span')).click();
        browser.driver.sleep(600);
        const albumName = browser.driver.findElement(By.css('artists-list artist-box div.media-box-meta a')).getText();
        expect(albumName).toBe('Mercyful Fate');
      });

      it('redirects to artist page', () => {
        browser.driver.findElement(By.css('md-tab-item:nth-child(3) span')).click();
        browser.driver.sleep(600);
        browser.driver.findElement(By.css('artists-list artist-box div.media-box-meta a')).click();
        browser.getCurrentUrl().then((url) => {
          expect(url).toBe(`${ browser.baseUrl }/artists/2`);
          debugger;
        });
      });
    });
  });
});
