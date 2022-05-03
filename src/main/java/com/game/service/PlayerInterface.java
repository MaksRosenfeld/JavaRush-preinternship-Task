package com.game.service;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface PlayerInterface {
    public List<Player> findAllByNameContains(String letters, Pageable pageable);
    public List<Player> findAll(Specification<Player> specification, Pageable pageable);
    public Integer countAll(Specification<Player> specification);
    public void savePlayer(Player player);
    public Optional<Player> getPlayer(long id);
    public void deletePlayer(long id);
    public void updatePlayer(Player player);


}
