import { IUser } from 'app/core/user/user.model';

export interface ICompany {
  id?: number;
  name?: string;
  idName?: string;
  users?: IUser[];
}

export class Company implements ICompany {
  constructor(public id?: number, public name?: string, public idName?: string, public users?: IUser[]) {}
}
