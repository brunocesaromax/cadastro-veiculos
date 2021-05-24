import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VehiclesSearchComponent } from "./vehicles-search/vehicles-search.component";
import { VehiclesRoutingModule } from "./vehicles-routing.module";
import { FormsModule } from "@angular/forms";
import { InputTextModule } from "primeng/inputtext";
import { ButtonModule } from "primeng/button";
import { TableModule } from "primeng/table";
import { TooltipModule } from "primeng/tooltip";
import { VehiclesService } from "./vehicles.service";
import { HttpClientModule } from "@angular/common/http";
import { VehiclesFormComponent } from './vehicles-form/vehicles-form.component';
import { DropdownModule } from "primeng/dropdown";
import { InputMaskModule } from "primeng/inputmask";
import { MessageComponent } from "../message/message.component";
import { CheckboxModule } from "primeng/checkbox";


@NgModule({
  declarations: [
    VehiclesSearchComponent,
    VehiclesFormComponent,
    MessageComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,

    VehiclesRoutingModule,

    InputTextModule,
    CheckboxModule,
    ButtonModule,
    TableModule,
    TooltipModule,
    DropdownModule,
    InputMaskModule
  ],
  providers: [VehiclesService]
})
export class VehiclesModule {
}
