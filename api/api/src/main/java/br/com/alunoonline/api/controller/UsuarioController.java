package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.domain.usuario.Usuario;
import br.com.alunoonline.api.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("cadastros")
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping
    @Transactional
    public ResponseEntity cadastro(@RequestBody
                                       @Valid DadosCadastroUsuario dados,
                                   UriComponentsBuilder uriBuilder) {
        var senhaCriptografada = passwordEncoder.encode(dados.senha());
        var usuario = new Usuario(dados.login(), senhaCriptografada);
        repository.save(usuario);


        var uri= uriBuilder.path("/cadastros/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }
}
