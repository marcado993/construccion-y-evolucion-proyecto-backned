# 📝 Guía de Implementación - Base de Datos Supabase

## 🎯 Pasos para configurar la base de datos

### 1️⃣ **Acceder a Supabase**
1. Ve a [https://supabase.com](https://supabase.com)
2. Inicia sesión con tu cuenta
3. Selecciona tu proyecto o crea uno nuevo
4. Ve a **SQL Editor** en el menú lateral

---

### 2️⃣ **Ejecutar el script SQL**
1. Copia el contenido de `src/main/resources/schema.sql`
2. Pégalo en el SQL Editor de Supabase
3. Haz clic en **Run** (botón verde)
4. Verifica que todas las tablas se crearon correctamente

---

### 3️⃣ **Habilitar Row Level Security (RLS)**

En Supabase SQL Editor, ejecuta:

```sql
-- Habilitar RLS en tablas sensibles
ALTER TABLE conversiones ENABLE ROW LEVEL SECURITY;
ALTER TABLE senaleticas ENABLE ROW LEVEL SECURITY;
ALTER TABLE estadisticas_uso ENABLE ROW LEVEL SECURITY;

-- Policy: Los usuarios solo ven sus propias conversiones
CREATE POLICY "users_view_own_conversions"
ON conversiones FOR SELECT
USING (auth.uid()::text = user_id::text);

CREATE POLICY "users_insert_own_conversions"
ON conversiones FOR INSERT
WITH CHECK (auth.uid()::text = user_id::text);

CREATE POLICY "users_delete_own_conversions"
ON conversiones FOR DELETE
USING (auth.uid()::text = user_id::text);

-- Policy: Los usuarios solo ven su propia señalética
CREATE POLICY "users_view_own_senaleticas"
ON senaleticas FOR SELECT
USING (auth.uid()::text = user_id::text);

CREATE POLICY "users_insert_own_senaleticas"
ON senaleticas FOR INSERT
WITH CHECK (auth.uid()::text = user_id::text);

CREATE POLICY "users_delete_own_senaleticas"
ON senaleticas FOR DELETE
USING (auth.uid()::text = user_id::text);

-- Policy: Estadísticas privadas por usuario
CREATE POLICY "users_view_own_stats"
ON estadisticas_uso FOR SELECT
USING (auth.uid()::text = user_id::text);
```

---

### 4️⃣ **Crear Storage Bucket para archivos PDF/PNG**

1. Ve a **Storage** en Supabase
2. Crea un nuevo bucket llamado: `senaletica-files`
3. Configura como **Public** (para descargas directas)
4. Políticas de acceso:

```sql
-- Policy para subir archivos (solo usuarios autenticados)
CREATE POLICY "Authenticated users upload files"
ON storage.objects FOR INSERT
TO authenticated
WITH CHECK (bucket_id = 'senaletica-files' AND auth.uid()::text = owner);

-- Policy para ver archivos propios
CREATE POLICY "Users view own files"
ON storage.objects FOR SELECT
TO authenticated
USING (bucket_id = 'senaletica-files' AND auth.uid()::text = owner);

-- Policy para eliminar archivos propios
CREATE POLICY "Users delete own files"
ON storage.objects FOR DELETE
TO authenticated
USING (bucket_id = 'senaletica-files' AND auth.uid()::text = owner);
```

---

### 5️⃣ **Obtener credenciales de conexión**

1. Ve a **Settings** > **Database**
2. Copia las credenciales de conexión:
   - **Host**: `db.xxxxx.supabase.co`
   - **Database name**: `postgres`
   - **Port**: `5432`
   - **User**: `postgres`
   - **Password**: [tu contraseña]

3. Actualiza tu archivo `.env` en el backend:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://db.xxxxx.supabase.co:5432/postgres
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=tu_password_aqui
```

---

### 6️⃣ **Verificar la conexión desde Spring Boot**

Ejecuta en el backend:

```bash
mvn clean install
mvn spring-boot:run
```

Revisa los logs para confirmar:
```
✅ HikariPool-1 - Starting...
✅ HikariPool-1 - Start completed.
```

---

## 🧪 **Testing de endpoints**

### **POST /api/convertir** (Conversión con guardado en BD)

```bash
curl -X POST http://localhost:8080/api/convertir?userId=1&guardar=true \
  -H "Content-Type: application/json" \
  -d '{
    "texto": "Hola mundo",
    "tipo": "texto-a-braille"
  }'
```

Respuesta esperada:
```json
{
  "textoOriginal": "Hola mundo",
  "resultado": "⠨⠓⠕⠇⠁ ⠍⠥⠝⠙⠕",
  "tipo": "texto-a-braille",
  "exito": true,
  "mensaje": "Conversión exitosa"
}
```

### **GET /api/historial** (Ver historial)

```bash
curl http://localhost:8080/api/historial?userId=1
```

Respuesta esperada:
```json
[
  {
    "id": 1,
    "textoOriginal": "Hola mundo",
    "resultado": "⠨⠓⠕⠇⠁ ⠍⠥⠝⠙⠕",
    "tipo": "texto-a-braille",
    "fecha": "2024-01-15T10:30:00"
  }
]
```

### **GET /api/historial/recientes** (Últimas 10 conversiones)

```bash
curl http://localhost:8080/api/historial/recientes?userId=1
```

### **DELETE /api/historial/{id}** (Eliminar del historial)

```bash
curl -X DELETE http://localhost:8080/api/historial/1?userId=1
```

---

## 📊 **Arquitectura Final**

```
┌─────────────────────────────────────────────────────────┐
│                    FRONTEND (Next.js)                    │
│  - Conversión instantánea (0ms) con braille-converter   │
│  - Opcional: Guardar en backend si usuario lo desea     │
└─────────────────────┬───────────────────────────────────┘
                      │
                      │ HTTP/REST API
                      ▼
┌─────────────────────────────────────────────────────────┐
│              BACKEND (Spring Boot :8080)                 │
│  - BrailleController: /api/convertir, /api/historial    │
│  - BrailleService: Lógica de conversión                 │
│  - ConversionService: Gestión de historial              │
└─────────────────────┬───────────────────────────────────┘
                      │
                      │ JDBC
                      ▼
┌─────────────────────────────────────────────────────────┐
│              SUPABASE (PostgreSQL)                       │
│  Tablas:                                                 │
│  - users (autenticación)                                 │
│  - conversiones (historial) ⭐                           │
│  - senaleticas (PDFs generados)                          │
│  - plantillas_senaletica (prediseños)                    │
│  - estadisticas_uso (métricas)                           │
│  Storage:                                                │
│  - senaletica-files/ (PDFs y PNGs)                       │
└─────────────────────────────────────────────────────────┘
```

---

## ✅ **Checklist de implementación**

- [ ] Ejecutar `schema.sql` en Supabase SQL Editor
- [ ] Habilitar Row Level Security (RLS)
- [ ] Crear Storage Bucket `senaletica-files`
- [ ] Actualizar `.env` con credenciales de Supabase
- [ ] Compilar backend: `mvn clean install`
- [ ] Ejecutar backend: `mvn spring-boot:run`
- [ ] Probar endpoint `/api/convertir`
- [ ] Verificar que se guarden datos en tabla `conversiones`
- [ ] Probar endpoint `/api/historial`
- [ ] Integrar frontend con API del backend

---

## 🎯 **Próximos pasos**

1. **Autenticación real**: Integrar Supabase Auth o Spring Security con JWT
2. **Frontend integration**: Crear hook `useConversionHistory()` para llamar al backend
3. **Señalética service**: Implementar generación de PDFs en backend con iText/PDFBox
4. **Dashboard**: Mostrar estadísticas con gráficos (Chart.js/Recharts)
5. **Testing**: JUnit para servicios, Jest para frontend
