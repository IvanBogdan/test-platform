package ru.bogdanovip.testplatform.repository;

import ru.bogdanovip.testplatform.model.EnergyLevel;
import ru.bogdanovip.testplatform.model.Quote;

import java.util.List;

public interface EnergyLevelRepository {

    List<EnergyLevel> findAll();

    EnergyLevel findOneByIsin(String isin);

    void updateEnergyLevel(Quote quote);
}
