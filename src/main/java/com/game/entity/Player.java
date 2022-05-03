package com.game.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "player")
public class Player {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 12)
    private String name;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "race", nullable = false)
    private Race race;

    @Enumerated(EnumType.STRING)
    @Column(name = "profession", nullable = false)
    private Profession profession;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Column(name = "banned", nullable = false)
    private Boolean banned = false;

    @Column(name = "experience", nullable = false)
    private Integer experience;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "untilNextLevel", nullable = false)
    private Integer untilNextLevel;

    public boolean areAllFieldsFilled() {
        if (this.name == null
                || this.title == null
                || this.race == null
                || this.profession == null
                || this.birthday == null
                || this.experience == null) return false;
        return true;
    }

    public void ignoringUnnecessaryData() {
        this.id = null;
        this.untilNextLevel = null;
        this.level = null;
    }

    public boolean isAllDataCorrect() {
        if (this.name.length() > 12
                || this.name.isEmpty()
                || this.title.length() > 30
                || this.experience < 0
                || this.experience > 10_000_000
                || this.birthday.getTime() < 0
                || this.birthday.getYear() < 100
                || this.birthday.getYear() > 1100) return false;
        return true;
    }

    public void updatingWith(Player updatedFields) {
        updatedFields.id = null;
        updatedFields.level = null;
        updatedFields.untilNextLevel = null;
        if (updatedFields.name != null) this.name = updatedFields.name;
        if (updatedFields.title != null) this.title = updatedFields.title;
        if (updatedFields.race != null) this.race = updatedFields.race;
        if (updatedFields.profession != null) this.profession = updatedFields.profession;
        if (updatedFields.birthday != null) this.birthday = updatedFields.birthday;
        if (updatedFields.banned != null) this.banned = updatedFields.banned;
        if (updatedFields.experience != null) {
            this.experience = updatedFields.experience;
            countLvlAndUnl(this);
        }
    }

    public void countLvlAndUnl(Player player) {
        Integer exp = player.getExperience();
        Integer lvl = (int)(Math.sqrt(2500 + 200 * exp) - 50) / 100;
        player.setLevel(lvl);
        Integer uNl = 50 * (lvl + 1) * (lvl + 2) - exp;
        player.setUntilNextLevel(uNl);
    }

    public Long getId() {return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (id != player.id) return false;
        if (name != null ? !name.equals(player.name) : player.name != null) return false;
        if (title != null ? !title.equals(player.title) : player.title != null) return false;
        if (race != null ? !race.equals(player.race) : player.race != null) return false;
        if (profession != null ? !profession.equals(player.profession) : player.profession != null) return false;
        if (birthday != null ? !birthday.equals(player.birthday) : player.birthday != null) return false;
        if (banned != null ? !banned.equals(player.banned) : player.banned != null) return false;
        if (experience != null ? !experience.equals(player.experience) : player.experience != null) return false;
        if (level != null ? !level.equals(player.level) : player.level != null) return false;
        if (untilNextLevel != null ? !untilNextLevel.equals(player.untilNextLevel) : player.untilNextLevel != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (race != null ? race.hashCode() : 0);
        result = 31 * result + (profession != null ? profession.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (banned != null ? banned.hashCode() : 0);
        result = 31 * result + (experience != null ? experience.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (untilNextLevel != null ? untilNextLevel.hashCode() : 0);
        return result;
    }
}
