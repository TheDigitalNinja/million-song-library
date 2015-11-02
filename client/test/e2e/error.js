describe('error page', () => {

  beforeEach(() => {
    browser.driver.get(`${ browser.baseUrl }/errorPage`);
  });

  it('Checks error message', () => {
    const text = browser.driver.findElement(By.className('page-header')).getText();

    expect(text).toMatch('That page can’t be found.');
  });

  it('Redirects to homepage', () => {
    browser.driver.findElement(By.linkText('home page')).click();
    browser.getCurrentUrl().then((url) => {
      expect(url).toBe(`${ browser.baseUrl }/`);
      debugger;
    });
  });
});
