package com.example.demo.service;

import com.example.demo.dto.SenaleticaRequest;
import com.example.demo.dto.SenaleticaResponse;
import com.example.demo.model.Senaletica;
import com.example.demo.model.User;
import com.example.demo.repository.SenaleticaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar señalética
 */
@Service
public class SenaleticaService {
    
    private final SenaleticaRepository senaleticaRepository;
    private final BrailleService brailleService;
    
    public SenaleticaService(SenaleticaRepository senaleticaRepository, BrailleService brailleService) {
        this.senaleticaRepository = senaleticaRepository;
        this.brailleService = brailleService;
    }
    
    /**
     * Crea una nueva señalética
     */
    public SenaleticaResponse crearSenaletica(SenaleticaRequest request, User user) {
        SenaleticaResponse response = new SenaleticaResponse();
        
        try {
            // Validaciones
            if (request.getTitulo() == null || request.getTitulo().isEmpty()) {
                response.setExito(false);
                response.setMensaje("El título no puede estar vacío");
                return response;
            }
            
            if (request.getTextoOriginal() == null || request.getTextoOriginal().isEmpty()) {
                response.setExito(false);
                response.setMensaje("El texto no puede estar vacío");
                return response;
            }
            
            // Convertir texto a braille
            String textoBraille = brailleService.textoABraille(request.getTextoOriginal());
            
            // Crear entidad
            Senaletica senaletica = new Senaletica(
                request.getTitulo(),
                request.getTextoOriginal(),
                textoBraille
            );
            
            senaletica.setUser(user);
            senaletica.setTipoSenaletica(request.getTipoSenaletica() != null ? 
                request.getTipoSenaletica() : "personalizado");
            senaletica.setFormatoSalida(request.getFormatoSalida() != null ? 
                request.getFormatoSalida() : "PDF");
            senaletica.setAltoContraste(request.getAltoContraste() != null ? 
                request.getAltoContraste() : true);
            senaletica.setTamanoFuente(request.getTamanoFuente() != null ? 
                request.getTamanoFuente() : 24);
            senaletica.setColorFondo(request.getColorFondo() != null ? 
                request.getColorFondo() : "#000000");
            senaletica.setColorTexto(request.getColorTexto() != null ? 
                request.getColorTexto() : "#FFFFFF");
            
            // Guardar en BD
            senaletica = senaleticaRepository.save(senaletica);
            
            // Preparar respuesta
            response.setId(senaletica.getId());
            response.setTitulo(senaletica.getTitulo());
            response.setTextoOriginal(senaletica.getTextoOriginal());
            response.setTextoBraille(senaletica.getTextoBraille());
            response.setTipoSenaletica(senaletica.getTipoSenaletica());
            response.setFormatoSalida(senaletica.getFormatoSalida());
            response.setAltoContraste(senaletica.getAltoContraste());
            response.setFechaCreacion(senaletica.getFechaCreacion());
            response.setDescargas(senaletica.getDescargas());
            response.setExito(true);
            response.setMensaje("Señalética creada exitosamente");
            
        } catch (Exception e) {
            response.setExito(false);
            response.setMensaje("Error al crear señalética: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Obtiene todas las señaléticas de un usuario
     */
    public List<Senaletica> obtenerSenaleticasPorUsuario(User user) {
        return senaleticaRepository.findByUserOrderByFechaCreacionDesc(user);
    }
    
    /**
     * Obtiene una señalética por ID
     */
    public Optional<Senaletica> obtenerSenaleticaPorId(Long id) {
        return senaleticaRepository.findById(id);
    }
    
    /**
     * Registra una descarga de señalética
     */
    public boolean registrarDescarga(Long id, User user) {
        Optional<Senaletica> senaleticaOpt = senaleticaRepository.findById(id);
        
        if (senaleticaOpt.isPresent()) {
            Senaletica senaletica = senaleticaOpt.get();
            
            // Verificar que pertenece al usuario
            if (senaletica.getUser().getId().equals(user.getId())) {
                senaletica.incrementarDescargas();
                senaleticaRepository.save(senaletica);
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Elimina una señalética
     */
    public boolean eliminarSenaletica(Long id, User user) {
        Optional<Senaletica> senaleticaOpt = senaleticaRepository.findById(id);
        
        if (senaleticaOpt.isPresent()) {
            Senaletica senaletica = senaleticaOpt.get();
            
            // Verificar que pertenece al usuario
            if (senaletica.getUser().getId().equals(user.getId())) {
                senaleticaRepository.delete(senaletica);
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Obtiene señaléticas más descargadas
     */
    public List<Senaletica> obtenerSenaleticasPopulares() {
        return senaleticaRepository.findTop10ByOrderByDescargasDesc();
    }
}
