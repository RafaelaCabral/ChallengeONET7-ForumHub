package br.com.alura.forumhub.forumhub.controller;

import br.com.alura.forumhub.forumhub.dto.UsuarioDTO;
import br.com.alura.forumhub.forumhub.model.Usuario;
import br.com.alura.forumhub.forumhub.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token;
        try {
            token = usuarioService.autenticar(loginRequest.getEmail(), loginRequest.getSenha());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Credenciais inválidas");
        }
        return ResponseEntity.ok("Bearer " + token); // Retorna o token com o prefixo "Bearer"
    }

    // Endpoint para obter informações do usuário autenticado
    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> obterUsuarioAutenticado(Authentication authentication) {
        String email = ((User) authentication.getPrincipal()).getUsername();
        Usuario usuario = usuarioService.buscarPorEmail(email);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        return ResponseEntity.ok(usuarioDTO);
    }
    @PostMapping("/cadastroUSER")
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        // Chamando o método de cadastro no serviço
        Usuario usuarioCadastrado = usuarioService.cadastrarUsuario(usuario.getEmail(), usuario.getNome(), usuario.getSenha(), usuario.getRole());
        return ResponseEntity.status(201).body(usuarioCadastrado);
    }
}
