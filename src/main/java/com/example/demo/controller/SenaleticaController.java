package com.example.demo.controller;

import com.example.demo.dto.SenaleticaRequest;
import com.example.demo.dto.SenaleticaResponse;
import com.example.demo.model.Senaletica;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.SenaleticaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller para endpoints de señalética
 * 
 * Endpoints:
 * - POST /api/senaletica - Crea una nueva señalética
 * - GET /api/senaletica - Obtiene todas las señaléticas del usuario
 * - GET /api/senaletica/{id} - Obtiene una señalética por ID
 * - POST /api/senaletica/{id}/descarga - Registra una descarga
 * - DELETE /api/senaletica/{id} - Elimina una señalética
 * - GET /api/senaletica/populares - Obtiene señaléticas más descargadas
 */
@RestController
@RequestMapping("/api/senaletica")
@CrossOrigin(origins = "http://localhost:3000")
public class SenaleticaController {
    
    private final SenaleticaService senaleticaService;
    private final UserRepository userRepository;
    
    public SenaleticaController(SenaleticaService senaleticaService, UserRepository userRepository) {
        this.senaleticaService = senaleticaService;
        this.userRepository = userRepository;
    }
    
    /**
     * Crea una nueva señalética
     * 
     * POST /api/senaletica?userId=1
     * Body: {
     *   "titulo": "Baño Accesible",
     *   "textoOriginal": "Baño",
     *   "tipoSenaletica": "baño",
     *   "formatoSalida": "PDF",
     *   "altoContraste": true
     * }
     */
    @PostMapping
    public ResponseEntity<SenaleticaResponse> crearSenaletica(
            @RequestBody SenaleticaRequest request,
            @RequestParam Long userId
    ) {
        Optional<User> userOpt = userRepository.findById(userId);
        
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new SenaleticaResponse(false, "Usuario no encontrado"));
        }
        
        SenaleticaResponse response = senaleticaService.crearSenaletica(request, userOpt.get());
        
        if (response.isExito()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    /**
     * Obtiene todas las señaléticas del usuario
     * 
     * GET /api/senaletica?userId=1
     */
    @GetMapping
    public ResponseEntity<List<Senaletica>> obtenerSenaleticas(@RequestParam Long userId) {
        return userRepository.findById(userId)
            .map(user -> {
                List<Senaletica> senaleticas = senaleticaService.obtenerSenaleticasPorUsuario(user);
                return ResponseEntity.ok(senaleticas);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Obtiene una señalética por ID
     * 
     * GET /api/senaletica/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Senaletica> obtenerSenaletica(@PathVariable Long id) {
        return senaleticaService.obtenerSenaleticaPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Registra una descarga de señalética
     * 
     * POST /api/senaletica/{id}/descarga?userId=1
     */
    @PostMapping("/{id}/descarga")
    public ResponseEntity<Void> registrarDescarga(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        return userRepository.findById(userId)
            .map(user -> {
                boolean success = senaleticaService.registrarDescarga(id, user);
                if (success) {
                    return ResponseEntity.ok().<Void>build();
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).<Void>build();
                }
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Elimina una señalética
     * 
     * DELETE /api/senaletica/{id}?userId=1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSenaletica(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        return userRepository.findById(userId)
            .map(user -> {
                boolean success = senaleticaService.eliminarSenaletica(id, user);
                if (success) {
                    return ResponseEntity.noContent().<Void>build();
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).<Void>build();
                }
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Obtiene señaléticas más descargadas
     * 
     * GET /api/senaletica/populares
     */
    @GetMapping("/populares")
    public ResponseEntity<List<Senaletica>> obtenerSenaleticasPopulares() {
        List<Senaletica> senaleticas = senaleticaService.obtenerSenaleticasPopulares();
        return ResponseEntity.ok(senaleticas);
    }
}
