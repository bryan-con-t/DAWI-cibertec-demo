package pe.cibertec.cibertecdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.cibertecdemo.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
}
