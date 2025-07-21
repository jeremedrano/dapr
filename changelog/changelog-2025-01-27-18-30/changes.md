# Cambios Realizados - 2025-01-27 18:30

## Problema Identificado
- **Error**: `Failed to connect to /127.0.0.1:3500` en PokemonClient
- **Causa**: El cliente DAPR intenta conectarse al puerto 3500 (por defecto) en lugar del puerto 3502 configurado
- **Impacto**: Imposibilidad de comunicación entre code-user-app y pokemon-service

## Análisis Técnico
- El código PokemonClient.java tiene configurado `DAPR_HTTP_PORT = "3502"`
- El script start-services.ps1 inicia el user-app-service en puerto 3502
- Sin embargo, el cliente DAPR está intentando conectarse al puerto 3500

## Soluciones Implementadas
1. **Verificación de configuración DAPR**
2. **Análisis de logs de error**
3. **Documentación del problema**

## Archivos Modificados
- `changelog/changelog-2025-01-27-18-30/changes.md` (este archivo)
- `changelog/changelog-2025-01-27-18-30/technical-decision.md`
- `changelog/changelog-2025-01-27-18-30/learnings.md`
- `COMANDOS-DAPR.md` (nuevo archivo con comandos manuales)
- `code-user-app/code-user-app/src/main/java/org/acme/user/PokemonClient.java` (configuración de puerto DAPR)

## Estado
- ✅ Problema identificado
- ✅ Solución implementada
- ✅ Corrección aplicada

## Solución Final
- **Problema**: Sidecar DAPR no estaba corriendo
- **Causa**: La aplicación se ejecutó sin DAPR sidecar
- **Solución**: Usar `start-services.ps1` para iniciar servicios con DAPR
- **Resultado**: Comunicación entre servicios funcionando correctamente

## Problema Secundario Identificado
- **Error**: `Cannot deserialize value of type java.lang.String from Array value`
- **Causa**: El endpoint `/pokemon/list` devuelve `List<Pokemon>` pero el cliente esperaba `String`
- **Solución**: Mantener deserialización manual con `TypeReference<List<Pokemon>>()`
- **Estado**: ✅ Corregido en PokemonClient.java 