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
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";


@NgModule({
  declarations: [VehiclesSearchComponent],
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,

    VehiclesRoutingModule,

    InputTextModule,
    ButtonModule,
    TableModule,
    TooltipModule,
  ],
  providers: [VehiclesService]
})
export class VehiclesModule {
}
