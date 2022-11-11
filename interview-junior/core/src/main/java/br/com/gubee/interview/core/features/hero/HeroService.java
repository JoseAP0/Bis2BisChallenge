package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final PowerStatsService powerStatsService;
    private final HeroRepository heroRepository;

    @Transactional
    public UUID create(CreateHeroRequest createHeroRequest) {
        return heroRepository.create(new Hero(createHeroRequest, powerStatsService.create(new PowerStats(createHeroRequest))));
    }

    public List<Object> findById (UUID id) {
        return heroRepository.findById(id);
    }

    public List<Object> findByName (String name) { return heroRepository.findByName(name); }

    public int update(CreateHeroRequest updateHeroRequest, UUID id) {
        UUID generatedStats = powerStatsService.create(new PowerStats(updateHeroRequest));
        return heroRepository.update(new Hero(updateHeroRequest, generatedStats), id);
    }

    public int delete(UUID id) {
        return heroRepository.delete(id);
    }

    public List<Integer> compare(String firstName, String secondName) {
        PowerStats firstHero = (PowerStats) findByName(firstName).get(1);
        PowerStats secondHero = (PowerStats) findByName(secondName).get(1);

        List<Integer> result = Arrays.asList(firstHero.getStrength()-secondHero.getStrength(),
                                             firstHero.getAgility()-secondHero.getAgility(),
                                             firstHero.getDexterity()-secondHero.getDexterity(),
                                             firstHero.getIntelligence()-secondHero.getIntelligence());
        return  result;
    }
}
