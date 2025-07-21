# Decisiones Técnicas - 2025-01-27 18:45

## Problema: Error de Deserialización JSON

### Contexto
El endpoint `/users/pokemon/list` fallaba con error de deserialización JSON después de que la comunicación DAPR funcionara correctamente.

### Análisis del Error
```
Cannot deserialize value of type `java.lang.String` from Array value (token `JsonToken.START_ARRAY`)
```

### Causa Raíz Identificada
1. **Endpoint pokemon-service**: Devuelve `List<Pokemon>` directamente
2. **PokemonClient**: Intentaba deserializar como `String.class`
3. **Jackson**: No puede convertir array JSON a String automáticamente

### Posibles Soluciones

#### Opción 1: Deserialización Manual con TypeReference
- **Ventaja**: Mantiene control total sobre el proceso de deserialización
- **Desventaja**: Requiere código adicional
- **Implementación**: Usar `objectMapper.readValue(responseBody, new TypeReference<List<Pokemon>>() {})`

#### Opción 2: Cambiar Tipo de Retorno en DAPR Client
- **Ventaja**: Deserialización automática
- **Desventaja**: Requiere TypeRef específico de DAPR
- **Implementación**: Usar `TypeRef<List<Pokemon>>` en invokeMethod

#### Opción 3: Modificar Endpoint Pokemon Service
- **Ventaja**: Consistencia en tipos de retorno
- **Desventaja**: Cambia la API del servicio
- **Implementación**: Envolver lista en objeto wrapper

### Decisión Tomada
**Opción 1**: Deserialización manual con TypeReference
- Es la solución más directa y controlable
- No requiere cambios en la API del pokemon-service
- Mantiene la flexibilidad para diferentes tipos de respuesta

### Implementación
1. Mantener `String.class` en `invokeMethod` para obtener respuesta raw
2. Usar `TypeReference<List<Pokemon>>()` para deserialización manual
3. Preservar logging detallado para debugging
4. Validar respuesta antes de procesar

### Beneficios
- ✅ Comunicación DAPR funcionando
- ✅ Deserialización JSON correcta
- ✅ Logging exhaustivo mantenido
- ✅ API del pokemon-service sin cambios 