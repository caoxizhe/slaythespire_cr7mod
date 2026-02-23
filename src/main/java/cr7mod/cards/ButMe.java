package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RepairPower;
import cr7mod.characters.CR7Character;


public class ButMe extends CustomCard {
    public static final String ID = "CR7Mod:ButMe";
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "card_picture/butme.png";
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int BASE_DAMAGE = 2;
    private static final int UPGRADE_DAMAGE = 1;
    private static final int HITS = 7;
    private static final int HP_LOSS = 7;
    private static final int HEAL_AFTER = 7;

    public ButMe() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = BASE_DAMAGE;
        this.damage = this.baseDamage;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // lose HP immediately
        AbstractDungeon.actionManager.addToBottom(
            new LoseHPAction(p, p, HP_LOSS)
        );

        // deal damage to all enemies HITS times
        for (int i = 0; i < HITS; i++) {
            AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(
                    p, 
                    this.multiDamage, 
                    DamageInfo.DamageType.NORMAL, 
                    AttackEffect.SLASH_HORIZONTAL
                )
            );
        }

        // apply a power to heal after victory
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(p, p, new RepairPower(p, HEAL_AFTER), HEAL_AFTER)
        );
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
        return new ButMe();
    }
}
