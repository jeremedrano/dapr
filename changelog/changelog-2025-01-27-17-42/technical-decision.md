# Decisiones Técnicas - 2025-01-27 17:42

## Implementación de Comunicación DAPR

### 1. Elección de DAPR Service Invocation

#### Decisión
Utilizar DAPR Service Invocation para la comunicación entre microservicios en lugar de comunicación HTTP directa.

#### Justificación
- **Desacoplamiento**: Los servicios no necesitan conocer las URLs directas de otros servicios
- **Service Discovery**: DAPR maneja automáticamente el descubrimiento de servicios
- **Resiliencia**: Retry automático y circuit breaker integrado
- **Observabilidad**: Trazabilidad automática de llamadas entre servicios
- **Configuración Simplificada**: Menos configuración manual de endpoints

#### Alternativas Consideradas
- **HTTP Directo**: Requeriría configuración manual de URLs y manejo de errores
- **Message Broker**: Sobrecarga innecesaria para comunicación síncrona simple
- **API Gateway**: Agregaría complejidad adicional para este caso de uso

### 2. Arquitectura de Servicios

#### Decisión
- **User-App**: Actúa como cliente que invoca al servicio Pokemon
- **Pokemon**: Actúa como servicio que expone endpoints REST

#### Justificación
- **Separación de Responsabilidades**: Cada servicio tiene una función específica
- **Escalabilidad**: Permite escalar servicios independientemente
- **Mantenibilidad**: Cambios en un servicio no afectan al otro

### 3. Configuración de Puertos

#### Decisión
Mantener los puertos actuales:
- User-App: 8088
- Pokemon: 8086

#### Justificación
- **Consistencia**: Mantener la configuración existente
- **Evitar Conflictos**: Puertos ya configurados y funcionando
- **Simplicidad**: No requiere cambios adicionales

### 4. Estructura de Endpoints

#### Decisión
- Pokemon: `/pokemon/*` para todos los endpoints relacionados
- User-App: `/users/pokemon` para el endpoint que invoca Pokemon

#### Justificación
- **Claridad**: URLs descriptivas y organizadas
- **RESTful**: Sigue convenciones REST
- **Escalabilidad**: Fácil agregar nuevos endpoints relacionados

### 5. Manejo de Datos

#### Decisión
Usar clases POJO simples para el modelo Pokemon

#### Justificación
- **Simplicidad**: Fácil serialización/deserialización
- **Compatibilidad**: Funciona bien con DAPR y Quarkus
- **Mantenibilidad**: Código claro y fácil de entender 