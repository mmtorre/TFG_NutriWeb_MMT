package com.nutriweb100.repository;

//clase que vamos a utilizar para hablar con la BBDD sin SQL
import com.nutriweb100.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
//<Entidad, tipo de la private key>
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String email);
}
