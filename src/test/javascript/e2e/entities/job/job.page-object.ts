import { element, by, ElementFinder } from 'protractor';

export class JobComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-job div table .btn-danger'));
  title = element.all(by.css('jhi-job div h2#page-heading span')).first();
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

export class JobUpdatePage {
  pageTitle = element(by.id('jhi-job-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  titleInput = element(by.id('field_title'));
  typeSelect = element(by.id('field_type'));
  minSalaryInput = element(by.id('field_minSalary'));
  maxSalaryInput = element(by.id('field_maxSalary'));

  choreSelect = element(by.id('field_chore'));
  empSelect = element(by.id('field_emp'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setTitleInput(title: string): Promise<void> {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput(): Promise<string> {
    return await this.titleInput.getAttribute('value');
  }

  async setTypeSelect(type: string): Promise<void> {
    await this.typeSelect.sendKeys(type);
  }

  async getTypeSelect(): Promise<string> {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption(): Promise<void> {
    await this.typeSelect.all(by.tagName('option')).last().click();
  }

  async setMinSalaryInput(minSalary: string): Promise<void> {
    await this.minSalaryInput.sendKeys(minSalary);
  }

  async getMinSalaryInput(): Promise<string> {
    return await this.minSalaryInput.getAttribute('value');
  }

  async setMaxSalaryInput(maxSalary: string): Promise<void> {
    await this.maxSalaryInput.sendKeys(maxSalary);
  }

  async getMaxSalaryInput(): Promise<string> {
    return await this.maxSalaryInput.getAttribute('value');
  }

  async choreSelectLastOption(): Promise<void> {
    await this.choreSelect.all(by.tagName('option')).last().click();
  }

  async choreSelectOption(option: string): Promise<void> {
    await this.choreSelect.sendKeys(option);
  }

  getChoreSelect(): ElementFinder {
    return this.choreSelect;
  }

  async getChoreSelectedOption(): Promise<string> {
    return await this.choreSelect.element(by.css('option:checked')).getText();
  }

  async empSelectLastOption(): Promise<void> {
    await this.empSelect.all(by.tagName('option')).last().click();
  }

  async empSelectOption(option: string): Promise<void> {
    await this.empSelect.sendKeys(option);
  }

  getEmpSelect(): ElementFinder {
    return this.empSelect;
  }

  async getEmpSelectedOption(): Promise<string> {
    return await this.empSelect.element(by.css('option:checked')).getText();
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

export class JobDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-job-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-job'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
