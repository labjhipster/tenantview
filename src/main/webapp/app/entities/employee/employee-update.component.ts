import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ISilverBadge } from 'app/shared/model/silver-badge.model';
import { SilverBadgeService } from 'app/entities/silver-badge/silver-badge.service';
import { IGoldenBadge } from 'app/shared/model/golden-badge.model';
import { GoldenBadgeService } from 'app/entities/golden-badge/golden-badge.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';

type SelectableEntity = IUser | IEmployee | ISilverBadge | IGoldenBadge | IDepartment;

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html',
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  employees: IEmployee[] = [];
  silverbadges: ISilverBadge[] = [];
  goldenbadges: IGoldenBadge[] = [];
  departments: IDepartment[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    email: [],
    phoneNumber: [],
    hireDate: [],
    salary: [],
    commissionPct: [],
    user: [],
    manager: [],
    sibag: [null, Validators.required],
    gobag: [null, Validators.required],
    department: [],
  });

  constructor(
    protected employeeService: EmployeeService,
    protected userService: UserService,
    protected silverBadgeService: SilverBadgeService,
    protected goldenBadgeService: GoldenBadgeService,
    protected departmentService: DepartmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      if (!employee.id) {
        const today = moment().startOf('day');
        employee.hireDate = today;
      }

      this.updateForm(employee);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));

      this.silverBadgeService.query().subscribe((res: HttpResponse<ISilverBadge[]>) => (this.silverbadges = res.body || []));

      this.goldenBadgeService.query().subscribe((res: HttpResponse<IGoldenBadge[]>) => (this.goldenbadges = res.body || []));

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));
    });
  }

  updateForm(employee: IEmployee): void {
    this.editForm.patchValue({
      id: employee.id,
      firstName: employee.firstName,
      lastName: employee.lastName,
      email: employee.email,
      phoneNumber: employee.phoneNumber,
      hireDate: employee.hireDate ? employee.hireDate.format(DATE_TIME_FORMAT) : null,
      salary: employee.salary,
      commissionPct: employee.commissionPct,
      user: employee.user,
      manager: employee.manager,
      sibag: employee.sibag,
      gobag: employee.gobag,
      department: employee.department,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  private createFromForm(): IEmployee {
    return {
      ...new Employee(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      hireDate: this.editForm.get(['hireDate'])!.value ? moment(this.editForm.get(['hireDate'])!.value, DATE_TIME_FORMAT) : undefined,
      salary: this.editForm.get(['salary'])!.value,
      commissionPct: this.editForm.get(['commissionPct'])!.value,
      user: this.editForm.get(['user'])!.value,
      manager: this.editForm.get(['manager'])!.value,
      sibag: this.editForm.get(['sibag'])!.value,
      gobag: this.editForm.get(['gobag'])!.value,
      department: this.editForm.get(['department'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
