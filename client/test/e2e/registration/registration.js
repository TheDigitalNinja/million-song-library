describe('registration', () => {

  beforeEach(() => {
    browser.driver.get(`${ browser.baseUrl }/register`);
    browser.driver.sleep(600);
  });

  describe('When the form is empty', () => {
    it('Should disable the sign up button', () => {
      const button = browser.driver.findElement(By.css('#registration-form button[type=submit]'));
      const isDisabled = button.getAttribute('disabled');
      expect(isDisabled).toBe('true');
    });
  });

  describe('When insert data', () => {
    let registrationURL;

    beforeEach(() => {
      browser.driver.findElement(by.name('email')).clear();
      browser.driver.findElement(by.name('password')).clear();
      browser.driver.findElement(by.name('confirmationPassword')).clear();
    });

    describe('when the credentials are incorrect', () => {
      beforeEach(() => {
        registrationURL = browser.driver.getCurrentUrl();
        browser.driver.findElement(By.name('email')).sendKeys('fail@email.com');
        browser.driver.findElement(By.name('password')).sendKeys('1234');
        browser.driver.findElement(By.name('confirmationPassword')).sendKeys('123');
        browser.driver.findElement(By.css('#registration-form button[type=submit]')).click();
      });

      it('should remain on the same page', () => {
        expect(browser.driver.getCurrentUrl()).toMatch(registrationURL);
      });

      it('should display the button disable', () => {
        const button = browser.driver.findElement(By.css('#registration-form button[type=submit]'));
        expect(button.getAttribute('disabled')).toBe('true');
      });
    });

    describe('when the credentials are correct', () => {
      it('Correct email or password', () => {
        browser.driver.findElement(by.name('email')).sendKeys('correct@email.com');
        browser.driver.findElement(by.name('password')).sendKeys('12345678A?');
        browser.driver.findElement(by.name('confirmationPassword')).sendKeys('12345678A?');
        browser.driver.findElement(By.css('#registration-form button[type=submit]')).click();
        browser.driver.sleep(600);
        registrationURL = browser.driver.getCurrentUrl();
        expect(registrationURL).toBe(`${ browser.baseUrl }/`);
      });
    });
  });

});
