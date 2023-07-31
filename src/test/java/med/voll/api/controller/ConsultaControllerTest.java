package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultasService;
import med.voll.api.domain.consulta.DadosAgendamentoConsultaDTO;
import med.voll.api.domain.consulta.DadosDetalhamentoConsultaDTO;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsultaDTO> inputDto;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsultaDTO> outputDto;

    @MockBean
    private AgendaDeConsultasService agendaDeConsultasService;

    @Test
    @DisplayName("Deveria devolver código HTTP 400 quando input está inválido")
    @WithMockUser
    void cenario1() throws Exception {
        MockHttpServletResponse response = mvc.perform(post("/consultas")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver código HTTP 200 quando input está válido")
    @WithMockUser
    void cenario2() throws Exception {
        LocalDateTime data = LocalDateTime.now().plusHours(1);
        Especialidade especialidade = Especialidade.DERMATOLOGIA;
        DadosDetalhamentoConsultaDTO dadosDetalhamento = new DadosDetalhamentoConsultaDTO(
                null, 1l, 1l, data
        );

        when(this.agendaDeConsultasService.agendar(any())).thenReturn(dadosDetalhamento);

        MockHttpServletResponse response = mvc.perform(
                    post("/consultas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(this.inputDto.write(
                                    new DadosAgendamentoConsultaDTO(1l, 1l, data, especialidade)
                            ).getJson())
                )
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = this.outputDto.write(
              dadosDetalhamento
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}