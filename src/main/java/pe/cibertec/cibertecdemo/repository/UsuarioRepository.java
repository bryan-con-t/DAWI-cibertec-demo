package pe.cibertec.cibertecdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.cibertecdemo.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
