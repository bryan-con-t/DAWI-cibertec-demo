package pe.cibertec.cibertecdemo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.cibertec.cibertecdemo.model.ItemLista;
import pe.cibertec.cibertecdemo.model.ListaCompra;
import pe.cibertec.cibertecdemo.repository.ItemListaRepository;
import pe.cibertec.cibertecdemo.repository.ListaCompraRepository;

import java.util.List;

@Service
public class HistorialService {

    private final ListaCompraRepository listaRepo;
    private final ItemListaRepository itemRepo;

    public HistorialService(ListaCompraRepository listaRepo, ItemListaRepository itemRepo) {
        this.listaRepo = listaRepo;
        this.itemRepo = itemRepo;
    }

    public List<ListaCompra> obtenerHistorial(Long idUsuario) {
        return listaRepo.findByUsuarioId(idUsuario);
    }

    public Page<ListaCompra> obtenerHistorialPaginado(Long idUsuario, Pageable pageable) {
        return listaRepo.findByUsuarioId(idUsuario, pageable);
    }

    public List<ItemLista> obtenerDetalle(Long idLista, String estado) {
        if (estado == null || estado.isBlank()) {
            return itemRepo.detalleLista(idLista);
        }
        return itemRepo.buscarPorestado(idLista, estado);
    }
}
