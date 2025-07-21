package org.acme.pokemon.service;

import org.acme.pokemon.domain.Pokemon;
import java.util.List;
import java.util.Optional;

public interface PokemonService {
    
    /**
     * Obtiene un Pokemon aleatorio
     * @return Pokemon aleatorio
     */
    Pokemon getRandomPokemon();
    
    /**
     * Obtiene un Pokemon por su ID
     * @param id ID del Pokemon
     * @return Optional con el Pokemon si existe
     */
    Optional<Pokemon> getPokemonById(int id);
    
    /**
     * Obtiene todos los Pokemons
     * @return Lista de todos los Pokemons
     */
    List<Pokemon> getAllPokemons();
    
    /**
     * Crea un nuevo Pokemon
     * @param pokemon Pokemon a crear
     * @return Pokemon creado
     */
    Pokemon createPokemon(Pokemon pokemon);
    
    /**
     * Actualiza un Pokemon existente
     * @param pokemon Pokemon a actualizar
     * @return Pokemon actualizado
     */
    Pokemon updatePokemon(Pokemon pokemon);
    
    /**
     * Elimina un Pokemon por su ID
     * @param id ID del Pokemon a eliminar
     * @return true si se eliminó correctamente
     */
    boolean deletePokemon(int id);
} 