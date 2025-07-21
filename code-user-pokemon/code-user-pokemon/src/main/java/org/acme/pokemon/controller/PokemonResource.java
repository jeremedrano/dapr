package org.acme.pokemon.controller;

import org.acme.pokemon.domain.Pokemon;
import org.acme.pokemon.service.PokemonService;
import org.jboss.logging.Logger;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/pokemon")
public class PokemonResource {
    
    private static final Logger LOG = Logger.getLogger(PokemonResource.class);
    
    @Inject
    PokemonService pokemonService;
    
    static {
        LOG.info("=== INICIALIZACIÓN: PokemonResource class ===");
        LOG.info("PokemonResource cargado en memoria");
        LOG.info("Endpoints disponibles:");
        LOG.info("  - GET /pokemon/random");
        LOG.info("  - GET /pokemon/{id}");
        LOG.info("  - GET /pokemon/list");
        LOG.info("  - GET /pokemon/hello");
        LOG.info("  - POST /pokemon");
        LOG.info("  - PUT /pokemon/{id}");
        LOG.info("  - DELETE /pokemon/{id}");
        LOG.info("=== FIN INICIALIZACIÓN: PokemonResource ===");
    }

    @GET
    @Path("/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Pokemon getRandomPokemon() {
        LOG.info("=== ENDPOINT LLAMADO: GET /pokemon/random ===");
        LOG.info("Iniciando proceso para obtener Pokemon aleatorio");
        
        long startTime = System.currentTimeMillis();
        Pokemon pokemon = pokemonService.getRandomPokemon();
        long endTime = System.currentTimeMillis();
        
        LOG.info("Pokemon aleatorio obtenido: " + pokemon.getName());
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN ENDPOINT: GET /pokemon/random ===");
        
        return pokemon;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPokemonById(@PathParam("id") int id) {
        LOG.info("=== ENDPOINT LLAMADO: GET /pokemon/" + id + " ===");
        LOG.info("Iniciando proceso para obtener Pokemon con ID: " + id);
        
        long startTime = System.currentTimeMillis();
        var pokemonOptional = pokemonService.getPokemonById(id);
        long endTime = System.currentTimeMillis();
        
        if (pokemonOptional.isPresent()) {
            Pokemon pokemon = pokemonOptional.get();
            LOG.info("Pokemon encontrado: " + pokemon.getName() + " (ID: " + id + ")");
            LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
            LOG.info("=== FIN ENDPOINT: GET /pokemon/" + id + " (SUCCESS) ===");
            return Response.ok(pokemon).build();
        } else {
            LOG.warn("Pokemon no encontrado con ID: " + id);
            LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
            LOG.info("=== FIN ENDPOINT: GET /pokemon/" + id + " (NOT_FOUND) ===");
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Pokemon with ID " + id + " not found")
                    .build();
        }
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pokemon> getAllPokemons() {
        LOG.info("=== ENDPOINT LLAMADO: GET /pokemon/list ===");
        LOG.info("Iniciando proceso para obtener lista de todos los Pokemons");
        
        long startTime = System.currentTimeMillis();
        List<Pokemon> pokemons = pokemonService.getAllPokemons();
        long endTime = System.currentTimeMillis();
        
        LOG.info("Lista de Pokemons obtenida: " + pokemons.size() + " Pokemons");
        LOG.info("Pokemons en la lista: " + pokemons.stream().map(Pokemon::getName).toList());
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN ENDPOINT: GET /pokemon/list ===");
        
        return pokemons;
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        LOG.info("=== ENDPOINT LLAMADO: GET /pokemon/hello ===");
        LOG.info("Respondiendo saludo del Pokemon Service");
        
        long startTime = System.currentTimeMillis();
        String response = "Hello from Pokemon Service!";
        long endTime = System.currentTimeMillis();
        
        LOG.info("Respuesta enviada: " + response);
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN ENDPOINT: GET /pokemon/hello ===");
        
        return response;
    }

    // Nuevos endpoints para gestión de Pokemons
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPokemon(Pokemon pokemon) {
        LOG.info("=== ENDPOINT LLAMADO: POST /pokemon ===");
        LOG.info("Iniciando proceso para crear nuevo Pokemon");
        
        try {
            long startTime = System.currentTimeMillis();
            Pokemon createdPokemon = pokemonService.createPokemon(pokemon);
            long endTime = System.currentTimeMillis();
            
            LOG.info("Pokemon creado: " + createdPokemon.getName() + " (ID: " + createdPokemon.getId() + ")");
            LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
            LOG.info("=== FIN ENDPOINT: POST /pokemon (SUCCESS) ===");
            
            return Response.status(Response.Status.CREATED).entity(createdPokemon).build();
        } catch (IllegalArgumentException e) {
            LOG.error("Error de validación: " + e.getMessage());
            LOG.info("=== FIN ENDPOINT: POST /pokemon (BAD_REQUEST) ===");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePokemon(@PathParam("id") int id, Pokemon pokemon) {
        LOG.info("=== ENDPOINT LLAMADO: PUT /pokemon/" + id + " ===");
        LOG.info("Iniciando proceso para actualizar Pokemon con ID: " + id);
        
        pokemon.setId(id); // Asegurar que el ID coincida con el path parameter
        
        try {
            long startTime = System.currentTimeMillis();
            Pokemon updatedPokemon = pokemonService.updatePokemon(pokemon);
            long endTime = System.currentTimeMillis();
            
            if (updatedPokemon != null) {
                LOG.info("Pokemon actualizado: " + updatedPokemon.getName());
                LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
                LOG.info("=== FIN ENDPOINT: PUT /pokemon/" + id + " (SUCCESS) ===");
                return Response.ok(updatedPokemon).build();
            } else {
                LOG.warn("Pokemon con ID " + id + " no encontrado para actualizar");
                LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
                LOG.info("=== FIN ENDPOINT: PUT /pokemon/" + id + " (NOT_FOUND) ===");
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Pokemon con ID " + id + " no encontrado")
                        .build();
            }
        } catch (IllegalArgumentException e) {
            LOG.error("Error de validación: " + e.getMessage());
            LOG.info("=== FIN ENDPOINT: PUT /pokemon/" + id + " (BAD_REQUEST) ===");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePokemon(@PathParam("id") int id) {
        LOG.info("=== ENDPOINT LLAMADO: DELETE /pokemon/" + id + " ===");
        LOG.info("Iniciando proceso para eliminar Pokemon con ID: " + id);
        
        try {
            long startTime = System.currentTimeMillis();
            boolean deleted = pokemonService.deletePokemon(id);
            long endTime = System.currentTimeMillis();
            
            if (deleted) {
                LOG.info("Pokemon con ID " + id + " eliminado correctamente");
                LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
                LOG.info("=== FIN ENDPOINT: DELETE /pokemon/" + id + " (SUCCESS) ===");
                return Response.noContent().build();
            } else {
                LOG.warn("Pokemon con ID " + id + " no encontrado para eliminar");
                LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
                LOG.info("=== FIN ENDPOINT: DELETE /pokemon/" + id + " (NOT_FOUND) ===");
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Pokemon con ID " + id + " no encontrado")
                        .build();
            }
        } catch (IllegalArgumentException e) {
            LOG.error("Error de validación: " + e.getMessage());
            LOG.info("=== FIN ENDPOINT: DELETE /pokemon/" + id + " (BAD_REQUEST) ===");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage())
                    .build();
        }
    }
} 