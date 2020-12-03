import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  SilverBadgeComponentsPage,
  /* SilverBadgeDeleteDialog, */
  SilverBadgeUpdatePage,
} from './silver-badge.page-object';

const expect = chai.expect;

describe('SilverBadge e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let silverBadgeComponentsPage: SilverBadgeComponentsPage;
  let silverBadgeUpdatePage: SilverBadgeUpdatePage;
  /* let silverBadgeDeleteDialog: SilverBadgeDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SilverBadges', async () => {
    await navBarPage.goToEntity('silver-badge');
    silverBadgeComponentsPage = new SilverBadgeComponentsPage();
    await browser.wait(ec.visibilityOf(silverBadgeComponentsPage.title), 5000);
    expect(await silverBadgeComponentsPage.getTitle()).to.eq('Silver Badges');
    await browser.wait(
      ec.or(ec.visibilityOf(silverBadgeComponentsPage.entities), ec.visibilityOf(silverBadgeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create SilverBadge page', async () => {
    await silverBadgeComponentsPage.clickOnCreateButton();
    silverBadgeUpdatePage = new SilverBadgeUpdatePage();
    expect(await silverBadgeUpdatePage.getPageTitle()).to.eq('Create or edit a Silver Badge');
    await silverBadgeUpdatePage.cancel();
  });

  /* it('should create and save SilverBadges', async () => {
        const nbButtonsBeforeCreate = await silverBadgeComponentsPage.countDeleteButtons();

        await silverBadgeComponentsPage.clickOnCreateButton();

        await promise.all([
            silverBadgeUpdatePage.setNameInput('name'),
            silverBadgeUpdatePage.idenSelectLastOption(),
        ]);

        expect(await silverBadgeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');

        await silverBadgeUpdatePage.save();
        expect(await silverBadgeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await silverBadgeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last SilverBadge', async () => {
        const nbButtonsBeforeDelete = await silverBadgeComponentsPage.countDeleteButtons();
        await silverBadgeComponentsPage.clickOnLastDeleteButton();

        silverBadgeDeleteDialog = new SilverBadgeDeleteDialog();
        expect(await silverBadgeDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Silver Badge?');
        await silverBadgeDeleteDialog.clickOnConfirmButton();

        expect(await silverBadgeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
