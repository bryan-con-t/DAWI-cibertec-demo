package pe.cibertec.cibertecdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.cibertecdemo.model.ItemLista;

public interface ItemListaRepository extends JpaRepository<ItemLista, Long> {
}
