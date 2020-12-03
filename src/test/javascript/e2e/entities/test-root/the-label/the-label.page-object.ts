import { element, by, ElementFinder } from 'protractor';

export class TheLabelComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-the-label div table .btn-danger'));
  title = element.all(by.css('jhi-the-label div h2#page-heading span')).first();
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

export class TheLabelUpdatePage {
  pageTitle = element(by.id('jhi-the-label-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  labelNameInput = element(by.id('field_labelName'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setLabelNameInput(labelName: string): Promise<void> {
    await this.labelNameInput.sendKeys(labelName);
  }

  async getLabelNameInput(): Promise<string> {
    return await this.labelNameInput.getAttribute('value');
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

export class TheLabelDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-theLabel-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-theLabel'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
