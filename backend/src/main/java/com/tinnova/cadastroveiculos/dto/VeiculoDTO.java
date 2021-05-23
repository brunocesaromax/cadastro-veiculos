package com.tinnova.cadastroveiculos.dto;

import com.tinnova.cadastroveiculos.entities.Veiculo;
import com.tinnova.cadastroveiculos.enumerated.MarcaVeiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoDTO {

    private Long id;

    @NotNull
    private MarcaVeiculo marca;

    @NotNull
    private Integer ano;

    @NotEmpty
    private String descricao;

    @NotNull
    private Boolean vendido;

    public VeiculoDTO(Veiculo entidade) {
        id = entidade.getId();
        marca = entidade.getMarca();
        ano = entidade.getAno();
        descricao = entidade.getDescricao();
        vendido = entidade.getVendido();
    }
}
