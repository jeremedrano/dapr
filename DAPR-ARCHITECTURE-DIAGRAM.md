# Diagrama de Arquitectura DAPR - Microservicios Pokemon

## 📊 Diagrama de Puertos y Sidecars

```
┌─────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                    ARQUITECTURA DAPR COMPLETA                                    │
└─────────────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────────────┐
│                                    DESARROLLO LOCAL (localhost)                                  │
└─────────────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────────────┐
│  🎮 POKEMON SERVICE (Microservicio 1)                                                           │
├─────────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                                 │
│  ┌─────────────────────────────────────────────────────────────────────────────────────────────┐ │
│  │                           POKEMON SERVICE CONTAINER                                        │ │
│  │                                                                                             │ │
│  │  ┌─────────────────────────────────────────────────────────────────────────────────────────┐ │ │
│  │  │                    🎮 POKEMON SERVICE (Quarkus)                                        │ │ │
│  │  │                                                                                         │ │ │
│  │  │  📡 Puerto de Aplicación: 8086                                                        │ │ │
│  │  │  🌐 Endpoints REST:                                                                    │ │ │
│  │  │     • GET /pokemon/random                                                              │ │ │
│  │  │     • GET /pokemon/{id}                                                                │ │ │
│  │  │     • GET /pokemon/list                                                                │ │ │
│  │  │     • GET /pokemon/hello                                                               │ │ │
│  │  │     • POST /pokemon                                                                    │ │ │
│  │  │     • PUT /pokemon/{id}                                                                │ │ │
│  │  │     • DELETE /pokemon/{id}                                                             │ │ │
│  │  │                                                                                         │ │ │
│  │  │  🔗 Comunicación: Solo recibe requests HTTP                                           │ │ │
│  │  │  📊 App ID: "pokemon-service"                                                          │ │ │
│  │  └─────────────────────────────────────────────────────────────────────────────────────────┘ │ │
│  │                                                                                             │ │
│  │  ┌─────────────────────────────────────────────────────────────────────────────────────────┐ │ │
│  │  │                    🔧 DAPR SIDECAR (Pokemon)                                          │ │ │
│  │  │                                                                                         │ │ │
│  │  │  📡 Puerto HTTP DAPR: 3501                                                            │ │ │
│  │  │  📡 Puerto gRPC DAPR: 50002                                                           │ │ │
│  │  │                                                                                         │ │ │
│  │  │  🔍 Service Discovery:                                                                │ │ │
│  │  │     • Registra "pokemon-service" en el registry                                       │ │ │
│  │  │     • Expone endpoints para service invocation                                        │ │ │
│  │  │                                                                                         │ │ │
│  │  │  📊 Funcionalidades:                                                                   │ │ │
│  │  │     • Service Invocation (enabled)                                                    │ │ │
│  │  │     • Observability (tracing)                                                         │ │ │
│  │  │     • CORS handling                                                                    │ │ │
│  │  │     • Load Balancing (automático)                                                     │ │ │
│  │  │     • Circuit Breaker (automático)                                                    │ │ │
│  │  │     • Retry Logic (automático)                                                        │ │ │
│  │  └─────────────────────────────────────────────────────────────────────────────────────────┘ │ │
│  └─────────────────────────────────────────────────────────────────────────────────────────────┘ │
│                                                                                                 │
├─────────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                                 │
│  ┌─────────────────────────────────────────────────────────────────────────────────────────────┐ │
│  │                           🔗 COMUNICACIÓN INTER-SERVICIOS                                  │ │ │
│  │                                                                                             │ │
│  │  ┌─────────────────────────────────────────────────────────────────────────────────────────┐ │ │
│  │  │                    🌐 DAPR SERVICE INVOCATION                                          │ │ │
│  │  │                                                                                         │ │ │
│  │  │  📡 Protocolo: HTTP                                                                    │ │ │
│  │  │  🔍 Service Discovery: Automático por App ID                                          │ │ │
│  │  │  ⚡ Load Balancing: Automático                                                         │ │ │
│  │  │  🔄 Retry Logic: Automático                                                            │ │ │
│  │  │  🛡️ Circuit Breaker: Automático                                                       │ │ │
│  │  │  📊 Tracing: Automático (Zipkin en puerto 9411)                                       │ │ │
│  │  └─────────────────────────────────────────────────────────────────────────────────────────┘ │ │
│  └─────────────────────────────────────────────────────────────────────────────────────────────┘ │
│                                                                                                 │
├─────────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                                 │
│  ┌─────────────────────────────────────────────────────────────────────────────────────────────┐ │
│  │  👤 USER-APP SERVICE (Microservicio 2)                                                    │ │
│  ├─────────────────────────────────────────────────────────────────────────────────────────────┤ │
│  │                                                                                             │ │
│  │  ┌─────────────────────────────────────────────────────────────────────────────────────────┐ │ │ │
│  │  │                    🔧 DAPR SIDECAR (User-App)                                         │ │ │ │
│  │  │                                                                                         │ │ │ │
│  │  │  📡 Puerto HTTP DAPR: 3502                                                            │ │ │ │
│  │  │  📡 Puerto gRPC DAPR: 50003                                                           │ │ │ │
│  │  │                                                                                         │ │ │ │
│  │  │  🔍 Service Discovery:                                                                │ │ │ │
│  │  │     • Registra "user-app-service" en el registry                                      │ │ │ │
│  │  │     • Conecta con "pokemon-service" via service invocation                            │ │ │ │
│  │  │                                                                                         │ │ │ │
│  │  │  📊 Funcionalidades:                                                                   │ │ │ │
│  │  │     • Service Invocation (enabled)                                                    │ │ │ │
│  │  │     • Observability (tracing)                                                         │ │ │ │
│  │  │     • CORS handling                                                                    │ │ │ │
│  │  │     • Load Balancing (automático)                                                     │ │ │ │
│  │  │     • Circuit Breaker (automático)                                                    │ │ │ │
│  │  │     • Retry Logic (automático)                                                        │ │ │ │
│  │  └─────────────────────────────────────────────────────────────────────────────────────────┘ │ │ │
│  │                                                                                             │ │ │
│  │  ┌─────────────────────────────────────────────────────────────────────────────────────────┐ │ │ │
│  │  │                    👤 USER-APP SERVICE (Quarkus)                                      │ │ │ │
│  │  │                                                                                         │ │ │ │
│  │  │  📡 Puerto de Aplicación: 8088                                                        │ │ │ │
│  │  │  🌐 Endpoints REST:                                                                    │ │ │ │
│  │  │     • GET /users/hello                                                                 │ │ │ │
│  │  │     • GET /users                                                                       │ │ │ │
│  │  │     • GET /users/{id}                                                                  │ │ │ │
│  │  │     • POST /users                                                                      │ │ │ │
│  │  │     • PUT /users/{id}                                                                  │ │ │ │
│  │  │     • DELETE /users/{id}                                                               │ │ │ │
│  │  │     • GET /users/pokemon                                                               │ │ │ │
│  │  │     • GET /users/pokemon/{id}                                                          │ │ │ │
│  │  │     • GET /users/pokemon/list                                                          │ │ │ │
│  │  │     • GET /users/pokemon-service/hello                                                 │ │ │ │
│  │  │                                                                                         │ │ │ │
│  │  │  🔗 Comunicación:                                                                      │ │ │ │
│  │  │     • Recibe requests HTTP externos                                                   │ │ │ │
│  │  │     • Envía requests a Pokemon Service via DAPR                                       │ │ │ │
│  │  │                                                                                         │ │ │ │
│  │  │  📊 App ID: "user-app-service"                                                         │ │ │ │
│  │  │  🔧 PokemonClient: Conecta a DAPR sidecar en puerto 3502                              │ │ │ │
│  │  └─────────────────────────────────────────────────────────────────────────────────────────┘ │ │ │
│  └─────────────────────────────────────────────────────────────────────────────────────────────┘ │ │
│                                                                                                 │ │
└─────────────────────────────────────────────────────────────────────────────────────────────────┘ │
│                                                                                                 │
┌─────────────────────────────────────────────────────────────────────────────────────────────────┐ │
│  📊 DAPR RUNTIME (Global)                                                                      │ │
├─────────────────────────────────────────────────────────────────────────────────────────────────┤ │
│                                                                                                 │ │
│  ┌─────────────────────────────────────────────────────────────────────────────────────────────┐ │ │
│  │                    🌐 DAPR RUNTIME                                                        │ │ │
│  │                                                                                             │ │ │
│  │  📡 Puerto HTTP DAPR: 3500                                                                │ │ │
│  │  📡 Puerto gRPC DAPR: 50001                                                               │ │ │
│  │                                                                                             │ │ │
│  │  🔍 Service Registry:                                                                      │ │ │
│  │     • Registra todos los servicios                                                        │ │ │
│  │     • Maneja service discovery                                                            │ │ │
│  │     • Coordina comunicación entre sidecars                                               │ │ │
│  │                                                                                             │ │ │
│  │  📊 Funcionalidades Globales:                                                             │ │ │
│  │     • Service Discovery                                                                   │ │ │
│  │     • Load Balancing                                                                      │ │ │
│  │     • Health Checking                                                                     │ │ │
│  │     • Metrics Collection                                                                  │ │ │
│  └─────────────────────────────────────────────────────────────────────────────────────────────┘ │ │
└─────────────────────────────────────────────────────────────────────────────────────────────────┘ │
│                                                                                                 │
┌─────────────────────────────────────────────────────────────────────────────────────────────────┐ │
│  📈 OBSERVABILITY (Zipkin)                                                                     │ │
├─────────────────────────────────────────────────────────────────────────────────────────────────┤ │
│                                                                                                 │ │
│  ┌─────────────────────────────────────────────────────────────────────────────────────────────┐ │ │
│  │                    📊 ZIPKIN TRACING                                                       │ │ │
│  │                                                                                             │ │ │
│  │  📡 Puerto: 9411                                                                           │ │ │
│  │  🌐 Endpoint: http://localhost:9411/api/v2/spans                                          │ │ │
│  │                                                                                             │ │ │
│  │  📊 Funcionalidades:                                                                       │ │ │
│  │     • Distributed Tracing                                                                  │ │ │
│  │     • Request Flow Visualization                                                          │ │ │
│  │     • Performance Monitoring                                                               │ │ │
│  │     • Error Tracking                                                                       │ │ │
│  └─────────────────────────────────────────────────────────────────────────────────────────────┘ │ │
└─────────────────────────────────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────────────────────────────┘

```

## 🔄 Flujo de Comunicación Detallado

### 1. Request Externo → User-App Service

```
Cliente HTTP → Puerto 8088 → User-App Service (Quarkus)
```

### 2. User-App Service → DAPR Sidecar (Local)

```
User-App Service → Puerto 3502 → DAPR Sidecar (User-App)
```

### 3. DAPR Sidecar → Service Discovery

```
DAPR Sidecar (User-App) → DAPR Runtime (Puerto 3500) → Service Registry
```

### 4. Service Discovery → Pokemon Service

```
DAPR Runtime → DAPR Sidecar (Pokemon) → Puerto 8086 → Pokemon Service
```

### 5. Response Flow (Reverso)

```
Pokemon Service → DAPR Sidecar (Pokemon) → DAPR Runtime → DAPR Sidecar (User-App) → User-App Service → Cliente
```

## 📋 Resumen de Puertos

| Componente | Puerto HTTP | Puerto gRPC | Descripción |
|------------|-------------|-------------|-------------|
| **DAPR Runtime** | 3500 | 50001 | Runtime global de DAPR |
| **Pokemon Service** | 8086 | - | Aplicación Quarkus Pokemon |
| **DAPR Sidecar (Pokemon)** | 3501 | 50002 | Sidecar del Pokemon Service |
| **User-App Service** | 8088 | - | Aplicación Quarkus User-App |
| **DAPR Sidecar (User-App)** | 3502 | 50003 | Sidecar del User-App Service |
| **Zipkin Tracing** | 9411 | - | Observabilidad y tracing |

## 🔧 Configuración de Puertos en Código

### PokemonClient.java
```java
private static final String DAPR_HTTP_PORT = "3502"; // Puerto DAPR del User-App Service
```

### start-services.ps1
```powershell
# DAPR Runtime
--dapr-http-port 3500 --dapr-grpc-port 50001

# Pokemon Service
--app-port 8086 --dapr-http-port 3501 --dapr-grpc-port 50002

# User-App Service  
--app-port 8088 --dapr-http-port 3502 --dapr-grpc-port 50003
```

## 🎯 Beneficios de esta Arquitectura

1. **Service Discovery Automático**: DAPR encuentra servicios por App ID
2. **Load Balancing**: Distribuye requests automáticamente
3. **Circuit Breaker**: Protege contra fallos en cascada
4. **Retry Logic**: Reintentos automáticos en fallos
5. **Observabilidad**: Tracing completo de requests
6. **Resiliencia**: Manejo automático de errores
7. **Escalabilidad**: Fácil agregar más instancias

## 🚀 Comandos de Inicio

```powershell
# Iniciar DAPR Runtime
dapr run --app-id dapr-runtime --dapr-http-port 3500 --dapr-grpc-port 50001

# Iniciar Pokemon Service
dapr run --app-id pokemon-service --app-port 8086 --dapr-http-port 3501 --dapr-grpc-port 50002 -- cd code-user-pokemon/code-user-pokemon && ./gradlew quarkusDev

# Iniciar User-App Service
dapr run --app-id user-app-service --app-port 8088 --dapr-http-port 3502 --dapr-grpc-port 50003 -- cd code-user-app/code-user-app && ./gradlew quarkusDev
```

## 🔍 Endpoints de Prueba

```bash
# Test Pokemon Service directamente
curl http://localhost:8086/pokemon/random
curl http://localhost:8086/pokemon/25

# Test User-App Service (que usa DAPR)
curl http://localhost:8088/users/pokemon
curl http://localhost:8088/users/pokemon/25
curl http://localhost:8088/users/pokemon-service/hello
``` 