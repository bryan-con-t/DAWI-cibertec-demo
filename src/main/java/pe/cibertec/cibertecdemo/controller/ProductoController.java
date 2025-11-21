package pe.cibertec.cibertecdemo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.cibertecdemo.model.Producto;
import pe.cibertec.cibertecdemo.repository.ProductoRepository;
import pe.cibertec.cibertecdemo.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;
    private final ProductoRepository productoRepo;

    public ProductoController(ProductoService productoService, ProductoRepository productoRepo) {
        this.productoService = productoService;
        this.productoRepo = productoRepo;
    }

    @PostMapping("/lote")
    public ResponseEntity<String> registrarLote(@RequestBody List<Producto> productos) {
        productoService.registrarLote(productos);
        return ResponseEntity.ok("Productos registrados exitosamente");
    }

    @GetMapping
    public List<Producto> listar() {
        return productoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto nuevo) {
        return productoRepo.findById(id).map(prod -> {
            prod.setNombre(nuevo.getNombre());
            prod.setPrecio(nuevo.getPrecio());
            Producto actualizado = productoRepo.save(prod);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarPorNombre")
    public ResponseEntity<List<Producto>> buscarPorNombre(
            @RequestParam String texto
    ) {
        List<Producto> resultados = productoRepo.buscarPorNombre(texto);
        if (resultados.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<Producto>> buscar(
            @RequestParam String nombre,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Producto> resultado = productoService.buscarPorNombre(nombre, pageable);
        return ResponseEntity.ok(resultado);
    }
}
