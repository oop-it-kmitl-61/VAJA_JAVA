package com.vaja.game.battle.event;

import com.badlogic.gdx.graphics.Texture;
import com.vaja.game.battle.BATTLE_PARTY;

public class MonsterSpriteEvent extends BattleEvent{
    private Texture region;
    private BATTLE_PARTY party;

    public MonsterSpriteEvent(Texture region, BATTLE_PARTY party) {
        this.region = region;
        this.party = party;
    }

    @Override
    public void begin(BattleEventPlayer player) {
        super.begin(player);
        player.setPokemonSprite(region, party);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public boolean finished() {
        return true;
    }
}
