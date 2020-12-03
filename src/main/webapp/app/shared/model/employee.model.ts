import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IJob } from 'app/shared/model/job.model';
import { ISilverBadge } from 'app/shared/model/silver-badge.model';
import { IGoldenBadge } from 'app/shared/model/golden-badge.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IJobHistory } from 'app/shared/model/job-history.model';

export interface IEmployee {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  hireDate?: Moment;
  salary?: number;
  commissionPct?: number;
  user?: IUser;
  jobs?: IJob[];
  manager?: IEmployee;
  sibag?: ISilverBadge;
  gobag?: IGoldenBadge;
  department?: IDepartment;
  histories?: IJobHistory[];
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: string,
    public hireDate?: Moment,
    public salary?: number,
    public commissionPct?: number,
    public user?: IUser,
    public jobs?: IJob[],
    public manager?: IEmployee,
    public sibag?: ISilverBadge,
    public gobag?: IGoldenBadge,
    public department?: IDepartment,
    public histories?: IJobHistory[]
  ) {}
}
