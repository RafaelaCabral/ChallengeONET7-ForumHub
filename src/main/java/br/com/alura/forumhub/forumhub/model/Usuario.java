package br.com.alura.forumhub.forumhub.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    private String email;
    private String nome;
    private String senha;
    private String role; // Adicione este campo para armazenar a função do usuário

    // Getters e Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getRole() { // Método getter para o campo role
        return role;
    }

    public void setRole(String role) { // Método setter para o campo role
        this.role = role;
    }
}
