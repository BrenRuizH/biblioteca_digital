package com.biblioteca.revista.controller;

import com.biblioteca.revista.model.Revista;
import com.biblioteca.revista.service.RevistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/revistas")
public class RevistaController {

    @Autowired
    private RevistaService revistaService;

    @GetMapping
    public List<Revista> getAllRevistas() {
        return revistaService.getAllRevistas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Revista> getRevistaById(@PathVariable String id) {
        return revistaService.getRevistaById(id);
    }

    @PostMapping
    public Revista createRevista(@RequestBody Revista revista) {
        return revistaService.createRevista(revista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Revista> updateRevista(@PathVariable String id, @RequestBody Revista revista) {
        return revistaService.updateRevista(id, revista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRevista(@PathVariable String id) {
        return revistaService.deleteRevista(id);
    }

    @GetMapping("/search")
    public List<Revista> searchRevistas(@RequestParam(required = false) String titulo,
                                        @RequestParam(required = false) String noEdicion,
                                        @RequestParam(required = false) String categoria) {
        return revistaService.searchRevistas(titulo, noEdicion, categoria);
    }

    @GetMapping("/sorted")
    public List<Revista> getRevistasSortedByFechaPublicacion() {
        return revistaService.getRevistasSortedByFechaPublicacion();
    }
}
