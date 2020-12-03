import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITheLabel } from 'app/shared/model/test-root/the-label.model';

type EntityResponseType = HttpResponse<ITheLabel>;
type EntityArrayResponseType = HttpResponse<ITheLabel[]>;

@Injectable({ providedIn: 'root' })
export class TheLabelService {
  public resourceUrl = SERVER_API_URL + 'api/the-labels';

  constructor(protected http: HttpClient) {}

  create(theLabel: ITheLabel): Observable<EntityResponseType> {
    return this.http.post<ITheLabel>(this.resourceUrl, theLabel, { observe: 'response' });
  }

  update(theLabel: ITheLabel): Observable<EntityResponseType> {
    return this.http.put<ITheLabel>(this.resourceUrl, theLabel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITheLabel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITheLabel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
