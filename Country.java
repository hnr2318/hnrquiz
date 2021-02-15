package edu.uga.cs1302.quiz; //this properly packages the country class

import java.util.List; //these imports allow for the use of lists and arraylists
import java.util.ArrayList;

public class Country {

    public String country; //these string represent the name of the country and its respective continent
    public String continent;

    public Country(String country, String continent){ //this creates a contructor for a country object
	this.country = country;
	this.continent = continent;
    }

    public String getName(){ //this method is a getter for the name of the country 
	return country;
    }

    public String getContinent(){ //this method is a getter for the name of the continent
	return continent;
    }    
}
