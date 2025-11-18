package pe.cibertec.cibertecdemo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.cibertecdemo.model.ListaCompra;

import java.util.List;

public interface ListaCompraRepository extends JpaRepository<ListaCompra, Long> {
    List<ListaCompra> findByUsuarioId(Long idUsuario);

    Page<ListaCompra> findByUsuarioId(Long idUsuario, Pageable pageable);
}
