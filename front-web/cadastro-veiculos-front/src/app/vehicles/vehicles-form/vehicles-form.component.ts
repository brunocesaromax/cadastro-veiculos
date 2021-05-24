import { Component, OnInit } from '@angular/core';
import { Veiculo } from "../Vehicle";
import { ErrorHandlerService } from "../../error-handler.service";
import { ToastyService } from "ng2-toasty";
import { ActivatedRoute, Router } from "@angular/router";
import { Title } from "@angular/platform-browser";
import { VehiclesService } from "../vehicles.service";
import { NgForm } from "@angular/forms";

@Component({
  selector: 'app-vehicles-form',
  templateUrl: './vehicles-form.component.html',
  styleUrls: ['./vehicles-form.component.scss']
})
export class VehiclesFormComponent implements OnInit {

  veiculo = new Veiculo();
  marcas: any[] = [];

  constructor(private errorHandlerService: ErrorHandlerService,
              private toastyService: ToastyService,
              private activatedRoute: ActivatedRoute,
              private vehiclesService: VehiclesService,
              private router: Router,
              private title: Title) {
  }

  get isEdit() {
    return Boolean(this.veiculo.id);
  }

  ngOnInit(): void {
    this.title.setTitle('Novo veículo');
    this.loadBrands();

    const vehicleId = this.activatedRoute.snapshot.params.id;

    if (vehicleId) {
      this.loadVehicle(vehicleId);
    }
  }

  save(vehicleForm: NgForm) {
    if (this.isEdit) {
      this.update(vehicleForm);
    } else {
      this.add(vehicleForm);
    }
  }

  update(vehicleForm: NgForm) {
    this.vehiclesService.update(this.veiculo)
      .subscribe(vehicleUpdated => {
          this.veiculo = vehicleUpdated;
          this.updateEditTitle();
          this.toastyService.success('Veículo atualizado com sucesso!');
        },
        error => this.errorHandlerService.handle(error)
      );
  }

  add(vehicleForm: NgForm) {
    this.vehiclesService.save(this.veiculo)
      .subscribe(() => {
          this.toastyService.success('Veículo adicionado com sucesso!');
          vehicleForm.reset();
          this.veiculo = new Veiculo();
        },
        error => this.errorHandlerService.handle(error)
      );
  }

  new(personForm: NgForm) {
    personForm.reset();
    this.router.navigate(['/persons/new']);
  }

  private loadBrands() {
    let brands = ['FIAT','CHEVROLET','VOLKSWAGEN','HYUNDAI','JEEP',
      'RENAULT','TOYOTA','FORD','HONDA','CITROEN'];

    this.marcas = brands.map(brand => ({label: brand, value: brand}));
  }

  private loadVehicle(vehicleId: number) {
    this.vehiclesService.findById(vehicleId)
      .subscribe(vehicle => {
          this.veiculo = vehicle;
          this.updateEditTitle();
        },
        error => this.errorHandlerService.handle(error));
  }

  private updateEditTitle() {
    this.title.setTitle(`Edição do veículo: ${this.veiculo.descricao}`);
  }
}
