package com.projeto1.projeto1.model.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projeto1.projeto1.model.entity.Lancamento;
import com.projeto1.projeto1.model.enums.StatusLancamento;
import com.projeto1.projeto1.model.enums.TipoLancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
	Optional<Lancamento> findById(Long id);
	
	@Query(value = " select sum(l.valor) from Lancamento l join l.usuario u where u.id = :idUsuario and l.tipo =:tipo and l.status =:status group by u")
	BigDecimal obterSaldoPorTipoDeLancamentoEUsuarioEStatus(@Param("idUsuario") Long idUsuario, @Param("tipo") TipoLancamento tipo, @Param("status") StatusLancamento status);
}
