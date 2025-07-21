# Aprendizajes - DAPR y Microservicios

## Fecha: 2025-07-21 16:15

### 🎯 **Objetivo del Aprendizaje**
Implementar comunicación entre microservicios usando DAPR (Distributed Application Runtime) para aprender sus capacidades y limitaciones.

### 🚀 **Conceptos DAPR Aprendidos**

#### **1. Service Invocation**
- **Definición**: Mecanismo de DAPR para comunicación entre servicios
- **Ventajas**:
  - Service Discovery automático
  - Load Balancing integrado
  - Retry Logic automático
  - Circuit Breaker automático
  - Observabilidad automática

#### **2. Sidecar Pattern**
- **Funcionamiento**: Cada servicio tiene un sidecar DAPR
- **Comunicación**: Los sidecars se comunican entre sí
- **Configuración**: Requiere App IDs únicos y puertos específicos

#### **3. Configuración DAPR**
```yaml
# Ejemplo de configuración
app-id: user-app-service
app-port: 8088
dapr-http-port: 3502
dapr-grpc-port: 50003
```

### 🔧 **Problemas Técnicos Encontrados**

#### **1. Deserialización JSON**
- **Problema**: DAPR intenta deserializar automáticamente
- **Síntomas**: Errores de tipo `Cannot deserialize value of type String from Array`
- **Solución**: Usar `Object.class` + deserialización manual

#### **2. Tipos de Respuesta**
- **Arrays JSON**: Requieren `TypeReference<List<T>>`
- **Objetos JSON**: Requieren `Class<T>`
- **Texto plano**: Problemas de parsing JSON

#### **3. Versionado DAPR**
- **Diferentes APIs**: Versiones tienen métodos diferentes
- **Compatibilidad**: `TypeReference` no disponible en todas las versiones
- **Documentación**: Importante verificar la versión específica

### 🛠️ **Patrones de Solución Aprendidos**

#### **Patrón 1: Arrays JSON**
```java
Object response = daprClient.invokeMethod(
    serviceId, endpoint, null, HttpExtension.GET, Object.class
).block();

String jsonResponse = objectMapper.writeValueAsString(response);
List<T> result = objectMapper.readValue(
    jsonResponse, 
    new TypeReference<List<T>>() {}
);
```

#### **Patrón 2: Objetos JSON**
```java
Object response = daprClient.invokeMethod(
    serviceId, endpoint, null, HttpExtension.GET, Object.class
).block();

String jsonResponse = objectMapper.writeValueAsString(response);
T result = objectMapper.readValue(jsonResponse, T.class);
```

#### **Patrón 3: Logging Exhaustivo**
```java
LOG.info("🔧 PASO X: Descripción del paso");
LOG.info("   - Detalle 1: valor");
LOG.info("   - Detalle 2: valor");
LOG.info("✅ Resultado: descripción");
```

### 📊 **Métricas de Rendimiento**

#### **Tiempos de Respuesta**
- **Directo (sin DAPR)**: ~50ms
- **Con DAPR**: ~100-150ms
- **Overhead DAPR**: ~50-100ms

#### **Funcionalidad**
- **Endpoints funcionando**: 3/4 (75%)
- **Comunicación estable**: ✅
- **Error handling**: ✅

### 🎯 **Lecciones Clave**

#### **1. DAPR es Potente pero Complejo**
- **Pros**: Service discovery, load balancing, observabilidad
- **Contras**: Deserialización compleja, overhead de latencia
- **Uso**: Ideal para microservicios complejos

#### **2. Debugging es Esencial**
- **Logs detallados**: Cruciales para troubleshooting
- **Tracing**: DAPR proporciona tracing automático
- **Métricas**: Importante monitorear overhead

#### **3. Arquitectura Importa**
- **Layered Architecture**: Facilita testing y mantenimiento
- **Separation of Concerns**: Cada capa tiene responsabilidad específica
- **Dependency Injection**: Facilita testing y configuración

### 🔮 **Aplicaciones Futuras**

#### **1. Escenarios Ideales para DAPR**
- Microservicios complejos
- Necesidad de service discovery
- Requerimientos de observabilidad
- Load balancing automático

#### **2. Escenarios Menos Ideales**
- Comunicación simple HTTP
- Performance crítica
- Respuestas de texto plano
- APIs muy simples

#### **3. Mejoras Futuras**
- Actualizar a versión más reciente de DAPR
- Implementar health checks
- Agregar métricas de performance
- Configurar tracing avanzado

### 📚 **Recursos Aprendidos**

#### **Documentación Útil**
- DAPR Service Invocation docs
- Jackson ObjectMapper docs
- Quarkus DAPR integration
- Microservices patterns

#### **Herramientas**
- `dapr list`: Verificar servicios corriendo
- `dapr run`: Ejecutar servicios con DAPR
- `netstat`: Verificar puertos
- Logs de Quarkus: Debugging

### 🎉 **Logros Alcanzados**

1. ✅ **Comunicación DAPR establecida** entre 2 microservicios
2. ✅ **Arquitectura layered implementada** en ambos servicios
3. ✅ **Logging exhaustivo** para debugging
4. ✅ **3/4 endpoints funcionando** correctamente
5. ✅ **Entendimiento profundo** de DAPR Service Invocation
6. ✅ **Patrones de solución** para problemas de deserialización

### 🚀 **Próximos Pasos Sugeridos**

1. **Resolver endpoint de texto plano**
2. **Implementar health checks**
3. **Agregar métricas de performance**
4. **Configurar observabilidad avanzada**
5. **Implementar circuit breaker manual**
6. **Agregar tests automatizados** 