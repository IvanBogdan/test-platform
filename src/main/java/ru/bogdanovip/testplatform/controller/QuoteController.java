package ru.bogdanovip.testplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bogdanovip.testplatform.service.QuoteService;
import ru.bogdanovip.testplatform.model.EnergyLevel;
import ru.bogdanovip.testplatform.model.Quote;

import javax.validation.Valid;
import java.util.List;

@RestController
public class QuoteController {

    private final QuoteService quoteService;


    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }


    @PostMapping(value = "/quote")
    public ResponseEntity saveQuote(@Valid @RequestBody Quote quote) {
        quoteService.saveQuote(quote);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/energy-level")
    public ResponseEntity<List<EnergyLevel>> getEnergyLevels() {
        return ResponseEntity.ok(quoteService.getEnergyLevels());
    }

    @GetMapping(value = "/energy-level/{isin}")
    public ResponseEntity<EnergyLevel> getEnergyLevel(@PathVariable("isin") String isin) {
        EnergyLevel energyLevel = quoteService.getEnergyLevel(isin);
        if (energyLevel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(energyLevel);
    }
}
