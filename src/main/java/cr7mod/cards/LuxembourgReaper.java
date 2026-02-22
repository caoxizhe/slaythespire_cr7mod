package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import cr7mod.characters.CR7Character;



public class LuxembourgReaper extends CustomCard {
    public static final String ID = "CR7Mod:LuxembourgReaper";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/lux_reaper.png";
    private static final int COST = 3;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int BASE_DAMAGE = 4;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int HITS = 5;

    public LuxembourgReaper() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = BASE_DAMAGE;
        this.damage = this.baseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Queue HITS DamageAction(s) with SLASH_HORIZONTAL effect
        for (int i = 0; i < HITS; i++) {
            AbstractDungeon.actionManager.addToBottom(
                new DamageAction(
                    m, 
                    new DamageInfo(p, this.damage, this.damageTypeForTurn), 
                    AttackEffect.SLASH_HORIZONTAL)
                );
        }

        // After damage actions resolve, check once for kill (exclude minions) and grant rewards
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                boolean killed = (m.isDying || m.isDead || m.currentHealth <= 0);
                boolean isMinion = false;
                
                isMinion = m.halfDead || m.hasPower("Minion");
                
                if (killed && !isMinion) {
                    AbstractDungeon.actionManager.addToBottom(
                        new GainEnergyAction(2)
                    );
                    AbstractDungeon.actionManager.addToBottom(
                        new DrawCardAction(p, 1)
                    );
                }
                if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().monsters != null && AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new LuxembourgReaper();
    }
}
