import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from "@angular/common/http";
import { environment } from "../../environments/environment";
import { Observable } from "rxjs";
import { Veiculo } from "./Vehicle";

@Injectable()
export class VehiclesService {

  vehiclesUrl: string;

  constructor(private httpClient: HttpClient) {
    this.vehiclesUrl = `${environment.apiUrl}/veiculos`;
  }

  findAll(): Observable<any> {
    return this.httpClient.get(`${this.vehiclesUrl}`);
  }

  findAllByTerm(term: string): Observable<any> {
    let params = new HttpParams();
    params = params.set('term', term);

    return this.httpClient.get(`${this.vehiclesUrl}/find`, {params});
  }

  findById(id: number): Observable<any> {
    return this.httpClient.get(`${this.vehiclesUrl}/${id}`);
  }

  delete(id: number): Observable<any> {
    return this.httpClient.delete(`${this.vehiclesUrl}/${id}`);
  }

  save(veiculo: Veiculo): Observable<any> {
    return this.httpClient.post(this.vehiclesUrl, veiculo);
  }

  update(veiculo: Veiculo): Observable<any> {
    return this.httpClient.put(`${this.vehiclesUrl}/${veiculo.id}`, veiculo);
  }
}
