describe('genre filter in home page', () => {

  beforeEach(() => {
    browser.driver.get(`${ browser.baseUrl }/`);
  });

  it('checks genre name', () => {
    browser.driver.findElement(By.css('genre-filter li:nth-child(1) a')).click();
    const genreName = browser.driver.findElement(By.css('genre-filter li:nth-child(1) a')).getText();
    expect(genreName).toBe('Rock');
  });

  it('checks active genre name', () => {
    browser.driver.findElement(By.css('genre-filter li:nth-child(1) a')).click();
    const activeGenre = browser.driver.findElement(By.css('genre-filter li:nth-child(1) a'));
    expect(activeGenre.getAttribute('class')).toContain('active');
  });

  it('checks button to deselect filter', () => {
    browser.driver.findElement(By.css('genre-filter li:nth-child(1) a')).click();
    const buttonText = browser.driver.findElement(By.css('genre-filter li:nth-child(1) button > span')).getText();
    expect(buttonText).toBe('×');
  });

  it('checks deselect filter', () => {
    browser.driver.findElement(By.css('genre-filter li:nth-child(1) a')).click();
    browser.driver.findElement(By.css('genre-filter > ul > li:nth-child(1) > button > span')).click();
    const deselectFilter = browser.driver.findElement(By.css('genre-filter li:nth-child(1) > button'));
    expect(deselectFilter.getAttribute('class')).toContain('ng-hide');
  });
});
