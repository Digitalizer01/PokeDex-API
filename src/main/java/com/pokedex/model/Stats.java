package com.pokedex.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Stats {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column(name = "hp", nullable = false)
	private int hp;

	@NotNull
	@Column(name = "attack", nullable = false)
	private int attack;

	@NotNull
	@Column(name = "defense", nullable = false)
	private int defense;

	@NotNull
	@Column(name = "special_attack", nullable = false)
	private int specialAttack;

	@NotNull
	@Column(name = "special_defense", nullable = false)
	private int specialDefense;

	@NotNull
	@Column(name = "speed", nullable = false)
	private int speed;

	public Stats() {

	}

	public Stats(@NotNull int hp, @NotNull int attack, @NotNull int defense, @NotNull int specialAttack,
			@NotNull int specialDefense, @NotNull int speed) {
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
		this.specialAttack = specialAttack;
		this.specialDefense = specialDefense;
		this.speed = speed;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getSpecialAttack() {
		return specialAttack;
	}

	public void setSpecialAttack(int specialAttack) {
		this.specialAttack = specialAttack;
	}

	public int getSpecialDefense() {
		return specialDefense;
	}

	public void setSpecialDefense(int specialDefense) {
		this.specialDefense = specialDefense;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stats other = (Stats) obj;
		if (attack != other.attack)
			return false;
		if (defense != other.defense)
			return false;
		if (hp != other.hp)
			return false;
		if (id != other.id)
			return false;
		if (specialAttack != other.specialAttack)
			return false;
		if (specialDefense != other.specialDefense)
			return false;
		if (speed != other.speed)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Stats [id=" + id + ", hp=" + hp + ", attack=" + attack + ", defense=" + defense + ", specialAttack="
				+ specialAttack + ", specialDefense=" + specialDefense + ", speed=" + speed + "]";
	}

}
