package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import cr7mod.characters.CR7Character;
import cr7mod.powers.DaikuanPower;

public class Daikuan extends CustomCard {
	public static final String ID = "CR7Mod:Daikuan";
	private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
	private static final String NAME = CARD_STRINGS.NAME;
	private static final String IMG_PATH = "card_picture/daikuan.png";
	private static final int COST = -1; // X cost
	private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
	private static final CardType TYPE = CardType.SKILL;
	private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;

	public Daikuan() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.cardsToPreview = new Laoba();    
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) effect = this.energyOnUse;
        if (p.hasRelic("Chemical X")) {
            p.getRelic("Chemical X").flash();
            effect += 2;
        }
        if (effect > 0) {
            int turns = effect + (this.upgraded ? 1 : 0);
            AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new DaikuanPower(p, turns), turns)
            );
        }
        if (!this.freeToPlayOnce) {
            AbstractDungeon.player.energy.use(effect);
        }
        AbstractDungeon.actionManager.addToBottom(
            new MakeTempCardInDrawPileAction(new Laoba(), 1, true, false)
        );
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
            upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
	}

	@Override
	public AbstractCard makeCopy() {
		return new Daikuan();
	}
}

