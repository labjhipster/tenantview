import { IOperation } from 'app/shared/model/test-root/operation.model';

export interface ITheLabel {
  id?: number;
  labelName?: string;
  operations?: IOperation[];
}

export class TheLabel implements ITheLabel {
  constructor(public id?: number, public labelName?: string, public operations?: IOperation[]) {}
}
