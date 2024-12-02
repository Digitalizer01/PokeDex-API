package com.pokedex.service;

import java.util.List;

import com.pokedex.dto.GenerationDTO;
import com.pokedex.model.Generation;

public interface GenerationService {
	// Create
	Generation addGeneration(GenerationDTO generationDTO);

	List<Generation> addGenerationList(List<GenerationDTO> generationDTOList);

	// Get
	Generation getGenerationById(int id);

	List<Generation> getAllGenerations();

	List<String> getAllGenerationRegions();

	// Set

	// Update
	Generation updateGenerationById(int id, GenerationDTO newGenerationDTO);

	// Delete
	void deleteGenerationById(int id);
}
