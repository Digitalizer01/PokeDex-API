package com.pokedex.dto;

public class GenerationDTO {
	private int number;
	private String region;
	private int year;

	public GenerationDTO() {

	}

	public GenerationDTO(int number, String region, int year) {
		this.number = number;
		this.region = region;
		this.year = year;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenerationDTO other = (GenerationDTO) obj;
		if (number != other.number)
			return false;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GenerationDTO [number=" + number + ", region=" + region + ", year=" + year + "]";
	}

}
