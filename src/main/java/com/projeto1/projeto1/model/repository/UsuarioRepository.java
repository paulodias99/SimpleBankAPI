package com.projeto1.projeto1.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto1.projeto1.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	boolean existsByEmail(String email);
	
	// Ao colocar 'findBy'+propriedade torna-se desnecessário utilizar 'select * from...'
	Optional<Usuario> findByEmail(String email);
	
}
