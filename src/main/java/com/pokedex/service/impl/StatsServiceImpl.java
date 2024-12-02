package com.pokedex.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pokedex.dto.StatsDTO;
import com.pokedex.exception.ResourceInvalidDataException;
import com.pokedex.exception.ResourceNotFoundException;
import com.pokedex.model.Stats;
import com.pokedex.repository.StatsRepository;
import com.pokedex.service.StatsService;

@Service
public class StatsServiceImpl implements StatsService {

	@Autowired
	private StatsRepository statsRepository;

	@Override
	public Stats addStats(StatsDTO statsDTO) {
		Stats stats = addStatsUtil(statsDTO);
		return statsRepository.saveAndFlush(stats);
	}

	private Stats addStatsUtil(StatsDTO statsDTO) {
		checkDataStatsDTO(statsDTO);

		Stats stats = new Stats(statsDTO.getHp(), statsDTO.getAttack(), statsDTO.getDefense(),
				statsDTO.getSpecialAttack(), statsDTO.getSpecialDefense(), statsDTO.getSpeed());
		return stats;
	}

	@Override
	public Stats getStatsById(int id) {
		return statsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Stats not found. Id: " + id));
	}

	@Override
	public Stats updateStatsById(int id, StatsDTO newStatsDTO) {
		Stats stats = updateStatsUtil(id, newStatsDTO);

		stats.setHp(newStatsDTO.getHp());
		stats.setAttack(newStatsDTO.getAttack());
		stats.setDefense(newStatsDTO.getDefense());
		stats.setSpecialAttack(newStatsDTO.getSpecialAttack());
		stats.setSpecialDefense(newStatsDTO.getSpecialDefense());
		stats.setSpeed(newStatsDTO.getSpeed());

		statsRepository.saveAndFlush(stats);

		return stats;
	}

	private Stats updateStatsUtil(int id, StatsDTO newStatsDTO) {
		checkDataStatsDTO(newStatsDTO);

		Stats existingStats = getStatsById(id);

		return existingStats;
	}

	private void checkDataStatsDTO(StatsDTO statsDTO) {
		if (statsDTO.getAttack() < 0 || statsDTO.getDefense() < 0 || statsDTO.getSpecialAttack() < 0
				|| statsDTO.getSpecialDefense() < 0 || statsDTO.getSpeed() < 0) {
			throw new ResourceInvalidDataException("Couldn't save the Stats. Invalid data: " + statsDTO.toString());
		}
	}

	@Override
	public void deleteStatsById(int id) {
		getStatsById(id);
		statsRepository.deleteById(id);
	}

}
