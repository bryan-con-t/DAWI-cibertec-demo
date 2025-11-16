package pe.cibertec.cibertecdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.cibertecdemo.model.ListaCompra;

import java.util.List;

public interface ListaCompraRepository extends JpaRepository<ListaCompra, Long> {
    List<ListaCompra> findByUsuarioId(Long idUsuario);
}
