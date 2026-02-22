package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import cr7mod.characters.CR7Character;
import cr7mod.powers.FansPower;

public class DragonCurve extends CustomCard {
    public static final String ID = "CR7Mod:DragonCurve";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/dragon_curve.png";
    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public DragonCurve() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Grant fans equal to lost HP (maxHealth - currentHealth)
        int lostHp = p.maxHealth - p.currentHealth;
        if (lostHp > 0) {
            AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new FansPower(p, lostHp), lostHp)
            );
        }

        // After fans are applied, deal damage equal to current fans to target
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractPower fans = p.getPower(FansPower.POWER_ID);
                int fansAmount = fans == null ? 0 : fans.amount;
                int dmg = fansAmount;
                    if (dmg > 0 && m != null && !m.isDeadOrEscaped()) {
                    AbstractDungeon.actionManager.addToBottom(
                        new DamageAction(
                            m, 
                            new DamageInfo(p, dmg, this.damageType), 
                            AttackEffect.SLASH_HEAVY
                        )
                    );
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DragonCurve();
    }
}
