# Decisiones Técnicas - 2025-01-27 18:30

## Problema: Error de Conexión DAPR

### Contexto
El `code-user-app` no puede comunicarse con el `pokemon-service` debido a un error de conexión al puerto 3500, cuando debería conectarse al puerto 3502.

### Análisis del Error
```
java.net.ConnectException: Failed to connect to /127.0.0.1:3500
```

### Causa Raíz Identificada
1. **Configuración de Puerto**: El `PokemonClient.java` define `DAPR_HTTP_PORT = "3502"`
2. **Script de Inicio**: `start-services.ps1` inicia el user-app-service en puerto 3502
3. **Cliente DAPR**: Está intentando conectarse al puerto 3500 (por defecto)

### Posibles Soluciones

#### Opción 1: Configurar Variable de Entorno DAPR
- **Ventaja**: Solución estándar de DAPR
- **Desventaja**: Requiere configuración adicional
- **Implementación**: Establecer `DAPR_HTTP_PORT=3502` como variable de entorno

#### Opción 2: Modificar Configuración del Cliente DAPR
- **Ventaja**: Control directo en el código
- **Desventaja**: Menos flexible
- **Implementación**: Configurar el puerto en el DaprClientBuilder

#### Opción 3: Usar Puerto por Defecto
- **Ventaja**: Simplicidad
- **Desventaja**: Requiere cambiar el script de inicio
- **Implementación**: Modificar start-services.ps1 para usar puerto 3500

### Decisión Tomada
**Opción 1**: Configurar variable de entorno DAPR
- Es la práctica recomendada por DAPR
- Mantiene la flexibilidad de configuración
- No requiere cambios en el código de la aplicación

### Implementación Planificada
1. Verificar que el sidecar DAPR esté corriendo en el puerto correcto
2. Configurar la variable de entorno `DAPR_HTTP_PORT=3502`
3. Reiniciar los servicios con la configuración correcta
4. Validar la comunicación entre servicios 