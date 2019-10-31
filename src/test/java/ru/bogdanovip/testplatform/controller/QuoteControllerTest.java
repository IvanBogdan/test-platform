package ru.bogdanovip.testplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.bogdanovip.testplatform.model.Quote;

import java.sql.ResultSet;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:test")
@AutoConfigureMockMvc
class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private final static RowMapper<Quote> MAPPER =
            (ResultSet rs, int rowNum) -> new Quote(
                    rs.getInt("id"),
                    rs.getString("isin"),
                    rs.getFloat("bid"),
                    rs.getFloat("ask"));

    @BeforeEach
    void before() throws Exception {
        jdbcTemplate.execute("TRUNCATE TABLE quote");
        jdbcTemplate.execute("TRUNCATE TABLE energy_level");
    }

    @Test
    void saveQuote() throws Exception {

        saveQuote(null, 45f, 64f)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Please provide a isin"));

        saveQuote("RU000A0JX0J", 45f, 64f)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("isin length must be 12 symbols"));

        saveQuote("RU000A0JX0J1", 5f, 3f)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("bid should be less then ask"));

        Quote quote = new Quote("RU000A0JX0J3", 46f, 65f);
        saveQuote(quote)
                .andExpect(status().isNoContent());

        List<Quote> quotes = jdbcTemplate.query(
                "SELECT * FROM quote WHERE isin = ?",
                new Object[]{"RU000A0JX0J3"},
                MAPPER);

        assertEquals(1, quotes.size());
        Quote quote1 = quotes.get(0);
        assertEquals(quote1.getIsin(), quote.getIsin());
        assertEquals(quote1.getBid(), quote.getBid());
        assertEquals(quote1.getAsk(), quote.getAsk());
    }

    private ResultActions saveQuote(String isin, Float bid, Float ask) throws Exception {
        return saveQuote(new Quote(isin, bid, ask));
    }

    private ResultActions saveQuote(Quote quote) throws Exception {
        return mockMvc.perform(post("/quote")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(quote)));
    }

    @Test
    void getEnergyLevels() throws Exception {

        mockMvc.perform(get("/energy-level"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        saveQuote("RU000A0JX0J1", 45f, 62f)
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/energy-level"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].isin").value("RU000A0JX0J1"));

        saveQuote("RU000A0JX0J2", 45f, 62f)
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/energy-level"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getEnergyLevel() throws Exception {

        mockMvc.perform(get("/energy-level/RU000A0JX0J1"))
                .andExpect(status().isNotFound());

        saveQuote("RU000A0JX0J1", null, 65f)
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/energy-level/RU000A0JX0J1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isin").value("RU000A0JX0J1"))
                .andExpect(jsonPath("$.elvl").value(65f));

        saveQuote("RU000A0JX0J1", null, 66f)
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/energy-level/RU000A0JX0J1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isin").value("RU000A0JX0J1"))
                .andExpect(jsonPath("$.elvl").value(65f));

        saveQuote("RU000A0JX0J1", null, 64f)
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/energy-level/RU000A0JX0J1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isin").value("RU000A0JX0J1"))
                .andExpect(jsonPath("$.elvl").value(64f));

        saveQuote("RU000A0JX0J1", 45f, 64f)
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/energy-level/RU000A0JX0J1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isin").value("RU000A0JX0J1"))
                .andExpect(jsonPath("$.elvl").value(64f));

        saveQuote("RU000A0JX0J1", 66f, 70f)
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/energy-level/RU000A0JX0J1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isin").value("RU000A0JX0J1"))
                .andExpect(jsonPath("$.elvl").value(66f));

        saveQuote("RU000A0JX0J1", 45f, 62f)
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/energy-level/RU000A0JX0J1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isin").value("RU000A0JX0J1"))
                .andExpect(jsonPath("$.elvl").value(62f));
    }

}