# Aprendizajes - 2025-07-20 23:16

## Lecciones Aprendidas del Proyecto DAPR

### 1. Configuración de Microservicios con DAPR

#### Service Discovery Automático
- **Aprendizaje**: DAPR proporciona service discovery automático usando App IDs
- **Beneficio**: No es necesario configurar URLs hardcodeadas
- **Patrón**: Usar `dapr.app-id` en `application.properties`
- **Ejemplo**: `pokemon-service` y `user-app-service`

#### Configuración de Puertos
- **Aprendizaje**: Es crucial configurar puertos específicos para evitar conflictos
- **Patrón**: Usar rangos consecutivos (8086-8089)
- **Configuración**: `quarkus.http.port` y `%test.quarkus.http.port`

### 2. Logging Exhaustivo para Desarrollo

#### Configuración de Logs en Quarkus
- **Aprendizaje**: Quarkus permite configuración granular de logging por categoría
- **Patrón**: 
  ```properties
  quarkus.log.category."org.acme.pokemon".level=DEBUG
  quarkus.log.category."org.acme.user".level=DEBUG
  ```
- **Beneficio**: Logs específicos por microservicio

#### Formato de Logs
- **Aprendizaje**: Formato estructurado facilita debugging
- **Patrón**: `%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c{3.}] (%t) %s%e%n`
- **Componentes**: Timestamp, nivel, clase, thread, mensaje, excepción

### 3. Automatización con PowerShell

#### Scripts de Desarrollo
- **Aprendizaje**: PowerShell es efectivo para automatización en Windows
- **Patrón**: Verificación de dependencias antes de ejecutar
- **Beneficio**: Reducción de errores manuales

#### Testing Automatizado
- **Aprendizaje**: Scripts pueden simular testing E2E
- **Patrón**: Función `Test-Endpoint` con métricas de tiempo
- **Beneficio**: Validación rápida de funcionalidad

### 4. Configuración DAPR

#### Archivos YAML
- **Aprendizaje**: Configuración separada facilita mantenimiento
- **Patrón**: Un archivo por microservicio
- **Estructura**: Component + Configuration

#### Observabilidad
- **Aprendizaje**: Zipkin integrado facilita tracing
- **Configuración**: Sampling rate 100% para desarrollo
- **Beneficio**: Trazabilidad completa de requests

### 5. Patrones de Comunicación

#### Service Invocation
- **Aprendizaje**: DAPR abstrae la complejidad de comunicación HTTP
- **Patrón**: Invocar servicios por App ID, no por URL
- **Beneficio**: Resiliencia automática (retry, circuit breaker)

#### Health Checks
- **Aprendizaje**: Endpoints `/hello` son útiles para verificación
- **Patrón**: Implementar en cada microservicio
- **Uso**: Scripts de testing y monitoreo

### 6. Gestión de Dependencias

#### Gradle Wrapper
- **Aprendizaje**: Gradle Wrapper asegura consistencia de versiones
- **Beneficio**: No requiere instalación global de Gradle
- **Patrón**: Incluir en cada proyecto

#### Dependencias DAPR
- **Aprendizaje**: DAPR SDK para Java facilita integración
- **Configuración**: Añadir en `build.gradle`
- **Beneficio**: APIs nativas para DAPR

### 7. Desarrollo y Testing

#### Hot Reload
- **Aprendizaje**: `quarkusDev` proporciona hot reload automático
- **Beneficio**: Desarrollo más rápido
- **Patrón**: Usar en desarrollo, build para producción

#### Testing Strategy
- **Aprendizaje**: Separar tests unitarios de integración
- **Patrón**: Tests unitarios con JUnit, integración con scripts
- **Beneficio**: Feedback rápido en desarrollo

### 8. Documentación

#### README Exhaustivo
- **Aprendizaje**: Documentación clara acelera onboarding
- **Estructura**: Prerrequisitos, instalación, ejecución, testing
- **Beneficio**: Reducción de tiempo de setup

#### Changelog Estructurado
- **Aprendizaje**: Changelog organizado facilita seguimiento
- **Estructura**: changes.md, technical-decision.md, learnings.md
- **Beneficio**: Historial de decisiones y aprendizajes

### 9. Mejores Prácticas Identificadas

#### Configuración
- **Puertos específicos** para cada servicio
- **Logging estructurado** para debugging
- **Health checks** en cada microservicio
- **Documentación actualizada** con cada cambio

#### Desarrollo
- **Scripts de automatización** para tareas repetitivas
- **Testing exhaustivo** antes de commits
- **Logs detallados** durante desarrollo
- **Configuración separada** por servicio

#### Operaciones
- **Verificación de dependencias** antes de ejecutar
- **Métricas de performance** en testing
- **Trazabilidad completa** con DAPR
- **Monitoreo de health checks**

### 10. Próximos Pasos Identificados

#### Funcionalidades
- **Persistencia de datos** con DAPR State Management
- **Comunicación asíncrona** con DAPR Pub/Sub
- **Testing de integración** con TestContainers
- **CI/CD pipeline** automatizado

#### Mejoras
- **Métricas de performance** más detalladas
- **Alertas automáticas** para fallos
- **Documentación de API** con OpenAPI
- **Containerización** con Docker

### 11. Comandos Útiles Aprendidos

#### DAPR
```powershell
# Verificar instalación
dapr --version

# Inicializar DAPR
dapr init

# Ver logs de servicio
dapr logs --app-id pokemon-service

# Ver estado de servicios
dapr status
```

#### Quarkus
```powershell
# Desarrollo con hot reload
./gradlew quarkusDev

# Build para producción
./gradlew build

# Tests
./gradlew test
```

#### PowerShell
```powershell
# Verificar puertos en uso
netstat -a -b -n -o | findstr :8086

# Matar proceso por PID
taskkill /PID <pid> /F

# Testing HTTP
Invoke-RestMethod -Uri "http://localhost:8086/pokemon/hello"
```

### 12. Patrones para Futuros Proyectos

#### Arquitectura
- **Service mesh** para microservicios
- **Logging estructurado** desde el inicio
- **Health checks** en cada servicio
- **Configuración externalizada**

#### Desarrollo
- **Scripts de automatización** para tareas comunes
- **Testing exhaustivo** en cada iteración
- **Documentación actualizada** continuamente
- **Changelog estructurado** para seguimiento 