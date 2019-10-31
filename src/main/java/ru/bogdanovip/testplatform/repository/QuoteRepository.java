package ru.bogdanovip.testplatform.repository;

import ru.bogdanovip.testplatform.model.Quote;

public interface QuoteRepository {

    void save(Quote quote);
}
