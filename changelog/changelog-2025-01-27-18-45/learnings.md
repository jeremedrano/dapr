# Aprendizajes - 2025-01-27 18:45

## Lecciones Aprendidas sobre Deserialización JSON con DAPR

### 1. Tipos de Respuesta en DAPR
- **Aprendizaje**: DAPR puede devolver diferentes tipos de respuesta según el endpoint
- **Implicación**: El cliente debe manejar correctamente el tipo de respuesta esperado
- **Mejor Práctica**: Usar deserialización manual para control total

### 2. Jackson TypeReference
- **Aprendizaje**: `TypeReference` es esencial para deserializar colecciones complejas
- **Uso**: `new TypeReference<List<Pokemon>>() {}` para arrays JSON
- **Ventaja**: Permite deserialización de tipos genéricos

### 3. DAPR Service Invocation
- **Aprendizaje**: DAPR mantiene la estructura de respuesta original del servicio
- **Comportamiento**: Si el servicio devuelve `List<Pokemon>`, DAPR devuelve array JSON
- **Implicación**: El cliente debe deserializar según el tipo real de respuesta

### 4. Debugging de Deserialización
- **Aprendizaje**: Los errores de Jackson son muy específicos sobre el problema
- **Síntomas Comunes**:
  - `Cannot deserialize value of type String from Array`: Esperaba String, recibió Array
  - `Cannot deserialize value of type Object from String`: Esperaba Object, recibió String
  - `Unexpected token`: Formato JSON incorrecto

### 5. Estrategias de Deserialización
- **Aprendizaje**: Hay múltiples formas de manejar deserialización en DAPR
- **Opciones**:
  1. **String + Manual**: Obtener String y deserializar manualmente
  2. **TypeRef Directo**: Usar TypeRef específico de DAPR
  3. **Object Wrapper**: Envolver respuestas en objetos

### 6. Logging en Deserialización
- **Aprendizaje**: Los logs detallados son cruciales para debugging de deserialización
- **Beneficio**: Permiten ver exactamente qué datos se están procesando
- **Implementación**: Log del response body antes de deserializar

### 7. Validación de Respuestas
- **Aprendizaje**: Siempre validar que la respuesta no sea null antes de deserializar
- **Patrón**: `if (responseBody != null) { /* deserializar */ }`
- **Beneficio**: Evita NullPointerException en deserialización

## Mejores Prácticas Identificadas
1. **Usar TypeReference para colecciones complejas**
2. **Mantener logging detallado durante deserialización**
3. **Validar respuestas antes de procesar**
4. **Entender el tipo de respuesta del servicio destino**
5. **Usar deserialización manual para control total**
6. **Documentar los tipos de respuesta esperados**
7. **Probar endpoints con diferentes tipos de datos**

## Patrones de Deserialización Recomendados
```java
// Para respuestas simples (String, Integer, etc.)
String response = daprClient.invokeMethod(appId, method, null, HttpExtension.GET, String.class).block();

// Para objetos complejos
MyObject obj = daprClient.invokeMethod(appId, method, null, HttpExtension.GET, MyObject.class).block();

// Para colecciones (List, Set, etc.)
String responseBody = daprClient.invokeMethod(appId, method, null, HttpExtension.GET, String.class).block();
List<MyObject> list = objectMapper.readValue(responseBody, new TypeReference<List<MyObject>>() {});
``` 