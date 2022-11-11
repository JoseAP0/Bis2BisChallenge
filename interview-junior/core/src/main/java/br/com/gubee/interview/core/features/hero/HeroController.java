package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    @Autowired HeroRepository heroRepository;
    @Autowired
    HeroService heroService;
    @Autowired
    PowerStatsService powerStatsService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Validated
                                       @RequestBody CreateHeroRequest createHeroRequest) {
        final UUID id = heroService.create(createHeroRequest);
        return created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    @GetMapping (path="/findbyid")
    @ResponseBody
    public List<Object> searchById (@RequestParam UUID id) {
        return heroService.findById(id);
    }

    @GetMapping (path="/findbyname")
    @ResponseBody
    public List<Object> searchByName (@RequestParam String name) {
        return heroService.findByName(name);
    }

    @PutMapping("/update")
    @ResponseBody
    public String update (@RequestBody CreateHeroRequest updateHeroRequest, @RequestParam ("id") UUID id) {

    final int response = heroService.update(updateHeroRequest, id);
    return "Successfully updated" + response + " Hero";
    }

    @DeleteMapping
    @ResponseBody
    public String delete (@RequestParam UUID id) {
        int response = heroService.delete(id);

        if (response == 0) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }

        return "Successfully deleted " + response + " Hero";
    }

    @GetMapping
    @ResponseBody
    public String compare (@RequestParam String firstName, @RequestParam String secondName){
        List<Integer> result = heroService.compare(firstName, secondName);

        return "Comparing " + firstName + " vs " + secondName +
                " the differences are"+": " + result;
    }
}
