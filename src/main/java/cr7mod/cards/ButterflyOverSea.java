package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import cr7mod.characters.CR7Character;
import cr7mod.relics.LuoJieJing;
import cr7mod.relics.Tichun;

public class ButterflyOverSea extends CustomCard {
    public static final String ID = "CR7Mod:ButterflyOverSea";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/butterfly_over_sea.png";
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ButterflyOverSea() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractRelic relic = AbstractDungeon.player.getRelic(LuoJieJing.ID);
        if (relic != null && relic instanceof LuoJieJing) {
            LuoJieJing lj = (LuoJieJing) relic;
            lj.addFansGain(1);
            return;
        }
        if (relic == null){
            relic = AbstractDungeon.player.getRelic(Tichun.ID);
            if (relic != null && relic instanceof   Tichun) {
                Tichun tj = (Tichun) relic;
                tj.addFansGain(1);
                return;
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ButterflyOverSea();
    }
}
