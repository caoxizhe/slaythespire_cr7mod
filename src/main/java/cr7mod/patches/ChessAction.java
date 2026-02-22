package cr7mod.patches;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class ChessAction extends AbstractGameAction {
	private final AbstractPlayer p;

	public ChessAction(AbstractPlayer player) {
		this.amount = 2;
		this.p = player;
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
	}

	@Override
	public void update() {
    	// 第一帧：只打开一次选择界面
    	if (this.duration == Settings.ACTION_DUR_FAST) {
        	AbstractDungeon.handCardSelectScreen.open("将左侧的牌替换为右侧", this.amount, false);
        	this.tickDuration();
        	return;
    	}

    	// 等待玩家做出选择并在下一帧处理
    	if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
        	CardGroup selected = AbstractDungeon.handCardSelectScreen.selectedCards;
        	if (selected != null && selected.group.size() == 2) {
            	AbstractCard first = selected.group.get(0);
            	AbstractCard second = selected.group.get(1);

            	if (first != null && second != null) {
                	
                	AbstractCard right = second;

                	AbstractCard copy = right.makeStatEquivalentCopy();
                	copy.setCostForTurn(right.costForTurn); 
					
					p.hand.addToHand(copy);
					p.hand.addToHand(second);
                	
            	}
        	}
        	// 清理并标记选卡已处理
       	 	selected.clear();
        	AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        	this.isDone = true;
        	return;
    	}

    	this.tickDuration();
	}
}
