package com.tinnova.cadastroveiculos.services;

import com.tinnova.cadastroveiculos.dto.VeiculoDTO;
import com.tinnova.cadastroveiculos.entities.Veiculo;
import com.tinnova.cadastroveiculos.exception.NotFoundException;
import com.tinnova.cadastroveiculos.repositories.VeiculoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;
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

    @Transactional
    public VeiculoDTO create(VeiculoDTO veiculoDTO) {
        checkIfExistsToCreate(veiculoDTO);
        Veiculo veiculo = new Veiculo();
        BeanUtils.copyProperties(veiculoDTO, veiculo, "id");
        veiculo = repository.save(veiculo);
        return new VeiculoDTO(veiculo);
    }

    @Transactional
    public VeiculoDTO update(Long id, VeiculoDTO veiculoDTO) {
        Veiculo veiculoPersisted = repository.findById(id).orElseThrow(() -> new NotFoundException("Veículo não encontrado."));
        checkIfExistsToUpdate(id, veiculoDTO);
        BeanUtils.copyProperties(veiculoDTO, veiculoPersisted, "id");
        veiculoPersisted = repository.save(veiculoPersisted);
        return new VeiculoDTO(veiculoPersisted);
    }

    @Transactional
    public VeiculoDTO partiallyUpdate(Long id, VeiculoDTO veiculoDTO) {
        Veiculo veiculoPersisted = repository.findById(id).orElseThrow(() -> new NotFoundException("Veículo não encontrado."));

        /*Necessário usar variavel de veiculo temporario afim de evitar erro de duplicação de registros no BD no metodo checkIfExistsToUpdate*/
        Veiculo veiculoTemp = new Veiculo(veiculoPersisted.getMarca(), veiculoPersisted.getDescricao(), veiculoPersisted.getAno());
        veiculoTemp.copyNotNullProperties(veiculoDTO);
        checkIfExistsToUpdate(id, new VeiculoDTO(veiculoTemp));

        veiculoPersisted.copyNotNullProperties(veiculoDTO);
        veiculoPersisted = repository.save(veiculoPersisted);
        return new VeiculoDTO(veiculoPersisted);
    }

    @Transactional
    public void delete(Long id) {
        Veiculo veiculoPersisted = repository.findById(id).orElseThrow(() -> new NotFoundException("Veículo não encontrado."));
        repository.delete(veiculoPersisted);
    }

    private void checkIfExistsToCreate(VeiculoDTO veiculoDTO) {
        boolean veiculoExists = repository.findByMainVeiculoInfo(veiculoDTO.getMarca(), veiculoDTO.getDescricao(), veiculoDTO.getAno()).isPresent();

        if (veiculoExists) {
            throw new EntityExistsException("Já existe um veiculo com essas informações.");
        }
    }

    private void checkIfExistsToUpdate(Long id, VeiculoDTO veiculoDTO) {
        Optional<Veiculo> veiculoOptional = repository.findByMainVeiculoInfo(veiculoDTO.getMarca(), veiculoDTO.getDescricao(), veiculoDTO.getAno());

        if (veiculoOptional.isPresent()){
            Veiculo veiculo = veiculoOptional.get();

            if (!veiculo.getId().equals(id)) {
                throw new EntityExistsException("Já existe um veiculo com essas informações.");
            }
        }
    }
}
