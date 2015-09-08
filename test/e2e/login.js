describe('login', function () {

  beforeEach(function() {
    browser.driver.get('http://localhost:3000/#/login');
  });

  describe('When the form is empty', function () {
    it('Should disable the sign in button', function () {
      var button = browser.driver.findElement(By.buttonText('Sign in'));
      var css = button.getAttribute('class');
      expect(css).toMatch('disabled');
    });
  });

  describe('When insert data', function () {
    it('Incorrect email or password', function () {
      var loginURL = browser.driver.getCurrentUrl();
      var email = browser.driver.findElement(by.id('email')).sendKeys('fail@email.com');
      var password = browser.driver.findElement(by.id('password')).sendKeys('1234');
      expect(browser.driver.getCurrentUrl()).toMatch(loginURL);
    });

    it('Correct email or password', function () {
      var loginURL = browser.driver.getCurrentUrl();
      var email = browser.driver.findElement(by.id('email')).sendKeys('fail@email.com');
      var password = browser.driver.findElement(by.id('password')).sendKeys('1234');
      expect(loginURL).toMatch('http://localhost:3000/#');
    });
  });

});
