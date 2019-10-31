package ru.bogdanovip.testplatform.service;

import ru.bogdanovip.testplatform.model.EnergyLevel;
import ru.bogdanovip.testplatform.model.Quote;

import java.util.List;

public interface QuoteService {

    void saveQuote(Quote quote);
    List<EnergyLevel> getEnergyLevels();
    EnergyLevel getEnergyLevel(String isin);
}
