package br.com.gubee.interview.core.util;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class HeroRowMapper implements RowMapper<Object> {

    @Override
    public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {

        List list = new ArrayList();
        Hero hero = new Hero();
        PowerStats powerStats = new PowerStats();

        hero.setName(rs.getString("name"));
        hero.setRace(Race.valueOf(rs.getString("race")));
        powerStats.setAgility(rs.getInt("agility"));
        powerStats.setStrength(rs.getInt("strength"));
        powerStats.setDexterity(rs.getInt("dexterity"));
        powerStats.setIntelligence(rs.getInt("intelligence"));

        list.add(hero);
        list.add(powerStats);

        return list;
    }
}