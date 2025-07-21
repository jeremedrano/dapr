# Decisiones Técnicas - 2025-07-20 23:47

## Migración de HTTP Directo a DAPR Service Invocation

### Decisión Principal
Refactorizar completamente el `PokemonClient.java` para usar DAPR Service Invocation en lugar de comunicación HTTP directa.

### Justificación Técnica

#### Problema Original
1. **Comunicación HTTP Directa**: El cliente usaba `HttpClient` para comunicarse directamente con el Pokemon service
2. **URLs Hardcodeadas**: Dependencia directa de `http://localhost:8086`
3. **Falta de Observabilidad**: No aprovechaba las capacidades de DAPR
4. **Logging Insuficiente**: No explicaba cómo funciona DAPR paso a paso

#### Análisis de Alternativas

##### Opción 1: Mantener HTTP Directo
- ❌ **Pros**: Simplicidad, menos dependencias
- ❌ **Contras**: 
  - No aprovecha DAPR
  - URLs hardcodeadas
  - Sin service discovery
  - Sin resiliencia automática
  - Sin observabilidad integrada

##### Opción 2: Migrar a DAPR Service Invocation
- ✅ **Pros**:
  - Service discovery automático
  - Resiliencia integrada (retry, circuit breaker)
  - Observabilidad automática
  - Desacoplamiento de URLs
  - Load balancing automático
- ❌ **Contras**: 
  - Añade dependencias
  - Curva de aprendizaje inicial

##### Opción 3: Usar DAPR con InvokeMethodRequest Builder
- ❌ **Pros**: API más explícita
- ❌ **Contras**: 
  - API no disponible en versión actual
  - Más complejidad innecesaria

### Decisión Tomada: Opción 2

#### Criterios de Selección
1. **Aprovechamiento de DAPR**: Maximizar el uso de las capacidades de DAPR
2. **Simplicidad**: Usar la API más directa de DAPR
3. **Educativo**: Proporcionar logs explicativos paso a paso
4. **Mantenibilidad**: Código más limpio y desacoplado

### Implementación Técnica

#### 1. Dependencias DAPR
```gradle
implementation 'io.dapr:dapr-sdk:1.10.0'
implementation 'io.dapr:dapr-sdk-autogen:1.10.0'
```

**Justificación**: Versión estable y compatible con Java 21

#### 2. API DAPR Seleccionada
```java
daprClient.invokeMethod(appId, method, data, httpExtension, responseType)
```

**Justificación**: 
- API más directa y simple
- Compatible con la versión actual de DAPR
- No requiere builders complejos

#### 3. Configuración de App ID
```java
private static final String POKEMON_SERVICE_APP_ID = "pokemon-service";
```

**Justificación**: 
- Consistente con configuración DAPR
- Elimina dependencia de URLs hardcodeadas
- Facilita service discovery

### Patrones de Diseño Aplicados

#### 1. Service Invocation Pattern
- **Propósito**: Comunicación síncrona entre servicios
- **Implementación**: DAPR Service Invocation
- **Beneficio**: Abstracción de la comunicación HTTP

#### 2. Dependency Injection
- **Propósito**: Inyección del DaprClient
- **Implementación**: Constructor injection
- **Beneficio**: Testabilidad y flexibilidad

#### 3. Logging Strategy Pattern
- **Propósito**: Logs explicativos paso a paso
- **Implementación**: Logs estructurados con emojis
- **Beneficio**: Facilita debugging y aprendizaje

### Consideraciones de Performance

#### Ventajas de DAPR
1. **Service Discovery**: No requiere DNS lookups
2. **Connection Pooling**: Manejo automático de conexiones
3. **Load Balancing**: Distribución automática de carga
4. **Caching**: Posibilidad de cache automático

#### Overhead Mínimo
1. **Sidecar Communication**: Comunicación local con sidecar DAPR
2. **Serialization**: JSON serialization/deserialization
3. **Tracing**: Headers de observabilidad mínimos

### Configuración de Logging

#### Niveles de Log
- **INFO**: Pasos principales del proceso DAPR
- **DEBUG**: Detalles de request/response
- **ERROR**: Errores con contexto completo

#### Formato de Logs
- **Estructura**: PASO X: Descripción
- **Emojis**: Para facilitar identificación visual
- **Contexto**: Información técnica relevante

### Manejo de Errores

#### Estrategia Implementada
1. **Try-Catch**: Captura de excepciones DAPR
2. **Logging Detallado**: Contexto completo del error
3. **RuntimeException**: Re-throw con contexto
4. **Null Checks**: Validación de respuestas

#### Tipos de Error Manejados
1. **Service Unavailable**: Pokemon service no responde
2. **Network Errors**: Problemas de comunicación
3. **Serialization Errors**: Problemas con JSON
4. **DAPR Errors**: Errores del sidecar DAPR

### Testing Strategy

#### Unit Testing
- **Mock DaprClient**: Para tests unitarios
- **Test Scenarios**: Éxito, error, timeout
- **Verification**: Logs y comportamiento esperado

#### Integration Testing
- **Real DAPR**: Tests con sidecar real
- **End-to-End**: Comunicación completa
- **Performance**: Tiempos de respuesta

### Métricas de Calidad

#### Código
- **Lines of Code**: Reducción de complejidad
- **Dependencies**: Añadidas dependencias DAPR
- **Test Coverage**: Objetivo 80%+

#### Performance
- **Response Time**: < 100ms para requests simples
- **Throughput**: Capacidad de múltiples requests
- **Reliability**: 99.9% uptime

### Próximas Decisiones

#### 1. Observabilidad Avanzada
- **Zipkin Integration**: Tracing distribuido
- **Metrics Collection**: Métricas de performance
- **Alerting**: Alertas automáticas

#### 2. Resiliencia Avanzada
- **Retry Policies**: Configuración de reintentos
- **Circuit Breaker**: Configuración de circuit breaker
- **Timeout Configuration**: Timeouts específicos

#### 3. Testing Avanzado
- **Contract Testing**: Tests de contrato
- **Chaos Testing**: Tests de resiliencia
- **Load Testing**: Tests de carga

### Lecciones Aprendidas

#### 1. API DAPR
- **Versión**: Verificar compatibilidad de API
- **Documentación**: Revisar documentación oficial
- **Ejemplos**: Usar ejemplos oficiales

#### 2. Logging
- **Estructura**: Logs estructurados facilitan debugging
- **Contexto**: Información técnica relevante
- **Educativo**: Logs que explican el proceso

#### 3. Testing
- **Incremental**: Migración paso a paso
- **Verification**: Verificar cada cambio
- **Documentation**: Documentar decisiones 