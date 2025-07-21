package org.acme.user.controller;

import org.acme.user.domain.Pokemon;
import org.acme.user.domain.User;
import org.acme.user.service.PokemonClientService;
import org.acme.user.service.UserService;
import org.jboss.logging.Logger;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserResource {

    private static final Logger LOG = Logger.getLogger(UserResource.class);
    
    @Inject
    PokemonClientService pokemonClientService;
    
    @Inject
    UserService userService;
    
    static {
        LOG.info("=== INICIALIZACIÓN: UserResource class ===");
        LOG.info("UserResource cargado en memoria");
        LOG.info("Endpoints disponibles:");
        LOG.info("  - GET /users/hello");
        LOG.info("  - GET /users");
        LOG.info("  - GET /users/{id}");
        LOG.info("  - POST /users");
        LOG.info("  - PUT /users/{id}");
        LOG.info("  - DELETE /users/{id}");
        LOG.info("  - GET /users/pokemon");
        LOG.info("  - GET /users/pokemon/{id}");
        LOG.info("  - GET /users/pokemon/list");
        LOG.info("  - GET /users/pokemon-service/hello");
        LOG.info("=== FIN INICIALIZACIÓN: UserResource ===");
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        LOG.info("=== ENDPOINT LLAMADO: GET /users/hello ===");
        LOG.info("Respondiendo saludo del User App Service");
        
        long startTime = System.currentTimeMillis();
        String response = "Hello from User App Service!";
        long endTime = System.currentTimeMillis();
        
        LOG.info("Respuesta enviada: " + response);
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN ENDPOINT: GET /users/hello ===");
        
        return response;
    }

    // Endpoints para gestión de usuarios
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        LOG.info("=== ENDPOINT LLAMADO: GET /users ===");
        LOG.info("Iniciando proceso para obtener todos los usuarios");
        
        long startTime = System.currentTimeMillis();
        List<User> users = userService.getAllUsers();
        long endTime = System.currentTimeMillis();
        
        LOG.info("Usuarios obtenidos: " + users.size());
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN ENDPOINT: GET /users ===");
        
        return users;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id) {
        LOG.info("=== ENDPOINT LLAMADO: GET /users/" + id + " ===");
        LOG.info("Iniciando proceso para obtener usuario con ID: " + id);
        
        long startTime = System.currentTimeMillis();
        var userOptional = userService.getUserById(id);
        long endTime = System.currentTimeMillis();
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            LOG.info("Usuario encontrado: " + user.getName() + " (ID: " + id + ")");
            LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
            LOG.info("=== FIN ENDPOINT: GET /users/" + id + " (SUCCESS) ===");
            return Response.ok(user).build();
        } else {
            LOG.warn("Usuario no encontrado con ID: " + id);
            LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
            LOG.info("=== FIN ENDPOINT: GET /users/" + id + " (NOT_FOUND) ===");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuario con ID " + id + " no encontrado")
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        LOG.info("=== ENDPOINT LLAMADO: POST /users ===");
        LOG.info("Iniciando proceso para crear nuevo usuario");
        
        try {
            long startTime = System.currentTimeMillis();
            User createdUser = userService.createUser(user);
            long endTime = System.currentTimeMillis();
            
            LOG.info("Usuario creado: " + createdUser.getName() + " (ID: " + createdUser.getId() + ")");
            LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
            LOG.info("=== FIN ENDPOINT: POST /users (SUCCESS) ===");
            
            return Response.status(Response.Status.CREATED).entity(createdUser).build();
        } catch (IllegalArgumentException e) {
            LOG.error("Error de validación: " + e.getMessage());
            LOG.info("=== FIN ENDPOINT: POST /users (BAD_REQUEST) ===");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, User user) {
        LOG.info("=== ENDPOINT LLAMADO: PUT /users/" + id + " ===");
        LOG.info("Iniciando proceso para actualizar usuario con ID: " + id);
        
        user.setId(id); // Asegurar que el ID coincida con el path parameter
        
        try {
            long startTime = System.currentTimeMillis();
            User updatedUser = userService.updateUser(user);
            long endTime = System.currentTimeMillis();
            
            if (updatedUser != null) {
                LOG.info("Usuario actualizado: " + updatedUser.getName());
                LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
                LOG.info("=== FIN ENDPOINT: PUT /users/" + id + " (SUCCESS) ===");
                return Response.ok(updatedUser).build();
            } else {
                LOG.warn("Usuario con ID " + id + " no encontrado para actualizar");
                LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
                LOG.info("=== FIN ENDPOINT: PUT /users/" + id + " (NOT_FOUND) ===");
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuario con ID " + id + " no encontrado")
                        .build();
            }
        } catch (IllegalArgumentException e) {
            LOG.error("Error de validación: " + e.getMessage());
            LOG.info("=== FIN ENDPOINT: PUT /users/" + id + " (BAD_REQUEST) ===");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        LOG.info("=== ENDPOINT LLAMADO: DELETE /users/" + id + " ===");
        LOG.info("Iniciando proceso para eliminar usuario con ID: " + id);
        
        try {
            long startTime = System.currentTimeMillis();
            boolean deleted = userService.deleteUser(id);
            long endTime = System.currentTimeMillis();
            
            if (deleted) {
                LOG.info("Usuario con ID " + id + " eliminado correctamente");
                LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
                LOG.info("=== FIN ENDPOINT: DELETE /users/" + id + " (SUCCESS) ===");
                return Response.noContent().build();
            } else {
                LOG.warn("Usuario con ID " + id + " no encontrado para eliminar");
                LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
                LOG.info("=== FIN ENDPOINT: DELETE /users/" + id + " (NOT_FOUND) ===");
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuario con ID " + id + " no encontrado")
                        .build();
            }
        } catch (IllegalArgumentException e) {
            LOG.error("Error de validación: " + e.getMessage());
            LOG.info("=== FIN ENDPOINT: DELETE /users/" + id + " (BAD_REQUEST) ===");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage())
                    .build();
        }
    }

    // Endpoints para comunicación con Pokemon Service
    @GET
    @Path("/pokemon")
    @Produces(MediaType.APPLICATION_JSON)
    public Pokemon getRandomPokemon() {
        LOG.info("=== ENDPOINT LLAMADO: GET /users/pokemon ===");
        LOG.info("Iniciando proceso para obtener Pokemon aleatorio desde Pokemon Service");
        
        long startTime = System.currentTimeMillis();
        Pokemon pokemon = pokemonClientService.getRandomPokemon();
        long endTime = System.currentTimeMillis();
        
        LOG.info("Pokemon aleatorio obtenido: " + pokemon.getName());
        LOG.info("Tiempo total de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN ENDPOINT: GET /users/pokemon ===");
        
        return pokemon;
    }

    @GET
    @Path("/pokemon/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPokemonById(@PathParam("id") int id) {
        LOG.info("=== ENDPOINT LLAMADO: GET /users/pokemon/" + id + " ===");
        LOG.info("Iniciando proceso para obtener Pokemon con ID: " + id + " desde Pokemon Service");
        
        try {
            long startTime = System.currentTimeMillis();
            Pokemon pokemon = pokemonClientService.getPokemonById(id);
            long endTime = System.currentTimeMillis();
            
            LOG.info("Pokemon obtenido: " + pokemon.getName());
            LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
            LOG.info("=== FIN ENDPOINT: GET /users/pokemon/" + id + " (SUCCESS) ===");
            
            return Response.ok(pokemon).build();
        } catch (Exception e) {
            LOG.error("Error obteniendo Pokemon: " + e.getMessage());
            LOG.info("=== FIN ENDPOINT: GET /users/pokemon/" + id + " (ERROR) ===");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Pokemon with ID " + id + " not found or service unavailable")
                    .build();
        }
    }

    @GET
    @Path("/pokemon/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pokemon> getAllPokemons() {
        LOG.info("=== ENDPOINT LLAMADO: GET /users/pokemon/list ===");
        LOG.info("Iniciando proceso para obtener lista de Pokemons desde Pokemon Service");
        
        long startTime = System.currentTimeMillis();
        List<Pokemon> pokemons = pokemonClientService.getAllPokemons();
        long endTime = System.currentTimeMillis();
        
        LOG.info("Lista de Pokemons obtenida: " + pokemons.size() + " Pokemons");
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN ENDPOINT: GET /users/pokemon/list ===");
        
        return pokemons;
    }

    @GET
    @Path("/pokemon-service/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPokemonServiceHello() {
        LOG.info("=== ENDPOINT LLAMADO: GET /users/pokemon-service/hello ===");
        LOG.info("Iniciando proceso para obtener saludo desde Pokemon Service");
        
        long startTime = System.currentTimeMillis();
        String response = pokemonClientService.getPokemonServiceHello();
        long endTime = System.currentTimeMillis();
        
        LOG.info("Saludo obtenido: " + response);
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN ENDPOINT: GET /users/pokemon-service/hello ===");
        
        return response;
    }
} 