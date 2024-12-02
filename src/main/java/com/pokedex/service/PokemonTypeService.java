package com.pokedex.service;

import java.util.List;

import com.pokedex.dto.PokemonTypeDTO;
import com.pokedex.model.PokemonType;

public interface PokemonTypeService {
	// Create
	PokemonType addPokemonType(PokemonTypeDTO pokemonTypeDTO);

	List<PokemonType> addPokemonTypeList(List<PokemonTypeDTO> pokemonTypeDTOList);

	// Get
	PokemonType getPokemonTypeById(int id);
	
	PokemonType getPokemonTypeByName(String name);

	List<PokemonType> getAllPokemonTypes();

	// Set

	// Update
	PokemonType updatePokemonTypeById(int id, PokemonTypeDTO newPokemonTypeDTO);

	// Delete
	void deletePokemonTypeById(int id);
}
