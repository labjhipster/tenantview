import { IIdentifier } from 'app/shared/model/identifier.model';

export interface IGoldenBadge {
  id?: number;
  name?: string;
  iden?: IIdentifier;
}

export class GoldenBadge implements IGoldenBadge {
  constructor(public id?: number, public name?: string, public iden?: IIdentifier) {}
}
