package org.acme.pokemon.service.impl;

import org.acme.pokemon.domain.Pokemon;
import org.acme.pokemon.repository.PokemonRepository;
import org.acme.pokemon.service.PokemonService;
import org.jboss.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PokemonServiceImpl implements PokemonService {
    
    private static final Logger LOG = Logger.getLogger(PokemonServiceImpl.class);
    
    @Inject
    PokemonRepository pokemonRepository;
    
    @Override
    public Pokemon getRandomPokemon() {
        LOG.info("=== SERVICE: getRandomPokemon() ===");
        LOG.info("Iniciando proceso para obtener Pokemon aleatorio");
        
        long startTime = System.currentTimeMillis();
        Pokemon pokemon = pokemonRepository.getRandomPokemon();
        long endTime = System.currentTimeMillis();
        
        LOG.info("Pokemon aleatorio obtenido: " + pokemon.getName());
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN SERVICE: getRandomPokemon() ===");
        
        return pokemon;
    }

    @Override
    public Optional<Pokemon> getPokemonById(int id) {
        LOG.info("=== SERVICE: getPokemonById(id=" + id + ") ===");
        LOG.info("Iniciando proceso para obtener Pokemon con ID: " + id);
        
        long startTime = System.currentTimeMillis();
        Optional<Pokemon> pokemon = pokemonRepository.findById(id);
        long endTime = System.currentTimeMillis();
        
        if (pokemon.isPresent()) {
            LOG.info("Pokemon encontrado: " + pokemon.get().getName() + " (ID: " + id + ")");
        } else {
            LOG.warn("Pokemon no encontrado con ID: " + id);
        }
        
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN SERVICE: getPokemonById(id=" + id + ") ===");
        
        return pokemon;
    }

    @Override
    public List<Pokemon> getAllPokemons() {
        LOG.info("=== SERVICE: getAllPokemons() ===");
        LOG.info("Iniciando proceso para obtener lista de todos los Pokemons");
        
        long startTime = System.currentTimeMillis();
        List<Pokemon> pokemons = pokemonRepository.findAll();
        long endTime = System.currentTimeMillis();
        
        LOG.info("Lista de Pokemons obtenida: " + pokemons.size() + " Pokemons");
        LOG.info("Pokemons en la lista: " + pokemons.stream().map(Pokemon::getName).toList());
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN SERVICE: getAllPokemons() ===");
        
        return pokemons;
    }

    @Override
    public Pokemon createPokemon(Pokemon pokemon) {
        LOG.info("=== SERVICE: createPokemon(pokemon=" + pokemon.getName() + ") ===");
        LOG.info("Iniciando proceso para crear nuevo Pokemon");
        
        // Validaciones de negocio
        if (pokemon.getName() == null || pokemon.getName().trim().isEmpty()) {
            LOG.error("Error: El nombre del Pokemon no puede estar vacío");
            throw new IllegalArgumentException("El nombre del Pokemon no puede estar vacío");
        }
        
        if (pokemon.getType() == null || pokemon.getType().trim().isEmpty()) {
            LOG.error("Error: El tipo del Pokemon no puede estar vacío");
            throw new IllegalArgumentException("El tipo del Pokemon no puede estar vacío");
        }
        
        if (pokemon.getLevel() <= 0) {
            LOG.error("Error: El nivel del Pokemon debe ser mayor a 0");
            throw new IllegalArgumentException("El nivel del Pokemon debe ser mayor a 0");
        }
        
        long startTime = System.currentTimeMillis();
        Pokemon createdPokemon = pokemonRepository.save(pokemon);
        long endTime = System.currentTimeMillis();
        
        LOG.info("Pokemon creado: " + createdPokemon.getName() + " (ID: " + createdPokemon.getId() + ")");
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN SERVICE: createPokemon() ===");
        
        return createdPokemon;
    }

    @Override
    public Pokemon updatePokemon(Pokemon pokemon) {
        LOG.info("=== SERVICE: updatePokemon(pokemon=" + pokemon.getName() + ", ID=" + pokemon.getId() + ") ===");
        LOG.info("Iniciando proceso para actualizar Pokemon");
        
        // Validaciones de negocio
        if (pokemon.getId() <= 0) {
            LOG.error("Error: ID de Pokemon inválido: " + pokemon.getId());
            throw new IllegalArgumentException("ID de Pokemon inválido");
        }
        
        if (pokemon.getName() == null || pokemon.getName().trim().isEmpty()) {
            LOG.error("Error: El nombre del Pokemon no puede estar vacío");
            throw new IllegalArgumentException("El nombre del Pokemon no puede estar vacío");
        }
        
        if (pokemon.getType() == null || pokemon.getType().trim().isEmpty()) {
            LOG.error("Error: El tipo del Pokemon no puede estar vacío");
            throw new IllegalArgumentException("El tipo del Pokemon no puede estar vacío");
        }
        
        if (pokemon.getLevel() <= 0) {
            LOG.error("Error: El nivel del Pokemon debe ser mayor a 0");
            throw new IllegalArgumentException("El nivel del Pokemon debe ser mayor a 0");
        }
        
        long startTime = System.currentTimeMillis();
        Pokemon updatedPokemon = pokemonRepository.update(pokemon);
        long endTime = System.currentTimeMillis();
        
        if (updatedPokemon != null) {
            LOG.info("Pokemon actualizado: " + updatedPokemon.getName());
        } else {
            LOG.warn("Pokemon con ID " + pokemon.getId() + " no encontrado para actualizar");
        }
        
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN SERVICE: updatePokemon() ===");
        
        return updatedPokemon;
    }

    @Override
    public boolean deletePokemon(int id) {
        LOG.info("=== SERVICE: deletePokemon(id=" + id + ") ===");
        LOG.info("Iniciando proceso para eliminar Pokemon");
        
        // Validaciones de negocio
        if (id <= 0) {
            LOG.error("Error: ID de Pokemon inválido: " + id);
            throw new IllegalArgumentException("ID de Pokemon inválido");
        }
        
        long startTime = System.currentTimeMillis();
        boolean deleted = pokemonRepository.deleteById(id);
        long endTime = System.currentTimeMillis();
        
        if (deleted) {
            LOG.info("Pokemon con ID " + id + " eliminado correctamente");
        } else {
            LOG.warn("Pokemon con ID " + id + " no encontrado para eliminar");
        }
        
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN SERVICE: deletePokemon() ===");
        
        return deleted;
    }
} 