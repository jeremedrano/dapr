package org.acme.user;

import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

// DAPR Imports
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.HttpExtension;
import io.dapr.client.domain.Metadata;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class PokemonClient {
    
    private static final Logger LOG = Logger.getLogger(PokemonClient.class);
    private final DaprClient daprClient;
    private final ObjectMapper objectMapper;
    
    // DAPR Configuration
    private static final String POKEMON_SERVICE_APP_ID = "pokemon-service";
    private static final String DAPR_HTTP_PORT = "3502"; // Puerto DAPR del User-App Service
    
    static {
        LOG.info("=== INICIALIZACIÓN: PokemonClient class ===");
        LOG.info("PokemonClient cargado en memoria");
        LOG.info("DAPR Pokemon Service App ID: " + POKEMON_SERVICE_APP_ID);
        LOG.info("DAPR HTTP Port: " + DAPR_HTTP_PORT);
        LOG.info("=== FIN INICIALIZACIÓN: PokemonClient ===");
    }
    
    public PokemonClient() {
        LOG.info("=== CONSTRUCTOR: PokemonClient ===");
        
        LOG.info("🔧 PASO 1: Configurando puerto DAPR...");
        LOG.info("   - Puerto configurado: " + DAPR_HTTP_PORT);
        LOG.info("   - Configurando variable de entorno DAPR_HTTP_PORT");
        System.setProperty("DAPR_HTTP_PORT", DAPR_HTTP_PORT);
        
        LOG.info("🔧 PASO 2: Creando DaprClient...");
        LOG.info("   - DAPR creará un cliente HTTP que se conectará al sidecar DAPR");
        LOG.info("   - El sidecar DAPR manejará la comunicación con otros servicios");
        this.daprClient = new DaprClientBuilder().build();
        LOG.info("✅ DaprClient creado exitosamente");
        
        LOG.info("🔧 PASO 2: Creando ObjectMapper...");
        LOG.info("   - ObjectMapper se usará para serializar/deserializar JSON");
        this.objectMapper = new ObjectMapper();
        LOG.info("✅ ObjectMapper creado exitosamente");
        
        LOG.info("🎯 CONFIGURACIÓN DAPR:");
        LOG.info("   - Service Discovery: DAPR encontrará automáticamente el pokemon-service");
        LOG.info("   - Load Balancing: DAPR distribuirá requests si hay múltiples instancias");
        LOG.info("   - Resiliencia: DAPR manejará retries y circuit breakers automáticamente");
        LOG.info("   - Observabilidad: DAPR añadirá tracing automático");
        
        LOG.info("✅ PokemonClient inicializado correctamente con DAPR");
        LOG.info("=== FIN CONSTRUCTOR: PokemonClient ===");
    }
    
    public Pokemon getRandomPokemon() {
        LOG.info("=== MÉTODO LLAMADO: getRandomPokemon() ===");
        LOG.info("🚀 INICIANDO COMUNICACIÓN DAPR CON POKEMON SERVICE");
        LOG.info("   Objetivo: Obtener un Pokemon aleatorio usando DAPR Service Invocation");
        
        try {
            LOG.info("🔧 PASO 1: Preparando DAPR Service Invocation...");
            LOG.info("   - App ID destino: " + POKEMON_SERVICE_APP_ID);
            LOG.info("   - Método HTTP: GET");
            LOG.info("   - Endpoint: /pokemon/random");
            LOG.info("   - DAPR manejará automáticamente:");
            LOG.info("     * Service Discovery (encontrar el servicio)");
            LOG.info("     * Load Balancing (si hay múltiples instancias)");
            LOG.info("     * Retry Logic (reintentos automáticos)");
            LOG.info("     * Circuit Breaker (protección contra fallos)");
            
            LOG.info("🔧 PASO 2: Preparando llamada DAPR...");
            LOG.info("   - DAPR creará un request que será enviado al sidecar DAPR");
            LOG.info("   - El sidecar DAPR se encargará de la comunicación real");
            
            LOG.info("🔧 PASO 3: Enviando request a través de DAPR...");
            LOG.info("   - DaprClient enviará el request al sidecar DAPR local");
            LOG.info("   - Sidecar DAPR buscará el pokemon-service usando service discovery");
            LOG.info("   - Sidecar DAPR enviará el request HTTP al pokemon-service");
            LOG.info("   - Sidecar DAPR recibirá la respuesta y la devolverá");
            
            long startTime = System.currentTimeMillis();
            String responseBody = daprClient.invokeMethod(
                POKEMON_SERVICE_APP_ID, 
                "pokemon/random", 
                null, 
                HttpExtension.GET, 
                String.class
            ).block();
            long endTime = System.currentTimeMillis();
            
            LOG.info("✅ Response recibido de DAPR");
            LOG.info("   - Tiempo total: " + (endTime - startTime) + " ms");
            LOG.info("   - DAPR añadió automáticamente:");
            LOG.info("     * Correlation ID para tracing");
            LOG.info("     * Headers de observabilidad");
            LOG.info("     * Métricas de performance");
            
            if (responseBody != null) {
                LOG.info("🔧 PASO 4: Procesando respuesta...");
                LOG.info("   - Extrayendo body de la respuesta DAPR");
                LOG.debug("   - Response body: " + responseBody);
                
                LOG.info("🔧 PASO 5: Deserializando JSON a objeto Pokemon...");
                LOG.info("   - ObjectMapper convertirá el JSON en un objeto Pokemon");
                Pokemon pokemon = objectMapper.readValue(responseBody, Pokemon.class);
                LOG.info("✅ Pokemon deserializado exitosamente: " + pokemon.getName());
                
                LOG.info("🎯 RESUMEN DAPR:");
                LOG.info("   - Service Discovery: ✅ DAPR encontró automáticamente pokemon-service");
                LOG.info("   - Communication: ✅ Request enviado y respuesta recibida");
                LOG.info("   - Serialization: ✅ JSON convertido a objeto Java");
                LOG.info("   - Performance: ✅ " + (endTime - startTime) + " ms total");
                
                LOG.info("=== FIN MÉTODO: getRandomPokemon() (SUCCESS) ===");
                return pokemon;
            } else {
                LOG.error("❌ Pokemon service retornó respuesta nula");
                LOG.error("   - DAPR recibió un error del pokemon-service");
                LOG.error("   - DAPR no pudo procesar la respuesta correctamente");
                throw new RuntimeException("Pokemon service returned null response");
            }
        } catch (Exception e) {
            LOG.error("❌ ERROR durante la comunicación DAPR con Pokemon service", e);
            LOG.error("   - Posibles causas:");
            LOG.error("     * pokemon-service no está corriendo");
            LOG.error("     * DAPR sidecar no está disponible");
            LOG.error("     * Problema de red entre servicios");
            LOG.error("     * Error en el pokemon-service");
            LOG.info("=== FIN MÉTODO: getRandomPokemon() (ERROR) ===");
            throw new RuntimeException("Failed to get random Pokemon via DAPR", e);
        }
    }
    
    public Pokemon getPokemonById(int id) {
        LOG.info("=== MÉTODO LLAMADO: getPokemonById(" + id + ") ===");
        LOG.info("🚀 INICIANDO COMUNICACIÓN DAPR CON POKEMON SERVICE");
        LOG.info("   Objetivo: Obtener Pokemon con ID " + id + " usando DAPR Service Invocation");
        
        try {
            LOG.info("🔧 PASO 1: Preparando DAPR Service Invocation...");
            LOG.info("   - App ID destino: " + POKEMON_SERVICE_APP_ID);
            LOG.info("   - Método HTTP: GET");
            LOG.info("   - Endpoint: /pokemon/" + id);
            
            LOG.info("🔧 PASO 2: Preparando llamada DAPR...");
            
            LOG.info("🔧 PASO 3: Enviando request a través de DAPR...");
            long startTime = System.currentTimeMillis();
            String responseBody = daprClient.invokeMethod(
                POKEMON_SERVICE_APP_ID, 
                "pokemon/" + id, 
                null, 
                HttpExtension.GET, 
                String.class
            ).block();
            long endTime = System.currentTimeMillis();
            
            LOG.info("✅ Response recibido de DAPR");
            LOG.info("   - Tiempo total: " + (endTime - startTime) + " ms");
            
            if (responseBody != null) {
                LOG.info("🔧 PASO 4: Procesando respuesta...");
                LOG.debug("   - Response body: " + responseBody);
                
                LOG.info("🔧 PASO 5: Deserializando JSON a objeto Pokemon...");
                Pokemon pokemon = objectMapper.readValue(responseBody, Pokemon.class);
                LOG.info("✅ Pokemon deserializado exitosamente: " + pokemon.getName());
                
                LOG.info("🎯 RESUMEN DAPR:");
                LOG.info("   - Service Discovery: ✅ DAPR encontró automáticamente pokemon-service");
                LOG.info("   - Communication: ✅ Request enviado y respuesta recibida");
                LOG.info("   - Pokemon ID " + id + ": " + pokemon.getName());
                LOG.info("   - Performance: ✅ " + (endTime - startTime) + " ms total");
                
                LOG.info("=== FIN MÉTODO: getPokemonById(" + id + ") (SUCCESS) ===");
                return pokemon;
            } else {
                LOG.error("❌ Pokemon service retornó respuesta nula");
                throw new RuntimeException("Pokemon service returned null response");
            }
        } catch (Exception e) {
            LOG.error("❌ ERROR durante la comunicación DAPR con Pokemon service para ID: " + id, e);
            LOG.info("=== FIN MÉTODO: getPokemonById(" + id + ") (ERROR) ===");
            throw new RuntimeException("Failed to get Pokemon with ID: " + id + " via DAPR", e);
        }
    }
    
    public List<Pokemon> getAllPokemons() {
        LOG.info("=== MÉTODO LLAMADO: getAllPokemons() ===");
        LOG.info("🚀 INICIANDO COMUNICACIÓN DAPR CON POKEMON SERVICE");
        LOG.info("   Objetivo: Obtener lista completa de Pokemons usando DAPR Service Invocation");
        
        try {
            LOG.info("🔧 PASO 1: Preparando DAPR Service Invocation...");
            LOG.info("   - App ID destino: " + POKEMON_SERVICE_APP_ID);
            LOG.info("   - Método HTTP: GET");
            LOG.info("   - Endpoint: /pokemon/list");
            
            LOG.info("🔧 PASO 2: Preparando llamada DAPR...");
            
            LOG.info("🔧 PASO 3: Enviando request a través de DAPR...");
            long startTime = System.currentTimeMillis();
            byte[] responseBytes = daprClient.invokeMethod(
                POKEMON_SERVICE_APP_ID, 
                "pokemon/list", 
                null, 
                HttpExtension.GET, 
                byte[].class
            ).block();
            long endTime = System.currentTimeMillis();
            
            LOG.info("✅ Response recibido de DAPR");
            LOG.info("   - Tiempo total: " + (endTime - startTime) + " ms");
            
            if (responseBytes != null) {
                LOG.info("🔧 PASO 4: Procesando respuesta...");
                String responseBody = new String(responseBytes);
                LOG.debug("   - Response body: " + responseBody);
                
                LOG.info("🔧 PASO 5: Deserializando JSON a lista de Pokemons...");
                List<Pokemon> pokemons = objectMapper.readValue(responseBody, 
                    new TypeReference<List<Pokemon>>() {});
                LOG.info("✅ Lista de Pokemons deserializada exitosamente: " + pokemons.size() + " Pokemons");
                
                LOG.info("🎯 RESUMEN DAPR:");
                LOG.info("   - Service Discovery: ✅ DAPR encontró automáticamente pokemon-service");
                LOG.info("   - Communication: ✅ Request enviado y respuesta recibida");
                LOG.info("   - Total Pokemons: " + pokemons.size());
                LOG.info("   - Performance: ✅ " + (endTime - startTime) + " ms total");
                
                LOG.info("=== FIN MÉTODO: getAllPokemons() (SUCCESS) ===");
                return pokemons;
            } else {
                LOG.error("❌ Pokemon service retornó respuesta nula");
                throw new RuntimeException("Pokemon service returned null response");
            }
        } catch (Exception e) {
            LOG.error("❌ ERROR durante la comunicación DAPR con Pokemon service para obtener todos los Pokemons", e);
            LOG.info("=== FIN MÉTODO: getAllPokemons() (ERROR) ===");
            throw new RuntimeException("Failed to get all Pokemons via DAPR", e);
        }
    }
    
    public String getPokemonServiceHello() {
        LOG.info("=== MÉTODO LLAMADO: getPokemonServiceHello() ===");
        LOG.info("🚀 INICIANDO COMUNICACIÓN DAPR CON POKEMON SERVICE");
        LOG.info("   Objetivo: Obtener saludo del Pokemon service usando DAPR Service Invocation");
        
        try {
            LOG.info("🔧 PASO 1: Preparando DAPR Service Invocation...");
            LOG.info("   - App ID destino: " + POKEMON_SERVICE_APP_ID);
            LOG.info("   - Método HTTP: GET");
            LOG.info("   - Endpoint: /pokemon/hello");
            
            LOG.info("🔧 PASO 2: Preparando llamada DAPR...");
            
            LOG.info("🔧 PASO 3: Enviando request a través de DAPR...");
            long startTime = System.currentTimeMillis();
            String responseBody = daprClient.invokeMethod(
                POKEMON_SERVICE_APP_ID, 
                "pokemon/hello", 
                null, 
                HttpExtension.GET, 
                String.class
            ).block();
            long endTime = System.currentTimeMillis();
            
            LOG.info("✅ Response recibido de DAPR");
            LOG.info("   - Tiempo total: " + (endTime - startTime) + " ms");
            
            if (responseBody != null) {
                LOG.info("🔧 PASO 4: Procesando respuesta...");
                LOG.info("✅ Saludo recibido: " + responseBody);
                
                LOG.info("🎯 RESUMEN DAPR:");
                LOG.info("   - Service Discovery: ✅ DAPR encontró automáticamente pokemon-service");
                LOG.info("   - Communication: ✅ Request enviado y respuesta recibida");
                LOG.info("   - Health Check: ✅ Pokemon service responde correctamente");
                LOG.info("   - Performance: ✅ " + (endTime - startTime) + " ms total");
                
                LOG.info("=== FIN MÉTODO: getPokemonServiceHello() (SUCCESS) ===");
                return responseBody;
            } else {
                LOG.error("❌ Pokemon service retornó respuesta nula");
                throw new RuntimeException("Pokemon service returned null response");
            }
        } catch (Exception e) {
            LOG.error("❌ ERROR durante la comunicación DAPR con Pokemon service para obtener saludo", e);
            LOG.info("=== FIN MÉTODO: getPokemonServiceHello() (ERROR) ===");
            throw new RuntimeException("Failed to get Pokemon service hello via DAPR", e);
        }
    }
} 