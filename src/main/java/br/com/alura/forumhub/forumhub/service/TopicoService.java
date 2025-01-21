package br.com.alura.forumhub.forumhub.service;

import br.com.alura.forumhub.forumhub.dto.DadosCadastroTopico;
import br.com.alura.forumhub.forumhub.model.Topico;
import br.com.alura.forumhub.forumhub.repository.TopicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicoService {

    private final TopicoRepository repository;

    public TopicoService(TopicoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Topico cadastrar(DadosCadastroTopico dados) {
        if (repository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            throw new IllegalArgumentException("Já existe um tópico com o mesmo título e mensagem.");
        }

        Topico topico = new Topico();
        topico.setTitulo(dados.titulo());
        topico.setMensagem(dados.mensagem());
        topico.setAutor(dados.autor());
        topico.setCurso(dados.curso());
        return repository.save(topico);
    }
}
