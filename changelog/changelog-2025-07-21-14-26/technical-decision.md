# Decisiones Técnicas - Arquitectura en Capas

## Fecha: 2025-07-21-14-26

### 1. Decisión: Implementar Arquitectura en Capas

#### Contexto
El microservicio `code-user-app` tenía toda la lógica mezclada en un solo paquete, lo que dificultaba el mantenimiento, testing y escalabilidad.

#### Opciones Consideradas
1. **Mantener estructura actual**: Código mezclado en un solo paquete
2. **Arquitectura en capas**: Separación clara de responsabilidades
3. **Arquitectura hexagonal**: Más compleja, no necesaria para este caso

#### Decisión Tomada
Implementar **arquitectura en capas** con las siguientes capas:
- **Domain Layer**: Entidades y DTOs
- **Repository Layer**: Acceso a datos (solo estructura)
- **Service Layer**: Lógica de negocio
- **Client Layer**: Comunicación externa
- **Controller Layer**: Presentación

#### Justificación
- **Simplicidad**: Fácil de entender y mantener
- **Separación de responsabilidades**: Cada capa tiene un propósito específico
- **Testabilidad**: Cada capa puede ser testeada independientemente
- **Escalabilidad**: Fácil agregar nuevas funcionalidades

### 2. Decisión: No Implementar Persistencia Real

#### Contexto
El microservicio no requiere base de datos en este momento, pero se necesita la estructura para futuras implementaciones.

#### Opciones Consideradas
1. **Implementar persistencia real**: Base de datos con JPA/Hibernate
2. **Solo estructura**: Interfaces y clases mock
3. **Sin capa de persistencia**: Eliminar completamente

#### Decisión Tomada
Implementar **solo la estructura** de la capa de persistencia con datos mock.

#### Justificación
- **Preparación para el futuro**: Estructura lista para cuando se necesite BD
- **Simplicidad**: No requiere configuración de BD
- **Testing**: Datos mock facilitan testing
- **Desarrollo rápido**: No hay dependencias externas

### 3. Decisión: Usar CopyOnWriteArrayList para Datos Mock

#### Contexto
Necesitamos una estructura de datos thread-safe para los datos mock de usuarios.

#### Opciones Consideradas
1. **ArrayList**: Simple pero no thread-safe
2. **Vector**: Thread-safe pero sincronizado (más lento)
3. **CopyOnWriteArrayList**: Thread-safe y optimizado para lecturas
4. **ConcurrentHashMap**: Para acceso por ID

#### Decisión Tomada
Usar **CopyOnWriteArrayList** para almacenar usuarios.

#### Justificación
- **Thread-safe**: Seguro para múltiples hilos
- **Optimizado para lecturas**: La mayoría de operaciones son lecturas
- **Simplicidad**: Fácil de usar y entender
- **Rendimiento**: Bueno para casos de uso con más lecturas que escrituras

### 4. Decisión: Mantener PokemonClient Separado

#### Contexto
El PokemonClient maneja comunicación externa con DAPR y tiene lógica específica.

#### Opciones Consideradas
1. **Integrar en Service Layer**: Mover toda la lógica al servicio
2. **Mantener separado**: PokemonClient como capa independiente
3. **Crear abstracción**: Interfaz genérica para clientes externos

#### Decisión Tomada
Mantener **PokemonClient separado** en la capa Client y crear un servicio wrapper.

#### Justificación
- **Separación de responsabilidades**: PokemonClient maneja solo comunicación DAPR
- **Reutilización**: PokemonClient puede ser usado por otros servicios
- **Mantenibilidad**: Cambios en DAPR solo afectan PokemonClient
- **Testing**: Fácil mockear PokemonClient para testing

### 5. Decisión: Usar Inyección de Dependencias

#### Contexto
Necesitamos conectar las diferentes capas de la aplicación.

#### Opciones Consideradas
1. **Inyección de dependencias**: Usar @Inject de CDI
2. **Factory pattern**: Crear instancias manualmente
3. **Singleton pattern**: Instancias únicas

#### Decisión Tomada
Usar **inyección de dependencias** con CDI (@Inject).

#### Justificación
- **Desacoplamiento**: Las clases no crean sus dependencias
- **Testing**: Fácil inyectar mocks
- **Gestión de ciclo de vida**: CDI maneja la creación y destrucción
- **Estándar**: Usa las anotaciones estándar de Jakarta EE

### 6. Decisión: Logs Exhaustivos

#### Contexto
Necesitamos observabilidad para debugging y monitoreo.

#### Opciones Consideradas
1. **Logs mínimos**: Solo errores y warnings
2. **Logs moderados**: Información importante
3. **Logs exhaustivos**: Detalle completo de cada operación

#### Decisión Tomada
Implementar **logs exhaustivos** en todas las capas.

#### Justificación
- **Debugging**: Fácil identificar problemas
- **Observabilidad**: Visibilidad completa del flujo de datos
- **Performance**: Medición de tiempos de respuesta
- **Mantenimiento**: Facilita el soporte en producción

### 7. Decisión: Validaciones en Service Layer

#### Contexto
Necesitamos validar datos de entrada antes de procesarlos.

#### Opciones Consideradas
1. **Validaciones en Controller**: Validar en la capa de presentación
2. **Validaciones en Service**: Validar en la lógica de negocio
3. **Validaciones en ambas**: Duplicar validaciones

#### Decisión Tomada
Implementar **validaciones en Service Layer**.

#### Justificación
- **Lógica de negocio**: Las validaciones son parte del negocio
- **Reutilización**: Servicios pueden ser usados por diferentes controladores
- **Consistencia**: Validaciones centralizadas
- **Testing**: Fácil testear validaciones independientemente

### 8. Decisión: Manejo de Errores con Excepciones

#### Contexto
Necesitamos manejar errores de manera consistente.

#### Opciones Consideradas
1. **Códigos de error**: Devolver códigos numéricos
2. **Excepciones personalizadas**: Crear jerarquía de excepciones
3. **Excepciones estándar**: Usar IllegalArgumentException, etc.

#### Decisión Tomada
Usar **excepciones estándar** (IllegalArgumentException) y manejar en Controller.

#### Justificación
- **Simplicidad**: No requiere crear excepciones personalizadas
- **Estándar**: Usa excepciones bien conocidas
- **Claridad**: Fácil entender qué tipo de error ocurrió
- **Mantenimiento**: Menos código para mantener 