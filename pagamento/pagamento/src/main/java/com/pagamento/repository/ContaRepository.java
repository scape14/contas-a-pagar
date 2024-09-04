package com.pagamento.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pagamento.domain.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

	@Query("SELECT c FROM Conta c WHERE c.dataVencimento = :dataVencimento AND c.descricao = :descricao")
	Page<Conta> findByDataVencimentoAndDescricao(@Param("dataVencimento") LocalDate dataVencimento,
			@Param("descricao") String descricao, Pageable pageable);

	@Query("SELECT SUM(c.valor) FROM Conta c WHERE c.dataPagamento BETWEEN :dataInicio AND :dataFim AND c.situacao = 'PAGA'")
	BigDecimal sumValorByDataPagamentoBetween(@Param("dataInicio") LocalDate dataInicio,
			@Param("dataFim") LocalDate dataFim);
}