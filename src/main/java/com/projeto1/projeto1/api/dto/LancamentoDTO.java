package com.projeto1.projeto1.api.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LancamentoDTO {

	private Long id;
	private String descricao;
	private Integer ano;
	private Integer mes;
	private BigDecimal valor;
	private Long usuario;
	private String tipo;
	private String status;
	
}
