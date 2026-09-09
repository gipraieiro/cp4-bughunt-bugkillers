package br.com.fiap.streamfiap.controller;

import br.com.fiap.streamfiap.model.Conteudo;

import br.com.fiap.streamfiap.repository.ConteudoRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/conteudos")

public class ConteudoController {

    @Autowired

    private ConteudoRepository conteudoRepository;

    // Bug 11: Retorno adequado quando o conteúdo não existe

    @GetMapping("/{id}")

    public ResponseEntity<Conteudo> buscarPorId(@PathVariable Long id) {

        return conteudoRepository.findById(id)

                .map(ResponseEntity::ok)

                .orElse(ResponseEntity.notFound().build());

    }

    // Bug 12: Busca de conteúdos filtrada por categoria

    @GetMapping("/categoria/{categoria}")

    public ResponseEntity<List<Conteudo>> buscarPorCategoria(@PathVariable String categoria) {

        List<Conteudo> conteudos = conteudoRepository.findByCategoriaIgnoreCase(categoria);

        return ResponseEntity.ok(conteudos);

    }

}
 