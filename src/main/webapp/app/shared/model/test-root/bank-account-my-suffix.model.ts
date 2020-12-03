import { Moment } from 'moment';
import { IOperation } from 'app/shared/model/test-root/operation.model';
import { ICompany } from 'app/shared/model/../admin/company.model';
import { IUser } from 'app/core/user/user.model';
import { BankAccountType } from 'app/shared/model/enumerations/bank-account-type.model';

export interface IBankAccountMySuffix {
  id?: number;
  name?: string;
  bankNumber?: number;
  agencyNumber?: number;
  lastOperationDuration?: number;
  meanOperationDuration?: number;
  balance?: number;
  openingDay?: Moment;
  lastOperationDate?: Moment;
  active?: boolean;
  accountType?: BankAccountType;
  attachmentContentType?: string;
  attachment?: any;
  description?: any;
  operations?: IOperation[];
  company?: ICompany;
  user?: IUser;
}

export class BankAccountMySuffix implements IBankAccountMySuffix {
  constructor(
    public id?: number,
    public name?: string,
    public bankNumber?: number,
    public agencyNumber?: number,
    public lastOperationDuration?: number,
    public meanOperationDuration?: number,
    public balance?: number,
    public openingDay?: Moment,
    public lastOperationDate?: Moment,
    public active?: boolean,
    public accountType?: BankAccountType,
    public attachmentContentType?: string,
    public attachment?: any,
    public description?: any,
    public operations?: IOperation[],
    public company?: ICompany,
    public user?: IUser
  ) {
    this.active = this.active || false;
  }
}
