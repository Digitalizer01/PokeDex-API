package com.pokedex.service;

import com.pokedex.dto.StatsDTO;
import com.pokedex.model.Stats;

public interface StatsService {

	// Create
	Stats addStats(StatsDTO statsDTO);

	// Get
	Stats getStatsById(int id);

	// Update
	Stats updateStatsById(int id, StatsDTO newStatsDTO);

	// Delete
	void deleteStatsById(int id);

}
