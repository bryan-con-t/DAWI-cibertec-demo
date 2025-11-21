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
import pe.cibertec.cibertecdemo.service.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioService.login(loginRequest.getCorreo(), loginRequest.getClave());
        if (usuario == null) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
        return ResponseEntity.ok(usuario);
    }
}
