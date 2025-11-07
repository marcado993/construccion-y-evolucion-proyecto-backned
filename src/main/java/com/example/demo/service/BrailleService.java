package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio de conversión Braille
 * Lógica de negocio para traducción texto <-> Braille
 */
@Service
public class BrailleService {
    
    private static final Map<Character, String> TEXTO_A_BRAILLE = new HashMap<>();
    private static final Map<String, Character> BRAILLE_A_TEXTO = new HashMap<>();
    private static final String INDICADOR_MAYUSCULA = "⠨";
    
    static {
        // Alfabeto español
        TEXTO_A_BRAILLE.put('a', "⠁");
        TEXTO_A_BRAILLE.put('b', "⠃");
        TEXTO_A_BRAILLE.put('c', "⠉");
        TEXTO_A_BRAILLE.put('d', "⠙");
        TEXTO_A_BRAILLE.put('e', "⠑");
        TEXTO_A_BRAILLE.put('f', "⠋");
        TEXTO_A_BRAILLE.put('g', "⠛");
        TEXTO_A_BRAILLE.put('h', "⠓");
        TEXTO_A_BRAILLE.put('i', "⠊");
        TEXTO_A_BRAILLE.put('j', "⠚");
        TEXTO_A_BRAILLE.put('k', "⠅");
        TEXTO_A_BRAILLE.put('l', "⠇");
        TEXTO_A_BRAILLE.put('m', "⠍");
        TEXTO_A_BRAILLE.put('n', "⠝");
        TEXTO_A_BRAILLE.put('ñ', "⠻");
        TEXTO_A_BRAILLE.put('o', "⠕");
        TEXTO_A_BRAILLE.put('p', "⠏");
        TEXTO_A_BRAILLE.put('q', "⠟");
        TEXTO_A_BRAILLE.put('r', "⠗");
        TEXTO_A_BRAILLE.put('s', "⠎");
        TEXTO_A_BRAILLE.put('t', "⠞");
        TEXTO_A_BRAILLE.put('u', "⠥");
        TEXTO_A_BRAILLE.put('v', "⠧");
        TEXTO_A_BRAILLE.put('w', "⠺");
        TEXTO_A_BRAILLE.put('x', "⠭");
        TEXTO_A_BRAILLE.put('y', "⠽");
        TEXTO_A_BRAILLE.put('z', "⠵");
        
        // Vocales con acento
        TEXTO_A_BRAILLE.put('á', "⠷");
        TEXTO_A_BRAILLE.put('é', "⠮");
        TEXTO_A_BRAILLE.put('í', "⠌");
        TEXTO_A_BRAILLE.put('ó', "⠾");
        TEXTO_A_BRAILLE.put('ú', "⠾");
        TEXTO_A_BRAILLE.put('ü', "⠳");
        
        // Números
        TEXTO_A_BRAILLE.put('0', "⠚");
        TEXTO_A_BRAILLE.put('1', "⠁");
        TEXTO_A_BRAILLE.put('2', "⠃");
        TEXTO_A_BRAILLE.put('3', "⠉");
        TEXTO_A_BRAILLE.put('4', "⠙");
        TEXTO_A_BRAILLE.put('5', "⠑");
        TEXTO_A_BRAILLE.put('6', "⠋");
        TEXTO_A_BRAILLE.put('7', "⠛");
        TEXTO_A_BRAILLE.put('8', "⠓");
        TEXTO_A_BRAILLE.put('9', "⠊");
        
        // Puntuación
        TEXTO_A_BRAILLE.put(' ', " ");
        TEXTO_A_BRAILLE.put(',', "⠂");
        TEXTO_A_BRAILLE.put('.', "⠲");
        TEXTO_A_BRAILLE.put('?', "⠢");
        TEXTO_A_BRAILLE.put('!', "⠖");
        TEXTO_A_BRAILLE.put(';', "⠆");
        TEXTO_A_BRAILLE.put(':', "⠒");
        
        // Crear mapa inverso
        TEXTO_A_BRAILLE.forEach((key, value) -> {
            if (!value.equals(" ")) {
                BRAILLE_A_TEXTO.put(value, key);
            }
        });
        BRAILLE_A_TEXTO.put(" ", ' ');
    }
    
    /**
     * Convierte texto español a Braille
     */
    public String textoABraille(String texto) {
        if (texto == null || texto.isEmpty()) {
            return "";
        }
        
        StringBuilder resultado = new StringBuilder();
        
        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.charAt(i);
            char caracterMinuscula = Character.toLowerCase(caracter);
            
            // Si es mayúscula, agregar indicador
            if (Character.isUpperCase(caracter) && caracter != ' ') {
                resultado.append(INDICADOR_MAYUSCULA);
            }
            
            // Convertir el carácter
            String brailleChar = TEXTO_A_BRAILLE.get(caracterMinuscula);
            if (brailleChar != null) {
                resultado.append(brailleChar);
            } else {
                // Si no se encuentra, mantener el carácter original
                resultado.append(caracter);
            }
        }
        
        return resultado.toString();
    }
    
    /**
     * Convierte Braille a texto español
     */
    public String brailleATexto(String braille) {
        if (braille == null || braille.isEmpty()) {
            return "";
        }
        
        StringBuilder resultado = new StringBuilder();
        boolean siguienteMayuscula = false;
        
        for (int i = 0; i < braille.length(); i++) {
            char caracter = braille.charAt(i);
            
            // Detectar indicador de mayúscula
            if (String.valueOf(caracter).equals(INDICADOR_MAYUSCULA)) {
                siguienteMayuscula = true;
                continue;
            }
            
            // Convertir el carácter
            Character textoChar = BRAILLE_A_TEXTO.get(String.valueOf(caracter));
            if (textoChar != null) {
                if (siguienteMayuscula && textoChar != ' ') {
                    resultado.append(Character.toUpperCase(textoChar));
                    siguienteMayuscula = false;
                } else {
                    resultado.append(textoChar);
                }
            } else {
                // Si no se encuentra, mantener el carácter original
                resultado.append(caracter);
            }
        }
        
        return resultado.toString();
    }
    
    /**
     * Valida si un texto puede convertirse a Braille
     */
    public boolean puedeConvertirABraille(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }
        
        for (char c : texto.toLowerCase().toCharArray()) {
            if (!TEXTO_A_BRAILLE.containsKey(c)) {
                return false;
            }
        }
        return true;
    }
}
