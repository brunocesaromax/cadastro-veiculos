package com.tinnova.cadastroveiculos.controllers;

import com.tinnova.cadastroveiculos.dto.DashboardDTO;
import com.tinnova.cadastroveiculos.dto.VeiculoDTO;
import com.tinnova.cadastroveiculos.services.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://cadastro-veiculos-angular.herokuapp.com", maxAge = 3600)
@RequestMapping(value = "/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<List<VeiculoDTO>> findAll() {
        List<VeiculoDTO> veiculos = veiculoService.findAll();
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("find")
    public ResponseEntity<List<VeiculoDTO>> findAllByTerm(@RequestParam(required = false) String term) {
        List<VeiculoDTO> veiculos = veiculoService.findAllByTerm(term);
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("{id}")
    public ResponseEntity<VeiculoDTO> findById(@PathVariable Long id) {
        VeiculoDTO veiculoDTO = veiculoService.findById(id);
        return ResponseEntity.ok(veiculoDTO);
    }

    @GetMapping("dashboard")
    public ResponseEntity<DashboardDTO> dashboard() {
        DashboardDTO dashboardDTO = veiculoService.findDashboard();
        return ResponseEntity.ok(dashboardDTO);
    }

    @PostMapping
    public ResponseEntity<VeiculoDTO> create(@Valid @RequestBody VeiculoDTO veiculoDTO) {
        VeiculoDTO veiculoCreated = veiculoService.create(veiculoDTO);

        //Criar URI correspondente ao recurso criado
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(veiculoCreated.getId()).toUri();
        return ResponseEntity.created(uri).body(veiculoCreated);
    }

    @PutMapping("{id}")
    public ResponseEntity<VeiculoDTO> update(@PathVariable Long id, @Valid @RequestBody VeiculoDTO veiculoDTO) {
        VeiculoDTO veiculoUpdated = veiculoService.update(id, veiculoDTO);
        return ResponseEntity.ok(veiculoUpdated);
    }

    @PatchMapping("{id}")
    public ResponseEntity<VeiculoDTO> partiallyUpdate(@PathVariable Long id, @RequestBody VeiculoDTO veiculoDTO) {
        VeiculoDTO veiculoUpdated = veiculoService.partiallyUpdate(id, veiculoDTO);
        return ResponseEntity.ok(veiculoUpdated);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        veiculoService.delete(id);
    }
}
