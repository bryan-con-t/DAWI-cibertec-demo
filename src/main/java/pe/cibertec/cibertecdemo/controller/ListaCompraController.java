package pe.cibertec.cibertecdemo.controller;

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
}
