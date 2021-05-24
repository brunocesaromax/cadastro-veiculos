package com.tinnova.cadastroveiculos.services;

import com.tinnova.cadastroveiculos.dto.DashboardDTO;
import com.tinnova.cadastroveiculos.dto.VeiculoDTO;
import com.tinnova.cadastroveiculos.entities.Veiculo;
import com.tinnova.cadastroveiculos.enumerated.MarcaVeiculo;
import com.tinnova.cadastroveiculos.exception.NotFoundException;
import com.tinnova.cadastroveiculos.repositories.VeiculoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Transactional(readOnly = true)
    public DashboardDTO findDashboard() {
        DashboardDTO dashboardDTO = new DashboardDTO();
        List<Veiculo> veiculos = repository.findAll();

        long totalUnsoldVehicles = veiculos.stream().filter(veiculo -> !veiculo.getVendido()).count();
        long totalRegisteredLastWeek = veiculos.stream().filter(veiculo -> veiculo.getCreated().isAfter(LocalDateTime.now().minusWeeks(1L))).count();
        List<DashboardDTO.VehiclePerBrandDTO> vehiclePerBrandDTOList = orderVehiclesByBrand(veiculos);
        List<DashboardDTO.VehiclePerDecadeDTO> vehiclePerDecadeDTOList = orderVehiclesByDecade(veiculos);

        dashboardDTO.setTotalUnsoldVehicles(totalUnsoldVehicles);
        dashboardDTO.setTotalRegisteredLastWeek(totalRegisteredLastWeek);
        dashboardDTO.setVehiclePerBrandList(vehiclePerBrandDTOList);
        dashboardDTO.setVehiclePerDecadeList(vehiclePerDecadeDTOList);

        return dashboardDTO;
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

    private List<DashboardDTO.VehiclePerDecadeDTO> orderVehiclesByDecade(List<Veiculo> veiculos) {
        List<DashboardDTO.VehiclePerDecadeDTO> vehiclePerDecadeDTOList = new ArrayList<>();
        int initialLimit = 1900;
        int finalLimit = LocalDate.now().getYear();

        for (int i = initialLimit; i < finalLimit; i += 10) {
            int finalCurrentDecade = i + 10 - 1; //Ex: Década de 90 [1990 - 1999]
            long total = veiculos.stream()
                    .filter(veiculo -> veiculo.getAno() >= initialLimit && veiculo.getAno() <= finalCurrentDecade)
                    .count();

            DashboardDTO.VehiclePerDecadeDTO vehiclePerDecadeDTO = new DashboardDTO.VehiclePerDecadeDTO(total, i);
            vehiclePerDecadeDTOList.add(vehiclePerDecadeDTO);
        }

        return vehiclePerDecadeDTOList;
    }

    private List<DashboardDTO.VehiclePerBrandDTO> orderVehiclesByBrand(List<Veiculo> veiculos) {
        List<DashboardDTO.VehiclePerBrandDTO> vehiclePerBrandDTOList = new ArrayList<>();

        Arrays.asList(MarcaVeiculo.values()).forEach(brand -> {
            long total = veiculos.stream().filter(veiculo -> veiculo.getMarca().equals(brand)).count();
            DashboardDTO.VehiclePerBrandDTO vehiclePerBrandDTO = new DashboardDTO.VehiclePerBrandDTO(total, brand);
            vehiclePerBrandDTOList.add(vehiclePerBrandDTO);
        });

        return vehiclePerBrandDTOList;
    }

    private void checkIfExistsToCreate(VeiculoDTO veiculoDTO) {
        boolean veiculoExists = repository.findByMainVeiculoInfo(veiculoDTO.getMarca(), veiculoDTO.getDescricao(), veiculoDTO.getAno()).isPresent();

        if (veiculoExists) {
            throw new EntityExistsException("Já existe um veiculo com essas informações.");
        }
    }

    private void checkIfExistsToUpdate(Long id, VeiculoDTO veiculoDTO) {
        Optional<Veiculo> veiculoOptional = repository.findByMainVeiculoInfo(veiculoDTO.getMarca(), veiculoDTO.getDescricao(), veiculoDTO.getAno());

        if (veiculoOptional.isPresent()) {
            Veiculo veiculo = veiculoOptional.get();

            if (!veiculo.getId().equals(id)) {
                throw new EntityExistsException("Já existe um veiculo com essas informações.");
            }
        }
    }
}
