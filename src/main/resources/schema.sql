-- ==========================================
-- SCHEMA SQL PARA SUPABASE (PostgreSQL)
-- Sistema de Conversión Braille
-- ==========================================

-- 1. Tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre_completo VARCHAR(100),
    rol VARCHAR(20) DEFAULT 'usuario',
    activo BOOLEAN DEFAULT true,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultima_sesion TIMESTAMP,
    CONSTRAINT check_email CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$'),
    CONSTRAINT check_rol CHECK (rol IN ('usuario', 'admin'))
);

CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);

-- 2. Tabla de conversiones (HISTORIAL)
CREATE TABLE IF NOT EXISTS conversiones (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    texto_original TEXT NOT NULL,
    resultado TEXT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    longitud_original INTEGER,
    longitud_resultado INTEGER,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip_origen VARCHAR(45),
    dispositivo VARCHAR(100),
    navegador VARCHAR(100),
    tiempo_conversion_ms INTEGER,
    CONSTRAINT check_tipo CHECK (tipo IN ('texto-a-braille', 'braille-a-texto'))
);

CREATE INDEX IF NOT EXISTS idx_conversiones_user_fecha ON conversiones(user_id, fecha DESC);
CREATE INDEX IF NOT EXISTS idx_conversiones_tipo ON conversiones(tipo);
CREATE INDEX IF NOT EXISTS idx_conversiones_fecha ON conversiones(fecha DESC);
CREATE INDEX IF NOT EXISTS idx_conversiones_user_tipo ON conversiones(user_id, tipo);

-- 3. Tabla de señalética generada
CREATE TABLE IF NOT EXISTS senaleticas (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    titulo VARCHAR(100) NOT NULL,
    texto_original TEXT NOT NULL,
    texto_braille TEXT NOT NULL,
    tipo_senaletica VARCHAR(50),
    formato_salida VARCHAR(10) DEFAULT 'PDF',
    tamano_fuente INTEGER DEFAULT 24,
    color_fondo VARCHAR(7) DEFAULT '#FFFFFF',
    color_texto VARCHAR(7) DEFAULT '#000000',
    alto_contraste BOOLEAN DEFAULT true,
    url_archivo VARCHAR(500),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    descargas INTEGER DEFAULT 0,
    ultima_descarga TIMESTAMP,
    CONSTRAINT check_formato CHECK (formato_salida IN ('PDF', 'PNG', 'SVG')),
    CONSTRAINT check_tipo_senaletica CHECK (tipo_senaletica IN (
        'ascensor', 'puerta', 'habitacion', 'baño', 'emergencia', 
        'escalera', 'salida', 'entrada', 'medicamento', 'alimento', 'personalizado'
    ))
);

CREATE INDEX IF NOT EXISTS idx_senaleticas_user_fecha ON senaleticas(user_id, fecha_creacion DESC);
CREATE INDEX IF NOT EXISTS idx_senaleticas_tipo ON senaleticas(tipo_senaletica);
CREATE INDEX IF NOT EXISTS idx_senaleticas_descargas ON senaleticas(descargas DESC);

-- 4. Tabla de plantillas de señalética
CREATE TABLE IF NOT EXISTS plantillas_senaletica (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    descripcion TEXT,
    tipo VARCHAR(50) NOT NULL,
    icono VARCHAR(50),
    textos_predefinidos JSONB,
    configuracion_diseno JSONB,
    es_publica BOOLEAN DEFAULT true,
    creado_por BIGINT REFERENCES users(id) ON DELETE SET NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    veces_usada INTEGER DEFAULT 0
);

-- 5. Tabla de estadísticas de uso
CREATE TABLE IF NOT EXISTS estadisticas_uso (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    fecha DATE DEFAULT CURRENT_DATE,
    conversiones_texto_braille INTEGER DEFAULT 0,
    conversiones_braille_texto INTEGER DEFAULT 0,
    senaleticas_generadas INTEGER DEFAULT 0,
    caracteres_convertidos INTEGER DEFAULT 0,
    tiempo_sesion_minutos INTEGER DEFAULT 0,
    UNIQUE(user_id, fecha)
);

CREATE INDEX IF NOT EXISTS idx_estadisticas_fecha ON estadisticas_uso(fecha DESC);
CREATE INDEX IF NOT EXISTS idx_estadisticas_user ON estadisticas_uso(user_id);

-- 6. Diccionario Braille (opcional, ya está en código)
CREATE TABLE IF NOT EXISTS diccionario_braille (
    id SERIAL PRIMARY KEY,
    caracter VARCHAR(5) UNIQUE NOT NULL,
    braille VARCHAR(5) NOT NULL,
    puntos VARCHAR(10),
    categoria VARCHAR(20),
    descripcion TEXT,
    es_especial BOOLEAN DEFAULT false,
    CONSTRAINT check_categoria CHECK (categoria IN ('letra', 'numero', 'signo', 'acento', 'especial'))
);

-- ==========================================
-- DATOS INICIALES
-- ==========================================

-- Plantillas predefinidas
INSERT INTO plantillas_senaletica (nombre, tipo, descripcion, icono, textos_predefinidos, configuracion_diseno) VALUES
('Ascensor Estándar', 'ascensor', 'Señalética estándar para ascensores con 5 pisos', 'ArrowUpDown',
 '["Ascensor", "Piso 1", "Piso 2", "Piso 3", "Piso 4", "Piso 5"]'::jsonb,
 '{"fontSize": 28, "padding": 30, "backgroundColor": "#1E40AF", "textColor": "#FFFFFF"}'::jsonb),
 
('Baño Accesible', 'baño', 'Señalización para baños con accesibilidad', 'Bath',
 '["Baño", "Hombres", "Mujeres", "Accesible"]'::jsonb,
 '{"fontSize": 32, "padding": 25, "backgroundColor": "#059669", "textColor": "#FFFFFF"}'::jsonb),
 
('Salida de Emergencia', 'emergencia', 'Señalética de emergencia y evacuación', 'AlertTriangle',
 '["Salida", "Emergencia", "No Bloquear", "Ruta de Evacuación"]'::jsonb,
 '{"fontSize": 36, "padding": 40, "backgroundColor": "#DC2626", "textColor": "#FFFFFF"}'::jsonb),

('Habitación Hospital', 'habitacion', 'Numeración de habitaciones hospitalarias', 'Bed',
 '["Habitación", "101", "102", "103"]'::jsonb,
 '{"fontSize": 30, "padding": 28, "backgroundColor": "#7C3AED", "textColor": "#FFFFFF"}'::jsonb),

('Medicamento', 'medicamento', 'Etiquetas para medicamentos', 'Pill',
 '["Medicamento", "Tomar con agua", "Antes de comer"]'::jsonb,
 '{"fontSize": 20, "padding": 15, "backgroundColor": "#F59E0B", "textColor": "#000000"}'::jsonb)
ON CONFLICT (nombre) DO NOTHING;

-- Diccionario Braille español completo
INSERT INTO diccionario_braille (caracter, braille, puntos, categoria, descripcion) VALUES
-- Letras a-j (primera serie)
('a', '⠁', '1', 'letra', 'Letra a'),
('b', '⠃', '1-2', 'letra', 'Letra b'),
('c', '⠉', '1-4', 'letra', 'Letra c'),
('d', '⠙', '1-4-5', 'letra', 'Letra d'),
('e', '⠑', '1-5', 'letra', 'Letra e'),
('f', '⠋', '1-2-4', 'letra', 'Letra f'),
('g', '⠛', '1-2-4-5', 'letra', 'Letra g'),
('h', '⠓', '1-2-5', 'letra', 'Letra h'),
('i', '⠊', '2-4', 'letra', 'Letra i'),
('j', '⠚', '2-4-5', 'letra', 'Letra j'),

-- Letras k-t (segunda serie: primera + punto 3)
('k', '⠅', '1-3', 'letra', 'Letra k'),
('l', '⠇', '1-2-3', 'letra', 'Letra l'),
('m', '⠍', '1-3-4', 'letra', 'Letra m'),
('n', '⠝', '1-3-4-5', 'letra', 'Letra n'),
('o', '⠕', '1-3-5', 'letra', 'Letra o'),
('p', '⠏', '1-2-3-4', 'letra', 'Letra p'),
('q', '⠟', '1-2-3-4-5', 'letra', 'Letra q'),
('r', '⠗', '1-2-3-5', 'letra', 'Letra r'),
('s', '⠎', '2-3-4', 'letra', 'Letra s'),
('t', '⠞', '2-3-4-5', 'letra', 'Letra t'),

-- Letras u-z (tercera serie: primera + puntos 3 y 6)
('u', '⠥', '1-3-6', 'letra', 'Letra u'),
('v', '⠧', '1-2-3-6', 'letra', 'Letra v'),
('w', '⠺', '2-4-5-6', 'letra', 'Letra w'),
('x', '⠭', '1-3-4-6', 'letra', 'Letra x'),
('y', '⠽', '1-3-4-5-6', 'letra', 'Letra y'),
('z', '⠵', '1-3-5-6', 'letra', 'Letra z'),

-- Letra especial española
('ñ', '⠻', '1-2-4-5-6', 'letra', 'Letra eñe'),

-- Vocales acentuadas
('á', '⠷', '1-2-3-5-6', 'acento', 'A con acento'),
('é', '⠮', '2-3-4-6', 'acento', 'E con acento'),
('í', '⠌', '3-4', 'acento', 'I con acento'),
('ó', '⠾', '1-3-4-6', 'acento', 'O con acento'),
('ú', '⠾', '1-3-4-6', 'acento', 'U con acento'),
('ü', '⠳', '1-2-5-6', 'acento', 'U con diéresis'),

-- Signos de puntuación
(',', '⠂', '2', 'signo', 'Coma'),
('.', '⠲', '2-5-6', 'signo', 'Punto'),
('?', '⠢', '2-6', 'signo', 'Signo de interrogación'),
('!', '⠖', '2-3-5', 'signo', 'Signo de exclamación'),
(';', '⠆', '2-3', 'signo', 'Punto y coma'),
(':', '⠒', '2-5', 'signo', 'Dos puntos'),

-- Indicador de mayúscula
('⠨', '⠨', '4-6', 'especial', 'Indicador de mayúscula')
ON CONFLICT (caracter) DO NOTHING;

-- ==========================================
-- COMENTARIOS FINALES
-- ==========================================

-- Para habilitar Row Level Security (RLS) en Supabase:
-- ALTER TABLE conversiones ENABLE ROW LEVEL SECURITY;
-- ALTER TABLE senaleticas ENABLE ROW LEVEL SECURITY;
-- ALTER TABLE estadisticas_uso ENABLE ROW LEVEL SECURITY;

-- Policies de ejemplo (ejecutar en Supabase Dashboard):
-- CREATE POLICY "Users view own data" ON conversiones FOR SELECT USING (auth.uid()::text = user_id::text);
