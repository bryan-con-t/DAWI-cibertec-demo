package pe.cibertec.cibertecdemo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.cibertecdemo.model.ItemLista;
import pe.cibertec.cibertecdemo.model.ListaCompra;
import pe.cibertec.cibertecdemo.service.HistorialService;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
public class HistorialController {

    private final HistorialService historialService;

    public HistorialController(HistorialService historialService) {
        this.historialService = historialService;
    }

    // HISTORIAL COMPLETO
    @GetMapping("/usuarios/{idUsuario}/listas")
    public ResponseEntity<List<ListaCompra>> historial(@PathVariable Long idUsuario) {
        List<ListaCompra> listas = historialService.obtenerHistorial(idUsuario);

        if (listas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listas);
    }

    // HISTORIAL PAGINADO
    @GetMapping("/usuarios/{idUsuario}/listas/paginado")
    public ResponseEntity<Page<ListaCompra>> historialPaginado(
            @PathVariable Long idUsuario,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ListaCompra> paginado = historialService.obtenerHistorialPaginado(idUsuario, pageable);
        return ResponseEntity.ok(paginado);
    }

    // DETALLE DE UNA LISTA (ITEMS)
    @GetMapping("/listas/{idLista}/items")
    public ResponseEntity<List<ItemLista>> detalleLista(
            @PathVariable Long idLista,
            @RequestParam(required = false) String estado
    ) {
        List<ItemLista> items = historialService.obtenerDetalle(idLista, estado);

        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items);
    }
}
