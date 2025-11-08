package com.example.demo.service;

import com.example.demo.dto.ConversionRequest;
import com.example.demo.dto.ConversionResponse;
import com.example.demo.model.Conversion;
import com.example.demo.model.User;
import com.example.demo.repository.ConversionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para gestionar conversiones y su persistencia
 */
@Service
public class ConversionService {
    
    private final BrailleService brailleService;
    private final ConversionRepository conversionRepository;
    
    public ConversionService(BrailleService brailleService, ConversionRepository conversionRepository) {
        this.brailleService = brailleService;
        this.conversionRepository = conversionRepository;
    }
    
    /**
     * Realiza conversión y opcionalmente la guarda en BD
     */
    public ConversionResponse convertir(ConversionRequest request, User user, boolean guardar) {
        String resultado;
        boolean exito = true;
        String mensaje = "Conversión exitosa";
        long startTime = System.currentTimeMillis();
        Conversion savedConversion = null;
        
        try {
            if ("texto-a-braille".equals(request.getTipo())) {
                resultado = brailleService.textoABraille(request.getTexto());
            } else if ("braille-a-texto".equals(request.getTipo())) {
                resultado = brailleService.brailleATexto(request.getTexto());
            } else {
                exito = false;
                mensaje = "Tipo de conversión no válido";
                resultado = "";
            }
            
            // Calcular tiempo de conversión
            long endTime = System.currentTimeMillis();
            int tiempoConversion = (int) (endTime - startTime);
            
            // Guardar en historial si se solicita y hay un usuario
            if (guardar && user != null && exito) {
                Conversion conversion = new Conversion(request.getTexto(), resultado, request.getTipo());
                conversion.setUser(user);
                conversion.setTiempoConversionMs(tiempoConversion);
                
                // Establecer información adicional si está disponible en el request
                if (request.getDispositivo() != null) {
                    conversion.setDispositivo(request.getDispositivo());
                }
                if (request.getNavegador() != null) {
                    conversion.setNavegador(request.getNavegador());
                }
                if (request.getIpOrigen() != null) {
                    conversion.setIpOrigen(request.getIpOrigen());
                }
                
                savedConversion = conversionRepository.save(conversion);
            }
            
        } catch (Exception e) {
            exito = false;
            mensaje = "Error en la conversión: " + e.getMessage();
            resultado = "";
        }
        
        ConversionResponse response = new ConversionResponse(
            request.getTexto(),
            resultado,
            request.getTipo(),
            exito
        );
        response.setMensaje(mensaje);
        
        // Si se guardó, agregar el ID y fecha
        if (savedConversion != null) {
            response.setId(savedConversion.getId());
            response.setFecha(savedConversion.getFecha().toString());
            response.setTiempoConversionMs(savedConversion.getTiempoConversionMs());
        }
        
        return response;
    }
    
    /**
     * Obtiene historial de conversiones de un usuario
     */
    public List<Conversion> obtenerHistorial(User user) {
        return conversionRepository.findByUserOrderByFechaDesc(user);
    }
    
    /**
     * Obtiene las últimas 10 conversiones de un usuario
     */
    public List<Conversion> obtenerUltimasConversiones(User user) {
        return conversionRepository.findTop10ByUserOrderByFechaDesc(user);
    }
    
    /**
     * Elimina una conversión del historial
     */
    public boolean eliminarConversion(Long id, User user) {
        return conversionRepository.findById(id)
            .map(conversion -> {
                // Verificar que la conversión pertenece al usuario
                if (conversion.getUser().getId().equals(user.getId())) {
                    conversionRepository.delete(conversion);
                    return true;
                }
                return false;
            })
            .orElse(false);
    }
    
    /**
     * Obtiene historial filtrado por tipo
     */
    public List<Conversion> obtenerHistorialPorTipo(User user, String tipo) {
        return conversionRepository.findByUserAndTipoOrderByFechaDesc(user, tipo);
    }
    
    /**
     * Limpia todo el historial de un usuario
     */
    @Transactional
    public void limpiarHistorial(User user) {
        conversionRepository.deleteByUser(user);
    }
    
    /**
     * Obtiene estadísticas del historial del usuario
     */
    public EstadisticasDTO obtenerEstadisticas(User user) {
        long totalConversiones = conversionRepository.countByUser(user);
        long totalTextoBraille = conversionRepository.countByUserAndTipo(user, "texto-a-braille");
        long totalBrailleTexto = conversionRepository.countByUserAndTipo(user, "braille-a-texto");
        Long caracteresConvertidos = conversionRepository.sumLongitudOriginalByUser(user);
        
        return new EstadisticasDTO(
            totalConversiones,
            totalTextoBraille,
            totalBrailleTexto,
            caracteresConvertidos != null ? caracteresConvertidos : 0L
        );
    }
    
    /**
     * DTO interno para estadísticas
     */
    public static class EstadisticasDTO {
        public final long totalConversiones;
        public final long conversionesTextoBraille;
        public final long conversionesBrailleTexto;
        public final long caracteresConvertidos;
        
        public EstadisticasDTO(long total, long textoBraille, long brailleTexto, long caracteres) {
            this.totalConversiones = total;
            this.conversionesTextoBraille = textoBraille;
            this.conversionesBrailleTexto = brailleTexto;
            this.caracteresConvertidos = caracteres;
        }
    }
}
