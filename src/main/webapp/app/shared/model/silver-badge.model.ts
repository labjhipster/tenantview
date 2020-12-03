import { IIdentifier } from 'app/shared/model/identifier.model';

export interface ISilverBadge {
  id?: number;
  name?: string;
  iden?: IIdentifier;
}

export class SilverBadge implements ISilverBadge {
  constructor(public id?: number, public name?: string, public iden?: IIdentifier) {}
}
