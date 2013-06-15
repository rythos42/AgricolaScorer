package com.geeksong.agricolascorer.model;

public enum GameType {
	// Don't re-arrange these enums - database relies on this ordering to re-load. Add to the end if you want to add a new one.
	Agricola,
	Farmers,
	AllCreatures
}
