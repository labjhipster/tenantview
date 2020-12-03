import { ILocation } from 'app/shared/model/location.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IJobHistory } from 'app/shared/model/job-history.model';

export interface IDepartment {
  id?: number;
  name?: string;
  description?: any;
  advertisementContentType?: string;
  advertisement?: any;
  logoContentType?: string;
  logo?: any;
  location?: ILocation;
  employees?: IEmployee[];
  histories?: IJobHistory[];
}

export class Department implements IDepartment {
  constructor(
    public id?: number,
    public name?: string,
    public description?: any,
    public advertisementContentType?: string,
    public advertisement?: any,
    public logoContentType?: string,
    public logo?: any,
    public location?: ILocation,
    public employees?: IEmployee[],
    public histories?: IJobHistory[]
  ) {}
}
