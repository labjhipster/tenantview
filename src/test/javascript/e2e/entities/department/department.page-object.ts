import { element, by, ElementFinder } from 'protractor';

export class DepartmentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-department div table .btn-danger'));
  title = element.all(by.css('jhi-department div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class DepartmentUpdatePage {
  pageTitle = element(by.id('jhi-department-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  advertisementInput = element(by.id('file_advertisement'));
  logoInput = element(by.id('file_logo'));

  locationSelect = element(by.id('field_location'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setAdvertisementInput(advertisement: string): Promise<void> {
    await this.advertisementInput.sendKeys(advertisement);
  }

  async getAdvertisementInput(): Promise<string> {
    return await this.advertisementInput.getAttribute('value');
  }

  async setLogoInput(logo: string): Promise<void> {
    await this.logoInput.sendKeys(logo);
  }

  async getLogoInput(): Promise<string> {
    return await this.logoInput.getAttribute('value');
  }

  async locationSelectLastOption(): Promise<void> {
    await this.locationSelect.all(by.tagName('option')).last().click();
  }

  async locationSelectOption(option: string): Promise<void> {
    await this.locationSelect.sendKeys(option);
  }

  getLocationSelect(): ElementFinder {
    return this.locationSelect;
  }

  async getLocationSelectedOption(): Promise<string> {
    return await this.locationSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class DepartmentDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-department-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-department'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
