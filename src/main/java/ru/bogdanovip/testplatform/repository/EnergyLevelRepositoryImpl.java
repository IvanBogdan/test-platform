package ru.bogdanovip.testplatform.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.bogdanovip.testplatform.model.EnergyLevel;
import ru.bogdanovip.testplatform.model.Quote;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class EnergyLevelRepositoryImpl implements EnergyLevelRepository {

    private final static RowMapper<EnergyLevel> MAPPER =
            (ResultSet rs, int rowNum) ->
                    new EnergyLevel(rs.getInt("id"), rs.getString("isin"), rs.getFloat("elvl"));

    private JdbcTemplate jdbcTemplate;


    public EnergyLevelRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<EnergyLevel> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM energy_level",
                MAPPER);
    }

    @Override
    public EnergyLevel findOneByIsin(String isin) {
        List<EnergyLevel> energyLevels = jdbcTemplate.query(
                "SELECT * FROM energy_level WHERE isin = ?",
                new Object[]{isin},
                MAPPER);

        if (energyLevels.isEmpty()) {
            return null;
        } else {
            return energyLevels.get(0);
        }
    }

    @Override
    public void updateEnergyLevel(Quote quote) {
        jdbcTemplate.update(
                "MERGE INTO energy_level (isin, elvl) KEY(isin) VALUES (" +
                        "  ?1," +
                        "  IFNULL(" +
                        "      IFNULL(" +
                        "          (SELECT LEAST(GREATEST(elvl, ?2), ?3) FROM energy_level WHERE isin = ?1)," +
                        "          ?2)," +
                        "      ?3))",
                quote.getIsin(),
                quote.getBid(),
                quote.getAsk());
    }


}
