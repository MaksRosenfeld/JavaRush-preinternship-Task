package com.game.repository;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PlayerSpecification {

    public static Specification<Player> likeName(String letters) {
        if (letters == null) return null;
        return (root, query, cb) -> cb.like(root.get("name"), "%" + letters + "%");
    }

    public static Specification<Player> likeTitle(String letters) {
        if (letters == null) return null;
        return (root, query, cb) -> cb.like(root.get("title"), "%" + letters + "%");
    }

    public static Specification<Player> equalsRace(Race race) {
        if (race == null) return null;
        return (root, query, cb) -> cb.equal(root.get("race"), race);
    }

    public static Specification<Player> equalsProfession(Profession profession) {
        if (profession == null) return null;
        return (root, query, cb) -> cb.equal(root.get("profession"), profession);
    }

    public static Specification<Player> dateAfter(Long after) {
        if (after == null) return null;
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("birthday"), new Date(after));
    }

    public static Specification<Player> dateBefore(Long before) {
        if (before == null) return null;
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("birthday"), new Date(before));
    }

    public static Specification<Player> minExp(Integer minExp) {
        if (minExp == null) return null;
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("experience"), minExp);
    }

    public static Specification<Player> maxExp(Integer maxExp) {
        if (maxExp == null) return null;
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("experience"), maxExp);
    }

    public static Specification<Player> minLvl(Integer minLvl) {
        if (minLvl == null) return null;
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("level"), minLvl);
    }

    public static Specification<Player> maxLvl(Integer maxLvl) {
        if (maxLvl == null) return null;
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("level"), maxLvl);
    }

    public static Specification<Player> banned(Boolean banned) {
        if (banned == null) return null;
        return (root, query, cb) -> cb.equal(root.get("banned"), banned);
    }




}
