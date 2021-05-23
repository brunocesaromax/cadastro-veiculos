package com.tinnova.cadastroveiculos.services;

import com.tinnova.cadastroveiculos.dto.VeiculoDTO;
import com.tinnova.cadastroveiculos.exception.NotFoundException;
import com.tinnova.cadastroveiculos.repositories.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository repository;

    @Transactional(readOnly = true)
    public List<VeiculoDTO> findAll() {
        return repository.findAll().stream().map(VeiculoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VeiculoDTO> findAllByTerm(String term) {
        return repository.findAllByTerm(term).stream().map(VeiculoDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VeiculoDTO findById(Long id) {
        return repository.findById(id).map(VeiculoDTO::new)
                .orElseThrow(() -> new NotFoundException("Veículo não encontrado."));
    }
}
