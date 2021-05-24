import { NgModule } from '@angular/core';
import { RouterModule, Routes } from "@angular/router";
import { VehiclesSearchComponent } from "./vehicles-search/vehicles-search.component";
import { VehiclesFormComponent } from "./vehicles-form/vehicles-form.component";

const routes: Routes = [
  {
    path: '',
    component: VehiclesSearchComponent
  },
  {
    path: 'new',
    component: VehiclesFormComponent
  },
  {
    path: ':id',
    component: VehiclesFormComponent
  }
];


@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule]
})
export class VehiclesRoutingModule {
}
