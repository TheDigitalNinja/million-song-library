describe('play button in home page', () => {

  beforeEach(() => {
    browser.driver.get('http://localhost:3000/#/');
  });

  xit('when click play button', () => {
    pending('Wait for functionality');
    const homeURL = browser.driver.getCurrentUrl();

    browser.driver.findElement(By.linkText('Play')).click();
    expect(browser.driver.getCurrentUrl()).toMatch(homeURL);
  });
});
