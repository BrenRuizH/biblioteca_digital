package com.biblioteca.prestamo.controller;

import com.biblioteca.prestamo.model.Prestamo;
import com.biblioteca.prestamo.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping
    public List<Prestamo> getAllPrestamos() {
        return prestamoService.getAllPrestamos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> getPrestamoById(@PathVariable String id) {
        return prestamoService.getPrestamoById(id);
    }

    @PostMapping
    public Prestamo createPrestamo(@RequestBody Prestamo prestamo) {
        return prestamoService.createPrestamo(prestamo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> updatePrestamo(@PathVariable String id, @RequestBody Prestamo prestamo) {
        return prestamoService.updatePrestamo(id, prestamo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrestamo(@PathVariable String id) {
        return prestamoService.deletePrestamo(id);
    }

    @GetMapping("/search")
    public List<Prestamo> searchPrestamos(@RequestParam(required = false) String idUsuario,
                                          @RequestParam(required = false) String idLibro,
                                          @RequestParam(required = false) String idRevista) {
        return prestamoService.searchPrestamos(idUsuario, idLibro, idRevista);
    }

    @GetMapping("/sorted")
    public List<Prestamo> getPrestamosSortedByFechaPrestamo() {
        return prestamoService.getPrestamosSortedByFechaPrestamo();
    }
}
