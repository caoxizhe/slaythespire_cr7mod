package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import cr7mod.characters.CR7Character;
import cr7mod.patches.ChessAction;


public class Chess extends CustomCard {
	public static final String ID = "CR7Mod:Chess";
	private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
	private static final String NAME = CARD_STRINGS.NAME;
	private static final String IMG_PATH = "card_picture/chess.png";
	private static final int COST = 1;
	private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
	private static final CardType TYPE = CardType.SKILL;
	private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;

	public Chess() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
            new ChessAction(p)
        );
	}

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		// 保留基类检查（例如沉睡、缴械等）
		if (!super.canUse(p, m)) {
			return false;
		}

		// 需要至少还有另一张手牌（hand 包含当前这张牌）
		if (p.hand.size() <= 1) {
			return false;
		}

		// 如果卡片已经被设为 freeToPlayOnce，则不检查能量
		if (p.energy.energy < this.costForTurn && !this.freeToPlayOnce) {
			return false;
		}

		return true;
    }

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
            this.exhaust = false;
			this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new Chess();
	}
	
}
