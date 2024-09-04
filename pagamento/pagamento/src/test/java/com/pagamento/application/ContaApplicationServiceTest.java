package com.pagamento.application;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.pagamento.domain.model.Conta;
import com.pagamento.repository.ContaRepository;

class ContaApplicationServiceTest {

	@Mock
	private ContaRepository contaRepository;

	@InjectMocks
	private ContaApplicationService contaApplicationService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCadastrarConta() {
		Conta conta = new Conta();
		when(contaRepository.save(conta)).thenReturn(conta);

		Conta result = contaApplicationService.cadastrarConta(conta);

		assertEquals(conta, result);
		verify(contaRepository, times(1)).save(conta);
	}

	@Test
	void testAtualizarConta() {
		Long id = 1L;
		Conta contaExistente = new Conta();
		Conta contaAtualizada = new Conta();
		when(contaRepository.findById(id)).thenReturn(Optional.of(contaExistente));
		when(contaRepository.save(contaExistente)).thenReturn(contaExistente);

		Conta result = contaApplicationService.atualizarConta(id, contaAtualizada);

		assertEquals(contaExistente, result);
		verify(contaRepository, times(1)).findById(id);
		verify(contaRepository, times(1)).save(contaExistente);
	}

	@Test
	void testAlterarSituacaoConta() {
		Long id = 1L;
		Conta contaExistente = new Conta();
		Conta.Situacao situacao = Conta.Situacao.PAGA;
		when(contaRepository.findById(id)).thenReturn(Optional.of(contaExistente));
		when(contaRepository.save(contaExistente)).thenReturn(contaExistente);

		Conta result = contaApplicationService.alterarSituacaoConta(id, situacao);

		assertEquals(contaExistente, result);
		verify(contaRepository, times(1)).findById(id);
		verify(contaRepository, times(1)).save(contaExistente);
	}

	@Test
	void testObterContasAPagar() {
		LocalDate dataVencimento = LocalDate.now();
		String descricao = "Descricao";
		Pageable pageable = Pageable.unpaged();
		Page<Conta> paginaContas = new PageImpl<>(new ArrayList<>());
		when(contaRepository.findByDataVencimentoAndDescricao(dataVencimento, descricao, pageable))
				.thenReturn(paginaContas);

		Page<Conta> result = contaApplicationService.obterContasAPagar(dataVencimento, descricao, pageable);

		assertEquals(paginaContas, result);
		verify(contaRepository, times(1)).findByDataVencimentoAndDescricao(dataVencimento, descricao, pageable);
	}

	@Test
	void testObterContaPorId() {
		Long id = 1L;
		Conta conta = new Conta();
		when(contaRepository.findById(id)).thenReturn(Optional.of(conta));

		Conta result = contaApplicationService.obterContaPorId(id);

		assertEquals(conta, result);
		verify(contaRepository, times(1)).findById(id);
	}

	@Test
	void testObterValorTotalPagoPorPeriodo() {
		LocalDate dataInicio = LocalDate.now().minusMonths(1);
		LocalDate dataFim = LocalDate.now();
		BigDecimal valorTotal = BigDecimal.valueOf(100.00);
		when(contaRepository.sumValorByDataPagamentoBetween(dataInicio, dataFim)).thenReturn(valorTotal);

		BigDecimal result = contaApplicationService.obterValorTotalPagoPorPeriodo(dataInicio, dataFim);

		assertEquals(valorTotal, result);
		verify(contaRepository, times(1)).sumValorByDataPagamentoBetween(dataInicio, dataFim);
	}

	@Test
	ResponseEntity<List<Conta>> testImportarContas() throws IOException {
		InputStream resourceStream = getClass().getResourceAsStream("/test-file.csv");
		if (resourceStream == null) {
			throw new IllegalStateException("Resource not found: test-file.csv");
		}
		byte[] fileBytes = resourceStream.readAllBytes();

		MultipartFile arquivo = mock(MultipartFile.class);
		when(arquivo.getInputStream()).thenReturn(new ByteArrayInputStream(fileBytes));

		List<Conta> contas = new ArrayList<>();
		Conta conta = new Conta();
		conta.setDataVencimento(LocalDate.parse("01/09/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		conta.setValor(BigDecimal.valueOf(50.00));
		conta.setDescricao("Descricao Teste");
		conta.setSituacao(Conta.Situacao.PENDENTE);
		conta.setDataPagamento(LocalDate.parse("01/09/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		contas.add(conta);

		when(contaRepository.saveAll(contas)).thenReturn(contas);

		List<Conta> result = contaApplicationService.importarContas(arquivo);

		assertEquals(contas, result);
		verify(contaRepository, times(1)).saveAll(contas);

		return ResponseEntity.ok(contas);
	}
}
