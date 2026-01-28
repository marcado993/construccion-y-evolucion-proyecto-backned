# Configuración de Supabase para el Backend

## 📋 Información de Conexión

Tu base de datos Supabase está configurada con **Transaction Pooler**:

```
Host: aws-1-us-east-1.pooler.supabase.com
Port: 6543
Database: postgres
User: postgres.bnfqxgzencmgqfiryrww
Pool Mode: transaction
```

## ⚠️ Importante: Transaction Pooler

El modo `transaction` de Supabase **NO SOPORTA PREPARE STATEMENTS**. Por eso, la configuración incluye:

```properties
spring.datasource.url=jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?prepareThreshold=0
```

El parámetro `prepareThreshold=0` desactiva los prepared statements en JDBC.

## 🔧 Configuración Paso a Paso

### 1. Crea el archivo `.env`

```bash
cd backend
cp .env.example .env
```

### 2. Edita `.env` con tu contraseña

Reemplaza `[YOUR_PASSWORD]` con tu contraseña real de Supabase:

```bash
SUPABASE_PASSWORD=tu_contraseña_real_aqui
```

### 3. Verifica `application.properties`

El archivo ya está configurado correctamente:

```properties
spring.datasource.url=jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?prepareThreshold=0
spring.datasource.username=postgres.bnfqxgzencmgqfiryrww
spring.datasource.password=${SUPABASE_PASSWORD:[YOUR_PASSWORD]}
```

### 4. Ejecuta el backend

```bash
mvn clean install
mvn spring-boot:run
```

## 🔄 Alternativa: Session Pooler

Si necesitas usar PREPARE statements (mejor rendimiento), puedes cambiar a **Session Pooler**:

```properties
# Puerto 5432 en lugar de 6543
spring.datasource.url=jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:5432/postgres

# Puedes remover prepareThreshold=0
```

**Nota**: Session Pooler mantiene conexiones persistentes y es ideal para aplicaciones con muchas queries preparadas.

## 🗄️ Inicialización de la Base de Datos

Las tablas se crearán automáticamente con `spring.jpa.hibernate.ddl-auto=update`.

Si prefieres ejecutar el script SQL manualmente:

```bash
# Conéctate a Supabase SQL Editor y ejecuta:
# backend/src/main/resources/schema.sql
```

## 🧪 Prueba la Conexión

```bash
curl http://localhost:8080/api/test
```

Deberías ver: `{"status":"ok","message":"Backend funcionando"}`

## 📚 Endpoints Disponibles

### Conversiones
- `POST /api/convertir` - Convierte texto ↔ Braille
- `GET /api/historial?userId={id}` - Historial completo
- `GET /api/historial/tipo/{tipo}?userId={id}` - Filtrar por tipo
- `DELETE /api/historial?userId={id}` - Limpiar historial
- `GET /api/historial/estadisticas?userId={id}` - Estadísticas

### Señalética
- `POST /api/senaletica` - Crear señalética
- `GET /api/senaletica?userId={id}` - Listar señaléticas
- `GET /api/senaletica/{id}` - Obtener señalética
- `POST /api/senaletica/{id}/descarga` - Registrar descarga
- `DELETE /api/senaletica/{id}` - Eliminar señalética
- `GET /api/senaletica/populares?userId={id}` - Más descargadas

## 🛠️ Troubleshooting

### Error: "PREPARE statements not supported"

✅ **Solución**: Verifica que tengas `prepareThreshold=0` en la URL de conexión.

### Error: "Connection refused"

✅ **Solución**: Verifica que:
1. El puerto sea 6543 (Transaction Pooler)
2. Tu IP esté en la whitelist de Supabase
3. La contraseña sea correcta

### Error: "Authentication failed"

✅ **Solución**: 
1. Verifica que `SUPABASE_PASSWORD` esté en `.env`
2. La contraseña NO debe tener comillas
3. El usuario es `postgres.bnfqxgzencmgqfiryrww`

## 🔐 Seguridad

- ✅ `.env` está en `.gitignore`
- ✅ Nunca subas contraseñas a Git
- ✅ Usa variables de entorno en producción
- ✅ Habilita IP whitelisting en Supabase

## 📖 Más Información

- [Supabase Connection Pooling](https://supabase.com/docs/guides/database/connecting-to-postgres#connection-pooler)
- [Spring Boot + PostgreSQL](https://spring.io/guides/gs/accessing-data-jpa/)
