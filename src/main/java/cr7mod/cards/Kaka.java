package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import cr7mod.characters.CR7Character;
import cr7mod.powers.FansPower;

public class Kaka extends CustomCard {
	public static final String ID = "CR7Mod:Kaka";
	private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
	private static final String NAME = CARD_STRINGS != null ? CARD_STRINGS.NAME : "Kaka";
	private static final String IMG_PATH = "card_picture/kaka.png";
	private static final int COST = 1;
	private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
	private static final CardType TYPE = CardType.SKILL;
	private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;

	public Kaka() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		int drawPileSize = p.drawPile == null ? 0 : p.drawPile.size();
		if (drawPileSize <= 0) return;
		AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(p, p, new FansPower(p, drawPileSize), drawPileSize)
        );
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
            this.isInnate = true;
            upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
	}

	@Override
	public AbstractCard makeCopy() {
		return new Kaka();
	}
}
