package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.dto.DadosEnderecoDto;
import med.voll.api.domain.medico.dto.CadastrarMedicoDto;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.dto.CadastrarPacienteDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Deveria devolver null quando o único médico cadastrado não está disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario1() {
        // given ou arrange
        LocalDateTime proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
        Paciente paciente = cadastrarPaciente("Maria", "maria@gmail.com", "12345678910");
        Medico medico = cadastrarMedico("João", "joao@voll.med", "123456", Especialidade.DERMATOLOGIA);
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        // when ou act
        Medico medicoLivre = this.medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.DERMATOLOGIA, proximaSegundaAs10);

        // then ou assert
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponível na data")
    void escolherMedicoAleatorioLivreNaDataCenario2() {
        Medico medico = cadastrarMedico("João", "joao@voll.med", "123456", Especialidade.DERMATOLOGIA);

        LocalDateTime proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        Medico medicoLivre = this.medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.DERMATOLOGIA, proximaSegundaAs10);

        assertThat(medicoLivre).isEqualTo(medico);
    }

    private Consulta cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        Consulta consulta = new Consulta(null, medico, paciente, data);

        entityManager.persist(consulta);

        return consulta;
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        Medico medico = new Medico(dadosMedico(nome, email, crm, especialidade));

        entityManager.persist(medico);

        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        Paciente paciente = new Paciente(dadosPaciente(nome, email, cpf));

        entityManager.persist(paciente);

        return paciente;
    }

    private CadastrarMedicoDto dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new CadastrarMedicoDto(
          nome,
          email,
          "1999999999",
          crm,
          especialidade, dadosEndereco()
        );
    }
    private CadastrarPacienteDto dadosPaciente(String nome, String email, String cpf) {
        return new CadastrarPacienteDto(
                nome,
                email,
                "19999999999",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEnderecoDto dadosEndereco() {
        return new DadosEnderecoDto(
                "rua a",
                "1",
                "bairro",
            "SJBV",
                "SP",
                null,
                "13982-772"
        );
    }
}