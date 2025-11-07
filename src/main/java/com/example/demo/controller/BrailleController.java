package com.example.demo.controller;

import com.example.demo.dto.ConversionRequest;
import com.example.demo.dto.ConversionResponse;
import com.example.demo.model.Conversion;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para endpoints de conversión Braille
 * 
 * Endpoints:
 * - POST /api/convertir - Convierte texto <-> Braille
 * - GET /api/historial - Obtiene historial de conversiones
 * - GET /api/historial/recientes - Últimas 10 conversiones
 * - DELETE /api/historial/{id} - Elimina una conversión del historial
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BrailleController {
    
    private final ConversionService conversionService;
    private final UserRepository userRepository;
    
    public BrailleController(ConversionService conversionService, UserRepository userRepository) {
        this.conversionService = conversionService;
        this.userRepository = userRepository;
    }
    
    /**
     * Historia 4 & 5: Endpoint de conversión texto ↔ Braille
     * 
     * POST /api/convertir
     * Body: {
     *   "texto": "Hola mundo",
     *   "tipo": "texto-a-braille" | "braille-a-texto"
     * }
     * Query params opcionales:
     *   - userId: ID del usuario (para guardar en historial)
     *   - guardar: true/false (guardar en historial)
     */
    @PostMapping("/convertir")
    public ResponseEntity<ConversionResponse> convertir(
            @RequestBody ConversionRequest request,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "false") boolean guardar
    ) {
        // Validaciones básicas
        if (request.getTexto() == null || request.getTexto().isEmpty()) {
            ConversionResponse errorResponse = new ConversionResponse();
            errorResponse.setExito(false);
            errorResponse.setMensaje("El texto no puede estar vacío");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        if (!("texto-a-braille".equals(request.getTipo()) || "braille-a-texto".equals(request.getTipo()))) {
            ConversionResponse errorResponse = new ConversionResponse();
            errorResponse.setExito(false);
            errorResponse.setMensaje("Tipo debe ser 'texto-a-braille' o 'braille-a-texto'");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        
        // Obtener usuario si se especificó
        User user = null;
        if (userId != null) {
            user = userRepository.findById(userId).orElse(null);
        }
        
        // Realizar conversión
        ConversionResponse response = conversionService.convertir(request, user, guardar);
        
        if (response.isExito()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Historia 6: Obtener historial completo de conversiones
     * 
     * GET /api/historial?userId=1
     */
    @GetMapping("/historial")
    public ResponseEntity<List<Conversion>> obtenerHistorial(@RequestParam Long userId) {
        return userRepository.findById(userId)
            .map(user -> {
                List<Conversion> historial = conversionService.obtenerHistorial(user);
                return ResponseEntity.ok(historial);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Historia 6: Obtener últimas 10 conversiones
     * 
     * GET /api/historial/recientes?userId=1
     */
    @GetMapping("/historial/recientes")
    public ResponseEntity<List<Conversion>> obtenerHistorialReciente(@RequestParam Long userId) {
        return userRepository.findById(userId)
            .map(user -> {
                List<Conversion> historial = conversionService.obtenerUltimasConversiones(user);
                return ResponseEntity.ok(historial);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Historia 6: Eliminar conversión del historial
     * 
     * DELETE /api/historial/{id}?userId=1
     */
    @DeleteMapping("/historial/{id}")
    public ResponseEntity<Void> eliminarConversion(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        return userRepository.findById(userId)
            .map(user -> {
                boolean eliminado = conversionService.eliminarConversion(id, user);
                if (eliminado) {
                    return ResponseEntity.noContent().<Void>build();
                } else {
                    return ResponseEntity.notFound().<Void>build();
                }
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Obtener historial filtrado por tipo
     * 
     * GET /api/historial/tipo/{tipo}?userId=1
     * tipo puede ser: texto-a-braille o braille-a-texto
     */
    @GetMapping("/historial/tipo/{tipo}")
    public ResponseEntity<List<Conversion>> obtenerHistorialPorTipo(
            @PathVariable String tipo,
            @RequestParam Long userId
    ) {
        if (!("texto-a-braille".equals(tipo) || "braille-a-texto".equals(tipo))) {
            return ResponseEntity.badRequest().build();
        }
        
        return userRepository.findById(userId)
            .map(user -> {
                List<Conversion> historial = conversionService.obtenerHistorialPorTipo(user, tipo);
                return ResponseEntity.ok(historial);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Limpiar todo el historial del usuario
     * 
     * DELETE /api/historial?userId=1
     */
    @DeleteMapping("/historial")
    public ResponseEntity<Void> limpiarHistorial(@RequestParam Long userId) {
        return userRepository.findById(userId)
            .map(user -> {
                conversionService.limpiarHistorial(user);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Obtener estadísticas del historial del usuario
     * 
     * GET /api/historial/estadisticas?userId=1
     */
    @GetMapping("/historial/estadisticas")
    public ResponseEntity<?> obtenerEstadisticas(@RequestParam Long userId) {
        return userRepository.findById(userId)
            .map(user -> {
                var estadisticas = conversionService.obtenerEstadisticas(user);
                return ResponseEntity.ok(estadisticas);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}

