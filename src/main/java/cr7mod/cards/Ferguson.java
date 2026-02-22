package cr7mod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import cr7mod.characters.CR7Character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Ferguson extends CustomCard {
	public static final String ID = "CR7Mod:Ferguson";
	private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
	private static final String NAME = CARD_STRINGS.NAME;
	private static final String IMG_PATH = "card_picture/ferguson.png";
	private static final int COST = 1;
	private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
	private static final CardType TYPE = CardType.SKILL;
	private static final CardColor COLOR = CR7Character.Enums.CR7_COLOR;
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;

	public Ferguson() {
		super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		// Discard all cards in hand
		int handSize = p.hand == null ? 0 : p.hand.size();
		if (handSize > 0) {
			AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, handSize, false));
		}

		// Shuffle entire discard pile into draw pile
		AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
			@Override
			public void update() {
				if (p.discardPile == null || p.drawPile == null) {
					this.isDone = true;
					return;
				}
				// Move all cards from discard to draw pile
				ArrayList<AbstractCard> toMove = new ArrayList<>(p.discardPile.group);
				if (!toMove.isEmpty()) {
					p.discardPile.group.clear();
					p.drawPile.group.addAll(toMove);
					// shuffle draw pile
					Collections.shuffle(p.drawPile.group, new Random(System.nanoTime()));
				}

                // Draw until hand is full (default 10)
		        int maxHand = 10;
		        int need = maxHand - (p.hand == null ? 0 : p.hand.size());
		        if (need > 0) {
			        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, need));
		        }
				this.isDone = true;
			}
		});

	}

	@Override
	public void upgrade() {
		if (!upgraded) {
            this.exhaust = false;
            upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
	}

	@Override
	public AbstractCard makeCopy() {
		return new Ferguson();
	}
}

