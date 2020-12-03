import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IJobHistory, JobHistory } from 'app/shared/model/job-history.model';
import { JobHistoryService } from './job-history.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IJob } from 'app/shared/model/job.model';
import { JobService } from 'app/entities/job/job.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

type SelectableEntity = IDepartment | IJob | IEmployee;

@Component({
  selector: 'jhi-job-history-update',
  templateUrl: './job-history-update.component.html',
})
export class JobHistoryUpdateComponent implements OnInit {
  isSaving = false;
  departments: IDepartment[] = [];
  jobs: IJob[] = [];
  employees: IEmployee[] = [];

  editForm = this.fb.group({
    id: [],
    startDate: [],
    endDate: [],
    language: [],
    departments: [],
    jobs: [],
    emps: [],
  });

  constructor(
    protected jobHistoryService: JobHistoryService,
    protected departmentService: DepartmentService,
    protected jobService: JobService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobHistory }) => {
      if (!jobHistory.id) {
        const today = moment().startOf('day');
        jobHistory.startDate = today;
        jobHistory.endDate = today;
      }

      this.updateForm(jobHistory);

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));

      this.jobService.query().subscribe((res: HttpResponse<IJob[]>) => (this.jobs = res.body || []));

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(jobHistory: IJobHistory): void {
    this.editForm.patchValue({
      id: jobHistory.id,
      startDate: jobHistory.startDate ? jobHistory.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: jobHistory.endDate ? jobHistory.endDate.format(DATE_TIME_FORMAT) : null,
      language: jobHistory.language,
      departments: jobHistory.departments,
      jobs: jobHistory.jobs,
      emps: jobHistory.emps,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobHistory = this.createFromForm();
    if (jobHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.jobHistoryService.update(jobHistory));
    } else {
      this.subscribeToSaveResponse(this.jobHistoryService.create(jobHistory));
    }
  }

  private createFromForm(): IJobHistory {
    return {
      ...new JobHistory(),
      id: this.editForm.get(['id'])!.value,
      startDate: this.editForm.get(['startDate'])!.value ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      language: this.editForm.get(['language'])!.value,
      departments: this.editForm.get(['departments'])!.value,
      jobs: this.editForm.get(['jobs'])!.value,
      emps: this.editForm.get(['emps'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobHistory>>): void {
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

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
