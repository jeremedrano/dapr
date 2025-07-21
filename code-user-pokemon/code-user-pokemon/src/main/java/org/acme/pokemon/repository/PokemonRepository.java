package org.acme.pokemon.repository;

import org.acme.pokemon.domain.Pokemon;
import java.util.List;
import java.util.Optional;

public interface PokemonRepository {
    
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
    Optional<Pokemon> findById(int id);
    
    /**
     * Obtiene todos los Pokemons
     * @return Lista de todos los Pokemons
     */
    List<Pokemon> findAll();
    
    /**
     * Guarda un Pokemon
     * @param pokemon Pokemon a guardar
     * @return Pokemon guardado
     */
    Pokemon save(Pokemon pokemon);
    
    /**
     * Actualiza un Pokemon existente
     * @param pokemon Pokemon a actualizar
     * @return Pokemon actualizado
     */
    Pokemon update(Pokemon pokemon);
    
    /**
     * Elimina un Pokemon por su ID
     * @param id ID del Pokemon a eliminar
     * @return true si se eliminó correctamente
     */
    boolean deleteById(int id);
} 