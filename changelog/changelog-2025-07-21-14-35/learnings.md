# Aprendizajes - Refactorización a Arquitectura en Capas

## Fecha: 2025-07-21-14-35

### Aprendizajes Principales

#### 1. Eliminación de Métodos Estáticos de Entidades

**Lo que aprendimos:**
- Los métodos estáticos en entidades mezclan responsabilidades
- Las entidades deben ser puras y solo representar datos
- La lógica de negocio debe estar en servicios
- Los métodos estáticos son difíciles de testear

**Aplicación práctica:**
```java
// ANTES (incorrecto)
public class Pokemon {
    public static Pokemon getRandomPokemon() { ... }
    public static Pokemon getPokemonById(int id) { ... }
}

// DESPUÉS (correcto)
public class Pokemon {
    // Solo getters, setters y toString
}

public class PokemonService {
    public Pokemon getRandomPokemon() { ... }
    public Optional<Pokemon> getPokemonById(int id) { ... }
}
```

#### 2. Repository Pattern para Acceso a Datos

**Lo que aprendimos:**
- El Repository Pattern abstrae el acceso a datos
- Permite cambiar implementación sin afectar otras capas
- Facilita el testing con mocks
- Es un patrón estándar y reconocido

**Aplicación práctica:**
```java
public interface PokemonRepository {
    Pokemon getRandomPokemon();
    Optional<Pokemon> findById(int id);
    List<Pokemon> findAll();
    Pokemon save(Pokemon pokemon);
}

@ApplicationScoped
public class PokemonRepositoryImpl implements PokemonRepository {
    // Implementación con datos mock
}
```

#### 3. Métodos Especializados en Repository

**Lo que aprendimos:**
- Los repositorios pueden tener métodos específicos del dominio
- `getRandomPokemon()` es más un acceso a datos que lógica de negocio
- No todos los métodos del repositorio deben ser CRUD estándar
- La simplicidad es preferible a la complejidad innecesaria

**Aplicación práctica:**
```java
public interface PokemonRepository {
    // Método especializado del dominio Pokemon
    Pokemon getRandomPokemon();
    
    // Métodos CRUD estándar
    Optional<Pokemon> findById(int id);
    List<Pokemon> findAll();
    Pokemon save(Pokemon pokemon);
}
```

#### 4. Validaciones Específicas del Dominio

**Lo que aprendimos:**
- Cada dominio tiene sus propias reglas de validación
- Las validaciones de Pokemon son diferentes a las de User
- El nivel de Pokemon debe ser mayor a 0
- El tipo de Pokemon no puede estar vacío

**Aplicación práctica:**
```java
// Validaciones específicas para Pokemon
if (pokemon.getLevel() <= 0) {
    throw new IllegalArgumentException("El nivel del Pokemon debe ser mayor a 0");
}

if (pokemon.getType() == null || pokemon.getType().trim().isEmpty()) {
    throw new IllegalArgumentException("El tipo del Pokemon no puede estar vacío");
}
```

#### 5. Compatibilidad con APIs Existentes

**Lo que aprendimos:**
- Es crucial mantener compatibilidad con servicios existentes
- Los endpoints públicos no deben cambiar sin versionado
- La refactorización interna no debe afectar la API externa
- Es mejor agregar funcionalidad que cambiar la existente

**Aplicación práctica:**
```java
// Mantener endpoints existentes exactamente igual
@GET
@Path("/random")
@Produces(MediaType.APPLICATION_JSON)
public Pokemon getRandomPokemon() {
    // Misma funcionalidad, implementación refactorizada
}

// Agregar nuevos endpoints sin afectar los existentes
@POST
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response createPokemon(Pokemon pokemon) {
    // Nueva funcionalidad
}
```

#### 6. Estructura de Datos Thread-Safe

**Lo que aprendimos:**
- En microservicios, la concurrencia es importante
- CopyOnWriteArrayList es bueno para datos que se leen más que se escriben
- Los datos mock también deben ser thread-safe
- La inicialización en el constructor es segura

**Aplicación práctica:**
```java
private final CopyOnWriteArrayList<Pokemon> pokemons = new CopyOnWriteArrayList<>();

public PokemonRepositoryImpl() {
    // Inicialización thread-safe en constructor
    pokemons.add(new Pokemon(1, "Bulbasaur", "Grass/Poison", 5, ...));
    pokemons.add(new Pokemon(4, "Charmander", "Fire", 5, ...));
    // ...
}
```

#### 7. Logs Específicos por Capa

**Lo que aprendimos:**
- Cada capa debe tener su propio formato de logs
- Los logs deben incluir el nombre de la capa para identificación
- Medir tiempos de respuesta en cada capa ayuda a identificar cuellos de botella
- Los logs deben ser consistentes en formato

**Aplicación práctica:**
```java
// Repository Layer
LOG.info("=== REPOSITORY: getRandomPokemon() ===");

// Service Layer  
LOG.info("=== SERVICE: getRandomPokemon() ===");

// Controller Layer
LOG.info("=== ENDPOINT LLAMADO: GET /pokemon/random ===");
```

#### 8. Manejo de Errores Consistente

**Lo que aprendimos:**
- Usar excepciones estándar cuando sea posible
- Manejar errores en la capa de presentación
- Proporcionar mensajes de error claros y útiles
- Mantener consistencia en el manejo de errores

**Aplicación práctica:**
```java
try {
    Pokemon createdPokemon = pokemonService.createPokemon(pokemon);
    return Response.status(Response.Status.CREATED).entity(createdPokemon).build();
} catch (IllegalArgumentException e) {
    return Response.status(Response.Status.BAD_REQUEST)
            .entity("Error de validación: " + e.getMessage())
            .build();
}
```

### Lecciones Aplicables a Futuros Proyectos

#### 1. Refactorización de Métodos Estáticos
- **Identificar**: Métodos estáticos en entidades
- **Separar**: Lógica de negocio de representación de datos
- **Mover**: A capas apropiadas (Service, Repository)
- **Testear**: Cada capa independientemente

#### 2. Diseño de APIs
- **Mantener compatibilidad**: Con APIs existentes
- **Agregar funcionalidad**: Sin romper lo existente
- **Documentar cambios**: En changelogs
- **Versionar cuando sea necesario**: Para cambios breaking

#### 3. Validaciones de Dominio
- **Identificar reglas**: Específicas de cada dominio
- **Implementar en Service Layer**: Donde pertenece la lógica de negocio
- **Usar excepciones apropiadas**: Para diferentes tipos de errores
- **Mensajes claros**: Para debugging

#### 4. Testing de Arquitectura en Capas
- **Testear cada capa**: Independientemente
- **Usar mocks**: Para dependencias
- **Validar integración**: Entre capas
- **Cubrir casos edge**: Y errores

### Errores Comunes a Evitar

#### 1. Mezclar Responsabilidades
- **No poner lógica de negocio**: En entidades
- **No usar métodos estáticos**: Para operaciones de negocio
- **No mezclar capas**: Cada una tiene su responsabilidad
- **No duplicar código**: Entre capas

#### 2. Romper Compatibilidad
- **No cambiar endpoints**: Sin versionado
- **No modificar contratos**: De APIs existentes
- **No eliminar funcionalidad**: Sin migración
- **No cambiar formatos**: De respuesta

#### 3. Sobre-ingeniería
- **No crear abstracciones**: Innecesarias
- **No agregar complejidad**: Sin beneficio claro
- **No implementar patrones**: Solo por moda
- **Mantener simplicidad**: Cuando sea posible

### Mejores Prácticas Identificadas

#### 1. Estructura de Paquetes
```
org.acme.pokemon/
├── domain/           # Entidades puras
├── repository/       # Acceso a datos
│   └── impl/        # Implementaciones
├── service/          # Lógica de negocio
│   └── impl/        # Implementaciones
└── controller/       # Presentación
```

#### 2. Nomenclatura
- **Interfaces**: Sin prefijo (PokemonService)
- **Implementaciones**: Con sufijo Impl (PokemonServiceImpl)
- **Paquetes**: Nombres descriptivos y consistentes
- **Métodos**: Nombres que describan la acción

#### 3. Logging
- **Formato consistente**: `=== CAPA: MÉTODO ===`
- **Información útil**: IDs, tiempos, resultados
- **Niveles apropiados**: INFO para flujo normal, ERROR para problemas
- **Contexto suficiente**: Para debugging

#### 4. Manejo de Errores
- **Excepciones específicas**: Usar tipos apropiados
- **Mensajes claros**: Explicar qué salió mal
- **Recuperación**: Proporcionar alternativas cuando sea posible
- **Consistencia**: En toda la aplicación

### Patrones Identificados

#### 1. Repository Pattern
- **Abstracción**: Del acceso a datos
- **Testabilidad**: Fácil mockear
- **Flexibilidad**: Cambiar implementación
- **Estándar**: Ampliamente reconocido

#### 2. Service Layer Pattern
- **Lógica de negocio**: Centralizada
- **Validaciones**: En la capa correcta
- **Reutilización**: Por diferentes controladores
- **Testabilidad**: Independiente

#### 3. Layered Architecture
- **Separación**: De responsabilidades
- **Mantenibilidad**: Código organizado
- **Escalabilidad**: Fácil agregar funcionalidades
- **Testabilidad**: Cada capa independiente 