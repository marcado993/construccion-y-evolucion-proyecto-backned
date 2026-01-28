# Guía de Despliegue en Render

## Archivos creados para Render

- `Dockerfile` - Imagen Docker multi-stage optimizada
- `.dockerignore` - Excluye archivos innecesarios del build
- `render.yaml` - Configuración Blueprint para auto-deploy
- `application.properties` - Actualizado para usar variables de entorno

## Pasos para desplegar en Render

### 1. Push de archivos al repositorio
```bash
git add .
git commit -m "Add Render deployment configuration"
git push origin main
```

### 2. Configuración en Render Dashboard

#### Opción A: Usando Blueprint (Recomendado)
1. Ve a https://dashboard.render.com
2. Click en **"Blueprints"** → **"New Blueprint Instance"**
3. Conecta tu repositorio: `construccion-y-evolucion-proyecto-backned`
4. Render detectará el `render.yaml` automáticamente
5. Configura la variable secreta:
   - `DATABASE_PASSWORD` = tu contraseña de Supabase

#### Opción B: Manual
1. Ve a https://dashboard.render.com
2. Click en **"New +"** → **"Web Service"**
3. Conecta tu repositorio
4. Configura:
   - **Name**: `construccion-backend`
   - **Region**: Oregon (US West)
   - **Branch**: `main`
   - **Runtime**: **Docker**
   - **Instance Type**: Free (o Starter para producción)

5. **Variables de Entorno** (Environment Variables):
   ```
   DATABASE_URL=jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?prepareThreshold=0
   DATABASE_USERNAME=postgres.bnfqxgzencmgqfiryrww
   DATABASE_PASSWORD=[TU_CONTRASEÑA_SUPABASE]
   CORS_ALLOWED_ORIGINS=http://localhost:3000,https://*.onrender.com
   JAVA_TOOL_OPTIONS=-Xmx512m -Xms256m
   ```

6. Click en **"Create Web Service"**

### 3. Verificación

Una vez desplegado, tu API estará disponible en:
```
https://construccion-backend.onrender.com
```

Prueba endpoints como:
- `https://construccion-backend.onrender.com/api/health`
- `https://construccion-backend.onrender.com/api/braille/convert`

### 4. Notas Importantes

#### Plan Free
- ⚠️ El servicio se "duerme" después de 15 minutos de inactividad
- Primera solicitud después de dormir tarda ~30-60 segundos
- Suficiente para desarrollo/pruebas

#### Plan Starter ($7/mes)
- ✅ Servicio siempre activo
- ✅ 512 MB RAM, 0.5 CPU
- ✅ Mejor para producción

#### Auto-deploy
- Cada push a `main` dispara un nuevo deploy automáticamente
- Puedes desactivarlo en configuración si prefieres deploys manuales

### 5. Actualizar frontend

Si tienes un frontend (React, etc.), actualiza la URL del backend:
```javascript
const API_BASE_URL = 'https://construccion-backend.onrender.com';
```

### 6. Logs y Monitoreo

Ver logs en tiempo real:
- Dashboard de Render → Tu servicio → Tab "Logs"
- O usa el CLI de Render

## Troubleshooting

### Error "Out of Memory"
- Aumenta `JAVA_TOOL_OPTIONS`: `-Xmx384m`
- O cambia a plan Starter

### Error de conexión a base de datos
- Verifica `DATABASE_PASSWORD` en variables de entorno
- Verifica que Supabase permite conexiones desde IPs de Render

### Build muy lento
- Normal la primera vez (descarga dependencias Maven)
- Builds subsecuentes son más rápidos (cache)

## Recursos

- [Render Docs - Docker](https://render.com/docs/docker)
- [Render Docs - Environment Variables](https://render.com/docs/environment-variables)
- [Render Docs - Blueprints](https://render.com/docs/infrastructure-as-code)
