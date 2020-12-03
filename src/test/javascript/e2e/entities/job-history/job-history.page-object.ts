import { element, by, ElementFinder } from 'protractor';

export class JobHistoryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-job-history div table .btn-danger'));
  title = element.all(by.css('jhi-job-history div h2#page-heading span')).first();
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

export class JobHistoryUpdatePage {
  pageTitle = element(by.id('jhi-job-history-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  startDateInput = element(by.id('field_startDate'));
  endDateInput = element(by.id('field_endDate'));
  languageSelect = element(by.id('field_language'));

  departmentSelect = element(by.id('field_department'));
  jobSelect = element(by.id('field_job'));
  empSelect = element(by.id('field_emp'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setStartDateInput(startDate: string): Promise<void> {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput(): Promise<string> {
    return await this.startDateInput.getAttribute('value');
  }

  async setEndDateInput(endDate: string): Promise<void> {
    await this.endDateInput.sendKeys(endDate);
  }

  async getEndDateInput(): Promise<string> {
    return await this.endDateInput.getAttribute('value');
  }

  async setLanguageSelect(language: string): Promise<void> {
    await this.languageSelect.sendKeys(language);
  }

  async getLanguageSelect(): Promise<string> {
    return await this.languageSelect.element(by.css('option:checked')).getText();
  }

  async languageSelectLastOption(): Promise<void> {
    await this.languageSelect.all(by.tagName('option')).last().click();
  }

  async departmentSelectLastOption(): Promise<void> {
    await this.departmentSelect.all(by.tagName('option')).last().click();
  }

  async departmentSelectOption(option: string): Promise<void> {
    await this.departmentSelect.sendKeys(option);
  }

  getDepartmentSelect(): ElementFinder {
    return this.departmentSelect;
  }

  async getDepartmentSelectedOption(): Promise<string> {
    return await this.departmentSelect.element(by.css('option:checked')).getText();
  }

  async jobSelectLastOption(): Promise<void> {
    await this.jobSelect.all(by.tagName('option')).last().click();
  }

  async jobSelectOption(option: string): Promise<void> {
    await this.jobSelect.sendKeys(option);
  }

  getJobSelect(): ElementFinder {
    return this.jobSelect;
  }

  async getJobSelectedOption(): Promise<string> {
    return await this.jobSelect.element(by.css('option:checked')).getText();
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

export class JobHistoryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-jobHistory-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-jobHistory'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}