package org.acme.pokemon;

import java.util.List;
import java.util.Random;
import org.jboss.logging.Logger;

public class Pokemon {
    private static final Logger LOG = Logger.getLogger(Pokemon.class);
    private int id;
    private String name;
    private String type;
    private int level;
    private List<String> abilities;

    public Pokemon() {
        LOG.debug("Pokemon constructor vacío llamado - creando Pokemon sin datos");
    }

    public Pokemon(int id, String name, String type, int level, List<String> abilities) {
        LOG.debug("Pokemon constructor con parámetros llamado - ID: " + id + ", Name: " + name + ", Type: " + type + ", Level: " + level + ", Abilities: " + abilities);
        this.id = id;
        this.name = name;
        this.type = type;
        this.level = level;
        this.abilities = abilities;
        LOG.debug("Pokemon creado exitosamente: " + this);
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public List<String> getAbilities() { return abilities; }
    public void setAbilities(List<String> abilities) { this.abilities = abilities; }

    // Método para obtener un Pokemon aleatorio
    public static Pokemon getRandomPokemon() {
        LOG.info("=== INICIO: getRandomPokemon() ===");
        LOG.debug("Creando array de Pokemons disponibles");
        
        Pokemon[] pokemons = {
            new Pokemon(1, "Bulbasaur", "Grass/Poison", 5, List.of("Overgrow", "Chlorophyll")),
            new Pokemon(4, "Charmander", "Fire", 5, List.of("Blaze", "Solar Power")),
            new Pokemon(7, "Squirtle", "Water", 5, List.of("Torrent", "Rain Dish")),
            new Pokemon(25, "Pikachu", "Electric", 5, List.of("Static", "Lightning Rod")),
            new Pokemon(133, "Eevee", "Normal", 5, List.of("Run Away", "Adaptability")),
            new Pokemon(6, "Charizard", "Fire/Flying", 36, List.of("Blaze", "Solar Power")),
            new Pokemon(9, "Blastoise", "Water", 36, List.of("Torrent", "Rain Dish")),
            new Pokemon(3, "Venusaur", "Grass/Poison", 32, List.of("Overgrow", "Chlorophyll"))
        };
        
        LOG.debug("Array de Pokemons creado con " + pokemons.length + " elementos");
        
        Random random = new Random();
        int selectedIndex = random.nextInt(pokemons.length);
        Pokemon selectedPokemon = pokemons[selectedIndex];
        
        LOG.info("Pokemon aleatorio seleccionado: " + selectedPokemon.getName() + " (índice: " + selectedIndex + ")");
        LOG.info("=== FIN: getRandomPokemon() ===");
        
        return selectedPokemon;
    }

    // Método para obtener un Pokemon por ID
    public static Pokemon getPokemonById(int id) {
        LOG.info("=== INICIO: getPokemonById(id=" + id + ") ===");
        
        Pokemon pokemon = null;
        switch (id) {
            case 1: 
                pokemon = new Pokemon(1, "Bulbasaur", "Grass/Poison", 5, List.of("Overgrow", "Chlorophyll"));
                break;
            case 4: 
                pokemon = new Pokemon(4, "Charmander", "Fire", 5, List.of("Blaze", "Solar Power"));
                break;
            case 7: 
                pokemon = new Pokemon(7, "Squirtle", "Water", 5, List.of("Torrent", "Rain Dish"));
                break;
            case 25: 
                pokemon = new Pokemon(25, "Pikachu", "Electric", 5, List.of("Static", "Lightning Rod"));
                break;
            case 133: 
                pokemon = new Pokemon(133, "Eevee", "Normal", 5, List.of("Run Away", "Adaptability"));
                break;
            case 6: 
                pokemon = new Pokemon(6, "Charizard", "Fire/Flying", 36, List.of("Blaze", "Solar Power"));
                break;
            case 9: 
                pokemon = new Pokemon(9, "Blastoise", "Water", 36, List.of("Torrent", "Rain Dish"));
                break;
            case 3: 
                pokemon = new Pokemon(3, "Venusaur", "Grass/Poison", 32, List.of("Overgrow", "Chlorophyll"));
                break;
            default: 
                LOG.warn("Pokemon con ID " + id + " no encontrado");
                pokemon = null;
                break;
        }
        
        if (pokemon != null) {
            LOG.info("Pokemon encontrado: " + pokemon.getName());
        } else {
            LOG.warn("No se encontró Pokemon con ID: " + id);
        }
        
        LOG.info("=== FIN: getPokemonById(id=" + id + ") ===");
        return pokemon;
    }

    // Método para obtener todos los Pokemons
    public static List<Pokemon> getAllPokemons() {
        LOG.info("=== INICIO: getAllPokemons() ===");
        LOG.debug("Creando lista de todos los Pokemons disponibles");
        
        List<Pokemon> pokemons = List.of(
            new Pokemon(1, "Bulbasaur", "Grass/Poison", 5, List.of("Overgrow", "Chlorophyll")),
            new Pokemon(4, "Charmander", "Fire", 5, List.of("Blaze", "Solar Power")),
            new Pokemon(7, "Squirtle", "Water", 5, List.of("Torrent", "Rain Dish")),
            new Pokemon(25, "Pikachu", "Electric", 5, List.of("Static", "Lightning Rod")),
            new Pokemon(133, "Eevee", "Normal", 5, List.of("Run Away", "Adaptability")),
            new Pokemon(6, "Charizard", "Fire/Flying", 36, List.of("Blaze", "Solar Power")),
            new Pokemon(9, "Blastoise", "Water", 36, List.of("Torrent", "Rain Dish")),
            new Pokemon(3, "Venusaur", "Grass/Poison", 32, List.of("Overgrow", "Chlorophyll"))
        );
        
        LOG.info("Lista de Pokemons creada con " + pokemons.size() + " elementos");
        LOG.info("Pokemons en la lista: " + pokemons.stream().map(Pokemon::getName).toList());
        LOG.info("=== FIN: getAllPokemons() ===");
        
        return pokemons;
    }
} 