import { Moment } from 'moment';
import { ITheLabel } from 'app/shared/model/test-root/the-label.model';
import { IBankAccountMySuffix } from 'app/shared/model/test-root/bank-account-my-suffix.model';

export interface IOperation {
  id?: number;
  date?: Moment;
  description?: string;
  amount?: number;
  theLabels?: ITheLabel[];
  bankAccount?: IBankAccountMySuffix;
}

export class Operation implements IOperation {
  constructor(
    public id?: number,
    public date?: Moment,
    public description?: string,
    public amount?: number,
    public theLabels?: ITheLabel[],
    public bankAccount?: IBankAccountMySuffix
  ) {}
}
