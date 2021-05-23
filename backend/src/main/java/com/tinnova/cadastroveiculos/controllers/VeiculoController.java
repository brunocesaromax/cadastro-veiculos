package com.tinnova.cadastroveiculos.controllers;

import com.tinnova.cadastroveiculos.dto.VeiculoDTO;
import com.tinnova.cadastroveiculos.services.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<List<VeiculoDTO>> findAll(){
        List<VeiculoDTO> veiculos = veiculoService.findAll();
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("find")
    public ResponseEntity<List<VeiculoDTO>> findAllByTerm(@RequestParam(required = false) String term){
        List<VeiculoDTO> veiculos = veiculoService.findAllByTerm(term);
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("{id}")
    public ResponseEntity<VeiculoDTO> findById(@PathVariable Long id){
        VeiculoDTO veiculoDTO = veiculoService.findById(id);
        return ResponseEntity.ok(veiculoDTO);
    }
}
