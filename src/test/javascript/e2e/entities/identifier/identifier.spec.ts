import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { IdentifierComponentsPage, IdentifierDeleteDialog, IdentifierUpdatePage } from './identifier.page-object';

const expect = chai.expect;

describe('Identifier e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let identifierComponentsPage: IdentifierComponentsPage;
  let identifierUpdatePage: IdentifierUpdatePage;
  let identifierDeleteDialog: IdentifierDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Identifiers', async () => {
    await navBarPage.goToEntity('identifier');
    identifierComponentsPage = new IdentifierComponentsPage();
    await browser.wait(ec.visibilityOf(identifierComponentsPage.title), 5000);
    expect(await identifierComponentsPage.getTitle()).to.eq('Identifiers');
    await browser.wait(ec.or(ec.visibilityOf(identifierComponentsPage.entities), ec.visibilityOf(identifierComponentsPage.noResult)), 1000);
  });

  it('should load create Identifier page', async () => {
    await identifierComponentsPage.clickOnCreateButton();
    identifierUpdatePage = new IdentifierUpdatePage();
    expect(await identifierUpdatePage.getPageTitle()).to.eq('Create or edit a Identifier');
    await identifierUpdatePage.cancel();
  });

  it('should create and save Identifiers', async () => {
    const nbButtonsBeforeCreate = await identifierComponentsPage.countDeleteButtons();

    await identifierComponentsPage.clickOnCreateButton();

    await promise.all([identifierUpdatePage.setNameInput('name')]);

    expect(await identifierUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');

    await identifierUpdatePage.save();
    expect(await identifierUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await identifierComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Identifier', async () => {
    const nbButtonsBeforeDelete = await identifierComponentsPage.countDeleteButtons();
    await identifierComponentsPage.clickOnLastDeleteButton();

    identifierDeleteDialog = new IdentifierDeleteDialog();
    expect(await identifierDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Identifier?');
    await identifierDeleteDialog.clickOnConfirmButton();

    expect(await identifierComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
