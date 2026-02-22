package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import cr7mod.characters.CR7Character;
import cr7mod.powers.FansPower;

public class LowScream extends CustomCard {
    public static final String ID = "CR7Mod:LowScream";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/low_scream.png";
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int BASE_DAMAGE = 5;
    private static final int UPGRADE_DAMAGE = 2;

    public LowScream() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = BASE_DAMAGE;
        this.damage = this.baseDamage;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Snapshot current HP of all monsters so we can compute actual HP loss after damage
        final java.util.List<AbstractMonster> monsters = AbstractDungeon.getCurrRoom().monsters.monsters;
        final float[] preHp = new float[monsters.size()];
        for (int j = 0; j < monsters.size(); j++) {
            preHp[j] = monsters.get(j).currentHealth;
        }

        // Play damage to all enemies using current multiDamage values
        AbstractDungeon.actionManager.addToBottom(
            new DamageAllEnemiesAction(
                p, 
                this.multiDamage, 
                this.damageTypeForTurn, 
                AttackEffect.BLUNT_HEAVY
            )
        );

        // After damage resolves, compute actual HP lost and grant Fans equal to total HP loss
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                int totalHpLost = 0;
                for (int k = 0; k < monsters.size(); k++) {
                    AbstractMonster mo = monsters.get(k);
                    if (mo == null) continue;
                    float before = preHp[k];
                    float after = mo.currentHealth;
                    int lost = (int) Math.max(0f, before - after);
                    totalHpLost += lost;
                }
                if (totalHpLost > 0) {
                    AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(p, p, new FansPower(p, totalHpLost), totalHpLost)
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
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new LowScream();
    }
}
