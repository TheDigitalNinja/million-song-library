describe('login', () => {

  beforeEach(() => {
    browser.driver.get('http://localhost:3000/#/login');
  });

  describe('When the form is empty', () => {
    it('Should disable the sign in button', () => {
      const button = browser.driver.findElement(By.name('login'));
      const isDisabled = button.getAttribute('disabled');

      expect(isDisabled).toBe('true');
    });
  });

  describe('When insert data', () => {
    describe('when the credentials are incorrect', () => {
      let loginURL;

      beforeEach(() => {
        const form = browser.driver.findElement(By.css('form'));

        loginURL = browser.driver.getCurrentUrl();
        browser.driver.findElement(By.name('email')).sendKeys('fail@email.com');
        browser.driver.findElement(By.name('password')).sendKeys('1234');
        form.submit();
      });

      xit('should remain on the same page', () => {
        pending('Wait for functionality');
        expect(browser.driver.getCurrentUrl()).toMatch(loginURL);
      });

      xit('should display the credentials invalid message', () => {
        pending('Wait for functionality');
        const text = browser.driver.findElement(By.className('alert-danger')).getText();

        expect(text).toMatch('Credentials are invalid!');
      });
    });

    describe('when the credentials are correct', () => {
      it('Correct email or password', () => {
        const form = browser.driver.findElement(By.css('form'));
        browser.driver.findElement(by.name('email')).sendKeys('correct@email.com');
        browser.driver.findElement(by.name('password')).sendKeys('1234');
        form.submit();

        const loginURL = browser.driver.getCurrentUrl();
        expect(loginURL).toMatch('http://localhost:3000/#');
      });
    });
  });

});
