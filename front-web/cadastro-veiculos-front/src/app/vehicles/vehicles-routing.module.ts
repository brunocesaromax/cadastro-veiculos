import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from "@angular/router";
import { VehiclesSearchComponent } from "./vehicles-search/vehicles-search.component";

const routes: Routes = [
  {
    path: '',
    component: VehiclesSearchComponent
  },
  // {
  //   path: 'new',
  //   component: PersonFormComponent,
  //   canActivate: [AuthGuard],
  //   data: {roles: ['ROLE_CREATE_PERSON']}
  // },
  // {
  //   path: ':id',
  //   component: PersonFormComponent,
  //   canActivate: [AuthGuard],
  //   data: {roles: ['ROLE_SEARCH_PERSON']}
  // }
];


@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class VehiclesRoutingModule {
}
