/* global describe, it, expect, beforeEach, pending */
describe('add to library button in home page', () => {

  beforeEach(() => {
    browser.driver.get('http://localhost:3000/#/');
  });

  xit('when click add to library button', () => {
    pending('Wait for functionality');
    const homeURL = browser.driver.getCurrentUrl();

    browser.driver.findElement(By.linkText('Add to library')).click();
    expect(browser.driver.getCurrentUrl()).toMatch(homeURL);
  });
});
