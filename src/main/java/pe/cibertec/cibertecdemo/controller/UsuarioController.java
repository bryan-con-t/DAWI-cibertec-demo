package pe.cibertec.cibertecdemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.cibertec.cibertecdemo.model.Usuario;
import pe.cibertec.cibertecdemo.repository.UsuarioRepository;
import pe.cibertec.cibertecdemo.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Registrar un nuevo usuario
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        Usuario guardado =  usuarioService.registrar(usuario);
        return ResponseEntity.ok(guardado);
    }

    // Listar todos los usuarios
    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtener(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }
}
