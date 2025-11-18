package pe.cibertec.cibertecdemo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.cibertecdemo.model.ItemLista;
import pe.cibertec.cibertecdemo.model.ListaCompra;
import pe.cibertec.cibertecdemo.model.Usuario;
import pe.cibertec.cibertecdemo.repository.ItemListaRepository;
import pe.cibertec.cibertecdemo.repository.ListaCompraRepository;
import pe.cibertec.cibertecdemo.repository.UsuarioRepository;

import java.util.List;

@RestController
@RequestMapping("/api/listas")
public class ListaCompraController {
    private final ListaCompraRepository listaRepo;
    private final ItemListaRepository itemRepo;
    private final UsuarioRepository usuarioRepo;

    public ListaCompraController(ListaCompraRepository listaRepo, ItemListaRepository itemRepo, UsuarioRepository usuarioRepo) {
        this.listaRepo = listaRepo;
        this.itemRepo = itemRepo;
        this.usuarioRepo = usuarioRepo;
    }

    // Crear lista de compras
    @PostMapping("/{idUsuario}/crear")
    public ResponseEntity<?> crear(@PathVariable Long idUsuario, @RequestBody ListaCompra listaCompra) {
        Usuario usuario = usuarioRepo.findById(idUsuario).orElse(null);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }
        listaCompra.setUsuario(usuario);
        return ResponseEntity.ok(listaRepo.save(listaCompra));
    }

    // Agregar item a lista de compra
    @PostMapping("/{idLista}/agregar-item")
    public ResponseEntity<?> agregarItem(@PathVariable Long idLista, @RequestBody ItemLista itemLista) {
        ListaCompra listaCompra = listaRepo.findById(idLista).orElse(null);
        if (listaCompra == null) {
            return ResponseEntity.notFound().build();
        }
        itemLista.setLista(listaCompra);
        return ResponseEntity.ok(itemRepo.save(itemLista));
    }

    // Cambiar estado de item de lista
    @PutMapping("/item/{idItem}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long idItem, @RequestParam String estado) {
        return itemRepo.findById(idItem)
                .map(item -> {
                    item.setEstado(estado);
                    return ResponseEntity.ok(itemRepo.save(item));
                }).orElse(ResponseEntity.notFound().build());
    }

    // Obtener historial de compras
    @GetMapping("/usuario/{idUsuario}")
    public List<ListaCompra> historial(@PathVariable Long idUsuario) {
        return listaRepo.findByUsuarioId(idUsuario);
    }

    // Obtener ítems filtrando por estado
    @GetMapping("/{idLista}/items")
    public ResponseEntity<List<ItemLista>> obtenerItemsPorEstado(
            @PathVariable Long idLista,
            @RequestParam String estado
    ) {
        List<ItemLista> items = itemRepo.buscarPorestado(idLista, estado);
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items);
    }

    // Obtener detalles de lista
    @GetMapping("/{idLista}")
    public ResponseEntity<List<ItemLista>> detalle(
            @PathVariable Long idLista
    ) {
        List<ItemLista> items = itemRepo.detalleLista(idLista);
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items);
    }

    // Obtener historial con paginación
    @GetMapping("/usuario/{idUsuario}/paginado")
    public Page<ListaCompra> historialPaginado(
            @PathVariable Long idUsuario,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return listaRepo.findByUsuarioId(idUsuario, pageable);
    }

    // Obtener historial con paginación y ordenamiento dinámico
    @GetMapping("/usuario/{idUsuario}/paginado/ordenado")
    public Page<ListaCompra> historialPaginado(
            @PathVariable Long idUsuario,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "fechaCreacion") String sortBy,
            @RequestParam(defaultValue = "desc") String order
    ) {
        Sort sort = order.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return listaRepo.findByUsuarioId(idUsuario, pageable);
    }
}
