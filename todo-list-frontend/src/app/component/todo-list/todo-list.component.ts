import { Component, OnInit } from '@angular/core';
import {TodoListService} from "../../service/todo-list/todo-list.service";
import {EMPTY, Observable} from "rxjs";
import {ToDoEntry} from "../../entry/ToDoEntry";

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css']
})
export class TodoListComponent implements OnInit {

  entries$: Observable<ToDoEntry[]>

  constructor(private todoListService: TodoListService) {
    this.entries$ = EMPTY
  }

  ngOnInit(): void {
    this.entries$ = this.todoListService.getEntries();
  }

}
