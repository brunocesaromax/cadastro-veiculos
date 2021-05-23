package com.tinnova.cadastroveiculos.entities;

import com.tinnova.cadastroveiculos.dto.VeiculoDTO;
import com.tinnova.cadastroveiculos.enumerated.MarcaVeiculo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "veiculos")
public class Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MarcaVeiculo marca;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Boolean vendido;

    private LocalDateTime created;

    private LocalDateTime updated;

    public Veiculo(MarcaVeiculo marca, String descricao, Integer ano) {
        this.marca = marca;
        this.descricao = descricao;
        this.ano = ano;
    }

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updated = LocalDateTime.now();
    }

    public void copyNotNullProperties(VeiculoDTO veiculoDTO) {
        if (veiculoDTO.getMarca() != null) marca = veiculoDTO.getMarca();
        if (veiculoDTO.getDescricao() != null) descricao = veiculoDTO.getDescricao();
        if (veiculoDTO.getAno() != null) ano = veiculoDTO.getAno();
        if (veiculoDTO.getVendido() != null) vendido = veiculoDTO.getVendido();
    }
}
