describe('genre filter in home page', () => {

  beforeEach(() => {
    browser.driver.get(`${ browser.baseUrl }/`);
  });

  it('checks genre name', () => {
    browser.driver.findElement(By.linkText('Rock')).click();
    const genreName = browser.driver.findElement(By.linkText('Rock')).getText();
    expect(genreName).toBe('Rock');
  });

  it('checks active genre name', () => {
    browser.driver.findElement(By.linkText('Rock')).click();
    const genreName = browser.driver.findElement(By.className('active')).getText();
    expect(genreName).toBe('Rock');
  });

  it('checks button to deselect filter', () => {
    browser.driver.findElement(By.linkText('Rock')).click();
    const buttonText = browser.driver.findElement(By.className('close')).getText();
    expect(buttonText).toBe('×');
  });

  it('checks deselect filter', () => {
    browser.driver.findElement(By.linkText('Rock')).click();
    browser.driver.findElement(By.className('close')).click();
    const deselectFilter = browser.driver.findElement(By.className('close'));
    expect(deselectFilter.getAttribute('class')).toContain('ng-hide');
  });
});
