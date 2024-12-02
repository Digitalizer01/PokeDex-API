package com.pokedex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pokedex.dto.PokemonDTO;
import com.pokedex.model.Pokemon;
import com.pokedex.service.PokemonService;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

	@Autowired
	private PokemonService pokemonService;

	// Create
	@PostMapping()
	public ResponseEntity<Pokemon> addPokemon(@RequestBody PokemonDTO pokemonDTO) {
		Pokemon pokemon = pokemonService.addPokemon(pokemonDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(pokemon);
	}

	@PostMapping("/batch")
	public ResponseEntity<List<Pokemon>> addPokemonList(@RequestBody List<PokemonDTO> pokemonDTOs) {
		List<Pokemon> pokemons = pokemonService.addPokemonList(pokemonDTOs);
		return ResponseEntity.status(HttpStatus.CREATED).body(pokemons);
	}

	// Get
	@GetMapping("/{id}")
	public ResponseEntity<Pokemon> getPokemonById(@PathVariable int id) {
		Pokemon pokemon = pokemonService.getPokemonById(id);
		return ResponseEntity.ok(pokemon);
	}

	@GetMapping("/pokedex/{idpokedex}")
	public ResponseEntity<Pokemon> getPokemonByIdPokedex(@PathVariable int idpokedex) {
		Pokemon pokemon = pokemonService.getPokemonByIdPokedex(idpokedex);
		return ResponseEntity.ok(pokemon);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<Pokemon> getPokemonByName(@PathVariable String name) {
		Pokemon pokemon = pokemonService.getPokemonByName(name);
		return ResponseEntity.ok(pokemon);
	}

	@GetMapping()
	public ResponseEntity<List<Pokemon>> getAllPokemon() {
		List<Pokemon> allPokemon = pokemonService.getAllPokemon();
		return ResponseEntity.ok(allPokemon);
	}

	@GetMapping("/type1/name/{namePokemonType1}")
	public ResponseEntity<List<Pokemon>> getAllPokemonByPokemonType1Name(@PathVariable String namePokemonType1) {
		List<Pokemon> pokemons = pokemonService.getAllPokemonByPokemonType1Name(namePokemonType1);
		return ResponseEntity.ok(pokemons);
	}

	@GetMapping("/type2/name/{namePokemonType2}")
	public ResponseEntity<List<Pokemon>> getAllPokemonByPokemonType2Name(@PathVariable String namePokemonType2) {
		List<Pokemon> pokemons = pokemonService.getAllPokemonByPokemonType2Name(namePokemonType2);
		return ResponseEntity.ok(pokemons);
	}

	@GetMapping("/type1andtype2")
	public ResponseEntity<List<Pokemon>> getAllPokemonByPokemonType1NameAndPokemonType2Name(
			@RequestParam String namePokemonType1, @RequestParam(required = false) String namePokemonType2) {
		List<Pokemon> pokemons = pokemonService.getAllPokemonByPokemonType1NameAndPokemonType2Name(namePokemonType1,
				namePokemonType2);
		return ResponseEntity.ok(pokemons);
	}

	@GetMapping("/generation/region/{region}")
	public ResponseEntity<List<Pokemon>> getAllPokemonByGenerationRegion(@PathVariable String region) {
		List<Pokemon> pokemons = pokemonService.getAllPokemonByGenerationRegion(region);
		return ResponseEntity.ok(pokemons);
	}

	@GetMapping("/legendary")
	public ResponseEntity<List<Pokemon>> getAllPokemonByLegendary(@RequestParam boolean legendary) {
		List<Pokemon> pokemons = pokemonService.getAllPokemonByLegendary(legendary);
		return ResponseEntity.ok(pokemons);
	}

	// Update
	@PutMapping("/{id}")
	public ResponseEntity<Pokemon> updatePokemonById(@PathVariable int id, @RequestBody PokemonDTO newPokemonDTO) {
		Pokemon pokemon = pokemonService.updatePokemonById(id, newPokemonDTO);
		return ResponseEntity.ok(pokemon);
	}

	// Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePokemonById(@PathVariable int id) {
		pokemonService.deletePokemonById(id);
		return ResponseEntity.noContent().build();
	}
}
