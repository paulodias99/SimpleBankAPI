package com.projeto1.projeto1.api.dto;

import lombok.Data;
import lombok.Builder;

@Builder
@Data
public class UsuarioDTO {

	private String nome;
	private String email;
	private String senha;
}
