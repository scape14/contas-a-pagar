package com.pagamento.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.pagamento.domain.model.Conta;

public interface ContaService {
	Conta cadastrarConta(Conta conta);

	Conta atualizarConta(Long id, Conta conta);

	Conta alterarSituacaoConta(Long id, Conta.Situacao situacao);

	BigDecimal obterValorTotalPagoPorPeriodo(LocalDate dataInicio, LocalDate dataFim);

	Page<Conta> obterContasAPagar(LocalDate dataVencimento, String descricao, Pageable pageable);

	Conta obterContaPorId(Long id);

	List<Conta> importarContas(MultipartFile arquivo);
}