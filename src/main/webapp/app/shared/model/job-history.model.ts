import { Moment } from 'moment';
import { IDepartment } from 'app/shared/model/department.model';
import { IJob } from 'app/shared/model/job.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { Language } from 'app/shared/model/enumerations/language.model';

export interface IJobHistory {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  language?: Language;
  departments?: IDepartment[];
  jobs?: IJob[];
  emps?: IEmployee[];
}

export class JobHistory implements IJobHistory {
  constructor(
    public id?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public language?: Language,
    public departments?: IDepartment[],
    public jobs?: IJob[],
    public emps?: IEmployee[]
  ) {}
}
