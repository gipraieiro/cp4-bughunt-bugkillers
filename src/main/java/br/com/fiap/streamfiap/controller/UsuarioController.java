package br.com.fiap.streamfiap.controller;
 
import br.com.fiap.streamfiap.model.Usuario;

import br.com.fiap.streamfiap.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController

@RequestMapping("/api/usuarios")

public class UsuarioController {
 
    @Autowired

    private UsuarioRepository usuarioRepository;
 
    @GetMapping

    public List<Usuario> listarTodos() {

        return usuarioRepository.findAll();

    }
 
    @PostMapping

    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuarioParaCadastrar) {

        // Clean Code 03: Variaveis renomeadas para nomes significativos

        Usuario usuarioSalvo = usuarioRepository.save(usuarioParaCadastrar);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);

    }

}
 