package com.projeto1.projeto1.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.projeto1.projeto1.model.entity.Usuario;
import com.projeto1.projeto1.model.repository.UsuarioRepository;
import com.projeto1.projeto1.service.exception.ErroAutenticacao;
import com.projeto1.projeto1.service.exception.RegraNegocioException;
import com.projeto1.projeto1.service.impl.UsuarioServiceImpl;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@SpyBean
	UsuarioServiceImpl service;
	
	@MockBean
	UsuarioRepository repository;
	
	
	@Test
	public void salvarUsuario() {
		//cenario
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		
		Usuario usuario = Usuario
				.builder()
				.id(1l)
				.nome("nome")
				.email("usuarioteste@teste.com")
				.senha("senha")
				.build();
		
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		//execução
		Usuario usuarioSalvo = service.cadastrarUsuario(new Usuario());
		
		//verificação
		Assertions.assertThat(usuarioSalvo).isNotNull();
		Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
		Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("usuarioteste@teste.com");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
		
	}
	
	public void naoDeveSalvarUsuarioComEmailJaCadastrado() {
		//cenario
		String email = "usuarioteste@teste.com";
		Usuario usuario = Usuario.builder().email(email).build();		
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		
		//execução
		org.junit.jupiter.api.Assertions.assertThrows(RegraNegocioException.class,() -> service.cadastrarUsuario(usuario));
		
		//verificação
		Mockito.verify(repository, Mockito.never()).save(usuario);
		
		
	}
	
	@Test
	public void deveAutenticarUmUsuarioComSucesso() {
		//cenario
		String email = "usuarioteste@teste.com";
		String senha = "senha";
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		//execução
		Usuario result = service.autenticar(email, senha);
		
		//verificação
		Assertions.assertThat(result).isNotNull();
		
	}
	
	@Test
	public void lancarErroNaoEncontrarUsuarioCadastradoComEmailInformado() {
		//cenario
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		//verificação
		
		Throwable exception = Assertions.catchThrowable( () -> service.autenticar("usuarioteste@teste.com", "senha"));
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Usuário não encontrado para o email informado.");
	}
	
	@Test
	public void lancarErroQuandoSenhaForIncorreta() {
		//cenario
		String senha = "senha";
		Usuario usuario = Usuario.builder().email("usuarioteste@teste.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		//verificação
		Throwable exception = Assertions.catchThrowable( () -> service.autenticar("usuarioteste@teste.com", "senhaincorreta"));
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha incorreta para o email informado.");
	}
	
	@Test
	public void deveValidarEmail() {
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false); //passando qualquer string
			
		//execução
		service.validarEmail("usuarioteste@teste.com");
	}
	
	@Test
	public void erroValidacaoEmail() {
		
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true); 
		
		//execução
		org.junit.jupiter.api.Assertions.assertThrows(RegraNegocioException.class,() -> service.validarEmail("usuarioteste@teste.com"));
	}
	
}
