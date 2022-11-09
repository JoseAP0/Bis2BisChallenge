package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.core.util.*;
import br.com.gubee.interview.model.Hero;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HeroRepository {
    @Autowired
    PowerStatsService powerStatsService;

    private static final String CREATE_HERO_QUERY = "INSERT INTO hero" +
            " (name, race, power_stats_id)" +
            " VALUES (:name, :race, :powerStatsId) RETURNING id";

    private static final String FIND_BY_ID_HERO_QUERY = "SELECT hero.id, name, race, strength, agility, dexterity, intelligence, power_stats_id " +
            "FROM interview_service.hero INNER JOIN interview_service.power_stats " +
            "ON hero.power_stats_id = power_stats.id " +
            "WHERE hero.id = ?";

    private static final String FIND_BY_NAME_HERO_QUERY = "SELECT hero.id, name, race, strength, agility, dexterity, intelligence, power_stats_id " +
            "FROM interview_service.hero INNER JOIN interview_service.power_stats " +
            "ON hero.power_stats_id = power_stats.id " +
            "WHERE name = ?";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    UUID create(Hero hero) {
        final Map<String, Object> params = Map.of("name", hero.getName(),
                "race", hero.getRace().name(),
                "powerStatsId", hero.getPowerStatsId());

        return namedParameterJdbcTemplate.queryForObject(
                CREATE_HERO_QUERY,
                params,
                UUID.class);
    }

    List<Object> findById(UUID id) {
        return (List<Object>) jdbcTemplate.queryForObject(
                FIND_BY_ID_HERO_QUERY,
                new Object[] {id},
                new HeroRowMapper());
    }

    List<Object> findByName (String name) {
        return (List<Object>) jdbcTemplate.queryForObject(
                FIND_BY_NAME_HERO_QUERY,
                new Object[] {name},
                new HeroRowMapper());
    }
}