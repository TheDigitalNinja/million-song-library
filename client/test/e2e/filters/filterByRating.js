describe('rating filter in home page', () => {

  beforeEach(() => {
    browser.driver.get(`${ browser.baseUrl }/`);
  });

  it('checks active rating', () => {
    browser.driver.findElement(By.className('stars4')).click();
    const activeRating = browser.driver.findElement(By.className('stars4'));
    expect(activeRating.getAttribute('class')).toContain('active');
  });

  it('checks button to deselect filter', () => {
    browser.driver.findElement(By.className('stars4')).click();
    const buttonText = browser.driver.findElement(By.className('deselecFilter4')).getText();
    expect(buttonText).toBe('×');
  });

  it('checks deselect filter', () => {
    browser.driver.findElement(By.className('stars4')).click();
    browser.driver.findElement(By.className('deselecFilter4')).click();
    const deselectFilter = browser.driver.findElement(By.className('deselecFilter4'));
    expect(deselectFilter.getAttribute('class')).toContain('ng-hide');
  });
});
