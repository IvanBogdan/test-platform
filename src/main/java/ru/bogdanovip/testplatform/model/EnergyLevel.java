package ru.bogdanovip.testplatform.model;

public class EnergyLevel {

    private Integer id;
    private String isin;
    private Float elvl;


    public EnergyLevel() {
    }

    public EnergyLevel(String isin, Float elvl) {
        this.isin = isin;
        this.elvl = elvl;
    }

    public EnergyLevel(Integer id, String isin, Float elvl) {
        this(isin, elvl);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Float getElvl() {
        return elvl;
    }

    public void setElvl(Float elvl) {
        this.elvl = elvl;
    }
}
