import { ICountry } from 'app/shared/model/country.model';

export interface IRegion {
  id?: number;
  name?: string;
  country?: ICountry;
}

export class Region implements IRegion {
  constructor(public id?: number, public name?: string, public country?: ICountry) {}
}
