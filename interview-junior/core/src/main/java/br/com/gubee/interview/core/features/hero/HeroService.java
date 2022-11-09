package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<Object> findByName (String name) {
        return heroRepository.findByName(name);
    }

    public UUID update(CreateHeroRequest createHeroRequest) {
    }
}
