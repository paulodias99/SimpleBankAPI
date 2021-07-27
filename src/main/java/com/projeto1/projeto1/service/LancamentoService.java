package com.projeto1.projeto1.service;

import com.projeto1.projeto1.model.entity.Lancamento;
import com.projeto1.projeto1.model.enums.StatusLancamento;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LancamentoService {

	Lancamento salvar(Lancamento lancamento);
	Lancamento atualizar(Lancamento lancamento);	
	void deletar(Lancamento lancamento);
	List<Lancamento> buscar( Lancamento lancamentofiltro );
	void atualizarStatus(Lancamento lancamento, StatusLancamento status);
	void validar(Lancamento lancamento);
	Optional<Lancamento> obterPorId(Long id);
	BigDecimal obterSaldoPorUsuario(Long id);
}
