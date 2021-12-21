import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {TodoListComponent} from "./component/todo-list/todo-list.component";
import {AuthGuard} from "./guard/auth.guard";

const routes: Routes = [
  {
    path: '',
    component: TodoListComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
