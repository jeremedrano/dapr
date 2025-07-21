# Comandos DAPR - Ejecución Manual

## **UBICACIÓN ESPECÍFICA:**
**Desde:** `C:\Users\jmedrano\Desktop\demo-dapr\dapr`

---

## **PASO 1: Verificar DAPR**
```powershell
dapr --version
```
**JUSTIFICACIÓN TÉCNICA:**
1. **¿Qué hace el comando?** - Verifica que DAPR esté instalado y muestra la versión
2. **¿Por qué es necesario?** - Confirma que DAPR CLI está disponible para ejecutar los servicios
3. **¿Qué problema resuelve?** - Evita errores si DAPR no está instalado
4. **¿Cuándo usarlo?** - Antes de iniciar cualquier servicio DAPR

**POSIBLES RESULTADOS:**
- Éxito: `CLI version: 1.15.1 Runtime version: n/a`
- Error: Si no está instalado, seguir instrucciones en https://docs.dapr.io/getting-started/install-dapr-cli/

---

## **PASO 2: Iniciar DAPR Runtime**
```powershell
dapr run --app-id dapr-runtime --dapr-http-port 3500 --dapr-grpc-port 50001
```
**JUSTIFICACIÓN TÉCNICA:**
1. **¿Qué hace el comando?** - Inicia el runtime DAPR base que maneja el service discovery
2. **¿Por qué es necesario?** - Proporciona la infraestructura base para que los servicios se comuniquen
3. **¿Qué problema resuelve?** - Establece el foundation para DAPR Service Invocation
4. **¿Cuándo usarlo?** - Antes de iniciar los microservicios específicos

**POSIBLES RESULTADOS:**
- Éxito: DAPR runtime iniciado en puerto 3500
- Error: Verificar que Docker esté corriendo
- Alternativas: Si falla, verificar `docker ps` para ver contenedores DAPR

---

## **PASO 3: Iniciar Pokemon Service con DAPR**
```powershell
dapr run --app-id pokemon-service --app-port 8086 --dapr-http-port 3501 --dapr-grpc-port 50002 -- cd code-user-pokemon/code-user-pokemon && ./gradlew quarkusDev
```
**JUSTIFICACIÓN TÉCNICA:**
1. **¿Qué hace el comando?** - Inicia el servicio Pokemon con sidecar DAPR en puerto 3501
2. **¿Por qué es necesario?** - Crea el servicio que proporcionará datos de Pokemon
3. **¿Qué problema resuelve?** - Establece el endpoint que será llamado por user-app-service
4. **¿Cuándo usarlo?** - Después del DAPR runtime, antes del user-app-service

**POSIBLES RESULTADOS:**
- Éxito: Pokemon service corriendo en http://localhost:8086 con DAPR sidecar en puerto 3501
- Error: Si falla Gradle, verificar que Java esté instalado
- Alternativas: Si falla, ejecutar `./gradlew clean` primero

---

## **PASO 4: Iniciar User-App Service con DAPR**
```powershell
dapr run --app-id user-app-service --app-port 8088 --dapr-http-port 3502 --dapr-grpc-port 50003 -- cd code-user-app/code-user-app && ./gradlew quarkusDev
```
**JUSTIFICACIÓN TÉCNICA:**
1. **¿Qué hace el comando?** - Inicia el servicio User-App con sidecar DAPR en puerto 3502
2. **¿Por qué es necesario?** - Crea el servicio que consumirá el pokemon-service
3. **¿Qué problema resuelve?** - Establece el endpoint principal que expone las APIs al usuario
4. **¿Cuándo usarlo?** - Después del pokemon-service, como último paso

**POSIBLES RESULTADOS:**
- Éxito: User-App service corriendo en http://localhost:8088 con DAPR sidecar en puerto 3502
- Error: Si falla, verificar que pokemon-service esté corriendo primero
- Alternativas: Si falla, verificar logs de DAPR con `dapr logs`

---

## **PASO 5: Verificar Servicios**
```powershell
# Verificar puertos en uso
netstat -an | findstr "8086"
netstat -an | findstr "8088"
netstat -an | findstr "3501"
netstat -an | findstr "3502"
```
**JUSTIFICACIÓN TÉCNICA:**
1. **¿Qué hace el comando?** - Verifica que los puertos estén ocupados por los servicios
2. **¿Por qué es necesario?** - Confirma que los servicios están realmente corriendo
3. **¿Qué problema resuelve?** - Valida que la configuración de puertos sea correcta
4. **¿Cuándo usarlo?** - Después de iniciar todos los servicios

**POSIBLES RESULTADOS:**
- Éxito: Ver puertos 8086, 8088, 3501, 3502 en estado LISTENING
- Error: Si algún puerto no aparece, el servicio correspondiente no está corriendo
- Alternativas: Usar `dapr list` para ver servicios DAPR activos

---

## **PASO 6: Probar Comunicación**
```powershell
# Probar endpoint de Pokemon list
curl http://localhost:8088/users/pokemon/list
```
**JUSTIFICACIÓN TÉCNICA:**
1. **¿Qué hace el comando?** - Prueba la comunicación entre user-app-service y pokemon-service
2. **¿Por qué es necesario?** - Valida que DAPR Service Invocation esté funcionando
3. **¿Qué problema resuelve?** - Confirma que la arquitectura de microservicios funciona
4. **¿Cuándo usarlo?** - Como prueba final después de iniciar todos los servicios

**POSIBLES RESULTADOS:**
- Éxito: Respuesta JSON con lista de Pokemons
- Error: Si falla, revisar logs de ambos servicios
- Alternativas: Probar otros endpoints como `/users/pokemon` o `/users/pokemon/1`

---

## **ORDEN DE EJECUCIÓN:**
1. **PASO 1** - Verificar DAPR
2. **PASO 2** - Iniciar DAPR Runtime (en ventana separada)
3. **PASO 3** - Iniciar Pokemon Service (en ventana separada)
4. **PASO 4** - Iniciar User-App Service (en ventana separada)
5. **PASO 5** - Verificar servicios
6. **PASO 6** - Probar comunicación

---

## **ENDPOINTS DISPONIBLES:**
- **Pokemon Service:** http://localhost:8086
- **User-App Service:** http://localhost:8088
- **DAPR Endpoints:**
  - GET http://localhost:8088/users/pokemon - Pokemon aleatorio
  - GET http://localhost:8088/users/pokemon/{id} - Pokemon por ID
  - GET http://localhost:8088/users/pokemon/list - Lista completa
  - GET http://localhost:8088/users/pokemon-service/hello - Health check

---

## **TROUBLESHOOTING:**
- **Error "Connection refused":** Verificar que Docker esté corriendo
- **Error "Port already in use":** Detener servicios anteriores con `dapr stop`
- **Error "Service not found":** Verificar que app-ids coincidan en ambos servicios
- **Error "Gradle failed":** Ejecutar `./gradlew clean` antes de `quarkusDev` 