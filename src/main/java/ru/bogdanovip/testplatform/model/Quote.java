package ru.bogdanovip.testplatform.model;

import ru.bogdanovip.testplatform.validator.BidLessAks;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@BidLessAks
public class Quote {

    private Integer id;

    @NotEmpty(message = "Please provide a isin")
    @Size(message = "isin length must be 12 symbols", min = 12, max = 12)
    private String isin;

    private Float bid;

    private Float ask;


    public Quote() {
    }

    public Quote(String isin, Float bid, Float ask) {
        this.isin = isin;
        this.bid = bid;
        this.ask = ask;
    }

    public Quote(Integer id, String isin, Float bid, Float ask) {
        this.id = id;
        this.isin = isin;
        this.bid = bid;
        this.ask = ask;
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

    public Float getBid() {
        return bid;
    }

    public void setBid(Float bid) {
        this.bid = bid;
    }

    public Float getAsk() {
        return ask;
    }

    public void setAsk(Float ask) {
        this.ask = ask;
    }
}
