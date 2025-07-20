# Aprendizajes - 2025-01-27 17:42

## Implementación de Comunicación DAPR entre Microservicios

### Conceptos Clave Aprendidos

#### 1. DAPR Service Invocation
- **¿Qué es?**: Mecanismo de DAPR para comunicación síncrona entre servicios
- **Ventajas**: Service discovery automático, retry, observabilidad
- **Configuración**: Requiere configuración en ambos servicios (cliente y servidor)

#### 2. Configuración de Microservicios con DAPR
- **Dependencias**: Necesarias en `build.gradle` para ambos servicios
- **Properties**: Configuración específica en `application.properties`
- **Puertos**: DAPR usa puertos específicos (3500, 3501) para sidecar

#### 3. Arquitectura de Comunicación
- **Cliente-Servidor**: Un servicio invoca, otro responde
- **Desacoplamiento**: Los servicios no necesitan conocer URLs directas
- **Resiliencia**: DAPR maneja automáticamente fallos y reintentos

### Mejores Prácticas Identificadas

#### 1. Nomenclatura de Servicios
- Usar nombres descriptivos para los servicios DAPR
- Mantener consistencia en la nomenclatura
- Documentar claramente qué servicio hace qué

#### 2. Configuración de Endpoints
- Seguir convenciones REST
- Usar paths descriptivos y organizados
- Mantener separación clara entre diferentes funcionalidades

#### 3. Manejo de Errores
- DAPR proporciona manejo automático de errores
- Implementar manejo específico en el código cuando sea necesario
- Usar logging para debugging

### Desafíos Encontrados

#### 1. Configuración Inicial
- **Problema**: Configurar correctamente las dependencias DAPR
- **Solución**: Seguir la documentación oficial y ejemplos

#### 2. Puertos y Configuración
- **Problema**: Asegurar que los puertos no entren en conflicto
- **Solución**: Usar puertos diferentes para cada servicio y DAPR sidecar

#### 3. Testing
- **Problema**: Probar la comunicación entre servicios
- **Solución**: Usar endpoints de prueba y logging detallado

### Herramientas y Recursos Útiles

#### 1. DAPR CLI
- Comando para iniciar servicios con DAPR
- Configuración de sidecar automática
- Logging y debugging integrado

#### 2. Documentación DAPR
- Ejemplos oficiales para Java/Quarkus
- Configuración de Service Invocation
- Mejores prácticas y patrones

#### 3. Quarkus Integration
- Configuración específica para Quarkus
- Dependencias necesarias
- Properties de configuración

### Próximos Pasos Sugeridos

#### 1. Implementar Observabilidad
- Configurar DAPR observability
- Implementar tracing distribuido
- Agregar métricas y logging

#### 2. Testing Automatizado
- Crear tests de integración
- Simular fallos y recuperación
- Validar comportamiento resiliente

#### 3. Escalabilidad
- Probar con múltiples instancias
- Configurar load balancing
- Implementar circuit breakers personalizados 