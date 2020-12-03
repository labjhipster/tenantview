import { ITask } from 'app/shared/model/task.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IJobHistory } from 'app/shared/model/job-history.model';
import { JobType } from 'app/shared/model/enumerations/job-type.model';

export interface IJob {
  id?: number;
  title?: string;
  type?: JobType;
  minSalary?: number;
  maxSalary?: number;
  chores?: ITask[];
  emp?: IEmployee;
  histories?: IJobHistory[];
}

export class Job implements IJob {
  constructor(
    public id?: number,
    public title?: string,
    public type?: JobType,
    public minSalary?: number,
    public maxSalary?: number,
    public chores?: ITask[],
    public emp?: IEmployee,
    public histories?: IJobHistory[]
  ) {}
}
