package com.pokedex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pokedex.model.PokemonType;

@Repository
public interface PokemonTypeRepository extends JpaRepository<PokemonType, Integer> {

	Optional<PokemonType> findPokemonTypeByName(String name);

}
