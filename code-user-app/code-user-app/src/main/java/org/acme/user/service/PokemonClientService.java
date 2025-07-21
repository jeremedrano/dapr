package org.acme.user.service;

import org.acme.user.domain.Pokemon;
import java.util.List;

public interface PokemonClientService {
    
    /**
     * Obtiene un Pokemon aleatorio desde el Pokemon Service
     * @return Pokemon aleatorio
     */
    Pokemon getRandomPokemon();
    
    /**
     * Obtiene un Pokemon por su ID desde el Pokemon Service
     * @param id ID del Pokemon
     * @return Pokemon encontrado
     */
    Pokemon getPokemonById(int id);
    
    /**
     * Obtiene todos los Pokemons desde el Pokemon Service
     * @return Lista de todos los Pokemons
     */
    List<Pokemon> getAllPokemons();
    
    /**
     * Obtiene el saludo del Pokemon Service
     * @return Saludo del servicio
     */
    String getPokemonServiceHello();
    
    /**
     * Obtiene todos los Pokemons desde el Pokemon Service usando DAPR gRPC
     * @return Lista de todos los Pokemons
     */
    List<Pokemon> getAllPokemonsGrpc();
} 