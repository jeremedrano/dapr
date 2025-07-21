# Cambios - Solución DAPR Communication

## Fecha: 2025-07-21 16:15

### 🎯 **Objetivo**
Resolver el problema de comunicación DAPR entre `user-app-service` y `pokemon-service` para aprender DAPR correctamente.

### ✅ **Problemas Resueltos**

#### 1. **Error de Deserialización JSON en Arrays**
- **Problema**: `Cannot deserialize value of type java.lang.String from Array value (token JsonToken.START_ARRAY)`
- **Causa**: DAPR intentaba deserializar arrays JSON como String
- **Solución**: Usar `Object.class` en lugar de `String.class` y luego deserializar manualmente

#### 2. **Error de Deserialización JSON en Objetos**
- **Problema**: `Cannot deserialize value of type java.lang.String from Object value (token JsonToken.START_OBJECT)`
- **Causa**: DAPR intentaba deserializar objetos JSON como String
- **Solución**: Usar `Object.class` y deserializar manualmente con ObjectMapper

### 🔧 **Cambios Implementados**

#### **PokemonClient.java - Métodos Corregidos**

1. **getAllPokemons()**
   ```java
   // ANTES
   String responseBody = daprClient.invokeMethod(
       POKEMON_SERVICE_APP_ID, 
       "pokemon/list", 
       null, 
       HttpExtension.GET, 
       String.class
   ).block();
   
   // DESPUÉS
   Object response = daprClient.invokeMethod(
       POKEMON_SERVICE_APP_ID, 
       "pokemon/list", 
       null, 
       HttpExtension.GET, 
       Object.class
   ).block();
   
   String jsonResponse = objectMapper.writeValueAsString(response);
   List<Pokemon> pokemons = objectMapper.readValue(
       jsonResponse, 
       new TypeReference<List<Pokemon>>() {}
   );
   ```

2. **getRandomPokemon()**
   ```java
   // ANTES
   String responseBody = daprClient.invokeMethod(
       POKEMON_SERVICE_APP_ID, 
       "pokemon/random", 
       null, 
       HttpExtension.GET, 
       String.class
   ).block();
   
   // DESPUÉS
   Object response = daprClient.invokeMethod(
       POKEMON_SERVICE_APP_ID, 
       "pokemon/random", 
       null, 
       HttpExtension.GET, 
       Object.class
   ).block();
   
   String jsonResponse = objectMapper.writeValueAsString(response);
   Pokemon pokemon = objectMapper.readValue(jsonResponse, Pokemon.class);
   ```

3. **getPokemonById()**
   ```java
   // Misma corrección que getRandomPokemon()
   ```

### ✅ **Endpoints Funcionando**

1. **GET /users/pokemon/list** ✅
   - Devuelve lista completa de Pokemons
   - Comunicación DAPR exitosa

2. **GET /users/pokemon** ✅
   - Devuelve Pokemon aleatorio
   - Comunicación DAPR exitosa

3. **GET /users/pokemon/{id}** ✅
   - Devuelve Pokemon específico por ID
   - Comunicación DAPR exitosa

### ⚠️ **Problema Pendiente**

#### **GET /users/pokemon-service/hello**
- **Problema**: DAPR intenta parsear texto plano como JSON
- **Error**: `Unrecognized token 'Hello': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')`
- **Estado**: Pendiente de resolver

### 🎯 **Aprendizajes DAPR**

1. **Service Invocation**: DAPR maneja automáticamente:
   - Service Discovery
   - Load Balancing
   - Retry Logic
   - Circuit Breaker
   - Observabilidad

2. **Deserialización**: DAPR tiene limitaciones con tipos complejos:
   - Arrays JSON requieren `Object.class` + deserialización manual
   - Objetos JSON requieren `Object.class` + deserialización manual
   - Texto plano tiene problemas de parsing

3. **Configuración**: DAPR requiere:
   - App IDs únicos
   - Puertos HTTP/gRPC configurados
   - Sidecars corriendo para cada servicio

### 📊 **Estado Final**

- **Servicios DAPR**: ✅ Ambos corriendo correctamente
- **Comunicación JSON**: ✅ Funcionando para arrays y objetos
- **Comunicación Texto**: ⚠️ Problema pendiente
- **Arquitectura**: ✅ Layered architecture implementada
- **Logging**: ✅ Logs exhaustivos para debugging 