package org.acme.user;

import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@ApplicationScoped
public class PokemonClient {
    
    private static final Logger LOG = Logger.getLogger(PokemonClient.class);
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final String POKEMON_SERVICE_URL = "http://localhost:8086";
    
    static {
        LOG.info("=== INICIALIZACIÓN: PokemonClient class ===");
        LOG.info("PokemonClient cargado en memoria");
        LOG.info("URL del Pokemon Service: " + POKEMON_SERVICE_URL);
        LOG.info("=== FIN INICIALIZACIÓN: PokemonClient ===");
    }
    
    public PokemonClient() {
        LOG.info("=== CONSTRUCTOR: PokemonClient ===");
        LOG.info("Creando HttpClient...");
        this.httpClient = HttpClient.newHttpClient();
        LOG.info("HttpClient creado exitosamente");
        
        LOG.info("Creando ObjectMapper...");
        this.objectMapper = new ObjectMapper();
        LOG.info("ObjectMapper creado exitosamente");
        
        LOG.info("PokemonClient inicializado correctamente");
        LOG.info("=== FIN CONSTRUCTOR: PokemonClient ===");
    }
    
    public Pokemon getRandomPokemon() {
        LOG.info("=== MÉTODO LLAMADO: getRandomPokemon() ===");
        LOG.info("Iniciando comunicación con Pokemon Service para obtener Pokemon aleatorio");
        
        try {
            String url = POKEMON_SERVICE_URL + "/pokemon/random";
            LOG.info("URL de destino: " + url);
            
            LOG.info("Creando HttpRequest...");
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
            LOG.info("HttpRequest creado exitosamente");
            
            LOG.info("Enviando request HTTP...");
            long startTime = System.currentTimeMillis();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            long endTime = System.currentTimeMillis();
            
            LOG.info("Response recibido - Status: " + response.statusCode() + ", Tiempo: " + (endTime - startTime) + " ms");
            LOG.debug("Response body: " + response.body());
            
            if (response.statusCode() == 200) {
                LOG.info("Deserializando response JSON a objeto Pokemon...");
                Pokemon pokemon = objectMapper.readValue(response.body(), Pokemon.class);
                LOG.info("Pokemon deserializado exitosamente: " + pokemon.getName());
                LOG.info("=== FIN MÉTODO: getRandomPokemon() (SUCCESS) ===");
                return pokemon;
            } else {
                LOG.error("Pokemon service retornó status code: " + response.statusCode());
                throw new RuntimeException("Pokemon service returned status: " + response.statusCode());
            }
        } catch (Exception e) {
            LOG.error("Error durante la comunicación con Pokemon service", e);
            LOG.info("=== FIN MÉTODO: getRandomPokemon() (ERROR) ===");
            throw new RuntimeException("Failed to get random Pokemon", e);
        }
    }
    
    public Pokemon getPokemonById(int id) {
        try {
            LOG.info("Invoking Pokemon service to get Pokemon with ID: " + id);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(POKEMON_SERVICE_URL + "/pokemon/" + id))
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                Pokemon pokemon = objectMapper.readValue(response.body(), Pokemon.class);
                LOG.info("Received Pokemon: " + pokemon);
                return pokemon;
            } else {
                throw new RuntimeException("Pokemon service returned status: " + response.statusCode());
            }
        } catch (Exception e) {
            LOG.error("Error invoking Pokemon service for ID: " + id, e);
            throw new RuntimeException("Failed to get Pokemon with ID: " + id, e);
        }
    }
    
    public List<Pokemon> getAllPokemons() {
        try {
            LOG.info("Invoking Pokemon service to get all Pokemons");
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(POKEMON_SERVICE_URL + "/pokemon/list"))
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                List<Pokemon> pokemons = objectMapper.readValue(response.body(), 
                    new TypeReference<List<Pokemon>>() {});
                LOG.info("Received " + pokemons.size() + " Pokemons");
                return pokemons;
            } else {
                throw new RuntimeException("Pokemon service returned status: " + response.statusCode());
            }
        } catch (Exception e) {
            LOG.error("Error invoking Pokemon service for all Pokemons", e);
            throw new RuntimeException("Failed to get all Pokemons", e);
        }
    }
    
    public String getPokemonServiceHello() {
        try {
            LOG.info("Invoking Pokemon service hello endpoint");
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(POKEMON_SERVICE_URL + "/pokemon/hello"))
                .GET()
                .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                LOG.info("Received response: " + response.body());
                return response.body();
            } else {
                throw new RuntimeException("Pokemon service returned status: " + response.statusCode());
            }
        } catch (Exception e) {
            LOG.error("Error invoking Pokemon service hello", e);
            throw new RuntimeException("Failed to get Pokemon service hello", e);
        }
    }
} 