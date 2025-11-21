package pe.cibertec.cibertecdemo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.cibertec.cibertecdemo.model.Producto;
import pe.cibertec.cibertecdemo.repository.ProductoRepository;
import pe.cibertec.cibertecdemo.util.FormatoUtil;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository productoRepo;
    private final FormatoUtil formatoUtil;

    @PersistenceContext
    private EntityManager em;

    public ProductoService(ProductoRepository productoRepo, FormatoUtil formatoUtil) {
        this.productoRepo = productoRepo;
        this.formatoUtil = formatoUtil;
    }

    // Inserción por lotes (batch insert)
    @Transactional
    public void registrarLote(List<Producto> productos) {
        int i = 0;
        for (Producto producto : productos) {
            producto.setNombre(formatoUtil.capitalizar(producto.getNombre()));
            em.persist(producto);
            i++;
            if (i % 10 == 0) { // Cada 10 inserciones
                em.flush();
                em.clear();
            }
        }
        em.flush();
        em.clear();
    }

    // Fetching con Entity Graph (solo nombre y precio) JPQL
    public List<Producto> listarTodos() {
        return em.createQuery("SELECT p FROM Producto p", Producto.class)
                .setHint("org.hibernate.fetchSize", 5)
                .getResultList();
    }

    //Buscar productos + paginado
    public Page<Producto> buscarPorNombre(String texto, Pageable pageable) {
        if (texto == null || texto.trim().isEmpty()) {
            return productoRepo.findAll(pageable);
        }
        return productoRepo.findByNombreContainingIgnoreCase(texto.trim(), pageable);
    }
}
