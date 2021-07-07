import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TodoListService {

  private serviceUrl = "/todo-list-service"

  constructor(private httpClient: HttpClient) {}

  public getEntries(): Observable<string[]> {
    return this.httpClient.get<string[]>(`${this.serviceUrl}/entries`);
  }
}
