package com.example.geonames.model;

import java.io.Serializable;

public class Geoname implements Serializable {

    private String continent;
    private String capital;
    private String languages;
    private Integer geonameId;
    private Double south;
    private String isoAlpha3;
    private Double north;
    private String fipsCode;
    private String population;
    private Double east;
    private String isoNumeric;
    private String areaInSqKm;
    private String countryCode;

    public Geoname(String population, String areaInSqKm, String countryCode, String countryName) {
        this.population = population;
        this.areaInSqKm = areaInSqKm;
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    private Double west;
    private String countryName;
    private String continentName;
    private String currencyCode;

    public String getContinent() {
    return continent;
    }

    public void setContinent(String continent) {
    this.continent = continent;
    }

    public String getCapital() {
    return capital;
    }

    public void setCapital(String capital) {
    this.capital = capital;
    }

    public String getLanguages() {
    return languages;
    }

    public void setLanguages(String languages) {
    this.languages = languages;
    }

    public Integer getGeonameId() {
    return geonameId;
    }

    public void setGeonameId(Integer geonameId) {
    this.geonameId = geonameId;
    }

    public Double getSouth() {
    return south;
    }

    public void setSouth(Double south) {
    this.south = south;
    }

    public String getIsoAlpha3() {
    return isoAlpha3;
    }

    public void setIsoAlpha3(String isoAlpha3) {
    this.isoAlpha3 = isoAlpha3;
    }

    public Double getNorth() {
    return north;
    }

    public void setNorth(Double north) {
    this.north = north;
    }

    public String getFipsCode() {
    return fipsCode;
    }

    public void setFipsCode(String fipsCode) {
    this.fipsCode = fipsCode;
    }

    public String getPopulation() {
    return population;
    }

    public void setPopulation(String population) {
    this.population = population;
    }

    public Double getEast() {
    return east;
    }

    public void setEast(Double east) {
    this.east = east;
    }

    public String getIsoNumeric() {
    return isoNumeric;
    }

    public void setIsoNumeric(String isoNumeric) {
    this.isoNumeric = isoNumeric;
    }

    public String getAreaInSqKm() {
    return areaInSqKm;
    }

    public void setAreaInSqKm(String areaInSqKm) {
    this.areaInSqKm = areaInSqKm;
    }

    public String getCountryCode() {
    return countryCode;
    }

    public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
    }

    public Double getWest() {
    return west;
    }

    public void setWest(Double west) {
    this.west = west;
    }

    public String getCountryName() {
    return countryName;
    }

    public void setCountryName(String countryName) {
    this.countryName = countryName;
    }

    public String getContinentName() {
    return continentName;
    }

    public void setContinentName(String continentName) {
    this.continentName = continentName;
    }

    public String getCurrencyCode() {
    return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
    }

}