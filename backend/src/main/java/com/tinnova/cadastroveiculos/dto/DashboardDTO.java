package com.tinnova.cadastroveiculos.dto;

import com.tinnova.cadastroveiculos.enumerated.MarcaVeiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDTO {

    private Long totalUnsoldVehicles;
    private Long totalRegisteredLastWeek;
    private List<VehiclePerDecadeDTO> vehiclePerDecadeList;
    private List<VehiclePerBrandDTO> vehiclePerBrandList;

    @AllArgsConstructor
    @Getter
    public static class VehiclePerDecadeDTO {
        private Long total;
        private Integer decade;
    }

    @AllArgsConstructor
    @Getter
    public static class VehiclePerBrandDTO {
        private Long total;
        private MarcaVeiculo brand;
    }
}
