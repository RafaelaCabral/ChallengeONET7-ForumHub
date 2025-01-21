package br.com.alura.forumhub.forumhub.dto;

import br.com.alura.forumhub.forumhub.model.Topico;
import java.time.LocalDateTime;

public record TopicoDTO(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, String status, String autor, String curso) {

    public TopicoDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getEstado().name(),  // Converte o enum para String
                topico.getAutor(),
                topico.getCurso()
        );
    }
}
