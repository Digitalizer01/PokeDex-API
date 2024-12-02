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

import com.pokedex.dto.GenerationDTO;
import com.pokedex.model.Generation;
import com.pokedex.service.GenerationService;

@RestController
@RequestMapping("/generation")
public class GenerationController {

	@Autowired
	private GenerationService generationService;

	@PostMapping
	public ResponseEntity<Generation> addGeneration(@RequestBody GenerationDTO generationDTO) {
		Generation generation = generationService.addGeneration(generationDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(generation);
	}

	@PostMapping("/batch")
	public ResponseEntity<List<Generation>> addGenerationList(@RequestBody List<GenerationDTO> generationDTOList) {
		List<Generation> generationList = generationService.addGenerationList(generationDTOList);
		return ResponseEntity.status(HttpStatus.CREATED).body(generationList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Generation> getGenerationById(@PathVariable int id) {
		Generation generation = generationService.getGenerationById(id);
		return ResponseEntity.ok(generation);
	}

	@GetMapping
	public ResponseEntity<List<Generation>> getAllGenerations() {
		List<Generation> generations = generationService.getAllGenerations();
		return ResponseEntity.ok(generations);
	}

	@GetMapping("/regions")
	public ResponseEntity<List<String>> getAllGenerationRegions() {
		List<String> generationNames = generationService.getAllGenerationRegions();
		return ResponseEntity.ok(generationNames);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Generation> updateGeneration(@PathVariable int id, @RequestBody GenerationDTO generationDTO) {
		Generation generation = generationService.updateGenerationById(id, generationDTO);
		return ResponseEntity.ok(generation);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteGeneration(@PathVariable int id) {
		generationService.deleteGenerationById(id);
		return ResponseEntity.noContent().build();
	}
}
