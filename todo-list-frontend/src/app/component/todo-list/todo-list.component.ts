import { Component, OnInit } from '@angular/core';
import {TodoListService} from "../../service/todo-list/todo-list.service";
import {EMPTY, Observable} from "rxjs";

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css']
})
export class TodoListComponent implements OnInit {

  entries$: Observable<string[]>

  constructor(private todoListService: TodoListService) {
    this.entries$ = EMPTY
  }

  ngOnInit(): void {
    this.entries$ = this.todoListService.getEntries();
  }

}
