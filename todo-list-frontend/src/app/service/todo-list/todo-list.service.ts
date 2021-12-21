import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ToDoEntry} from "../../entry/ToDoEntry";

@Injectable({
  providedIn: 'root'
})
export class TodoListService {

  private serviceUrl = "/todo-list-service"

  constructor(private httpClient: HttpClient) {}

  public getEntries(): Observable<ToDoEntry[]> {
    return this.httpClient.get<ToDoEntry[]>(`${this.serviceUrl}/entries`);
  }
}
