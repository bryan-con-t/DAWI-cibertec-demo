package pe.cibertec.cibertecdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.cibertec.cibertecdemo.dto.LoginRequest;
import pe.cibertec.cibertecdemo.model.Usuario;
import pe.cibertec.cibertecdemo.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UsuarioRepository usuarioRepo;

    public AuthController(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioRepo.findByCorreo(loginRequest.getCorreo()).orElse(null);
        if (usuario == null || !usuario.getClave().equals(loginRequest.getClave())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
        return ResponseEntity.ok(usuario);
    }
}
