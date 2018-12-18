package com.vaja.game.battle;

import com.vaja.game.Vaja;
import com.vaja.game.model.Monster;

import java.util.ArrayList;
import java.util.List;

public class Trainer {
    private List<Monster> team;

    public Trainer(Monster game) {
        team = new ArrayList<Monster>();
        team.add(game);
    }

    public boolean addMonster(Monster game) {
        if (team.size() >= 6) {
            return false;
        } else {
            team.add(game);
            return true;
        }
    }

    public Monster getPokemon(int index) {
        return team.get(index);
    }

    public int getTeamSize() {
        return team.size();
    }
}
