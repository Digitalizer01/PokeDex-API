package com.pokedex.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pokedex.dto.PokemonDTO;
import com.pokedex.dto.StatsDTO;
import com.pokedex.exception.ResourceDuplicatedException;
import com.pokedex.exception.ResourceInvalidDataException;
import com.pokedex.exception.ResourceNotFoundException;
import com.pokedex.model.Generation;
import com.pokedex.model.Pokemon;
import com.pokedex.model.PokemonType;
import com.pokedex.model.Stats;
import com.pokedex.repository.PokemonRepository;
import com.pokedex.service.GenerationService;
import com.pokedex.service.PokemonService;
import com.pokedex.service.PokemonTypeService;
import com.pokedex.service.StatsService;

@Service
public class PokemonServiceImpl implements PokemonService {

	@Autowired
	private PokemonTypeService pokemonTypeService;

	@Autowired
	private StatsService statsService;

	@Autowired
	private GenerationService generationService;

	@Autowired
	private PokemonRepository pokemonRepository;

	@Override
	public Pokemon addPokemon(PokemonDTO pokemonDTO) {
		Pokemon pokemon = addPokemonUtil(pokemonDTO);
		pokemonRepository.saveAndFlush(pokemon);

		return pokemon;
	}

	@Override
	public List<Pokemon> addPokemonList(List<PokemonDTO> pokemonDTOs) {
		List<Pokemon> pokemons = new ArrayList<>();

		for (PokemonDTO pokemonDTO : pokemonDTOs) {
			Pokemon pokemon = addPokemonUtil(pokemonDTO);
			pokemons.add(pokemonRepository.saveAndFlush(pokemon));
		}

		return pokemons;
	}

	private Pokemon addPokemonUtil(PokemonDTO pokemonDTO) {
		checkDataPokemonDTO(pokemonDTO);

		if (!pokemonRepository.findByName(pokemonDTO.getName()).isEmpty()) {
			throw new ResourceDuplicatedException(
					"Couldn't save the Pokemon. There is another Pokemon called " + pokemonDTO.getName());
		}

		if (!pokemonRepository.findByIdPokedex(pokemonDTO.getIdPokedex()).isEmpty()) {
			throw new ResourceDuplicatedException(
					"Couldn't save the Pokemon. There is another Pokemon with the same PokeDex id: "
							+ pokemonDTO.getIdPokedex());
		}

		Stats stats = statsService.addStats(pokemonDTO.getStats());

		PokemonType pokemonType1 = pokemonTypeService.getPokemonTypeByName(pokemonDTO.getNamePokemonType1());
		PokemonType pokemonType2 = null;
		if(pokemonDTO.getNamePokemonType2() != null && !pokemonDTO.getNamePokemonType2().isEmpty())
		{
			pokemonType2 = pokemonTypeService.getPokemonTypeByName(pokemonDTO.getNamePokemonType2());
		}

		Generation generation = generationService.getGenerationById(pokemonDTO.getIdGeneration());

		Pokemon pokemon = new Pokemon(pokemonDTO.getIdPokedex(), pokemonDTO.getName(), pokemonType1, pokemonType2,
				pokemonDTO.getDescription(), stats, generation, pokemonDTO.isLegendary());

		return pokemon;
	}

	@Override
	public Pokemon getPokemonById(int id) {
		return pokemonRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pokemon not found. Id: " + id));
	}

	@Override
	public Pokemon getPokemonByIdPokedex(int idpokedex) {
		return pokemonRepository.findByIdPokedex(idpokedex)
				.orElseThrow(() -> new ResourceNotFoundException("Pokemon not found. IdPokedex: " + idpokedex));
	}

	@Override
	public Pokemon getPokemonByName(String name) {
		return pokemonRepository.findByName(name)
				.orElseThrow(() -> new ResourceNotFoundException("Pokemon not found. Name: " + name));
	}

	@Override
	public List<Pokemon> getAllPokemon() {
		return pokemonRepository.findAll();
	}

	@Override
	public List<Pokemon> getAllPokemonByPokemonType1Name(String namePokemonType1) {
		if (namePokemonType1 == null || namePokemonType1.isEmpty()) {
			throw new ResourceInvalidDataException("Couldn't get the Pokemon. Invalid Pokemon type 1");
		}
		return pokemonRepository.findAllByPokemonType1Name(namePokemonType1);
	}

	@Override
	public List<Pokemon> getAllPokemonByPokemonType2Name(String namePokemonType2) {
		return pokemonRepository.findAllByPokemonType2Name(namePokemonType2);
	}

	@Override
	public List<Pokemon> getAllPokemonByPokemonType1NameAndPokemonType2Name(String namePokemonType1,
			String namePokemonType2) {
		pokemonTypeService.getPokemonTypeByName(namePokemonType1);
		if(namePokemonType2 != null && !namePokemonType2.isEmpty()) {
			pokemonTypeService.getPokemonTypeByName(namePokemonType2);
		}
		
		if (namePokemonType1 == null || namePokemonType1.isEmpty()) {
			throw new ResourceInvalidDataException("Couldn't get the Pokemon. Invalid Pokemon type 1");
		}
		if(namePokemonType2 != null && namePokemonType1.equals(namePokemonType2)) {
			throw new ResourceInvalidDataException("Couldn't get the Pokemon. Both Pokemon types can't be the same");
		}
		
		return pokemonRepository.findAllByPokemonType1NameAndPokemonType2Name(namePokemonType1, namePokemonType2);
	}

	public List<Pokemon> getAllPokemonByGeneration(int idgeneration) {
		return pokemonRepository.findAllByGenerationId(idgeneration);
	}

	@Override
	public List<Pokemon> getAllPokemonByGenerationNumber(int number) {
		return pokemonRepository.findAllByGenerationNumber(number);
	}

	@Override
	public List<Pokemon> getAllPokemonByGenerationRegion(String region) {
		return pokemonRepository.findAllByGenerationRegion(region);
	}

	@Override
	public List<Pokemon> getAllPokemonByGenerationYear(int year) {
		return pokemonRepository.findAllByGenerationYear(year);
	}

	@Override
	public List<Pokemon> getAllPokemonByLegendary(boolean legendary) {
		return pokemonRepository.findAllByLegendary(legendary);
	}

	public Pokemon updatePokemonById(int id, PokemonDTO newPokemonDTO) {
		Pokemon pokemon = updatePokemonUtil(id, newPokemonDTO);

		pokemon.setName(newPokemonDTO.getName());
		pokemon.setPokemonType1(pokemonTypeService.getPokemonTypeByName(newPokemonDTO.getNamePokemonType1()));
		pokemon.setPokemonType2(pokemonTypeService.getPokemonTypeByName(newPokemonDTO.getNamePokemonType2()));
		pokemon.setDescription(newPokemonDTO.getDescription());

		Stats stats = statsService.updateStatsById(pokemon.getStats().getId(), newPokemonDTO.getStats());

		pokemon.setStats(stats);
		pokemon.setGeneration(generationService.getGenerationById(newPokemonDTO.getIdGeneration()));
		pokemon.setLegendary(newPokemonDTO.isLegendary());

		pokemonRepository.saveAndFlush(pokemon);

		return pokemon;
	}

	private Pokemon updatePokemonUtil(int id, PokemonDTO newPokemonDTO) {
		checkDataPokemonDTO(newPokemonDTO);

		Pokemon originalPokemon = getPokemonById(id);
		Pokemon pokemonByName = pokemonRepository.findByName(newPokemonDTO.getName()).get();

		if (pokemonByName != null && !pokemonByName.equals(originalPokemon)) {
			throw new ResourceDuplicatedException(
					"Couldn't save the Pokemon. There is another Pokemon called " + pokemonByName.getName());
		}

		Pokemon pokemonByIdPokedex = pokemonRepository.findByIdPokedex(newPokemonDTO.getIdPokedex()).get();

		if (pokemonByIdPokedex != null && !pokemonByIdPokedex.equals(originalPokemon)) {
			throw new ResourceDuplicatedException(
					"Couldn't save the Pokemon. There is another Pokemon with the same PokeDex id: "
							+ pokemonByIdPokedex.getIdPokedex());
		}

		return originalPokemon;
	}

	private void checkDataPokemonDTO(PokemonDTO pokemonDTO) {
		if (pokemonDTO.getName() == null || pokemonDTO.getName().isEmpty()) {
			throw new ResourceInvalidDataException("Couldn't save the Pokemon. Invalid name");
		}
		if (pokemonDTO.getIdPokedex() < 1) {
			throw new ResourceInvalidDataException(
					"Couldn't save the Pokemon. Invalid PokeDex id: " + pokemonDTO.getIdPokedex());
		}
		if (pokemonDTO.getStats() == null) {
			throw new ResourceInvalidDataException("Couldn't save the Pokemon. Invalid stats");
		}
		if (pokemonDTO.getNamePokemonType1() == null || pokemonDTO.getNamePokemonType1().isEmpty()) {
			throw new ResourceInvalidDataException("Couldn't save the Pokemon. Invalid Pokemon type 1");
		}
		if (pokemonDTO.getNamePokemonType1().equals(pokemonDTO.getNamePokemonType2())) {
			throw new ResourceInvalidDataException("Couldn't save the Pokemon. Both Pokemon types can't be the same");
		}
	}

	@Override
	public void deletePokemonById(int id) {
		Pokemon pokemon = getPokemonById(id);
		pokemonRepository.deleteById(id);
		statsService.deleteStatsById(pokemon.getStats().getId());
	}

}
