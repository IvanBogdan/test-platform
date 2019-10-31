package ru.bogdanovip.testplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bogdanovip.testplatform.model.EnergyLevel;
import ru.bogdanovip.testplatform.model.Quote;
import ru.bogdanovip.testplatform.repository.EnergyLevelRepository;
import ru.bogdanovip.testplatform.repository.QuoteRepository;

import java.util.List;

@Service
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final EnergyLevelRepository energyLevelRepository;


    @Autowired
    public QuoteServiceImpl(QuoteRepository quoteRepository,
                            EnergyLevelRepository energyLevelRepository) {
        this.quoteRepository = quoteRepository;
        this.energyLevelRepository = energyLevelRepository;
    }


    public void saveQuote(Quote quote) {
        quoteRepository.save(quote);
        energyLevelRepository.updateEnergyLevel(quote);
    }

    public List<EnergyLevel> getEnergyLevels() {
        return energyLevelRepository.findAll();
    }

    public EnergyLevel getEnergyLevel(String isin) {
        return energyLevelRepository.findOneByIsin(isin);
    }
}
