# Decisiones Técnicas - 2025-07-20 23:16

## Análisis del Estado Actual del Proyecto DAPR

### Arquitectura de Microservicios

#### Decisión: Comunicación Síncrona con DAPR
- **Patrón**: Service-to-Service communication usando DAPR Service Invocation
- **Justificación**: Simplicidad para demostración y desarrollo inicial
- **Beneficios**: 
  - Service discovery automático
  - Resiliencia integrada (retry, circuit breaker)
  - Observabilidad automática
  - Desacoplamiento de URLs directas

#### Configuración de Puertos
- **Pokemon Service**: 8086 (app) / 8087 (tests)
- **User-App Service**: 8088 (app) / 8089 (tests)
- **Justificación**: Evitar conflictos y facilitar debugging
- **Patrón**: Puertos consecutivos para servicios relacionados

### Logging Exhaustivo

#### Decisión: Logging Detallado para Desarrollo
- **Nivel**: DEBUG para servicios específicos, INFO para general
- **Formato**: Timestamp, nivel, clase, thread, mensaje
- **Justificación**: Facilitar debugging en desarrollo
- **Beneficios**:
  - Trazabilidad completa de requests
  - Identificación rápida de problemas
  - Documentación automática del flujo

### Scripts de Automatización

#### Decisión: PowerShell para Automatización
- **Lenguaje**: PowerShell (nativo de Windows)
- **Justificación**: Compatibilidad con entorno de desarrollo
- **Funcionalidades**:
  - Verificación de dependencias
  - Inicio automático de servicios
  - Testing exhaustivo
  - Logs detallados

### Configuración DAPR

#### Decisión: Configuración YAML Separada
- **Archivos**: `dapr-pokemon.yaml`, `dapr-user-app.yaml`
- **Justificación**: Separación de responsabilidades
- **Beneficios**:
  - Configuración específica por servicio
  - Fácil mantenimiento
  - Escalabilidad para nuevos servicios

#### Configuración de Observabilidad
- **Zipkin**: Configurado para tracing
- **Sampling Rate**: 100% para desarrollo
- **Justificación**: Trazabilidad completa en desarrollo

### Tecnologías Seleccionadas

#### Stack Tecnológico
1. **Java 21**: Versión LTS más reciente
2. **Quarkus**: Framework nativo para cloud
3. **DAPR**: Service mesh para microservicios
4. **Gradle**: Build tool moderno y eficiente

#### Justificación de Selección
- **Java 21**: Performance y características modernas
- **Quarkus**: Fast startup, low memory footprint
- **DAPR**: Abstracción de infraestructura distribuida
- **Gradle**: Flexibilidad y performance

### Patrones de Desarrollo

#### 1. Service Discovery
- **DAPR App ID**: Identificadores únicos por servicio
- **Beneficio**: No requiere configuración manual de URLs

#### 2. Health Checks
- **Endpoints**: `/hello` en cada servicio
- **Propósito**: Verificación de disponibilidad
- **Uso**: Scripts de testing y monitoreo

#### 3. Error Handling
- **Logging**: Errores completos con stack traces
- **Resiliencia**: DAPR maneja retries automáticamente
- **Observabilidad**: Trazabilidad de errores

### Consideraciones de Escalabilidad

#### Horizontal Scaling
- **DAPR**: Soporte nativo para múltiples instancias
- **Load Balancing**: Automático a través de DAPR
- **Service Discovery**: Actualización automática

#### Vertical Scaling
- **Quarkus**: Optimizado para recursos limitados
- **Memory**: Configuración para JVM nativa
- **Performance**: Fast startup y low footprint

### Próximas Decisiones Técnicas

#### 1. Persistencia de Datos
- **Opción A**: DAPR State Management
- **Opción B**: Base de datos externa
- **Criterio**: Complejidad vs funcionalidad

#### 2. Comunicación Asíncrona
- **Opción A**: DAPR Pub/Sub
- **Opción B**: Message broker externo
- **Criterio**: Patrones de integración

#### 3. Testing Strategy
- **Unit Tests**: JUnit con Quarkus
- **Integration Tests**: TestContainers
- **E2E Tests**: Scripts de PowerShell

### Métricas de Calidad

#### Código
- **Coverage**: Objetivo 80%+
- **Performance**: Response time < 100ms
- **Reliability**: 99.9% uptime

#### Desarrollo
- **Time to Market**: Scripts automatizados
- **Debugging**: Logs exhaustivos
- **Documentation**: README actualizado 