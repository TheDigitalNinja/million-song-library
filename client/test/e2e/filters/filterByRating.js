describe('rating filter in home page', () => {

  beforeEach(() => {
    browser.driver.get(`${ browser.baseUrl }/`);
  });

  it('checks active rating', () => {
    browser.driver.findElement(By.css('rating-filter li:nth-child(1) > ul > a')).click();
    const activeRating = browser.driver.findElement(By.css('rating-filter li:nth-child(1) > ul > a'));
    expect(activeRating.getAttribute('class')).toContain('active');
  });

  it('checks button to deselect filter', () => {
    browser.driver.findElement(By.css('rating-filter li:nth-child(1) > ul > a')).click();
    const buttonText = browser.driver.findElement(By.css('rating-filter li:nth-child(1) > button > span')).getText();
    expect(buttonText).toBe('×');
  });

  it('checks deselect filter', () => {
    browser.driver.findElement(By.css('rating-filter li:nth-child(1) > ul > a')).click();
    browser.driver.findElement(By.css('rating-filter li:nth-child(1) > button > span')).click();
    const deselectFilter = browser.driver.findElement(By.css('rating-filter > ul > li:nth-child(1) > button'));
    expect(deselectFilter.getAttribute('class')).toContain('ng-hide');
  });
});
