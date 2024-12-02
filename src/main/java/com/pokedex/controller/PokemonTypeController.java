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
import org.springframework.web.bind.annotation.RestController;

import com.pokedex.dto.PokemonTypeDTO;
import com.pokedex.model.PokemonType;
import com.pokedex.service.PokemonTypeService;

@RestController
@RequestMapping("/pokemontype")
public class PokemonTypeController {

	@Autowired
	private PokemonTypeService pokemonTypeService;

	@PostMapping
	public ResponseEntity<PokemonType> addPokemonType(@RequestBody PokemonTypeDTO pokemonTypeDTO) {
		PokemonType pokemonType = pokemonTypeService.addPokemonType(pokemonTypeDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(pokemonType);
	}

	@PostMapping("/batch")
	public ResponseEntity<List<PokemonType>> addPokemonTypeList(@RequestBody List<PokemonTypeDTO> pokemonTypeDTOList) {
		List<PokemonType> pokemonTypeList = pokemonTypeService.addPokemonTypeList(pokemonTypeDTOList);
		return ResponseEntity.status(HttpStatus.CREATED).body(pokemonTypeList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PokemonType> getPokemonTypeById(@PathVariable int id) {
		PokemonType pokemonType = pokemonTypeService.getPokemonTypeById(id);
		return ResponseEntity.ok(pokemonType);
	}

	@GetMapping
	public ResponseEntity<List<PokemonType>> getAllPokemonTypes() {
		List<PokemonType> listAllPokemonTypes = pokemonTypeService.getAllPokemonTypes();
		return ResponseEntity.ok(listAllPokemonTypes);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PokemonType> updatePokemonType(@PathVariable int id, @RequestBody PokemonTypeDTO pokemonTypeDTO) {
		PokemonType pokemonType = pokemonTypeService.updatePokemonTypeById(id, pokemonTypeDTO);
		return ResponseEntity.ok(pokemonType);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PokemonType> deletePokemonTypeById(@PathVariable int id) {
		pokemonTypeService.deletePokemonTypeById(id);
		return ResponseEntity.noContent().build();
	}
}
