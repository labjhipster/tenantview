import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';

import { User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

import { AccountService } from 'app/core/auth/account.service';
import { ICompany } from '../../shared/admin/company.model';
import { CompanyService } from '../../admin/company/company.service';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html',
})
export class UserManagementUpdateComponent implements OnInit {
  user!: User;
  authorities: string[] = [];
  isSaving = false;
  currentAccount: any;
  companies: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    login: [
      '',
      [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50),
        Validators.pattern('^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$'),
      ],
    ],
    firstName: ['', [Validators.maxLength(50)]],
    lastName: ['', [Validators.maxLength(50)]],
    email: ['', [Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    activated: [],
    langKey: [],
    authorities: [],
    company: [],
  });

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private accountService: AccountService,
    private companyService: CompanyService
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });

    if (this.accountService.hasAnyAuthority('ROLE_ADMIN')) {
      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    }

    this.route.data.subscribe(({ user }) => {
      if (user) {
        this.user = user;
        if (this.user.id === undefined) {
          this.user.activated = true;
        }
        this.updateForm(user);
      }
    });
    this.userService.authorities().subscribe(authorities => {
      this.authorities = authorities;
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.updateUser(this.user);
    if (this.user.id !== undefined) {
      this.userService.update(this.user).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    } else {
      this.user.langKey = 'en';
      this.userService.create(this.user).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    }
  }

  private updateForm(user: User): void {
    this.editForm.patchValue({
      id: user.id,
      login: user.login,
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      activated: user.activated,
      langKey: user.langKey,
      authorities: user.authorities,
      company: user.company,
    });
  }

  private updateUser(user: User): void {
    user.login = this.editForm.get(['login'])!.value;
    user.firstName = this.editForm.get(['firstName'])!.value;
    user.lastName = this.editForm.get(['lastName'])!.value;
    user.email = this.editForm.get(['email'])!.value;
    user.activated = this.editForm.get(['activated'])!.value;
    user.langKey = this.editForm.get(['langKey'])!.value;
    user.authorities = this.editForm.get(['authorities'])!.value;
    user.company = this.editForm.get(['company'])!.value;
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }
  trackCompanyById(index: number, item: ICompany): any {
    return item.id;
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
