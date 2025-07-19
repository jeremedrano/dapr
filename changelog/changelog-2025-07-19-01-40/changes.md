# Cambios Realizados - 2025-07-19 01:40

## Configuración de Puertos para Microservicios

### Problema Identificado
- Conflicto de puertos entre microservicios al ejecutar `quarkusDev`
- Ambos servicios intentaban usar el puerto 8080 por defecto
- Error: `Port 8080 seems to be in use by another process`

### Solución Implementada

#### Microservicio User-App
- **Puerto Principal:** 8081
- **Puerto de Tests:** 8082
- **Archivo:** `code-user-app/code-user-app/src/main/resources/application.properties`

#### Microservicio Pokemon
- **Puerto Principal:** 8083
- **Puerto de Tests:** 8084
- **Archivo:** `code-user-pokemon/code-user-pokemon/src/main/resources/application.properties`

### Configuración Aplicada

```properties
# User-App
quarkus.http.port=8081
%test.quarkus.http.port=8082

# Pokemon
quarkus.http.port=8083
%test.quarkus.http.port=8084
```

### Beneficios
- Eliminación de conflictos de puertos
- Posibilidad de ejecutar ambos microservicios simultáneamente
- Separación clara entre entornos de desarrollo y testing 