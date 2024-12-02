package com.pokedex.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pokedex.dto.PokemonTypeDTO;
import com.pokedex.exception.ResourceDuplicatedException;
import com.pokedex.exception.ResourceInvalidDataException;
import com.pokedex.exception.ResourceNotFoundException;
import com.pokedex.model.PokemonType;
import com.pokedex.repository.PokemonTypeRepository;
import com.pokedex.service.PokemonTypeService;

@Service
public class PokemonTypeServiceImpl implements PokemonTypeService {

	@Autowired
	private PokemonTypeRepository pokemonTypeRepository;

	@Override
	public PokemonType addPokemonType(PokemonTypeDTO pokemonTypeDTO) {
		PokemonType pokemonType = addPokemonTypeUtil(pokemonTypeDTO);
		pokemonTypeRepository.saveAndFlush(pokemonType);

		return pokemonType;
	}

	@Override
	public List<PokemonType> addPokemonTypeList(List<PokemonTypeDTO> pokemonTypeDTOList) {
		List<PokemonType> pokemonTypes = new ArrayList<>();

		for (PokemonTypeDTO pokemonTypeDTO : pokemonTypeDTOList) {
			PokemonType pokemonType = addPokemonTypeUtil(pokemonTypeDTO);
			pokemonTypes.add(pokemonTypeRepository.saveAndFlush(pokemonType));
		}

		return pokemonTypes;
	}

	private PokemonType addPokemonTypeUtil(PokemonTypeDTO pokemonTypeDTO) {
		checkDataPokemonTypeDTO(pokemonTypeDTO);

		 Optional<PokemonType> existingPokemonType = pokemonTypeRepository.findPokemonTypeByName(pokemonTypeDTO.getName());

		if (!existingPokemonType.isEmpty()) {
			throw new ResourceDuplicatedException(
					"Couldn't save the Pokemon type. Duplicated data: " + pokemonTypeDTO.toString());
		}

		PokemonType pokemonType = new PokemonType(pokemonTypeDTO.getName());

		return pokemonType;
	}

	@Override
	public PokemonType getPokemonTypeById(int id) {
		return pokemonTypeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PokemonType not found. Id: " + id));
	}
	
	@Override
	public PokemonType getPokemonTypeByName(String name) {
		return pokemonTypeRepository.findPokemonTypeByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("PokemonType not found. Name: " + name));
	}

	@Override
	public List<PokemonType> getAllPokemonTypes() {
		return pokemonTypeRepository.findAll();
	}

	@Override
	public PokemonType updatePokemonTypeById(int id, PokemonTypeDTO newPokemonTypeDTO) {
		PokemonType pokemonType = updatePokemonTypeUtil(id, newPokemonTypeDTO);

		pokemonType.setName(newPokemonTypeDTO.getName());

		pokemonTypeRepository.saveAndFlush(pokemonType);

		return pokemonType;
	}

	private PokemonType updatePokemonTypeUtil(int id, PokemonTypeDTO newPokemonTypeDTO) {
		checkDataPokemonTypeDTO(newPokemonTypeDTO);

		PokemonType existingPokemonType = getPokemonTypeById(id);

		if (pokemonTypeRepository.findPokemonTypeByName(newPokemonTypeDTO.getName()) != null) {
			throw new ResourceDuplicatedException(
					"Couldn't save the Pokemon type. Duplicated data: " + newPokemonTypeDTO.toString());
		}

		return existingPokemonType;
	}

	private void checkDataPokemonTypeDTO(PokemonTypeDTO pokemonTypeDTO) {
		if (pokemonTypeDTO.getName() == null || pokemonTypeDTO.getName().isEmpty()) {
			throw new ResourceInvalidDataException(
					"Couldn't save the Pokemon type. Invalid data: " + pokemonTypeDTO.toString());
		}
	}

	@Override
	public void deletePokemonTypeById(int id) {
		getPokemonTypeById(id);
		pokemonTypeRepository.deleteById(id);
	}

}
