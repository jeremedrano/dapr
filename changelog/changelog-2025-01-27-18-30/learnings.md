# Aprendizajes - 2025-01-27 18:30

## Lecciones Aprendidas sobre DAPR

### 1. Configuración de Puertos DAPR
- **Aprendizaje**: DAPR usa puerto 3500 por defecto para HTTP
- **Implicación**: Si se configura un puerto diferente, debe especificarse explícitamente
- **Mejor Práctica**: Usar variables de entorno para configuración de puertos

### 2. Sidecar DAPR vs Cliente DAPR
- **Aprendizaje**: El cliente DAPR en la aplicación debe conectarse al sidecar DAPR local
- **Confusión**: El puerto configurado en el código no es el puerto del sidecar
- **Aclaración**: 
  - Sidecar DAPR: Corre en puerto 3502 (configurado en start-services.ps1)
  - Cliente DAPR: Debe conectarse al sidecar local

### 3. Variables de Entorno DAPR
- **Aprendizaje**: DAPR respeta variables de entorno específicas
- **Variables Importantes**:
  - `DAPR_HTTP_PORT`: Puerto del sidecar DAPR local
  - `DAPR_GRPC_PORT`: Puerto gRPC del sidecar DAPR local

### 4. Debugging de Comunicación DAPR
- **Aprendizaje**: Los errores de conexión indican problemas de configuración
- **Síntomas Comunes**:
  - `Connection refused`: Sidecar no está corriendo
  - `Failed to connect to /127.0.0.1:3500`: Puerto incorrecto
  - `Service not found`: App ID incorrecto

### 5. Arquitectura DAPR
- **Aprendizaje**: DAPR actúa como intermediario entre servicios
- **Flujo**:
  1. Aplicación → Sidecar DAPR local
  2. Sidecar DAPR local → Sidecar DAPR remoto
  3. Sidecar DAPR remoto → Aplicación destino

### 6. Logs Detallados
- **Aprendizaje**: Los logs exhaustivos son cruciales para debugging
- **Beneficio**: Permiten identificar exactamente dónde falla la comunicación
- **Implementación**: Usar logging detallado en todas las operaciones DAPR

### 7. Configuración de Desarrollo
- **Aprendizaje**: La configuración de desarrollo debe ser explícita
- **Recomendación**: Documentar todos los puertos y configuraciones
- **Herramienta**: Scripts de inicio con configuración clara

## Mejores Prácticas Identificadas
1. **Siempre usar variables de entorno para configuración DAPR**
2. **Documentar la configuración de puertos en scripts de inicio**
3. **Implementar logging detallado para debugging**
4. **Verificar que los sidecars DAPR estén corriendo antes de probar comunicación**
5. **Usar nombres descriptivos para app-ids**
6. **Crear documentación de comandos manuales para entornos con restricciones**
7. **Seguir un orden específico al iniciar servicios DAPR** 