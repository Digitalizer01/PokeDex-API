package com.pokedex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pokedex.model.Pokemon;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

	Optional<Pokemon> findByIdPokedex(int idpokedex);

	Optional<Pokemon> findByName(String name);

	List<Pokemon> findAllByPokemonType1Id(int idtype1);

	List<Pokemon> findAllByPokemonType2Id(int idtype2);

	List<Pokemon> findAllByPokemonType1IdAndPokemonType2Id(int idtype1, int idtype2);

	List<Pokemon> findAllByGenerationId(int idgeneration);

	List<Pokemon> findAllByGenerationNumber(int number);

	List<Pokemon> findAllByGenerationRegion(String region);

	List<Pokemon> findAllByGenerationYear(int year);

	List<Pokemon> findAllByLegendary(boolean legendary);

	void deleteByIdPokedex(int idpokedex);

	void deleteByName(String name);

	List<Pokemon> findAllByPokemonType1Name(String namePokemonType1);

	List<Pokemon> findAllByPokemonType2Name(String namePokemonType2);

	List<Pokemon> findAllByPokemonType1NameAndPokemonType2Name(String namePokemonType1, String namePokemonType2);
}
