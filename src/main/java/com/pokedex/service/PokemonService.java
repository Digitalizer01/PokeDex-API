package com.pokedex.service;

import java.util.List;

import com.pokedex.dto.PokemonDTO;
import com.pokedex.model.Pokemon;

public interface PokemonService {
	// Create
	Pokemon addPokemon(PokemonDTO pokemonDTO);

	List<Pokemon> addPokemonList(List<PokemonDTO> pokemonDTOs);

	// Get
	Pokemon getPokemonById(int id);

	Pokemon getPokemonByIdPokedex(int idpokedex);

	Pokemon getPokemonByName(String name);

	List<Pokemon> getAllPokemon();

	List<Pokemon> getAllPokemonByPokemonType1Name(String namePokemonType1);

	List<Pokemon> getAllPokemonByPokemonType2Name(String namePokemonType2);

	List<Pokemon> getAllPokemonByPokemonType1NameAndPokemonType2Name(String namePokemonType1, String namePokemonType2);

	List<Pokemon> getAllPokemonByGeneration(int idgeneration);

	List<Pokemon> getAllPokemonByGenerationNumber(int number);

	List<Pokemon> getAllPokemonByGenerationRegion(String region);

	List<Pokemon> getAllPokemonByGenerationYear(int year);

	List<Pokemon> getAllPokemonByLegendary(boolean legendary);

	// Update
	Pokemon updatePokemonById(int id, PokemonDTO newPokemonDTO);

	// Delete
	void deletePokemonById(int id);
}
