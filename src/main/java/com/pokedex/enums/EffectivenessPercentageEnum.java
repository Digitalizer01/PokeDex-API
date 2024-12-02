package com.pokedex.enums;

public enum EffectivenessPercentageEnum {
	NO_EFFECT(0), NOT_VERY_EFFECTIVE(50), NORMAL(100), SUPER_EFFECTIVE(200);

	private final int value;

	private EffectivenessPercentageEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
