# Decisiones Técnicas - Arquitectura en Capas

## Fecha: 2025-07-21-14-35

### 1. Decisión: Implementar Arquitectura en Capas

#### Contexto
El microservicio `code-user-pokemon` tenía toda la lógica mezclada en un solo paquete, incluyendo la entidad Pokemon con métodos estáticos que manejaban tanto datos como lógica de negocio.

#### Opciones Consideradas
1. **Mantener estructura actual**: Código mezclado con métodos estáticos
2. **Arquitectura en capas**: Separación clara de responsabilidades
3. **Arquitectura hexagonal**: Más compleja, no necesaria para este caso

#### Decisión Tomada
Implementar **arquitectura en capas** con las siguientes capas:
- **Domain Layer**: Entidades puras sin lógica de negocio
- **Repository Layer**: Acceso a datos (solo estructura)
- **Service Layer**: Lógica de negocio
- **Controller Layer**: Presentación

#### Justificación
- **Simplicidad**: Fácil de entender y mantener
- **Separación de responsabilidades**: Cada capa tiene un propósito específico
- **Testabilidad**: Cada capa puede ser testeada independientemente
- **Escalabilidad**: Fácil agregar nuevas funcionalidades

### 2. Decisión: Eliminar Métodos Estáticos de la Entidad

#### Contexto
La clase Pokemon original tenía métodos estáticos como `getRandomPokemon()`, `getPokemonById()`, etc., que mezclaban lógica de negocio con la entidad.

#### Opciones Consideradas
1. **Mantener métodos estáticos**: En la entidad Pokemon
2. **Mover a Service Layer**: Lógica de negocio en servicios
3. **Mover a Repository Layer**: Lógica de acceso a datos en repositorios

#### Decisión Tomada
**Eliminar métodos estáticos** de la entidad y mover la lógica a las capas apropiadas.

#### Justificación
- **Principio de responsabilidad única**: La entidad solo debe representar datos
- **Testabilidad**: Más fácil testear servicios que métodos estáticos
- **Flexibilidad**: Permite diferentes implementaciones de la lógica
- **Inyección de dependencias**: Facilita el uso de CDI

### 3. Decisión: Implementar Repository Pattern

#### Contexto
Necesitamos una capa de acceso a datos que sea independiente de la implementación específica.

#### Opciones Consideradas
1. **Acceso directo a datos**: Sin abstracción
2. **Repository Pattern**: Interfaz + implementación
3. **DAO Pattern**: Similar al Repository pero más específico

#### Decisión Tomada
Implementar **Repository Pattern** con interfaz y implementación.

#### Justificación
- **Abstracción**: Oculta detalles de implementación
- **Testabilidad**: Fácil mockear para testing
- **Flexibilidad**: Permite cambiar implementación sin afectar otras capas
- **Estándar**: Patrón ampliamente reconocido y usado

### 4. Decisión: Usar CopyOnWriteArrayList para Datos Mock

#### Contexto
Necesitamos una estructura de datos thread-safe para los datos mock de Pokemons.

#### Opciones Consideradas
1. **ArrayList**: Simple pero no thread-safe
2. **Vector**: Thread-safe pero sincronizado (más lento)
3. **CopyOnWriteArrayList**: Thread-safe y optimizado para lecturas
4. **ConcurrentHashMap**: Para acceso por ID

#### Decisión Tomada
Usar **CopyOnWriteArrayList** para almacenar Pokemons.

#### Justificación
- **Thread-safe**: Seguro para múltiples hilos
- **Optimizado para lecturas**: La mayoría de operaciones son lecturas
- **Simplicidad**: Fácil de usar y entender
- **Rendimiento**: Bueno para casos de uso con más lecturas que escrituras

### 5. Decisión: Mantener Método getRandomPokemon en Repository

#### Contexto
El método `getRandomPokemon()` es específico del dominio Pokemon y no es un CRUD estándar.

#### Opciones Consideradas
1. **Solo en Service Layer**: Lógica de negocio en servicio
2. **Solo en Repository Layer**: Lógica de acceso a datos
3. **En ambas capas**: Repository para datos, Service para lógica

#### Decisión Tomada
Implementar **en Repository Layer** con lógica simple de selección aleatoria.

#### Justificación
- **Acceso a datos**: La selección aleatoria es principalmente acceso a datos
- **Simplicidad**: No requiere lógica de negocio compleja
- **Eficiencia**: Acceso directo a la fuente de datos
- **Consistencia**: Mantiene la responsabilidad de acceso a datos en Repository

### 6. Decisión: Validaciones en Service Layer

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

### 7. Decisión: Agregar Endpoints CRUD Completos

#### Contexto
El microservicio solo tenía endpoints de lectura, pero podría necesitar operaciones completas de CRUD.

#### Opciones Consideradas
1. **Mantener solo lectura**: Endpoints existentes
2. **Agregar CRUD completo**: Todos los endpoints
3. **Agregar parcialmente**: Solo algunos endpoints

#### Decisión Tomada
**Agregar CRUD completo** con todos los endpoints.

#### Justificación
- **Completitud**: API completa para gestión de Pokemons
- **Flexibilidad**: Permite operaciones completas desde otros servicios
- **Estándar**: Sigue convenciones REST
- **Futuro**: Preparado para cuando se necesite gestión completa

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

### 9. Decisión: Logs Exhaustivos

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

### 10. Decisión: Compatibilidad Total con Endpoints Existentes

#### Contexto
El microservicio es usado por el User App Service y no podemos romper la funcionalidad existente.

#### Opciones Consideradas
1. **Cambiar endpoints**: Modificar URLs existentes
2. **Mantener compatibilidad**: Preservar endpoints exactos
3. **Versionar API**: Crear nueva versión

#### Decisión Tomada
**Mantener compatibilidad total** con endpoints existentes.

#### Justificación
- **Estabilidad**: No rompe integración existente
- **Simplicidad**: No requiere cambios en otros servicios
- **Riesgo**: Minimiza riesgo de regresiones
- **Incremental**: Permite mejoras graduales 