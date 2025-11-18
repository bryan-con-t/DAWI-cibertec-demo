package pe.cibertec.cibertecdemo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.cibertec.cibertecdemo.model.Producto;
import pe.cibertec.cibertecdemo.repository.ProductoRepository;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository productoRepo;

    @PersistenceContext
    private EntityManager em;

    public ProductoService(ProductoRepository productoRepo) {
        this.productoRepo = productoRepo;
    }

    // Inserción por lotes (batch insert)
    @Transactional
    public void registrarLote(List<Producto> productos) {
        int i = 0;
        for (Producto producto : productos) {
            em.persist(producto);
            i++;
            if (i % 10 == 0) { // Cada 10 inserciones
                em.flush();
                em.clear();
            }
        }
    }

    // Fetching con Entity Graph (solo nombre y precio) JPQL
    public List<Producto> listarTodos() {
        return em.createQuery("SELECT p FROM Producto p", Producto.class)
                .setHint("org.hibernate.fetchSize", 5)
                .getResultList();
    }

    //Buscar productos + paginado

}
