package br.com.alura.forumhub.forumhub.service;

import br.com.alura.forumhub.forumhub.model.Usuario;
import br.com.alura.forumhub.forumhub.repository.UsuarioRepository;
import br.com.alura.forumhub.forumhub.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Autowired
    private JwtUtils jwtUtils;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public String autenticar(String email, String senha) {
        // Usando Optional para tratar o retorno do repositório
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Validar a senha
        if (passwordEncoder.matches(senha, usuario.getSenha())) {
            // Gerar o token JWT
            return jwtUtils.gerarToken(email);
        } else {
            throw new RuntimeException("Credenciais inválidas");
        }
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }


    @Autowired
    public UsuarioService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // Método de cadastro
    public Usuario cadastrarUsuario(String email, String nome, String senha, String role) {
        // Criptografar a senha
        String senhaCriptografada = passwordEncoder.encode(senha);

        // Criar um novo usuário com todos os campos
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setSenha(senhaCriptografada);
        usuario.setRole(role);

        // Salvar o usuário no banco de dados
        return usuarioRepository.save(usuario);
    }
}
