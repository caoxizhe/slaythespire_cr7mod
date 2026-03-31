package cr7mod.characters;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import java.util.ArrayList;

import cr7mod.CR7Mod;
import cr7mod.cards.Duel;
import cr7mod.cards.Header;
import cr7mod.cards.Reign;
import cr7mod.cards.Shoot;
import cr7mod.relics.LuoJieJing;

public class CR7Character extends CustomPlayer {
    public static final String ID = "CR7Mod:CR7Character";

    private static final String CHARACTER_IMAGE = "char/character.png";
    private static final String SHOULDER_1 = "char/shoulder1.png";
    private static final String SHOULDER_2 = "char/shoulder2.png";
    private static final String CORPSE_IMAGE = "char/corpse.png";

    private static final String[] ORB_TEXTURES = new String[] {
        "UI/orb/layer5.png",
        "UI/orb/layer4.png",
        "UI/orb/layer3.png",
        "UI/orb/layer2.png",
        "UI/orb/layer1.png",
        "UI/orb/layer6.png",
        "UI/orb/layer5d.png",
        "UI/orb/layer4d.png",
        "UI/orb/layer3d.png",
        "UI/orb/layer2d.png",
        "UI/orb/layer1d.png"
    };
    private static final float[] LAYER_SPEED = new float[] { -40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F };

    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);

    public CR7Character(String name) {
        super(name, Enums.CR7_CHARACTER, ORB_TEXTURES, "UI/orb/vfx.png", LAYER_SPEED, null, null);

        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);

        this.initializeClass(
            CHARACTER_IMAGE,
            SHOULDER_2,
            SHOULDER_1,
            CORPSE_IMAGE,
            this.getLoadout(),
            0.0F, 0.0F,
            200.0F, 220.0F,
            new EnergyManager(3)
        );
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            retVal.add(Shoot.ID);
        }
        for (int i = 0; i < 4; i++) {
            retVal.add(Duel.ID);
        }
        retVal.add(Header.ID);
        retVal.add(Reign.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(LuoJieJing.ID);
        return retVal;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
            characterStrings.NAMES[0],
            characterStrings.TEXT[0],
            75,
            75,
            0,
            99,
            5,
            this,
            this.getStartingRelics(),
            this.getStartingDeck(),
            false
        );
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.CR7_COLOR;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Shoot();
    }

    @Override
    public Color getCardTrailColor() {
        return CR7Mod.CR7_GREEN;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel("char/Victory1.png", "ATTACK_MAGIC_FAST_1"));
        panels.add(new CutscenePanel("char/Victory2.png"));
        panels.add(new CutscenePanel("char/Victory3.png"));
        return panels;
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new CR7Character(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return CR7Mod.CR7_GREEN;
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }

    @Override
    public Color getCardRenderColor() {
        return CR7Mod.CR7_GREEN;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] {
            AbstractGameAction.AttackEffect.SLASH_HEAVY,
            AbstractGameAction.AttackEffect.FIRE,
            AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
            AbstractGameAction.AttackEffect.SLASH_HEAVY,
            AbstractGameAction.AttackEffect.FIRE,
            AbstractGameAction.AttackEffect.SLASH_DIAGONAL
        };
    }

    public static class Enums {
        @SpireEnum
        public static PlayerClass CR7_CHARACTER;

        @SpireEnum
        public static AbstractCard.CardColor CR7_COLOR;
    }

    public static class LibraryEnums {
        @SpireEnum
        public static CardLibrary.LibraryType CR7_COLOR;
    }
}
