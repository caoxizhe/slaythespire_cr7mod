package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import cr7mod.characters.CR7Character;

public class BigCock extends CustomCard {
    public static final String ID = "CR7Mod:BigCock";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/bigcock.png";
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int DAMAGE = 5;

    public BigCock() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.damage = this.baseDamage;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Deal damage
        AbstractDungeon.actionManager.addToBottom(
            new DamageAction(
                m, 
                new DamageInfo(p, this.damage, this.damageTypeForTurn), 
                AttackEffect.SLASH_HEAVY
            )
        );
        
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(m, p, new SlowPower(m, 0))
        );

        // Add 1 Yellow Card to hand
        AbstractDungeon.actionManager.addToBottom(
            new MakeTempCardInHandAction(new YellowCard(), 1)
        );
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
        return new BigCock();
    }
}
