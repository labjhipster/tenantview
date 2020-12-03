import { element, by, ElementFinder } from 'protractor';

export class GoldenBadgeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-golden-badge div table .btn-danger'));
  title = element.all(by.css('jhi-golden-badge div h2#page-heading span')).first();
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

export class GoldenBadgeUpdatePage {
  pageTitle = element(by.id('jhi-golden-badge-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));

  idenSelect = element(by.id('field_iden'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async idenSelectLastOption(): Promise<void> {
    await this.idenSelect.all(by.tagName('option')).last().click();
  }

  async idenSelectOption(option: string): Promise<void> {
    await this.idenSelect.sendKeys(option);
  }

  getIdenSelect(): ElementFinder {
    return this.idenSelect;
  }

  async getIdenSelectedOption(): Promise<string> {
    return await this.idenSelect.element(by.css('option:checked')).getText();
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

export class GoldenBadgeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-goldenBadge-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-goldenBadge'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
