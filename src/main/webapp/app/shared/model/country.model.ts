import { IRegion } from 'app/shared/model/region.model';
import { ILocation } from 'app/shared/model/location.model';

export interface ICountry {
  id?: number;
  name?: string;
  areas?: IRegion[];
  location?: ILocation;
}

export class Country implements ICountry {
  constructor(public id?: number, public name?: string, public areas?: IRegion[], public location?: ILocation) {}
}
