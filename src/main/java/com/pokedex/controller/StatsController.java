package com.pokedex.controller;

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

import com.pokedex.dto.StatsDTO;
import com.pokedex.model.Stats;
import com.pokedex.service.StatsService;

@RestController
@RequestMapping("/stats")
public class StatsController {
	@Autowired
	private StatsService statsService;

	@PostMapping()
	public ResponseEntity<Stats> addStats(@RequestBody StatsDTO statsDTO) {
		Stats stats = statsService.addStats(statsDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(stats);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Stats> getStatsById(@PathVariable int id) {
		Stats stats = statsService.getStatsById(id);
		return ResponseEntity.ok(stats);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Stats> updateStats(@PathVariable int id, @RequestBody StatsDTO newStatsDTO) {
		Stats updatedStats = statsService.updateStatsById(id, newStatsDTO);
		return ResponseEntity.ok(updatedStats);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStats(@PathVariable int id) {
		statsService.deleteStatsById(id);
		return ResponseEntity.noContent().build();
	}
}
