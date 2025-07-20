package org.acme.pokemon;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.jboss.logging.Logger;

@Path("/pokemon")
public class PokemonResource {
    
    private static final Logger LOG = Logger.getLogger(PokemonResource.class);
    
    static {
        LOG.info("=== INICIALIZACIÓN: PokemonResource class ===");
        LOG.info("PokemonResource cargado en memoria");
        LOG.info("Endpoints disponibles:");
        LOG.info("  - GET /pokemon/random");
        LOG.info("  - GET /pokemon/{id}");
        LOG.info("  - GET /pokemon/list");
        LOG.info("  - GET /pokemon/hello");
        LOG.info("=== FIN INICIALIZACIÓN: PokemonResource ===");
    }

    @GET
    @Path("/random")
    @Produces(MediaType.APPLICATION_JSON)
    public Pokemon getRandomPokemon() {
        LOG.info("=== ENDPOINT LLAMADO: GET /pokemon/random ===");
        LOG.info("Iniciando proceso para obtener Pokemon aleatorio");
        
        long startTime = System.currentTimeMillis();
        Pokemon pokemon = Pokemon.getRandomPokemon();
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
        Pokemon pokemon = Pokemon.getPokemonById(id);
        long endTime = System.currentTimeMillis();
        
        if (pokemon != null) {
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
        List<Pokemon> pokemons = Pokemon.getAllPokemons();
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
}
