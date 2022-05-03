package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerInterface {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> findAllByNameContains(String letters, Pageable pageable) {
        return playerRepository.findAllByNameContains(letters, pageable);
    }

    @Override
    public List<Player> findAll(Specification<Player> specification, Pageable pageable) {
        return playerRepository.findAll(specification, pageable).toList();
    }

    @Override
    public Integer countAll(Specification<Player> specification) {

        return (int) playerRepository.count(specification);
    }

    @Override
    public void savePlayer(Player player) {
        player.countLvlAndUnl(player);
        playerRepository.save(player);

    }


    @Override
    public Optional<Player> getPlayer(long id) {
        return playerRepository.findById(id);
    }

    @Override
    public void deletePlayer(long id) {
        playerRepository.deleteById(id);
    }


    @Override
    public void updatePlayer(Player player) {
        playerRepository.save(player);
    }


}
