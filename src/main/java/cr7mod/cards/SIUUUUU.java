package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.cards.CardGroup;
import cr7mod.characters.CR7Character;

public class SIUUUUU extends CustomCard {
    public static final String ID = "CR7Mod:SIUUUUU";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/siu.png";
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SIUUUUU() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            {
                this.duration = Settings.ACTION_DUR_MED;
                this.actionType = ActionType.WAIT;
            }

            @Override
            public void update() {
                upgradeAllCardsInGroup(p.hand);
                upgradeAllCardsInGroup(p.drawPile);
                upgradeAllCardsInGroup(p.discardPile);
                upgradeAllCardsInGroup(p.exhaustPile);
                this.isDone = true;
            }

            private void upgradeAllCardsInGroup(CardGroup cardGroup) {
                for (AbstractCard c : cardGroup.group) {
                    if (c.type == CardType.ATTACK && c.canUpgrade()) {
                        if (cardGroup.type == CardGroup.CardGroupType.HAND) {
                            c.superFlash();
                        }
                        c.upgrade();
                        c.applyPowers();
                    }
                }
            }
        });
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
        return new SIUUUUU();
    }
}
