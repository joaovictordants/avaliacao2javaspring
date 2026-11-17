package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.domain.usuario.Usuario;

public record DadosDetalhamentoUsuario(Long id, String login) {
    public DadosDetalhamentoUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getLogin());
    }
}
