package pe.cibertec.cibertecdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.cibertecdemo.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
