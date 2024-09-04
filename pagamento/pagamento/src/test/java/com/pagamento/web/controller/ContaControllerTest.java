package com.pagamento.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.pagamento.application.ContaApplicationService;
import com.pagamento.domain.model.Conta;

@RunWith(MockitoJUnitRunner.class)
public class ContaControllerTest {

	@InjectMocks
	private ContaController contaController;

	@Mock
	private ContaApplicationService contaService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void cadastrarConta() {

		Conta conta = new Conta();
		when(contaService.cadastrarConta(conta)).thenReturn(conta);

		ResponseEntity<Conta> response = contaController.cadastrarConta(conta);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(conta, response.getBody());
	}

	@Test
	public void atualizarConta() {

		Long id = 1L;
		Conta conta = new Conta();
		when(contaService.atualizarConta(id, conta)).thenReturn(conta);

		ResponseEntity<Conta> response = contaController.atualizarConta(id, conta);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(conta, response.getBody());
	}

	@Test
	public void alterarSituacaoConta() {

		Long id = 1L;
		Conta.Situacao situacao = Conta.Situacao.PAGA;
		Conta conta = new Conta();
		when(contaService.alterarSituacaoConta(id, situacao)).thenReturn(conta);

		ResponseEntity<Conta> response = contaController.alterarSituacaoConta(id, situacao);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(conta, response.getBody());
	}

	@Test
	public void obterContasAPagar() {
		LocalDate dataVencimento = LocalDate.now();
		String descricao = "descricao";
		int pagina = 0;
		int tamanho = 10;
		Page<Conta> contas = new PageImpl<>(new ArrayList<>());
		when(contaService.obterContasAPagar(dataVencimento, descricao, PageRequest.of(pagina, tamanho)))
				.thenReturn(contas);

		ResponseEntity<Page<Conta>> response = contaController.obterContasAPagar(dataVencimento, descricao, pagina,
				tamanho);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(contas, response.getBody());
	}

	@Test
	public void obterContaPorId() {

		Long id = 1L;
		Conta conta = new Conta();
		when(contaService.obterContaPorId(id)).thenReturn(conta);

		ResponseEntity<Conta> response = contaController.obterContaPorId(id);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(conta, response.getBody());
	}

	@Test
	public void obterValorTotalPagoPorPeriodo() {

		LocalDate dataInicio = LocalDate.now();
		LocalDate dataFim = LocalDate.now();
		BigDecimal valor = BigDecimal.valueOf(100);
		when(contaService.obterValorTotalPagoPorPeriodo(dataInicio, dataFim)).thenReturn(valor);

		ResponseEntity<BigDecimal> response = contaController.obterValorTotalPagoPorPeriodo(dataInicio, dataFim);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(valor, response.getBody());
	}

	@Test
	public void importarContas() throws IOException {

		MockMultipartFile arquivo = new MockMultipartFile("arquivo", "arquivo.txt", "text/plain",
				"conteúdo".getBytes());
		List<Conta> contas = new ArrayList<>();
		when(contaService.importarContas(arquivo)).thenReturn(contas);

		ResponseEntity<List<Conta>> response = contaController.importarContas(arquivo);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(contas, response.getBody());
	}
}
