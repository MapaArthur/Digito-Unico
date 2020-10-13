package com.banco.inter.desafio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banco.inter.desafio.entidade.Usuario;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {


}
