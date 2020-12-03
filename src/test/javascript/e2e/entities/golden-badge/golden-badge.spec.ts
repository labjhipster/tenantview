import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  GoldenBadgeComponentsPage,
  /* GoldenBadgeDeleteDialog, */
  GoldenBadgeUpdatePage,
} from './golden-badge.page-object';

const expect = chai.expect;

describe('GoldenBadge e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let goldenBadgeComponentsPage: GoldenBadgeComponentsPage;
  let goldenBadgeUpdatePage: GoldenBadgeUpdatePage;
  /* let goldenBadgeDeleteDialog: GoldenBadgeDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load GoldenBadges', async () => {
    await navBarPage.goToEntity('golden-badge');
    goldenBadgeComponentsPage = new GoldenBadgeComponentsPage();
    await browser.wait(ec.visibilityOf(goldenBadgeComponentsPage.title), 5000);
    expect(await goldenBadgeComponentsPage.getTitle()).to.eq('Golden Badges');
    await browser.wait(
      ec.or(ec.visibilityOf(goldenBadgeComponentsPage.entities), ec.visibilityOf(goldenBadgeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create GoldenBadge page', async () => {
    await goldenBadgeComponentsPage.clickOnCreateButton();
    goldenBadgeUpdatePage = new GoldenBadgeUpdatePage();
    expect(await goldenBadgeUpdatePage.getPageTitle()).to.eq('Create or edit a Golden Badge');
    await goldenBadgeUpdatePage.cancel();
  });

  /* it('should create and save GoldenBadges', async () => {
        const nbButtonsBeforeCreate = await goldenBadgeComponentsPage.countDeleteButtons();

        await goldenBadgeComponentsPage.clickOnCreateButton();

        await promise.all([
            goldenBadgeUpdatePage.setNameInput('name'),
            goldenBadgeUpdatePage.idenSelectLastOption(),
        ]);

        expect(await goldenBadgeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');

        await goldenBadgeUpdatePage.save();
        expect(await goldenBadgeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await goldenBadgeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last GoldenBadge', async () => {
        const nbButtonsBeforeDelete = await goldenBadgeComponentsPage.countDeleteButtons();
        await goldenBadgeComponentsPage.clickOnLastDeleteButton();

        goldenBadgeDeleteDialog = new GoldenBadgeDeleteDialog();
        expect(await goldenBadgeDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Golden Badge?');
        await goldenBadgeDeleteDialog.clickOnConfirmButton();

        expect(await goldenBadgeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
