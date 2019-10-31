package ru.bogdanovip.testplatform.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.bogdanovip.testplatform.model.Quote;

@Repository
public class QuoteRepositoryImpl implements QuoteRepository {

    private JdbcTemplate jdbcTemplate;


    public QuoteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void save(Quote quote) {
        jdbcTemplate.update(
                "INSERT INTO quote (isin, bid, ask) VALUES (?, ?, ?)",
                new Object[]{quote.getIsin(), quote.getBid(), quote.getAsk()});
    }
}
