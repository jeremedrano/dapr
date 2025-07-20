package org.acme.user;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.jboss.logging.Logger;

@Path("/users")
public class UserResource {

    private static final Logger LOG = Logger.getLogger(UserResource.class);
    
    @Inject
    PokemonClient pokemonClient;
    
    static {
        LOG.info("=== INICIALIZACIÓN: UserResource class ===");
        LOG.info("UserResource cargado en memoria");
        LOG.info("Endpoints disponibles:");
        LOG.info("  - GET /users/hello");
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

    @GET
    @Path("/pokemon")
    @Produces(MediaType.APPLICATION_JSON)
    public Pokemon getRandomPokemon() {
        LOG.info("=== ENDPOINT LLAMADO: GET /users/pokemon ===");
        LOG.info("Iniciando proceso para obtener Pokemon aleatorio desde Pokemon Service");
        
        long startTime = System.currentTimeMillis();
        Pokemon pokemon = pokemonClient.getRandomPokemon();
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
        try {
            Pokemon pokemon = pokemonClient.getPokemonById(id);
            return Response.ok(pokemon).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Pokemon with ID " + id + " not found or service unavailable")
                    .build();
        }
    }

    @GET
    @Path("/pokemon/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pokemon> getAllPokemons() {
        return pokemonClient.getAllPokemons();
    }

    @GET
    @Path("/pokemon-service/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPokemonServiceHello() {
        return pokemonClient.getPokemonServiceHello();
    }
}
