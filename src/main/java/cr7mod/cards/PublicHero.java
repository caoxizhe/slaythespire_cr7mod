package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import cr7mod.characters.CR7Character;
import cr7mod.powers.FansPower;

public class PublicHero extends CustomCard {
    public static final String ID = "CR7Mod:PublicHero";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/public_hero.png";
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int ENERGY_PER_FANS = 7;
    private static final int UPGRADE_ENERGY_PER_FANS = -2;

    public PublicHero() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseMagicNumber = ENERGY_PER_FANS;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int fans = 0;
        if (p.getPower(FansPower.POWER_ID) != null) fans = p.getPower(FansPower.POWER_ID).amount;
        int energy = fans / this.magicNumber;
        if (energy > 0) {
            AbstractDungeon.actionManager.addToBottom(
                new GainEnergyAction(energy)
            );
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_ENERGY_PER_FANS);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new PublicHero();
    }
}
