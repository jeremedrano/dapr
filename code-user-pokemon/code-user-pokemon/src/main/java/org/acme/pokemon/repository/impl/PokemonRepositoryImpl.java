package org.acme.pokemon.repository.impl;

import org.acme.pokemon.domain.Pokemon;
import org.acme.pokemon.repository.PokemonRepository;
import org.jboss.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class PokemonRepositoryImpl implements PokemonRepository {
    
    private static final Logger LOG = Logger.getLogger(PokemonRepositoryImpl.class);
    
    // Datos mock - en una implementación real esto vendría de una base de datos
    private final CopyOnWriteArrayList<Pokemon> pokemons = new CopyOnWriteArrayList<>();
    
    public PokemonRepositoryImpl() {
        // Inicializar con Pokemons de ejemplo
        pokemons.add(new Pokemon(1, "Bulbasaur", "Grass/Poison", 5, List.of("Overgrow", "Chlorophyll")));
        pokemons.add(new Pokemon(4, "Charmander", "Fire", 5, List.of("Blaze", "Solar Power")));
        pokemons.add(new Pokemon(7, "Squirtle", "Water", 5, List.of("Torrent", "Rain Dish")));
        pokemons.add(new Pokemon(25, "Pikachu", "Electric", 5, List.of("Static", "Lightning Rod")));
        pokemons.add(new Pokemon(133, "Eevee", "Normal", 5, List.of("Run Away", "Adaptability")));
        pokemons.add(new Pokemon(6, "Charizard", "Fire/Flying", 36, List.of("Blaze", "Solar Power")));
        pokemons.add(new Pokemon(9, "Blastoise", "Water", 36, List.of("Torrent", "Rain Dish")));
        pokemons.add(new Pokemon(3, "Venusaur", "Grass/Poison", 32, List.of("Overgrow", "Chlorophyll")));
        
        LOG.info("PokemonRepositoryImpl inicializado con " + pokemons.size() + " Pokemons de ejemplo");
    }

    @Override
    public Pokemon getRandomPokemon() {
        LOG.info("=== REPOSITORY: getRandomPokemon() ===");
        LOG.debug("Creando array de Pokemons disponibles");
        
        Random random = new Random();
        int selectedIndex = random.nextInt(pokemons.size());
        Pokemon selectedPokemon = pokemons.get(selectedIndex);
        
        LOG.info("Pokemon aleatorio seleccionado: " + selectedPokemon.getName() + " (índice: " + selectedIndex + ")");
        LOG.info("=== FIN REPOSITORY: getRandomPokemon() ===");
        
        return selectedPokemon;
    }

    @Override
    public Optional<Pokemon> findById(int id) {
        LOG.info("=== REPOSITORY: findById(id=" + id + ") ===");
        
        Optional<Pokemon> pokemon = pokemons.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        
        if (pokemon.isPresent()) {
            LOG.info("Pokemon encontrado: " + pokemon.get().getName());
        } else {
            LOG.warn("Pokemon con ID " + id + " no encontrado");
        }
        
        LOG.info("=== FIN REPOSITORY: findById(id=" + id + ") ===");
        return pokemon;
    }

    @Override
    public List<Pokemon> findAll() {
        LOG.info("=== REPOSITORY: findAll() ===");
        List<Pokemon> pokemonList = List.copyOf(pokemons);
        
        LOG.info("Lista de Pokemons obtenida: " + pokemonList.size() + " Pokemons");
        LOG.info("Pokemons en la lista: " + pokemonList.stream().map(Pokemon::getName).toList());
        LOG.info("=== FIN REPOSITORY: findAll() ===");
        
        return pokemonList;
    }

    @Override
    public Pokemon save(Pokemon pokemon) {
        LOG.info("=== REPOSITORY: save(pokemon=" + pokemon.getName() + ") ===");
        
        // Generar ID si no tiene uno
        if (pokemon.getId() == 0) {
            int newId = pokemons.stream()
                    .mapToInt(Pokemon::getId)
                    .max()
                    .orElse(0) + 1;
            pokemon.setId(newId);
            LOG.info("ID generado para nuevo Pokemon: " + newId);
        }
        
        pokemons.add(pokemon);
        LOG.info("Pokemon guardado: " + pokemon.getName() + " (ID: " + pokemon.getId() + ")");
        LOG.info("=== FIN REPOSITORY: save() ===");
        
        return pokemon;
    }

    @Override
    public Pokemon update(Pokemon pokemon) {
        LOG.info("=== REPOSITORY: update(pokemon=" + pokemon.getName() + ", ID=" + pokemon.getId() + ") ===");
        
        for (int i = 0; i < pokemons.size(); i++) {
            if (pokemons.get(i).getId() == pokemon.getId()) {
                pokemons.set(i, pokemon);
                LOG.info("Pokemon actualizado: " + pokemon.getName());
                LOG.info("=== FIN REPOSITORY: update() ===");
                return pokemon;
            }
        }
        
        LOG.warn("Pokemon con ID " + pokemon.getId() + " no encontrado para actualizar");
        LOG.info("=== FIN REPOSITORY: update() ===");
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        LOG.info("=== REPOSITORY: deleteById(id=" + id + ") ===");
        
        boolean removed = pokemons.removeIf(pokemon -> pokemon.getId() == id);
        
        if (removed) {
            LOG.info("Pokemon con ID " + id + " eliminado correctamente");
        } else {
            LOG.warn("Pokemon con ID " + id + " no encontrado para eliminar");
        }
        
        LOG.info("=== FIN REPOSITORY: deleteById() ===");
        return removed;
    }
} 