package com.pagamento.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pagamento.application.ContaApplicationService;
import com.pagamento.domain.model.Conta;

@Controller
@RequestMapping("/contas")
public class ContaController {

	@Autowired
	private ContaApplicationService contaService;

	@PostMapping
	public ResponseEntity<Conta> cadastrarConta(@RequestBody Conta conta) {
		return ResponseEntity.ok(contaService.cadastrarConta(conta));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Conta> atualizarConta(@PathVariable Long id, @RequestBody Conta conta) {
		return ResponseEntity.ok(contaService.atualizarConta(id, conta));
	}

	@PatchMapping("/{id}/situacao")
	public ResponseEntity<Conta> alterarSituacaoConta(@PathVariable Long id, @RequestBody Conta.Situacao situacao) {
		return ResponseEntity.ok(contaService.alterarSituacaoConta(id, situacao));
	}

	@GetMapping("/a-pagar")
	public ResponseEntity<Page<Conta>> obterContasAPagar(@RequestParam(required = false) LocalDate dataVencimento,
			@RequestParam(required = false) String descricao, @RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "10") int tamanho) {
		Pageable pageable = PageRequest.of(pagina, tamanho);
		return ResponseEntity.ok(contaService.obterContasAPagar(dataVencimento, descricao, pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Conta> obterContaPorId(@PathVariable Long id) {
		return ResponseEntity.ok(contaService.obterContaPorId(id));
	}

	@GetMapping("/valor-total-pago")
	public ResponseEntity<BigDecimal> obterValorTotalPagoPorPeriodo(@RequestParam LocalDate dataInicio,
			@RequestParam LocalDate dataFim) {
		return ResponseEntity.ok(contaService.obterValorTotalPagoPorPeriodo(dataInicio, dataFim));
	}

	@PostMapping("/importar")
	public ResponseEntity<List<Conta>> importarContas(@RequestParam("arquivo") MultipartFile arquivo)
			throws IOException {
		List<Conta> contas = contaService.importarContas(arquivo);
		return ResponseEntity.status(HttpStatus.CREATED).body(contas);
	}
}