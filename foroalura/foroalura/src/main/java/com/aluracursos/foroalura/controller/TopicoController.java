package com.aluracursos.foroalura.controller;

import com.aluracursos.foroalura.dto.TopicoDTO;
import com.aluracursos.foroalura.model.Topico;
import com.aluracursos.foroalura.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @GetMapping
    public List<TopicoDTO> getAllTopicos() {
        return topicoService.findAll().stream()
                .map(topico -> new TopicoDTO(topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(),
                        topico.getStatus(), topico.getAutor().getNombre(), topico.getCurso().getNombre()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDTO> getTopicoById(@PathVariable Long id) {
        return topicoService.findById(id)
                .map(topico -> ResponseEntity.ok(new TopicoDTO(topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(),
                        topico.getStatus(), topico.getAutor().getNombre(), topico.getCurso().getNombre())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Topico> createTopico(@Valid @RequestBody Topico topico) {
        try {
            Topico savedTopico = topicoService.save(topico);
            return ResponseEntity.ok(savedTopico);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Topico> updateTopico(@PathVariable Long id, @Valid @RequestBody Topico topicoDetails) {
        Optional<Topico> optionalTopico = topicoService.findById(id);
        if (optionalTopico.isPresent()) {
            Topico existingTopico = optionalTopico.get();
            existingTopico.setTitulo(topicoDetails.getTitulo());
            existingTopico.setMensaje(topicoDetails.getMensaje());
            existingTopico.setStatus(topicoDetails.getStatus());
            existingTopico.setAutor(topicoDetails.getAutor());
            existingTopico.setCurso(topicoDetails.getCurso());
            try {
                Topico updatedTopico = topicoService.update(existingTopico);
                return ResponseEntity.ok(updatedTopico);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopico(@PathVariable Long id) {
        return topicoService.findById(id)
                .map(topico -> {
                    topicoService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
