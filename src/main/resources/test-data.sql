-- ==========================================
-- SQL DE PRUEBA PARA HISTORIAL
-- ==========================================
-- Este script inserta datos de prueba para validar
-- la funcionalidad del historial de conversiones

-- 1. Insertar usuario de prueba
INSERT INTO users (username, email, password, nombre_completo, rol, activo) 
VALUES 
    ('testuser', 'test@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Usuario de Prueba', 'usuario', true),
    ('admin', 'admin@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Administrador', 'admin', true)
ON CONFLICT (email) DO NOTHING;

-- Nota: La contraseña hasheada es 'password123' (BCrypt)

-- 2. Obtener el ID del usuario de prueba (para las siguientes inserciones)
-- Ejecuta esto para ver los IDs:
SELECT id, username, email FROM users;

-- 3. Insertar conversiones de prueba (reemplaza USER_ID con el ID del usuario)
-- Ejemplo: Si el usuario tiene ID 1, usa 1 en lugar de {USER_ID}

-- Conversiones texto a braille
INSERT INTO conversiones (user_id, texto_original, resultado, tipo, longitud_original, longitud_resultado, fecha, dispositivo, navegador, tiempo_conversion_ms)
VALUES 
    (1, 'Hola mundo', '⠓⠕⠇⠁ ⠍⠥⠝⠙⠕', 'texto-a-braille', 10, 10, NOW() - INTERVAL '2 hours', 'Desktop', 'Chrome', 15),
    (1, 'Buenos días', '⠃⠥⠑⠝⠕⠎ ⠙⠊⠁⠎', 'texto-a-braille', 11, 11, NOW() - INTERVAL '1 hour', 'Mobile', 'Safari', 12),
    (1, 'Accesibilidad', '⠁⠉⠉⠑⠎⠊⠃⠊⠇⠊⠙⠁⠙', 'texto-a-braille', 14, 14, NOW() - INTERVAL '30 minutes', 'Desktop', 'Firefox', 18),
    (1, 'Bienvenido', '⠃⠊⠑⠝⠧⠑⠝⠊⠙⠕', 'texto-a-braille', 10, 10, NOW() - INTERVAL '15 minutes', 'Tablet', 'Edge', 14);

-- Conversiones braille a texto
INSERT INTO conversiones (user_id, texto_original, resultado, tipo, longitud_original, longitud_resultado, fecha, dispositivo, navegador, tiempo_conversion_ms)
VALUES 
    (1, '⠓⠕⠇⠁', 'hola', 'braille-a-texto', 4, 4, NOW() - INTERVAL '45 minutes', 'Desktop', 'Chrome', 10),
    (1, '⠛⠗⠁⠉⠊⠁⠎', 'gracias', 'braille-a-texto', 7, 7, NOW() - INTERVAL '20 minutes', 'Mobile', 'Safari', 11),
    (1, '⠁⠍⠊⠛⠕', 'amigo', 'braille-a-texto', 5, 5, NOW() - INTERVAL '10 minutes', 'Desktop', 'Chrome', 9);

-- 4. Verificar las conversiones insertadas
SELECT 
    c.id,
    c.texto_original,
    c.resultado,
    c.tipo,
    c.fecha,
    u.username
FROM conversiones c
JOIN users u ON c.user_id = u.id
ORDER BY c.fecha DESC;

-- 5. Verificar estadísticas
SELECT 
    u.username,
    COUNT(*) as total_conversiones,
    COUNT(CASE WHEN c.tipo = 'texto-a-braille' THEN 1 END) as texto_braille,
    COUNT(CASE WHEN c.tipo = 'braille-a-texto' THEN 1 END) as braille_texto,
    SUM(c.longitud_original) as caracteres_convertidos
FROM conversiones c
JOIN users u ON c.user_id = u.id
GROUP BY u.username;

-- ==========================================
-- CONSULTAS ÚTILES PARA DEBUGGING
-- ==========================================

-- Ver todos los usuarios
SELECT id, username, email, fecha_registro, ultima_sesion FROM users;

-- Ver conversiones recientes (últimas 10)
SELECT 
    c.id,
    u.username,
    c.tipo,
    SUBSTRING(c.texto_original, 1, 30) as texto_preview,
    c.fecha
FROM conversiones c
JOIN users u ON c.user_id = u.id
ORDER BY c.fecha DESC
LIMIT 10;

-- Contar conversiones por usuario
SELECT 
    u.username,
    COUNT(c.id) as total_conversiones
FROM users u
LEFT JOIN conversiones c ON u.id = c.user_id
GROUP BY u.username;

-- Ver conversiones de un usuario específico (reemplaza 'testuser' con el username)
SELECT 
    c.id,
    c.texto_original,
    c.resultado,
    c.tipo,
    c.fecha,
    c.dispositivo,
    c.navegador
FROM conversiones c
JOIN users u ON c.user_id = u.id
WHERE u.username = 'testuser'
ORDER BY c.fecha DESC;

-- Eliminar todas las conversiones de prueba (usar con precaución)
-- DELETE FROM conversiones WHERE user_id IN (SELECT id FROM users WHERE email LIKE '%@example.com');

-- Eliminar usuarios de prueba (usar con precaución - eliminará también sus conversiones por CASCADE)
-- DELETE FROM users WHERE email LIKE '%@example.com';
