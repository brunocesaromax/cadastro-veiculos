package com.tinnova.cadastroveiculos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinnova.cadastroveiculos.entities.Veiculo;
import com.tinnova.cadastroveiculos.enumerated.MarcaVeiculo;
import com.tinnova.cadastroveiculos.services.VeiculoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CadastroVeiculosApplicationTests {

    private static final String VEICULOS_URI = "/veiculos";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private VeiculoService veiculoService;

    @Test
    void findAllVehicles() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(VEICULOS_URI)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void findAllByTerm() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(VEICULOS_URI.concat("/find"))
                .param("term", "fiat")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(VEICULOS_URI.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void dashboard() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(VEICULOS_URI.concat("/dashboard"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        Veiculo veiculo = new Veiculo();
        veiculo.setAno(2021);
        veiculo.setDescricao("Honda Civic Importado Turbo 2.0");
        veiculo.setMarca(MarcaVeiculo.HONDA);
        veiculo.setVendido(true);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(VEICULOS_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculo)))
                .andExpect(status().isCreated()).andReturn();

        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
        assertEquals("http://localhost/veiculos/6", mvcResult.getResponse().getHeaderValue(HttpHeaders.LOCATION));
    }

    @Test
    void update() throws Exception {
        Veiculo veiculo = new Veiculo();
        veiculo.setAno(2021);
        veiculo.setDescricao("Honda Civic Importado Turbo 2.0");
        veiculo.setMarca(MarcaVeiculo.HONDA);
        veiculo.setVendido(true);

        mvc.perform(MockMvcRequestBuilders.put(VEICULOS_URI.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculo)))
                .andExpect(status().isOk());
    }

    @Test
    void partiallyUpdate() throws Exception {
        Veiculo veiculo = new Veiculo();
        veiculo.setAno(2000);

        mvc.perform(MockMvcRequestBuilders.patch(VEICULOS_URI.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculo)))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(VEICULOS_URI.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
