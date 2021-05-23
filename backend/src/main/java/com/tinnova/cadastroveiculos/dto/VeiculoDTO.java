package com.tinnova.cadastroveiculos.dto;

import com.tinnova.cadastroveiculos.entities.Veiculo;
import lombok.Data;

@Data
public class VeiculoDTO {
    private Long id;
    private String marca;
    private Integer ano;
    private String descricao;
    private Boolean vendido;

    public VeiculoDTO(Veiculo entidade) {
        id = entidade.getId();
        marca = entidade.getMarca();
        ano = entidade.getAno();
        descricao = entidade.getDescricao();
        vendido = entidade.getVendido();
    }
}
