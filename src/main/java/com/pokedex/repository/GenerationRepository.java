package com.pokedex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pokedex.model.Generation;

@Repository
public interface GenerationRepository extends JpaRepository<Generation, Integer> {
	@Query("SELECT DISTINCT g.region FROM Generation g")
	List<String> findDistinctRegion();

	Generation findGenerationByNumberAndRegionAndYear(int number, String region, int year);
}
