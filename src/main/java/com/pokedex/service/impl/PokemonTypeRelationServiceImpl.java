package com.pokedex.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pokedex.dto.PokemonTypeRelationDTO;
import com.pokedex.enums.EffectivenessPercentageEnum;
import com.pokedex.exception.ResourceInvalidDataException;
import com.pokedex.exception.ResourceNotFoundException;
import com.pokedex.model.PokemonType;
import com.pokedex.model.PokemonTypeRelation;
import com.pokedex.repository.PokemonTypeRelationRepository;
import com.pokedex.service.PokemonTypeRelationService;
import com.pokedex.service.PokemonTypeService;

@Service
public class PokemonTypeRelationServiceImpl implements PokemonTypeRelationService {

	@Autowired
	private PokemonTypeService pokemonTypeService;

	@Autowired
	private PokemonTypeRelationRepository pokemonTypeRelationRepository;

	@Override
	public PokemonTypeRelation addPokemonTypeRelation(PokemonTypeRelationDTO pokemonTypeRelationDTO) {
		PokemonTypeRelation pokemonTypeRelation = addPokemonTypeRelationUtil(pokemonTypeRelationDTO);
		return pokemonTypeRelationRepository.saveAndFlush(pokemonTypeRelation);
	}

	@Override
	public List<PokemonTypeRelation> addPokemonTypeRelationList(
			List<PokemonTypeRelationDTO> pokemonTypeRelationDTOList) {
		List<PokemonTypeRelation> pokemonTypeRelations = new ArrayList<>();

		for (PokemonTypeRelationDTO pokemonTypeRelationDTO : pokemonTypeRelationDTOList) {
			PokemonTypeRelation pokemonTypeRelation = addPokemonTypeRelationUtil(pokemonTypeRelationDTO);
			pokemonTypeRelations.add(pokemonTypeRelationRepository.saveAndFlush(pokemonTypeRelation));
		}
		return pokemonTypeRelations;
	}

	private PokemonTypeRelation addPokemonTypeRelationUtil(PokemonTypeRelationDTO pokemonTypeRelationDTO) {
		checkDataPokemonTypeRelationDTO(pokemonTypeRelationDTO);

		PokemonType pokemonType = pokemonTypeService.getPokemonTypeByName(pokemonTypeRelationDTO.getNamePokemonType());
		PokemonType relatedPokemonType = pokemonTypeService
				.getPokemonTypeByName(pokemonTypeRelationDTO.getNameRelatedPokemonType());
		
		boolean exists = pokemonTypeRelationRepository.existsByPokemonTypeNameAndRelatedPokemonTypeName(
	            pokemonTypeRelationDTO.getNamePokemonType(),
	            pokemonTypeRelationDTO.getNameRelatedPokemonType());

	    if (exists) {
	        throw new ResourceInvalidDataException("Duplicate PokemonTypeRelation. Relation already exists: "
	                + pokemonTypeRelationDTO.getNamePokemonType() + " -> "
	                + pokemonTypeRelationDTO.getNameRelatedPokemonType());
	    }

		PokemonTypeRelation pokemonTypeRelation = new PokemonTypeRelation(pokemonType, relatedPokemonType,
				pokemonTypeRelationDTO.getEffectivenessPercentage());

		return pokemonTypeRelation;
	}

	@Override
	public PokemonTypeRelation getPokemonTypeRelationById(int id) {
		return pokemonTypeRelationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PokemonTypeRelation not found. Id: " + id));
	}

	@Override
	public int getEffectivenessPercentage(String namePokemonType, String nameRelatedPokemonType) {
		checkDataNamePokemonType(namePokemonType);
		checkDataNamePokemonType(nameRelatedPokemonType);

		return pokemonTypeRelationRepository
				.findPokemonTypeRelationEffectiveness_percentageByPokemonTypeNameAndRelatedPokemonTypeName(
						namePokemonType, nameRelatedPokemonType).getEffectivenessPercentage();
	}

	// For example: type FIGHT, effectiveness_percentage: SUPER EFFECTIVE => Ice,
	// Steel, Normal
	@Override
	public List<PokemonTypeRelation> getPokemonTypeRelationByEffectivenessPercentage(String nameType,
			int effectivenessPercentage) {

		checkDataNamePokemonType(nameType);
		
		if (!isValidEffectivenessPercentage(effectivenessPercentage)) {
		    throw new ResourceInvalidDataException(
		            "Invalid effectiveness percentage: "
		                    + effectivenessPercentage);
		}
		
		return pokemonTypeRelationRepository.findAllByPokemonTypeNameAndEffectivenessPercentage(nameType,
				effectivenessPercentage);
	}

	@Override
	public List<PokemonTypeRelation> getAllPokemonTypeRelations() {
		return pokemonTypeRelationRepository.findAll();
	}

	@Override
	public PokemonTypeRelation updatePokemonTypeRelationById(int id, PokemonTypeRelationDTO newPokemonTypeRelationDTO) {
		PokemonTypeRelation pokemonTypeRelation = updatePokemonTypeRelationUtil(id, newPokemonTypeRelationDTO);

		PokemonType pokemonType = pokemonTypeService
				.getPokemonTypeByName(newPokemonTypeRelationDTO.getNamePokemonType());
		PokemonType relatedPokemonType = pokemonTypeService
				.getPokemonTypeByName(newPokemonTypeRelationDTO.getNameRelatedPokemonType());

		pokemonTypeRelation.setPokemonType(pokemonType);
		pokemonTypeRelation.setRelatedPokemonType(relatedPokemonType);
		pokemonTypeRelation.setEffectivenessPercentage(newPokemonTypeRelationDTO.getEffectivenessPercentage());
		
		pokemonTypeRelationRepository.saveAndFlush(pokemonTypeRelation);

		return pokemonTypeRelation;
	}

	private PokemonTypeRelation updatePokemonTypeRelationUtil(int id,
			PokemonTypeRelationDTO newPokemonTypeRelationDTO) {
		checkDataPokemonTypeRelationDTO(newPokemonTypeRelationDTO);

		PokemonTypeRelation existingPokemonTypeRelation = getPokemonTypeRelationById(id);

		boolean exists = pokemonTypeRelationRepository.existsByPokemonTypeNameAndRelatedPokemonTypeName(
				newPokemonTypeRelationDTO.getNamePokemonType(),
				newPokemonTypeRelationDTO.getNameRelatedPokemonType());

		boolean isSameRelation = existingPokemonTypeRelation.getPokemonType().getName().equals(newPokemonTypeRelationDTO.getNamePokemonType())
	            && existingPokemonTypeRelation.getRelatedPokemonType().getName().equals(newPokemonTypeRelationDTO.getNameRelatedPokemonType());

	    if (exists && !isSameRelation) {
	        throw new ResourceInvalidDataException("Duplicate PokemonTypeRelation. Relation already exists: "
	                + newPokemonTypeRelationDTO.getNamePokemonType() + " -> "
	                + newPokemonTypeRelationDTO.getNameRelatedPokemonType());
	    }
	    
		return existingPokemonTypeRelation;
	}

	private void checkDataNamePokemonType(String namePokemonType) {
		if (namePokemonType == null || namePokemonType.isEmpty()) {
			throw new ResourceInvalidDataException("Bad Pokemon Type name. Invalid data: " + namePokemonType);
		}

		pokemonTypeService.getPokemonTypeByName(namePokemonType);
	}

	private void checkDataPokemonTypeRelationDTO(PokemonTypeRelationDTO pokemonTypeRelationDTO) {
		if (pokemonTypeRelationDTO.getNamePokemonType() == null || pokemonTypeRelationDTO.getNamePokemonType().isEmpty()
				|| pokemonTypeRelationDTO.getNameRelatedPokemonType() == null
				|| pokemonTypeRelationDTO.getNameRelatedPokemonType().isEmpty()) {
			throw new ResourceInvalidDataException(
					"Couldn't save the Pokemon type relation. Invalid data: " + pokemonTypeRelationDTO.toString());
		}

		if (!isValidEffectivenessPercentage(pokemonTypeRelationDTO.getEffectivenessPercentage())) {
		    throw new ResourceInvalidDataException(
		            "Invalid effectiveness percentage: "
		                    + pokemonTypeRelationDTO.toString());
		}
	}

	@Override
	public void deletePokemonTypeRelationById(int id) {
		getPokemonTypeRelationById(id);
		pokemonTypeRelationRepository.deleteById(id);
	}

	private boolean isValidEffectivenessPercentage(int effectivenessPercentage) {
	    for (EffectivenessPercentageEnum value : EffectivenessPercentageEnum.values()) {
	        if (value.getValue() == effectivenessPercentage) {
	            return true;
	        }
	    }
	    return false;
	}
}
