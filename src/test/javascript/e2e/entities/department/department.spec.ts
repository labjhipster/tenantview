import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DepartmentComponentsPage, DepartmentDeleteDialog, DepartmentUpdatePage } from './department.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Department e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let departmentComponentsPage: DepartmentComponentsPage;
  let departmentUpdatePage: DepartmentUpdatePage;
  let departmentDeleteDialog: DepartmentDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Departments', async () => {
    await navBarPage.goToEntity('department');
    departmentComponentsPage = new DepartmentComponentsPage();
    await browser.wait(ec.visibilityOf(departmentComponentsPage.title), 5000);
    expect(await departmentComponentsPage.getTitle()).to.eq('Departments');
    await browser.wait(ec.or(ec.visibilityOf(departmentComponentsPage.entities), ec.visibilityOf(departmentComponentsPage.noResult)), 1000);
  });

  it('should load create Department page', async () => {
    await departmentComponentsPage.clickOnCreateButton();
    departmentUpdatePage = new DepartmentUpdatePage();
    expect(await departmentUpdatePage.getPageTitle()).to.eq('Create or edit a Department');
    await departmentUpdatePage.cancel();
  });

  it('should create and save Departments', async () => {
    const nbButtonsBeforeCreate = await departmentComponentsPage.countDeleteButtons();

    await departmentComponentsPage.clickOnCreateButton();

    await promise.all([
      departmentUpdatePage.setNameInput('name'),
      departmentUpdatePage.setDescriptionInput('description'),
      departmentUpdatePage.setAdvertisementInput(absolutePath),
      departmentUpdatePage.setLogoInput(absolutePath),
      departmentUpdatePage.locationSelectLastOption(),
    ]);

    expect(await departmentUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await departmentUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await departmentUpdatePage.getAdvertisementInput()).to.endsWith(
      fileNameToUpload,
      'Expected Advertisement value to be end with ' + fileNameToUpload
    );
    expect(await departmentUpdatePage.getLogoInput()).to.endsWith(
      fileNameToUpload,
      'Expected Logo value to be end with ' + fileNameToUpload
    );

    await departmentUpdatePage.save();
    expect(await departmentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await departmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Department', async () => {
    const nbButtonsBeforeDelete = await departmentComponentsPage.countDeleteButtons();
    await departmentComponentsPage.clickOnLastDeleteButton();

    departmentDeleteDialog = new DepartmentDeleteDialog();
    expect(await departmentDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Department?');
    await departmentDeleteDialog.clickOnConfirmButton();

    expect(await departmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
