package pe.cibertec.cibertecdemo.service;

import org.springframework.stereotype.Service;
import pe.cibertec.cibertecdemo.model.Usuario;
import pe.cibertec.cibertecdemo.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepo;

    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public Usuario registrar(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    public List<Usuario> listar() {
        return usuarioRepo.findAll();
    }

    public Usuario obtener(Long id) {
        return usuarioRepo.findById(id).orElse(null);
    }

    public Usuario login(String correo, String clave) {
        Usuario u = usuarioRepo.findByCorreo(correo).orElse(null);
        if (u != null && u.getClave().equals(clave)) {
            return u;
        }
        return null;
    }
}
