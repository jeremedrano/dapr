# Aprendizajes - 2025-07-19 01:40

## Configuración de Puertos en Microservicios Quarkus

### Lecciones Aprendidas

#### 1. Configuración de Puertos en Quarkus
- **Aprendizaje:** Quarkus por defecto usa puerto 8080, causando conflictos en microservicios
- **Solución:** Configurar `quarkus.http.port` en `application.properties`
- **Patrón:** Usar rangos de puertos específicos para cada servicio

#### 2. Separación de Entornos
- **Aprendizaje:** Es crucial separar puertos de desarrollo y testing
- **Patrón:** Usar `%test.quarkus.http.port` para tests
- **Beneficio:** Evita conflictos entre ejecución de app y tests

#### 3. Gestión de Conflictos de Puertos
- **Síntoma:** `QuarkusBindException` y mensajes de puerto ocupado
- **Diagnóstico:** Usar `netstat -a -b -n -o` para identificar procesos
- **Prevención:** Configuración proactiva de puertos específicos

### Mejores Prácticas Identificadas

#### Configuración de Puertos
```properties
# Servicio Principal
quarkus.http.port=8081

# Tests del Servicio
%test.quarkus.http.port=8082
```

#### Convenciones de Nomenclatura
- **Desarrollo:** Puertos 8081, 8083, 8085... (impares)
- **Tests:** Puertos 8082, 8084, 8086... (pares)
- **Lógica:** Fácil de recordar y documentar

### Comandos Útiles

#### Para Identificar Conflictos
```powershell
netstat -a -b -n -o | findstr :8080
```

#### Para Matar Procesos
```powershell
taskkill /PID <pid> /F
```

#### Para Build sin Tests
```powershell
./gradlew build -x test
```

### Consideraciones de Desarrollo

#### Debugging
- Puertos específicos facilitan debugging
- Permite ejecutar múltiples servicios simultáneamente
- Facilita testing de integración

#### Documentación
- Documentar puertos en README
- Mantener changelog de configuraciones
- Considerar variables de entorno para producción

### Patrones para Futuros Proyectos

1. **Configuración Temprana:** Definir puertos al inicio del proyecto
2. **Documentación:** Mantener registro de configuraciones
3. **Separación:** Distinguir claramente entre desarrollo y testing
4. **Escalabilidad:** Planificar rangos de puertos para futuros servicios 