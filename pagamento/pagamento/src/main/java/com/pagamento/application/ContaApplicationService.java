package com.pagamento.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pagamento.domain.model.Conta;
import com.pagamento.repository.ContaRepository;

@Service
public class ContaApplicationService {

	@Autowired
	private ContaRepository contaRepository;

	public Conta cadastrarConta(Conta conta) {
		return contaRepository.save(conta);
	}

	public Conta atualizarConta(Long id, Conta conta) {
		Conta contaExistente = contaRepository.findById(id).orElseThrow();
		contaExistente.setDataVencimento(conta.getDataVencimento());
		contaExistente.setValor(conta.getValor());
		contaExistente.setDescricao(conta.getDescricao());
		return contaRepository.save(contaExistente);
	}

	public Conta alterarSituacaoConta(Long id, Conta.Situacao situacao) {
		Conta contaExistente = contaRepository.findById(id).orElseThrow();
		contaExistente.setSituacao(situacao);
		return contaRepository.save(contaExistente);
	}

	public Page<Conta> obterContasAPagar(LocalDate dataVencimento, String descricao, Pageable pageable) {
		return contaRepository.findByDataVencimentoAndDescricao(dataVencimento, descricao, pageable);
	}

	public Conta obterContaPorId(Long id) {
		return contaRepository.findById(id).orElseThrow();
	}

	public BigDecimal obterValorTotalPagoPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
		return contaRepository.sumValorByDataPagamentoBetween(dataInicio, dataFim);
	}

	public List<Conta> importarContas(MultipartFile arquivo) throws IOException {
		List<Conta> contas = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(arquivo.getInputStream()));
		String linha;
		while ((linha = reader.readLine()) != null) {
			String[] campos = linha.split(",");
			if (campos.length == 5) {
				Conta conta = new Conta();
				conta.setDataVencimento(LocalDate.parse(campos[0], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				conta.setValor(new BigDecimal(campos[1]));
				conta.setDescricao(campos[2]);
				conta.setSituacao(Conta.Situacao.valueOf(campos[3]));
				conta.setDataPagamento(LocalDate.parse(campos[4], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				contas.add(conta);
			}
		}
		return contaRepository.saveAll(contas);
	}
}