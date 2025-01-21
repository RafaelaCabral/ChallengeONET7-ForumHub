package br.com.alura.forumhub.forumhub.controller;

import br.com.alura.forumhub.forumhub.dto.DadosCadastroTopico;
import br.com.alura.forumhub.forumhub.dto.TopicoDTO;
import br.com.alura.forumhub.forumhub.model.Topico;
import br.com.alura.forumhub.forumhub.repository.TopicoRepository;
import br.com.alura.forumhub.forumhub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService service;

    public TopicoController(TopicoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Topico> cadastrar(@RequestBody @Valid DadosCadastroTopico dados) {
        Topico topico = service.cadastrar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(topico);
    }

    @Autowired
    private TopicoRepository topicoRepository;
    @GetMapping
    public List<TopicoDTO> listar() {
        List<Topico> topicos = topicoRepository.findAll(); // Chama o método findAll() da instância topicoRepository
        return topicos.stream()
                .map(TopicoDTO::new) // Converte cada Topico para TopicoDTO
                .collect(Collectors.toList());
    }
}