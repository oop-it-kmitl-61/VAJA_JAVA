package com.vaja.game.battle.animation;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Linear;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class StartBattleAnimation extends BattleAnimation {

    /* Delay from start till the pokeball opens */
    private float playerOpenTime = 0.5f;

    private Texture whiteMask;

    public StartBattleAnimation() {
        super(0.5f);
    }

    @Override
    public void initialize(AssetManager assetManager, TweenManager tweenManager) {
        super.initialize(assetManager, tweenManager);

        TextureAtlas atlas = assetManager.get("res/graphics_packed/battle/battlepack.atlas", TextureAtlas.class);



        /* effects when the ball opens */
        Animation playerOpent = new Animation(0.025f, atlas.findRegions("pokeball_effect"));

        addEffectSprite(playerOpent, 0.5f, 0.62f, tweenManager);
        addEffectSprite(playerOpent, -0.5f, 0.62f, tweenManager);
        addEffectSprite(playerOpent, 0f, 0.8f, tweenManager);
        addEffectSprite(playerOpent, 0.8f, 0f, tweenManager);
        addEffectSprite(playerOpent, -0.8f, 0f, tweenManager);

        this.setPrimaryWidth(0f);
        this.setPrimaryHeight(0f);
        Tween.to(this, BattleAnimationAccessor.PRIMARY_WIDTH, 0.5f)
                .target(1f)
                .delay(playerOpenTime)
                .start(tweenManager);
        Tween.to(this, BattleAnimationAccessor.PRIMARY_HEIGHT, 0.5f)
                .target(1f)
                .delay(playerOpenTime)
                .start(tweenManager);

        whiteMask = assetManager.get("res/graphics/statuseffect/white.png", Texture.class);
        this.setPrimaryMask(whiteMask);
        this.setPrimaryMaskAmount(1f);
        Tween.to(this, BattleAnimationAccessor.PRIMARY_MASK_AMOUNT, 1f)
                .target(0f)
                .ease(Linear.INOUT)
                .delay(playerOpenTime +0.3f)
                .start(tweenManager);
    }

    private void addEffectSprite(Animation anim, float endX, float endY, TweenManager tweenManager) {
        AnimatedBattleSprite effectSprite = new AnimatedBattleSprite(
                anim,
                0f,
                0f,
                1f,
                1f,
                playerOpenTime);
        effectSprite.setAnimationMode(Animation.PlayMode.LOOP);
        effectSprite.setAlpha(0f);
        addSprite(effectSprite);

        Tween.to(effectSprite, BattleSpriteAccessor.ALPHA, 0f)
                .target(1f)
                .delay(playerOpenTime)
                .start(tweenManager);

        Tween.to(effectSprite, BattleSpriteAccessor.Y, 1f)
                .target(endY)
                .ease(Linear.INOUT)
                .delay(playerOpenTime)
                .start(tweenManager);

        Tween.to(effectSprite, BattleSpriteAccessor.X, 1f)
                .target(endX)
                .ease(Linear.INOUT)
                .delay(playerOpenTime)
                .start(tweenManager);
    }

}
