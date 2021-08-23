import { Component, OnInit, ViewChild } from '@angular/core';
import { Veiculo } from "../Vehicle";
import { Title } from "@angular/platform-browser";
import { VehiclesService } from "../vehicles.service";
import { ConfirmationService } from "primeng/api";
import { ErrorHandlerService } from "../../error-handler.service";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-vehicles-search',
  templateUrl: './vehicles-search.component.html',
  styleUrls: ['./vehicles-search.component.scss']
})
export class VehiclesSearchComponent implements OnInit {

  veiculos: Veiculo[] = [];
  headers = ['Marca', 'Ano', 'Descrição', 'Vendido', 'Ações'];
  term = '';
  totalElements = 0;

  @ViewChild('table', {static: true}) table: any;

  constructor(private title: Title,
              private vehiclesService: VehiclesService,
              private confirmationService: ConfirmationService,
              private toastrService: ToastrService,
              private errorHandlerService: ErrorHandlerService) {
  }

  ngOnInit(): void {
    this.title.setTitle('Listagem de veículos');
    this.findAllVehicles();
  }

  findAllByTerm() {
    this.vehiclesService.findAllByTerm(this.term)
      .subscribe(resp => {
        this.totalElements = resp.length;
        this.veiculos = resp.map((veiculo: any) => Object.assign(new Veiculo(), veiculo));
      });
  }

  deleteConfirm(vehicle: any) {
    this.confirmationService.confirm({
      message: 'Tem certeza que deseja excluir?',
      accept: () => {
        this.delete(vehicle);
      }
    });
  }

  private findAllVehicles() {
    this.vehiclesService.findAll()
      .subscribe(resp => {
        this.totalElements = resp.length;
        this.veiculos = resp.map((veiculo: any) => Object.assign(new Veiculo(), veiculo));
      });
  }

  private delete(vehicle: any) {
    this.vehiclesService.delete(vehicle.id)
      .subscribe(() => {
          this.findAllByTerm();
          this.toastrService.success('Veículo excluído com sucesso!');
        },
        error => this.errorHandlerService.handle(error)
      );
  }
}
