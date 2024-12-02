package com.pokedex.dto;

public class StatsDTO {
	private int id;
	private int hp;
	private int attack;
	private int defense;
	private int specialAttack;
	private int specialDefense;
	private int speed;

	public StatsDTO() {

	}

	public StatsDTO(int hp, int attack, int defense, int specialAttack, int specialDefense, int speed) {
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
		StatsDTO other = (StatsDTO) obj;
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
		return "StatsDTO [id=" + id + ", hp=" + hp + ", attack=" + attack + ", defense=" + defense + ", specialAttack="
				+ specialAttack + ", specialDefense=" + specialDefense + ", speed=" + speed + "]";
	}

}
