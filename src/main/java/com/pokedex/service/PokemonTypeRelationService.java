package com.pokedex.service;

import java.util.List;

import com.pokedex.dto.PokemonTypeRelationDTO;
import com.pokedex.model.PokemonTypeRelation;

public interface PokemonTypeRelationService {
	// Create
	PokemonTypeRelation addPokemonTypeRelation(PokemonTypeRelationDTO pokemonTypeRelationDTO);

	List<PokemonTypeRelation> addPokemonTypeRelationList(List<PokemonTypeRelationDTO> pokemonTypeRelationDTOList);

	// Get
	PokemonTypeRelation getPokemonTypeRelationById(int id);

	int getEffectivenessPercentage(String namePokemonType, String nameRelatedPokemonType);

	List<PokemonTypeRelation> getPokemonTypeRelationByEffectivenessPercentage(String namePokemonType,
			int effectiveness_percentage);

	List<PokemonTypeRelation> getAllPokemonTypeRelations();

	// Set

	// Update
	PokemonTypeRelation updatePokemonTypeRelationById(int id, PokemonTypeRelationDTO newPokemonTypeRelationDTO);

	// Delete
	void deletePokemonTypeRelationById(int id);
}
