package org.acme.user.service.impl;

import org.acme.user.domain.Pokemon;
import org.acme.user.service.PokemonClientService;
import org.acme.user.client.PokemonClient;
import org.jboss.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PokemonClientServiceImpl implements PokemonClientService {
    
    private static final Logger LOG = Logger.getLogger(PokemonClientServiceImpl.class);
    
    @Inject
    PokemonClient pokemonClient;
    
    @Override
    public Pokemon getRandomPokemon() {
        LOG.info("=== EXTERNAL SERVICE: getRandomPokemon() ===");
        LOG.info("Iniciando proceso para obtener Pokemon aleatorio desde Pokemon Service");
        
        long startTime = System.currentTimeMillis();
        Pokemon pokemon = pokemonClient.getRandomPokemon();
        long endTime = System.currentTimeMillis();
        
        LOG.info("Pokemon aleatorio obtenido: " + pokemon.getName());
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN EXTERNAL SERVICE: getRandomPokemon() ===");
        
        return pokemon;
    }

    @Override
    public Pokemon getPokemonById(int id) {
        LOG.info("=== EXTERNAL SERVICE: getPokemonById(id=" + id + ") ===");
        LOG.info("Iniciando proceso para obtener Pokemon con ID: " + id + " desde Pokemon Service");
        
        long startTime = System.currentTimeMillis();
        Pokemon pokemon = pokemonClient.getPokemonById(id);
        long endTime = System.currentTimeMillis();
        
        LOG.info("Pokemon obtenido: " + pokemon.getName());
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN EXTERNAL SERVICE: getPokemonById(id=" + id + ") ===");
        
        return pokemon;
    }

    @Override
    public List<Pokemon> getAllPokemons() {
        LOG.info("=== EXTERNAL SERVICE: getAllPokemons() ===");
        LOG.info("Iniciando proceso para obtener lista de Pokemons desde Pokemon Service");
        
        long startTime = System.currentTimeMillis();
        List<Pokemon> pokemons = pokemonClient.getAllPokemons();
        long endTime = System.currentTimeMillis();
        
        LOG.info("Lista de Pokemons obtenida: " + pokemons.size() + " Pokemons");
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN EXTERNAL SERVICE: getAllPokemons() ===");
        
        return pokemons;
    }

    @Override
    public String getPokemonServiceHello() {
        LOG.info("=== EXTERNAL SERVICE: getPokemonServiceHello() ===");
        LOG.info("Iniciando proceso para obtener saludo desde Pokemon Service");
        
        long startTime = System.currentTimeMillis();
        String response = pokemonClient.getPokemonServiceHello();
        long endTime = System.currentTimeMillis();
        
        LOG.info("Saludo obtenido: " + response);
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN EXTERNAL SERVICE: getPokemonServiceHello() ===");
        
        return response;
    }
} 