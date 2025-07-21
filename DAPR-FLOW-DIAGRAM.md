# Diagrama de Flujo DAPR - Comunicación entre Microservicios

## 🔄 Flujo Simplificado de Comunicación

```
┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                                    CLIENTE EXTERNO                                           │
│                                                                                             │
│  🌐 Cliente HTTP (Postman, curl, navegador, etc.)                                          │
│                                                                                             │
│  📡 Request: GET http://localhost:8088/users/pokemon                                       │
└─────────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                              👤 USER-APP SERVICE (Puerto 8088)                              │
│                                                                                             │
│  🎯 UserResource.getRandomPokemon()                                                        │
│  📞 pokemonClientService.getRandomPokemon()                                                │
│  🔧 PokemonClient.getRandomPokemon()                                                       │
│                                                                                             │
│  💻 Código Java:                                                                           │
│     daprClient.invokeMethod("pokemon-service", "pokemon/random", ...)                      │
└─────────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼ (Puerto 3502)
┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                        🔧 DAPR SIDECAR (User-App) - Puerto 3502                            │
│                                                                                             │
│  📥 Recibe request del User-App Service                                                   │
│  🔍 Service Discovery: Busca "pokemon-service"                                            │
│  📡 Envía request al DAPR Runtime                                                          │
└─────────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼ (Puerto 3500)
┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                           🌐 DAPR RUNTIME - Puerto 3500                                    │
│                                                                                             │
│  📋 Service Registry:                                                                      │
│     • pokemon-service → localhost:3501                                                    │
│     • user-app-service → localhost:3502                                                   │
│                                                                                             │
│  🔍 Encuentra pokemon-service en el registry                                              │
│  📡 Redirige request al sidecar correcto                                                   │
└─────────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼ (Puerto 3501)
┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                        🔧 DAPR SIDECAR (Pokemon) - Puerto 3501                             │
│                                                                                             │
│  📥 Recibe request del DAPR Runtime                                                        │
│  🔄 Load Balancing (si hay múltiples instancias)                                          │
│  🛡️ Circuit Breaker (protección contra fallos)                                           │
│  🔄 Retry Logic (reintentos automáticos)                                                  │
│  📡 Envía request al Pokemon Service                                                       │
└─────────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼ (Puerto 8086)
┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                            🎮 POKEMON SERVICE (Puerto 8086)                                │
│                                                                                             │
│  🎯 PokemonResource.getRandomPokemon()                                                    │
│  📞 pokemonService.getRandomPokemon()                                                     │
│  📞 pokemonRepository.getRandomPokemon()                                                  │
│                                                                                             │
│  💻 Código Java:                                                                           │
│     // Lógica de negocio y acceso a datos                                                 │
│     return pokemonRepository.getRandomPokemon();                                          │
└─────────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼ (Response JSON)
┌─────────────────────────────────────────────────────────────────────────────────────────────┘
│  📤 Response: {"id":25,"name":"Pikachu","type":"Electric","level":5,"abilities":[...]}     │
└─────────────────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼ (Flujo de Respuesta)
┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                              🔄 FLUJO DE RESPUESTA (REVERSO)                               │
│                                                                                             │
│  Pokemon Service (8086) → DAPR Sidecar (3501) → DAPR Runtime (3500) →                      │
│  DAPR Sidecar (3502) → User-App Service (8088) → Cliente Externo                           │
└─────────────────────────────────────────────────────────────────────────────────────────────┘
```

## 📊 Mapa de Puertos Detallado

```
┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│                                    MAPA DE PUERTOS DAPR                                     │
└─────────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│  🌐 DAPR RUNTIME (GLOBAL)                                                                   │
│                                                                                             │
│  📡 Puerto HTTP: 3500                                                                       │
│  📡 Puerto gRPC: 50001                                                                      │
│                                                                                             │
│  🔍 Service Registry:                                                                       │
│     ┌─────────────────────────────────────────────────────────────────────────────────────┐ │
│     │  📋 REGISTRO DE SERVICIOS                                                           │ │
│     │                                                                                     │ │
│     │  🎮 pokemon-service                                                                 │ │
│     │     ├── App ID: "pokemon-service"                                                   │ │
│     │     ├── Sidecar HTTP: localhost:3501                                                │ │
│     │     ├── Sidecar gRPC: localhost:50002                                               │ │
│     │     └── App Port: localhost:8086                                                    │ │
│     │                                                                                     │ │
│     │  👤 user-app-service                                                                │ │
│     │     ├── App ID: "user-app-service"                                                  │ │
│     │     ├── Sidecar HTTP: localhost:3502                                                │ │
│     │     ├── Sidecar gRPC: localhost:50003                                               │ │
│     │     └── App Port: localhost:8088                                                    │ │
│     └─────────────────────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│  🎮 POKEMON SERVICE CONTAINER                                                               │
│                                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────────────────────────┐ │
│  │  🔧 DAPR SIDECAR (Pokemon)                                                              │ │
│  │                                                                                         │ │
│  │  📡 Puerto HTTP: 3501                                                                   │ │
│  │  📡 Puerto gRPC: 50002                                                                  │ │
│  │                                                                                         │ │
│  │  🔗 Comunicación:                                                                       │ │
│  │     • ← DAPR Runtime (3500)                                                            │ │
│  │     • → Pokemon Service (8086)                                                         │ │
│  └─────────────────────────────────────────────────────────────────────────────────────────┘ │
│                                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────────────────────────┐ │
│  │  🎮 POKEMON SERVICE (Quarkus)                                                           │ │
│  │                                                                                         │ │
│  │  📡 Puerto HTTP: 8086                                                                   │ │
│  │                                                                                         │ │
│  │  🔗 Comunicación:                                                                       │ │
│  │     • ← DAPR Sidecar (3501)                                                            │ │
│  │     • ← Cliente Directo (para testing)                                                 │ │
│  └─────────────────────────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────────┐
│  👤 USER-APP SERVICE CONTAINER                                                              │
│                                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────────────────────────┐ │
│  │  🔧 DAPR SIDECAR (User-App)                                                             │ │
│  │                                                                                         │ │
│  │  📡 Puerto HTTP: 3502                                                                   │ │
│  │  📡 Puerto gRPC: 50003                                                                  │ │
│  │                                                                                         │ │
│  │  🔗 Comunicación:                                                                       │ │ │
│  │     • ← User-App Service (8088)                                                        │ │ │
│  │     • → DAPR Runtime (3500)                                                            │ │ │
│  └─────────────────────────────────────────────────────────────────────────────────────────┘ │ │
│                                                                                             │ │
│  ┌─────────────────────────────────────────────────────────────────────────────────────────┐ │ │
│  │  👤 USER-APP SERVICE (Quarkus)                                                          │ │ │
│  │                                                                                         │ │ │
│  │  📡 Puerto HTTP: 8088                                                                   │ │ │
│  │                                                                                         │ │ │
│  │  🔗 Comunicación:                                                                       │ │ │
│  │     • ← Cliente Externo                                                                │ │ │
│  │     • → DAPR Sidecar (3502)                                                            │ │ │
│  │                                                                                         │ │ │
│  │  💻 PokemonClient.java:                                                                │ │ │
│  │     System.setProperty("DAPR_HTTP_PORT", "3502");                                      │ │ │
│  └─────────────────────────────────────────────────────────────────────────────────────────┘ │ │
└─────────────────────────────────────────────────────────────────────────────────────────────┘ │

┌─────────────────────────────────────────────────────────────────────────────────────────────┐ │
│  📈 OBSERVABILITY                                                                           │ │
│                                                                                             │ │
│  ┌─────────────────────────────────────────────────────────────────────────────────────────┐ │ │
│  │  📊 ZIPKIN TRACING                                                                       │ │ │
│  │                                                                                         │ │ │
│  │  📡 Puerto: 9411                                                                        │ │ │
│  │  🌐 Endpoint: http://localhost:9411/api/v2/spans                                        │ │ │
│  │                                                                                         │ │ │
│  │  🔗 Recibe traces de:                                                                   │ │ │
│  │     • DAPR Sidecar (Pokemon) - 3501                                                    │ │ │
│  │     • DAPR Sidecar (User-App) - 3502                                                   │ │ │
│  └─────────────────────────────────────────────────────────────────────────────────────────┘ │ │
└─────────────────────────────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────────────────────────┘
```

## 🔧 Configuración en Código

### 1. PokemonClient.java
```java
// Configuración del puerto DAPR
private static final String DAPR_HTTP_PORT = "3502"; // Puerto DAPR del User-App Service

// En el constructor
System.setProperty("DAPR_HTTP_PORT", DAPR_HTTP_PORT);
this.daprClient = new DaprClientBuilder().build();
```

### 2. start-services.ps1
```powershell
# DAPR Runtime Global
dapr run --app-id dapr-runtime --dapr-http-port 3500 --dapr-grpc-port 50001

# Pokemon Service
dapr run --app-id pokemon-service --app-port 8086 --dapr-http-port 3501 --dapr-grpc-port 50002

# User-App Service  
dapr run --app-id user-app-service --app-port 8088 --dapr-http-port 3502 --dapr-grpc-port 50003
```

### 3. dapr-pokemon.yaml
```yaml
metadata:
- name: app-id
  value: pokemon-service
- name: app-port
  value: "8086"
```

### 4. dapr-user-app.yaml
```yaml
metadata:
- name: app-id
  value: user-app-service
- name: app-port
  value: "8088"
```

## 🎯 Puntos Clave de la Arquitectura

### 1. **Service Discovery Automático**
- DAPR Runtime mantiene un registry de todos los servicios
- Los sidecars se registran automáticamente al iniciar
- La comunicación se hace por App ID, no por IP/puerto

### 2. **Sidecar Pattern**
- Cada microservicio tiene su propio sidecar DAPR
- El sidecar maneja toda la comunicación externa
- La aplicación no necesita conocer detalles de red

### 3. **Resiliencia Automática**
- Circuit Breaker: Protege contra fallos en cascada
- Retry Logic: Reintentos automáticos en fallos
- Load Balancing: Distribuye requests automáticamente

### 4. **Observabilidad**
- Tracing automático con Zipkin
- Métricas de performance
- Logs estructurados

## 🚀 Comandos de Prueba

```bash
# 1. Test directo al Pokemon Service
curl http://localhost:8086/pokemon/random

# 2. Test User-App Service (que usa DAPR internamente)
curl http://localhost:8088/users/pokemon

# 3. Test de comunicación entre servicios
curl http://localhost:8088/users/pokemon/25

# 4. Test de saludo del servicio
curl http://localhost:8088/users/pokemon-service/hello
```

## 🔍 Debugging de Puertos

```bash
# Verificar puertos en uso
netstat -an | findstr "8086\|8088\|3500\|3501\|3502\|9411"

# Verificar servicios DAPR
dapr list

# Verificar logs de DAPR
dapr logs --app-id pokemon-service
dapr logs --app-id user-app-service
``` 