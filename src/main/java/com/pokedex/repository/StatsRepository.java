package com.pokedex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pokedex.model.Stats;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Integer> {
	Stats findByHpAndAttackAndDefenseAndSpecialAttackAndSpecialDefenseAndSpeed(int hp, int attack, int defense,
			int specialAttack, int specialDefense, int speed);
}
