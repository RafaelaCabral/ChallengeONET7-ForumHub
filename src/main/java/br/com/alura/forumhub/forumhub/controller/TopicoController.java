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
import java.util.Optional;
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
    @GetMapping("/{id}")
    public ResponseEntity<Topico> buscarTopicoPorId(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(topico.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody Topico topicoAtualizado) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            Topico topicoExistente = topico.get();
            topicoExistente.setTitulo(topicoAtualizado.getTitulo());
            topicoExistente.setMensagem(topicoAtualizado.getMensagem());
            topicoExistente.setEstado(topicoAtualizado.getEstado()); // Atualizando o status

            topicoRepository.save(topicoExistente);

            return ResponseEntity.ok(new TopicoDTO(topicoExistente));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Método para excluir um tópico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}