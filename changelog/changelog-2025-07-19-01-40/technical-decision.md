# Decisiones Técnicas - 2025-07-19 01:40

## Configuración de Puertos para Microservicios

### Decisión Tomada
Configurar puertos específicos para cada microservicio para evitar conflictos durante el desarrollo.

### Justificación Técnica

#### Problema Original
- Quarkus por defecto usa el puerto 8080
- Al ejecutar múltiples microservicios simultáneamente, se produce conflicto de puertos
- Error: `QuarkusBindException` y `Port 8080 seems to be in use`

#### Análisis de Alternativas

1. **Usar puertos aleatorios**
   - ❌ No garantiza consistencia entre ejecuciones
   - ❌ Dificulta debugging y testing

2. **Configurar puertos específicos**
   - ✅ Garantiza consistencia
   - ✅ Facilita debugging
   - ✅ Permite documentación clara

3. **Usar variables de entorno**
   - ❌ Añade complejidad innecesaria para desarrollo local
   - ✅ Sería útil para producción

### Criterios de Selección de Puertos

#### Rango 8081-8084
- **8081:** User-App (servicio principal)
- **8082:** User-App Tests
- **8083:** Pokemon (servicio secundario)
- **8084:** Pokemon Tests

#### Justificación
- Evita conflicto con puerto 8080 (estándar)
- Secuencia lógica y fácil de recordar
- Separación clara entre servicios y tests

### Configuración Implementada

```properties
# Microservicio User-App
quarkus.http.port=8081
%test.quarkus.http.port=8082

# Microservicio Pokemon  
quarkus.http.port=8083
%test.quarkus.http.port=8084
```

### Consideraciones Futuras
- Para producción, considerar uso de variables de entorno
- Documentar puertos en README del proyecto
- Considerar configuración de health checks en puertos específicos 