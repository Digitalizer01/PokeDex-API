package com.pokedex.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pokedex.dto.GenerationDTO;
import com.pokedex.exception.ResourceDuplicatedException;
import com.pokedex.exception.ResourceInvalidDataException;
import com.pokedex.exception.ResourceNotFoundException;
import com.pokedex.model.Generation;
import com.pokedex.repository.GenerationRepository;
import com.pokedex.service.GenerationService;

@Service
public class GenerationServiceImpl implements GenerationService {

	@Autowired
	private GenerationRepository generationRepository;

	@Override
	public Generation addGeneration(GenerationDTO generationDTO) {
		Generation generation = addGenerationUtil(generationDTO);
		return generationRepository.saveAndFlush(generation);
	}

	@Override
	public List<Generation> addGenerationList(List<GenerationDTO> generationDTOList) {
		List<Generation> generations = new ArrayList<>();

		for (GenerationDTO generationDTO : generationDTOList) {
			Generation generation = addGenerationUtil(generationDTO);
			generations.add(generationRepository.saveAndFlush(generation));
		}

		return generations;
	}

	private Generation addGenerationUtil(GenerationDTO generationDTO) {
		checkDataGenerationDTO(generationDTO);

		if (generationRepository.findGenerationByNumberAndRegionAndYear(generationDTO.getNumber(),
				generationDTO.getRegion(), generationDTO.getYear()) != null) {
			throw new ResourceDuplicatedException(
					"Couldn't save the Generation. Duplicated data: " + generationDTO.toString());
		}

		Generation generation = new Generation(generationDTO.getNumber(), generationDTO.getRegion(),
				generationDTO.getYear());
		return generation;
	}

	@Override
	public Generation getGenerationById(int id) {
		return generationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Generation not found. Id: " + id));
	}

	@Override
	public List<Generation> getAllGenerations() {
		return generationRepository.findAll();
	}

	@Override
	public List<String> getAllGenerationRegions() {
		return generationRepository.findDistinctRegion();
	}

	@Override
	public Generation updateGenerationById(int id, GenerationDTO newGenerationDTO) {
		Generation generation = updateGenerationUtil(id, newGenerationDTO);

		generation.setNumber(newGenerationDTO.getNumber());
		generation.setRegion(newGenerationDTO.getRegion());
		generation.setYear(newGenerationDTO.getYear());

		generationRepository.saveAndFlush(generation);

		return generation;
	}

	private Generation updateGenerationUtil(int id, GenerationDTO newGenerationDTO) {
		checkDataGenerationDTO(newGenerationDTO);

		Generation existingGeneration = getGenerationById(id);
		
		if (generationRepository.findGenerationByNumberAndRegionAndYear(newGenerationDTO.getNumber(),
				newGenerationDTO.getRegion(), newGenerationDTO.getYear()) != null) {
			throw new ResourceDuplicatedException(
					"Couldn't update the Generation. Duplicated data: " + newGenerationDTO.toString());
		}

		return existingGeneration;
	}

	private void checkDataGenerationDTO(GenerationDTO generationDTO) {
		if (generationDTO.getNumber() < 1) {
			throw new ResourceInvalidDataException(
					"Couldn't save the Generation. Invalid number: " + generationDTO.toString());
		}

		if (generationDTO.getRegion() == null || generationDTO.getRegion().isEmpty()) {
			throw new ResourceInvalidDataException(
					"Couldn't save the Generation. Invalid region: " + generationDTO.toString());
		}

		if (generationDTO.getYear() < 1) {
			throw new ResourceInvalidDataException(
					"Couldn't save the Generation. Invalid year: " + generationDTO.toString());
		}
	}

	@Override
	public void deleteGenerationById(int id) {
		getGenerationById(id);
		generationRepository.deleteById(id);
	}

}
