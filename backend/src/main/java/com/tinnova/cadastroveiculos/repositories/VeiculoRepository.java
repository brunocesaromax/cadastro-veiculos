package com.tinnova.cadastroveiculos.repositories;

import com.tinnova.cadastroveiculos.entities.Veiculo;
import com.tinnova.cadastroveiculos.enumerated.MarcaVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    @Query("select v from Veiculo v " +
           "where :term is null or (lower(v.marca) like lower(concat('%', :term, '%')) " +
           "or lower(v.descricao) like lower(concat('%', :term, '%'))) " +
           "order by v.marca, v.descricao")
    List<Veiculo> findAllByTerm(String term);

    @Query("select v from Veiculo v " +
           "where v.marca = :marca and v.descricao = :descricao and v.ano = :ano ")
    Optional<Veiculo> findByMainVeiculoInfo(MarcaVeiculo marca, String descricao, Integer ano);
}
