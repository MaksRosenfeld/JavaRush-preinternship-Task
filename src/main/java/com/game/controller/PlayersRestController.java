package com.game.controller;


import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerSpecification;
import com.game.service.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class PlayersRestController {

    @Autowired
    private PlayerServiceImpl playerService;

    private Specification<Player> specification;

    @GetMapping("/players")
    public List<Player> getAllPlayers(@RequestParam(name = "order", defaultValue = "ID",required = false) PlayerOrder order,
                                      @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                      @RequestParam(name = "pageSize", defaultValue = "3", required = false) Integer pageSize,
                                      @RequestParam(name = "name", required = false) String name,
                                      @RequestParam(name = "title", required = false) String title,
                                      @RequestParam(name = "race", required = false) Race race,
                                      @RequestParam(name = "profession", required = false) Profession profession,
                                      @RequestParam(name = "after", required = false) Long after,
                                      @RequestParam(name = "before", required = false) Long before,
                                      @RequestParam(name = "banned", required = false) Boolean banned,
                                      @RequestParam(name = "minExperience", required = false) Integer minExperience,
                                      @RequestParam(name = "maxExperience", required = false) Integer maxExperience,
                                      @RequestParam(name = "minLevel", required = false) Integer minLevel,
                                      @RequestParam(name = "maxLevel", required = false) Integer maxLevel) {

        specification = Specification.where(PlayerSpecification.likeName(name))
                .and(PlayerSpecification.likeTitle(title))
                .and(PlayerSpecification.equalsRace(race))
                .and(PlayerSpecification.equalsProfession(profession))
                .and(PlayerSpecification.dateAfter(after))
                .and(PlayerSpecification.dateBefore(before))
                .and(PlayerSpecification.minExp(minExperience))
                .and(PlayerSpecification.maxExp(maxExperience))
                .and(PlayerSpecification.minLvl(minLevel))
                .and(PlayerSpecification.maxLvl(maxLevel))
                .and(PlayerSpecification.banned(banned));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
        return playerService.findAll(specification, pageable);

    }

    @GetMapping("/players/count")
    public Integer countAll(@RequestParam(name = "name", required = false) String name,
                            @RequestParam(name = "title", required = false) String title,
                            @RequestParam(name = "race", required = false) Race race,
                            @RequestParam(name = "profession", required = false) Profession profession,
                            @RequestParam(name = "after", required = false) Long after,
                            @RequestParam(name = "before", required = false) Long before,
                            @RequestParam(name = "banned", required = false) Boolean banned,
                            @RequestParam(name = "minExperience", required = false) Integer minExperience,
                            @RequestParam(name = "maxExperience", required = false) Integer maxExperience,
                            @RequestParam(name = "minLevel", required = false) Integer minLevel,
                            @RequestParam(name = "maxLevel", required = false) Integer maxLevel) {
        specification = Specification.where(PlayerSpecification.likeName(name))
                .and(PlayerSpecification.likeTitle(title))
                .and(PlayerSpecification.equalsRace(race))
                .and(PlayerSpecification.equalsProfession(profession))
                .and(PlayerSpecification.dateAfter(after))
                .and(PlayerSpecification.dateBefore(before))
                .and(PlayerSpecification.minExp(minExperience))
                .and(PlayerSpecification.maxExp(maxExperience))
                .and(PlayerSpecification.minLvl(minLevel))
                .and(PlayerSpecification.maxLvl(maxLevel))
                .and(PlayerSpecification.banned(banned));
        return playerService.countAll(specification);
    }

    @PostMapping("/players")
    public Player savePlayer(@RequestBody Player player) {
        if (player.areAllFieldsFilled() && player.isAllDataCorrect()) {
            player.ignoringUnnecessaryData();
            playerService.savePlayer(player);
            return player;
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);



    }
    @GetMapping("/players/{id}")
    public Player getPlayer(@PathVariable long id) {
        if (id < 1) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        else {
            Optional<Player> player = playerService.getPlayer(id);
            if (!player.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            else return playerService.getPlayer(id).get();
        }


    }

    @PostMapping("/players/{id}")
    public Player updatePlayer(@PathVariable long id, @RequestBody Player updatedData) {
        Player player = getPlayer(id);
        player.updatingWith(updatedData);
        if (player.areAllFieldsFilled() && player.isAllDataCorrect()) {
            playerService.updatePlayer(player);
            return player;
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

    }
    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable long id) {
        getPlayer(id);
        playerService.deletePlayer(id);
    }
}
