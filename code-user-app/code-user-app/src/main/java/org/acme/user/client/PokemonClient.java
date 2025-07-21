package org.acme.user.client;

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
    
    public org.acme.user.domain.Pokemon getRandomPokemon() {
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
            
            // Usar el método correcto de DAPR para objetos JSON
            LOG.info("🔧 PASO 4: Usando DAPR con Object.class y cast...");
            Object response = daprClient.invokeMethod(
                POKEMON_SERVICE_APP_ID, 
                "pokemon/random", 
                null, 
                HttpExtension.GET, 
                Object.class
            ).block();
            
            LOG.info("🔧 PASO 5: Deserializando respuesta usando ObjectMapper...");
            String jsonResponse = objectMapper.writeValueAsString(response);
            org.acme.user.domain.Pokemon pokemon = objectMapper.readValue(
                jsonResponse, 
                org.acme.user.domain.Pokemon.class
            );
            
            long endTime = System.currentTimeMillis();
            
            LOG.info("✅ RESPUESTA RECIBIDA DEL POKEMON SERVICE");
            LOG.info("   - Tiempo de respuesta: " + (endTime - startTime) + " ms");
            LOG.info("   - Pokemon obtenido: " + pokemon.getName());
            LOG.info("✅ Pokemon deserializado: " + pokemon.getName());
            
            LOG.info("🎉 COMUNICACIÓN DAPR EXITOSA");
            LOG.info("   - Pokemon obtenido: " + pokemon.getName());
            LOG.info("   - ID: " + pokemon.getId());
            LOG.info("   - Tipo: " + pokemon.getType());
            LOG.info("   - Nivel: " + pokemon.getLevel());
            LOG.info("   - Habilidades: " + pokemon.getAbilities());
            
            LOG.info("=== FIN MÉTODO: getRandomPokemon() ===");
            return pokemon;
            
        } catch (Exception e) {
            LOG.error("❌ ERROR EN COMUNICACIÓN DAPR CON POKEMON SERVICE");
            LOG.error("   - Error: " + e.getMessage());
            LOG.error("   - Stack trace: ", e);
            LOG.error("   - DAPR no pudo comunicarse con el pokemon-service");
            LOG.error("   - Posibles causas:");
            LOG.error("     * pokemon-service no está ejecutándose");
            LOG.error("     * DAPR sidecar no está configurado correctamente");
            LOG.error("     * Problemas de red entre servicios");
            
            throw new RuntimeException("Error comunicándose con Pokemon Service a través de DAPR", e);
        }
    }
    
    public org.acme.user.domain.Pokemon getPokemonById(int id) {
        LOG.info("=== MÉTODO LLAMADO: getPokemonById(id=" + id + ") ===");
        LOG.info("🚀 INICIANDO COMUNICACIÓN DAPR CON POKEMON SERVICE");
        LOG.info("   Objetivo: Obtener Pokemon con ID " + id + " usando DAPR Service Invocation");
        
        try {
            LOG.info("🔧 PASO 1: Preparando DAPR Service Invocation...");
            LOG.info("   - App ID destino: " + POKEMON_SERVICE_APP_ID);
            LOG.info("   - Método HTTP: GET");
            LOG.info("   - Endpoint: /pokemon/" + id);
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
            
            // Usar el método correcto de DAPR para objetos JSON
            LOG.info("🔧 PASO 4: Usando DAPR con Object.class y cast...");
            Object response = daprClient.invokeMethod(
                POKEMON_SERVICE_APP_ID, 
                "pokemon/" + id, 
                null, 
                HttpExtension.GET, 
                Object.class
            ).block();
            
            LOG.info("🔧 PASO 5: Deserializando respuesta usando ObjectMapper...");
            String jsonResponse = objectMapper.writeValueAsString(response);
            org.acme.user.domain.Pokemon pokemon = objectMapper.readValue(
                jsonResponse, 
                org.acme.user.domain.Pokemon.class
            );
            
            long endTime = System.currentTimeMillis();
            
            LOG.info("✅ RESPUESTA RECIBIDA DEL POKEMON SERVICE");
            LOG.info("   - Tiempo de respuesta: " + (endTime - startTime) + " ms");
            LOG.info("   - Pokemon obtenido: " + pokemon.getName());
            LOG.info("✅ Pokemon deserializado: " + pokemon.getName());
            
            LOG.info("🎉 COMUNICACIÓN DAPR EXITOSA");
            LOG.info("   - Pokemon obtenido: " + pokemon.getName());
            LOG.info("   - ID: " + pokemon.getId());
            LOG.info("   - Tipo: " + pokemon.getType());
            LOG.info("   - Nivel: " + pokemon.getLevel());
            LOG.info("   - Habilidades: " + pokemon.getAbilities());
            
            LOG.info("=== FIN MÉTODO: getPokemonById(id=" + id + ") ===");
            return pokemon;
            
        } catch (Exception e) {
            LOG.error("❌ ERROR EN COMUNICACIÓN DAPR CON POKEMON SERVICE");
            LOG.error("   - Error: " + e.getMessage());
            LOG.error("   - Stack trace: ", e);
            LOG.error("   - DAPR no pudo comunicarse con el pokemon-service");
            LOG.error("   - Posibles causas:");
            LOG.error("     * pokemon-service no está ejecutándose");
            LOG.error("     * DAPR sidecar no está configurado correctamente");
            LOG.error("     * Problemas de red entre servicios");
            
            throw new RuntimeException("Error comunicándose con Pokemon Service a través de DAPR", e);
        }
    }
    
    public List<org.acme.user.domain.Pokemon> getAllPokemons() {
        LOG.info("=== MÉTODO LLAMADO: getAllPokemons() ===");
        LOG.info("🚀 INICIANDO COMUNICACIÓN DAPR CON POKEMON SERVICE");
        LOG.info("   Objetivo: Obtener todos los Pokemons usando DAPR Service Invocation");
        
        try {
            LOG.info("🔧 PASO 1: Preparando DAPR Service Invocation...");
            LOG.info("   - App ID destino: " + POKEMON_SERVICE_APP_ID);
            LOG.info("   - Método HTTP: GET");
            LOG.info("   - Endpoint: /pokemon/list");
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
            
            // Usar el método correcto de DAPR para arrays JSON
            LOG.info("🔧 PASO 4: Usando DAPR con Object.class y cast...");
            Object response = daprClient.invokeMethod(
                POKEMON_SERVICE_APP_ID, 
                "pokemon/list", 
                null, 
                HttpExtension.GET, 
                Object.class
            ).block();
            
            LOG.info("🔧 PASO 5: Deserializando respuesta usando ObjectMapper...");
            String jsonResponse = objectMapper.writeValueAsString(response);
            List<org.acme.user.domain.Pokemon> pokemons = objectMapper.readValue(
                jsonResponse, 
                new TypeReference<List<org.acme.user.domain.Pokemon>>() {}
            );
            
            long endTime = System.currentTimeMillis();
            
            LOG.info("✅ RESPUESTA RECIBIDA DEL POKEMON SERVICE");
            LOG.info("   - Pokemons obtenidos: " + pokemons.size());
            
            LOG.info("✅ Lista de Pokemons deserializada: " + pokemons.size() + " Pokemons");
            LOG.info("   - Tiempo de respuesta: " + (endTime - startTime) + " ms");
            
            LOG.info("🎉 COMUNICACIÓN DAPR EXITOSA");
            LOG.info("   - Pokemons obtenidos: " + pokemons.size());
            LOG.info("   - Nombres: " + pokemons.stream().map(org.acme.user.domain.Pokemon::getName).toList());
            
            LOG.info("=== FIN MÉTODO: getAllPokemons() ===");
            return pokemons;
            
        } catch (Exception e) {
            LOG.error("❌ ERROR EN COMUNICACIÓN DAPR CON POKEMON SERVICE");
            LOG.error("   - Error: " + e.getMessage());
            LOG.error("   - Stack trace: ", e);
            LOG.error("   - DAPR no pudo comunicarse con el pokemon-service");
            LOG.error("   - Posibles causas:");
            LOG.error("     * pokemon-service no está ejecutándose");
            LOG.error("     * DAPR sidecar no está configurado correctamente");
            LOG.error("     * Problemas de red entre servicios");
            
            throw new RuntimeException("Error comunicándose con Pokemon Service a través de DAPR", e);
        }
    }
    
    public String getPokemonServiceHello() {
        LOG.info("=== MÉTODO LLAMADO: getPokemonServiceHello() ===");
        LOG.info("🚀 INICIANDO COMUNICACIÓN DAPR CON POKEMON SERVICE");
        LOG.info("   Objetivo: Obtener saludo del Pokemon Service usando DAPR Service Invocation");
        
        try {
            LOG.info("🔧 PASO 1: Preparando DAPR Service Invocation...");
            LOG.info("   - App ID destino: " + POKEMON_SERVICE_APP_ID);
            LOG.info("   - Método HTTP: GET");
            LOG.info("   - Endpoint: /pokemon/hello");
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
            
            // Usar el método correcto de DAPR para texto plano
            LOG.info("🔧 PASO 4: Usando DAPR con Object.class para texto plano...");
            Object response = daprClient.invokeMethod(
                POKEMON_SERVICE_APP_ID, 
                "pokemon/hello", 
                null, 
                HttpExtension.GET, 
                Object.class
            ).block();
            
            LOG.info("🔧 PASO 5: Convirtiendo respuesta a String...");
            String responseBody = response.toString();
            
            long endTime = System.currentTimeMillis();
            
            LOG.info("✅ RESPUESTA RECIBIDA DEL POKEMON SERVICE");
            LOG.info("   - Tiempo de respuesta: " + (endTime - startTime) + " ms");
            LOG.info("   - Response body: " + responseBody);
            
            LOG.info("🎉 COMUNICACIÓN DAPR EXITOSA");
            LOG.info("   - Saludo obtenido: " + responseBody);
            
            LOG.info("=== FIN MÉTODO: getPokemonServiceHello() ===");
            return responseBody;
            
        } catch (Exception e) {
            LOG.error("❌ ERROR EN COMUNICACIÓN DAPR CON POKEMON SERVICE");
            LOG.error("   - Error: " + e.getMessage());
            LOG.error("   - Stack trace: ", e);
            LOG.error("   - DAPR no pudo comunicarse con el pokemon-service");
            LOG.error("   - Posibles causas:");
            LOG.error("     * pokemon-service no está ejecutándose");
            LOG.error("     * DAPR sidecar no está configurado correctamente");
            LOG.error("     * Problemas de red entre servicios");
            
            throw new RuntimeException("Error comunicándose con Pokemon Service a través de DAPR", e);
        }
    }
    
    public List<org.acme.user.domain.Pokemon> getAllPokemonsGrpc() {
        LOG.info("=== MÉTODO LLAMADO: getAllPokemonsGrpc() ===");
        LOG.info("🚀 INICIANDO COMUNICACIÓN DAPR gRPC CON POKEMON SERVICE");
        LOG.info("   Objetivo: Obtener todos los Pokemons usando DAPR con puerto gRPC");
        
        try {
            LOG.info("🔧 PASO 1: Configurando puerto gRPC DAPR...");
            LOG.info("   - Puerto gRPC configurado: 50003");
            LOG.info("   - Configurando variable de entorno DAPR_GRPC_PORT");
            System.setProperty("DAPR_GRPC_PORT", "50003");
            
            LOG.info("🔧 PASO 2: Preparando DAPR Service Invocation via gRPC...");
            LOG.info("   - App ID destino: " + POKEMON_SERVICE_APP_ID);
            LOG.info("   - Método HTTP: GET");
            LOG.info("   - Endpoint: /pokemon/list");
            LOG.info("   - Protocolo: DAPR gRPC (puerto 50003)");
            LOG.info("   - DAPR manejará automáticamente:");
            LOG.info("     * Service Discovery (encontrar el servicio)");
            LOG.info("     * Load Balancing (si hay múltiples instancias)");
            LOG.info("     * Retry Logic (reintentos automáticos)");
            LOG.info("     * Circuit Breaker (protección contra fallos)");
            LOG.info("     * Comunicación gRPC entre sidecars");
            
            LOG.info("🔧 PASO 3: Preparando llamada DAPR gRPC...");
            LOG.info("   - DAPR creará un request que será enviado al sidecar DAPR via gRPC");
            LOG.info("   - El sidecar DAPR se encargará de la comunicación gRPC real");
            
            LOG.info("🔧 PASO 4: Enviando request a través de DAPR gRPC...");
            LOG.info("   - DaprClient enviará el request al sidecar DAPR local via gRPC");
            LOG.info("   - Sidecar DAPR buscará el pokemon-service usando service discovery");
            LOG.info("   - Sidecar DAPR enviará el request HTTP al pokemon-service");
            LOG.info("   - Sidecar DAPR recibirá la respuesta y la devolverá via gRPC");
            
            long startTime = System.currentTimeMillis();
            
            // Usar el método correcto de DAPR para arrays JSON via gRPC
            LOG.info("🔧 PASO 5: Usando DAPR gRPC con Object.class y cast...");
            Object response = daprClient.invokeMethod(
                POKEMON_SERVICE_APP_ID, 
                "pokemon/list", 
                null, 
                HttpExtension.GET, 
                Object.class
            ).block();
            
            LOG.info("🔧 PASO 6: Deserializando respuesta usando ObjectMapper...");
            String jsonResponse = objectMapper.writeValueAsString(response);
            List<org.acme.user.domain.Pokemon> pokemons = objectMapper.readValue(
                jsonResponse, 
                new TypeReference<List<org.acme.user.domain.Pokemon>>() {}
            );
            
            long endTime = System.currentTimeMillis();
            
            LOG.info("✅ RESPUESTA DAPR gRPC RECIBIDA DEL POKEMON SERVICE");
            LOG.info("   - Protocolo usado: DAPR gRPC (puerto 50003)");
            LOG.info("   - Pokemons obtenidos: " + pokemons.size());
            LOG.info("   - Tiempo de respuesta: " + (endTime - startTime) + " ms");
            
            LOG.info("✅ Lista de Pokemons deserializada: " + pokemons.size() + " Pokemons");
            LOG.info("   - Tiempo total: " + (endTime - startTime) + " ms");
            
            LOG.info("🎉 COMUNICACIÓN DAPR gRPC EXITOSA");
            LOG.info("   - Protocolo: DAPR gRPC (más eficiente que HTTP directo)");
            LOG.info("   - Pokemons obtenidos: " + pokemons.size());
            LOG.info("   - Nombres: " + pokemons.stream().map(org.acme.user.domain.Pokemon::getName).toList());
            
            LOG.info("=== FIN MÉTODO: getAllPokemonsGrpc() ===");
            return pokemons;
            
        } catch (Exception e) {
            LOG.error("❌ ERROR EN COMUNICACIÓN DAPR gRPC CON POKEMON SERVICE");
            LOG.error("   - Error: " + e.getMessage());
            LOG.error("   - Stack trace: ", e);
            LOG.error("   - DAPR no pudo comunicarse con el pokemon-service via gRPC");
            LOG.error("   - Posibles causas:");
            LOG.error("     * pokemon-service no está ejecutándose");
            LOG.error("     * DAPR sidecar no está configurado correctamente");
            LOG.error("     * Problemas de red entre servicios");
            LOG.error("     * Puerto gRPC no configurado correctamente");
            
            throw new RuntimeException("Error comunicándose con Pokemon Service a través de DAPR gRPC", e);
        }
    }
} 