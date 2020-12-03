import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { TheLabelComponentsPage, TheLabelDeleteDialog, TheLabelUpdatePage } from './the-label.page-object';

const expect = chai.expect;

describe('TheLabel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let theLabelComponentsPage: TheLabelComponentsPage;
  let theLabelUpdatePage: TheLabelUpdatePage;
  let theLabelDeleteDialog: TheLabelDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TheLabels', async () => {
    await navBarPage.goToEntity('the-label');
    theLabelComponentsPage = new TheLabelComponentsPage();
    await browser.wait(ec.visibilityOf(theLabelComponentsPage.title), 5000);
    expect(await theLabelComponentsPage.getTitle()).to.eq('The Labels');
    await browser.wait(ec.or(ec.visibilityOf(theLabelComponentsPage.entities), ec.visibilityOf(theLabelComponentsPage.noResult)), 1000);
  });

  it('should load create TheLabel page', async () => {
    await theLabelComponentsPage.clickOnCreateButton();
    theLabelUpdatePage = new TheLabelUpdatePage();
    expect(await theLabelUpdatePage.getPageTitle()).to.eq('Create or edit a The Label');
    await theLabelUpdatePage.cancel();
  });

  it('should create and save TheLabels', async () => {
    const nbButtonsBeforeCreate = await theLabelComponentsPage.countDeleteButtons();

    await theLabelComponentsPage.clickOnCreateButton();

    await promise.all([theLabelUpdatePage.setLabelNameInput('labelName')]);

    expect(await theLabelUpdatePage.getLabelNameInput()).to.eq('labelName', 'Expected LabelName value to be equals to labelName');

    await theLabelUpdatePage.save();
    expect(await theLabelUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await theLabelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TheLabel', async () => {
    const nbButtonsBeforeDelete = await theLabelComponentsPage.countDeleteButtons();
    await theLabelComponentsPage.clickOnLastDeleteButton();

    theLabelDeleteDialog = new TheLabelDeleteDialog();
    expect(await theLabelDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this The Label?');
    await theLabelDeleteDialog.clickOnConfirmButton();

    expect(await theLabelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
