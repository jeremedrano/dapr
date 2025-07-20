# Comunicación DAPR entre Microservicios

Este proyecto demuestra la comunicación entre dos microservicios usando DAPR (Distributed Application Runtime).

## Arquitectura

```
┌─────────────────┐    DAPR Service Invocation    ┌─────────────────┐
│   User-App      │ ─────────────────────────────► │   Pokemon       │
│   (Puerto 8088) │                                │   (Puerto 8086) │
└─────────────────┘                                └─────────────────┘
```

## Microservicios

### 1. Pokemon Service (Puerto 8086)
- **App ID**: `pokemon-service`
- **Endpoints**:
  - `GET /pokemon/random` - Obtiene un Pokemon aleatorio
  - `GET /pokemon/{id}` - Obtiene un Pokemon por ID
  - `GET /pokemon/list` - Lista todos los Pokemons
  - `GET /pokemon/hello` - Saludo del servicio

### 2. User-App Service (Puerto 8088)
- **App ID**: `user-app-service`
- **Endpoints**:
  - `GET /users/hello` - Saludo del servicio
  - `GET /users/pokemon` - Invoca al servicio Pokemon (aleatorio)
  - `GET /users/pokemon/{id}` - Invoca al servicio Pokemon (por ID)
  - `GET /users/pokemon/list` - Invoca al servicio Pokemon (lista)
  - `GET /users/pokemon-service/hello` - Invoca saludo del Pokemon service

## Prerrequisitos

1. **DAPR CLI**: Instalar DAPR CLI
   ```powershell
   # Instalar DAPR CLI
   winget install Dapr.DaprCLI
   # o
   choco install dapr-cli
   ```

2. **Java 21**: Asegúrate de tener Java 21 instalado
3. **Gradle**: Los proyectos usan Gradle Wrapper

## Instalación y Configuración

### 1. Inicializar DAPR
```powershell
# Inicializar DAPR (solo la primera vez)
dapr init
```

### 2. Verificar instalación
```powershell
dapr --version
```

## Ejecución

### Opción 1: Script Automático (Recomendado)
```powershell
# Desde el directorio raíz del proyecto
.\start-services.ps1
```

### Opción 2: Manual

#### Paso 1: Iniciar Pokemon Service
```powershell
# Terminal 1 - Pokemon Service
cd code-user-pokemon/code-user-pokemon
dapr run --app-id pokemon-service --app-port 8086 --dapr-http-port 3501 --dapr-grpc-port 50002 -- ./gradlew quarkusDev
```

#### Paso 2: Iniciar User-App Service
```powershell
# Terminal 2 - User-App Service
cd code-user-app/code-user-app
dapr run --app-id user-app-service --app-port 8088 --dapr-http-port 3502 --dapr-grpc-port 50003 -- ./gradlew quarkusDev
```

## Testing

### 1. Probar Pokemon Service directamente
```powershell
# Pokemon aleatorio
curl http://localhost:8086/pokemon/random

# Pokemon por ID
curl http://localhost:8086/pokemon/25

# Lista de Pokemons
curl http://localhost:8086/pokemon/list

# Saludo
curl http://localhost:8086/pokemon/hello
```

### 2. Probar comunicación DAPR desde User-App
```powershell
# Obtener Pokemon aleatorio a través de DAPR
curl http://localhost:8088/users/pokemon

# Obtener Pokemon por ID a través de DAPR
curl http://localhost:8088/users/pokemon/25

# Lista de Pokemons a través de DAPR
curl http://localhost:8088/users/pokemon/list

# Saludo del Pokemon service a través de DAPR
curl http://localhost:8088/users/pokemon-service/hello
```

## Configuración DAPR

### Archivos de Configuración
- `dapr-pokemon.yaml` - Configuración para Pokemon Service
- `dapr-user-app.yaml` - Configuración para User-App Service

### Puertos DAPR
- **Pokemon Service**: 
  - App: 8086
  - DAPR HTTP: 3501
  - DAPR gRPC: 50002
- **User-App Service**:
  - App: 8088
  - DAPR HTTP: 3502
  - DAPR gRPC: 50003

## Logs y Debugging

### Ver logs de DAPR
```powershell
# Ver logs de un servicio específico
dapr logs --app-id pokemon-service
dapr logs --app-id user-app-service
```

### Ver componentes DAPR
```powershell
# Listar componentes
dapr components list
```

### Ver configuración
```powershell
# Ver configuración de DAPR
dapr configurations list
```

## Troubleshooting

### Problema: Servicio no responde
1. Verificar que DAPR esté corriendo: `dapr status`
2. Verificar logs: `dapr logs --app-id <service-name>`
3. Verificar puertos: Asegurar que no haya conflictos de puertos

### Problema: Error de comunicación
1. Verificar que ambos servicios estén corriendo
2. Verificar configuración de app-id en application.properties
3. Verificar que DAPR sidecar esté iniciado correctamente

### Problema: Dependencias no encontradas
1. Ejecutar `./gradlew build` en cada proyecto
2. Verificar que las dependencias DAPR estén en build.gradle
3. Limpiar y reconstruir: `./gradlew clean build`

## Beneficios de DAPR

1. **Service Discovery**: Automático entre servicios
2. **Resiliencia**: Retry automático y circuit breaker
3. **Observabilidad**: Trazabilidad automática
4. **Desacoplamiento**: Los servicios no necesitan conocer URLs directas
5. **Configuración Simplificada**: Menos configuración manual

## Logging Exhaustivo

### Configuración de Logs
Los microservicios están configurados con logging exhaustivo que muestra:

#### Información Loggeada
- **Instanciación de clases**: Cuándo se crean los objetos
- **Configuración de servicios**: Puertos, URLs, dependencias
- **Invocaciones HTTP**: Requests, responses, status codes
- **Serialización/Deserialización**: JSON parsing
- **Errores y excepciones**: Stack traces completos
- **Performance**: Tiempos de respuesta

#### Niveles de Log
- **INFO**: Información general del flujo
- **DEBUG**: Detalles de implementación
- **WARN**: Advertencias
- **ERROR**: Errores y excepciones

### Script de Pruebas
```powershell
# Ejecutar pruebas con logs detallados
.\test-communication.ps1
```

### Ver Logs en Tiempo Real
```powershell
# Ver logs del Pokemon Service
Get-Content -Path "logs/pokemon-service.log" -Wait

# Ver logs del User-App Service  
Get-Content -Path "logs/user-app-service.log" -Wait
```

## Próximos Pasos

1. **Observabilidad**: Configurar Zipkin para tracing
2. **Testing**: Crear tests de integración
3. **Escalabilidad**: Probar con múltiples instancias
4. **State Management**: Implementar persistencia con DAPR
5. **Pub/Sub**: Implementar comunicación asíncrona 