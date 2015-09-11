describe('login', function () {

  beforeEach(function() {
    browser.driver.get('http://localhost:3000/#/login');
  });

  describe('When the form is empty', function () {
    it('Should disable the sign in button', function () {
      var button = browser.driver.findElement(By.name('login'));
      var isDisabled = button.getAttribute('disabled');
      expect(isDisabled).toBe('true');
    });
  });

  describe('When insert data', function () {
    describe('when the credentials are incorrect', function() {
      var loginURL;

      beforeEach(function() {
        loginURL = browser.driver.getCurrentUrl();
        var form = browser.driver.findElement(By.css('form'));
        browser.driver.findElement(By.name('email')).sendKeys('fail@email.com');
        browser.driver.findElement(By.name('password')).sendKeys('1234');
        form.submit();
      });

      it('should remain on the same page', function () {
        expect(browser.driver.getCurrentUrl()).toMatch(loginURL);
      });

      it('should display the credentials invalid message', function() {
        var text = browser.driver.findElement(By.className('alert-danger')).getText();
        expect(text).toMatch('Credentials are invalid!');
      });
    });

    describe('when the credentials are correct', function() {
      xit('Correct email or password', function () {
        pending('Wait for functionality');
        var loginURL = browser.driver.getCurrentUrl();
        browser.driver.findElement(by.name('email')).sendKeys('correct@email.com');
        browser.driver.findElement(by.name('password')).sendKeys('1234');
        expect(loginURL).toMatch('http://localhost:3000/#');
      });
    });
  });

});
