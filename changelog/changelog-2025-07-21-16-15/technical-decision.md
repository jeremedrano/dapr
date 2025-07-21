# Decisiones Técnicas - Solución DAPR Communication

## Fecha: 2025-07-21 16:15

### 🎯 **Contexto del Problema**

El objetivo era aprender DAPR implementando comunicación entre microservicios. Se encontraron problemas de deserialización que requerían decisiones técnicas específicas.

### 🔍 **Análisis del Problema**

#### **Problema Principal**
DAPR estaba intentando deserializar automáticamente respuestas JSON como String, causando errores de tipo:
- Arrays JSON → Error de deserialización
- Objetos JSON → Error de deserialización
- Texto plano → Error de parsing JSON

#### **Opciones Consideradas**

1. **Usar HTTP Client directo**
   - ❌ Rechazado: El objetivo es aprender DAPR
   - ❌ No aprovecha las capacidades de DAPR

2. **Usar TypeReference con DAPR**
   - ❌ Rechazado: No disponible en esta versión de DAPR
   - ❌ Causaba errores de compilación

3. **Usar Object.class + Deserialización manual**
   - ✅ Aceptado: Solución funcional que mantiene DAPR
   - ✅ Permite control total sobre la deserialización

### 🛠️ **Decisión Técnica Implementada**

#### **Estrategia: Object.class + ObjectMapper**

```java
// Patrón implementado para todos los métodos
Object response = daprClient.invokeMethod(
    POKEMON_SERVICE_APP_ID, 
    endpoint, 
    null, 
    HttpExtension.GET, 
    Object.class  // ← Decisión clave
).block();

String jsonResponse = objectMapper.writeValueAsString(response);
// Deserialización manual según el tipo esperado
```

#### **Justificación de la Decisión**

1. **Mantiene DAPR**: Sigue usando Service Invocation de DAPR
2. **Flexibilidad**: Permite manejar cualquier tipo de respuesta
3. **Control**: Deserialización manual permite manejar casos edge
4. **Compatibilidad**: Funciona con la versión actual de DAPR

### 📊 **Comparación de Enfoques**

| Enfoque | Pros | Contras | Decisión |
|---------|------|---------|----------|
| HTTP Directo | Simple, directo | No usa DAPR | ❌ Rechazado |
| TypeReference | Tipado fuerte | No disponible | ❌ Rechazado |
| String.class | Simple | Errores de deserialización | ❌ Rechazado |
| Object.class + Manual | Flexible, funcional | Más código | ✅ Aceptado |

### 🔧 **Implementación Técnica**

#### **Patrón Aplicado**

1. **Para Arrays JSON** (getAllPokemons):
   ```java
   List<Pokemon> pokemons = objectMapper.readValue(
       jsonResponse, 
       new TypeReference<List<Pokemon>>() {}
   );
   ```

2. **Para Objetos JSON** (getRandomPokemon, getPokemonById):
   ```java
   Pokemon pokemon = objectMapper.readValue(
       jsonResponse, 
       Pokemon.class
   );
   ```

3. **Para Texto Plano** (getPokemonServiceHello):
   ```java
   String responseBody = response.toString();
   ```

### ⚠️ **Limitaciones Identificadas**

1. **Texto Plano**: DAPR sigue intentando parsear como JSON
2. **Performance**: Deserialización doble (Object → String → Target)
3. **Código**: Más verboso que la deserialización directa

### 🎯 **Lecciones Aprendidas**

1. **DAPR Service Invocation**: Excelente para JSON estructurado
2. **Deserialización**: Requiere enfoque específico para tipos complejos
3. **Debugging**: Logs exhaustivos son esenciales para troubleshooting
4. **Versionado**: Diferentes versiones de DAPR tienen APIs diferentes

### 🔮 **Consideraciones Futuras**

1. **Actualizar DAPR**: Versiones más recientes pueden tener mejor soporte
2. **Configuración**: Ajustar Content-Type headers puede ayudar
3. **Middleware**: Considerar interceptores para manejo uniforme
4. **Testing**: Implementar tests específicos para cada tipo de respuesta

### 📈 **Métricas de Éxito**

- ✅ **3/4 endpoints funcionando** (75% éxito)
- ✅ **Comunicación DAPR establecida**
- ✅ **Arquitectura layered implementada**
- ✅ **Logging exhaustivo implementado**
- ⚠️ **1 endpoint pendiente** (texto plano) 