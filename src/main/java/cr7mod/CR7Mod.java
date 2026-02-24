package cr7mod;

import basemod.BaseMod;
import cr7mod.helpers.Keyword;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.google.gson.Gson;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import java.nio.charset.StandardCharsets;
import cr7mod.cards.Duel;
import cr7mod.cards.BulletHeader;
import cr7mod.cards.Header;
import cr7mod.cards.Reign;
import cr7mod.cards.Shoot;
import cr7mod.cards.SilenceCampNou;
import cr7mod.cards.BicycleKick;
import cr7mod.cards.Ronalander;
import cr7mod.cards.ArabCup;
import cr7mod.cards.WarriorSpirit;
import cr7mod.cards.StrikeInstinct;
import cr7mod.cards.RedDevil;
import cr7mod.characters.CR7Character;
import cr7mod.relics.LuoJieJing;
import cr7mod.relics.Bacteria;
import cr7mod.relics.Tichun;
import cr7mod.relics.Whistle;
import cr7mod.relics.GoldShoe;
import cr7mod.relics.Contract;
import cr7mod.relics.ShinGuard;
import cr7mod.relics.Captain;
import cr7mod.cards.DragonCurve;
import cr7mod.cards.MadridistaStance;
import cr7mod.cards.HatTrick;
import cr7mod.cards.ButterflyOverSea;
import cr7mod.cards.Adversity;
import cr7mod.cards.BianconeriGlory;
import cr7mod.cards.UnbalancedShoot;
import cr7mod.cards.Roar;
import cr7mod.cards.Penalty;
import cr7mod.cards.RedCard;
import cr7mod.cards.YellowCard;
import cr7mod.cards.Dive;
import cr7mod.cards.Backheel;
import cr7mod.cards.Offside;
import cr7mod.cards.SIUUUUU;
import cr7mod.cards.CounterAttack;
import cr7mod.cards.HideBall;
import cr7mod.cards.AcrobaticShoot;
import cr7mod.cards.BananaShoot;
import cr7mod.cards.HeavyShoot;
import cr7mod.cards.FishTank;
import cr7mod.cards.BargeReferee;
import cr7mod.cards.PublicHero;
import cr7mod.cards.OneTouchShoot;
import cr7mod.cards.LowScream;
import cr7mod.cards.LuxembourgReaper;
import cr7mod.cards.FollowUpShoot;
import cr7mod.cards.Dribble;
import cr7mod.cards.SpeedUp;
import cr7mod.cards.VolleyShoot;
import cr7mod.cards.BestNo7;
import cr7mod.cards.Tackle;
import cr7mod.cards.WillPower;
import cr7mod.cards.Antony;
import cr7mod.cards.DoubleKing;
import cr7mod.cards.Morocco;
import cr7mod.cards.BloodSuck;
import cr7mod.cards.KnockOut;
import cr7mod.cards.Mbappe;
import cr7mod.cards.MyHero;
import cr7mod.cards.RonaldoRushdown;
import cr7mod.cards.Ramos;
import cr7mod.cards.Bale;
import cr7mod.cards.Modric;
import cr7mod.cards.ThreePeat;
import cr7mod.cards.Benzema;
import cr7mod.cards.Pepe;
import cr7mod.cards.KingOfDesert;
import cr7mod.cards.BallonDor;
import cr7mod.cards.GOAT;
import cr7mod.cards.StepOver;
import cr7mod.cards.TurnAround;
import cr7mod.cards.ThreeKick;
import cr7mod.cards.Chess;
import cr7mod.cards.Charge;
import cr7mod.cards.Bruno;
import cr7mod.cards.Laoba;
import cr7mod.cards.Zhen;
import cr7mod.cards.Daikuan;
import cr7mod.cards.WWE;
import cr7mod.cards.Kaka;
import cr7mod.cards.Circle;
import cr7mod.cards.Ferguson;
import cr7mod.cards.Unyield;
import cr7mod.cards.BigCock;
import cr7mod.cards.Jump;
import cr7mod.cards.Hired;
import cr7mod.cards.Angry;
import cr7mod.cards.XYY;
import cr7mod.cards.Young;
import cr7mod.cards.ButMe;
import cr7mod.cards.Dimaria;
import cr7mod.cards.Mourinho;
import cr7mod.cards.Rooney;
import cr7mod.cards.Marcelo;
import cr7mod.potions.BallonDorPotion;
import cr7mod.potions.CocaColaPotion;
import cr7mod.potions.CharacterPotion;


@SpireInitializer
public class CR7Mod implements EditCardsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber,  PostInitializeSubscriber {

    // 颜色基调（RGB/255）
    public static final Color CR7_GREEN = new Color(79.0F / 255.0F, 185.0F / 255.0F, 9.0F / 255.0F, 1.0F);

    // 角色与卡牌资源路径
    private static final String CHARACTER_BUTTON = "char/Character_Button.png";
    private static final String CHARACTER_PORTRAIT = "char/Character_Portrait.png";

    private static final String BG_ATTACK_512 = "512/cr_attack.png";
    private static final String BG_SKILL_512 = "512/cr_skill.png";
    private static final String BG_POWER_512 = "512/cr_power.png";
    private static final String SMALL_ORB = "char/small_orb.png";

    private static final String BG_ATTACK_1024 = "1024/cr_attack.png";
    private static final String BG_SKILL_1024 = "1024/cr_skill.png";
    private static final String BG_POWER_1024 = "1024/cr_power.png";
    private static final String BIG_ORB = "char/card_orb.png";
    private static final String ENERGY_ORB = "char/cost_orb.png";

    public CR7Mod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(
            CR7Character.Enums.CR7_COLOR,
            CR7_GREEN, CR7_GREEN, CR7_GREEN, CR7_GREEN, CR7_GREEN, CR7_GREEN, CR7_GREEN,
            BG_ATTACK_512, BG_SKILL_512, BG_POWER_512, ENERGY_ORB,
            BG_ATTACK_1024, BG_SKILL_1024, BG_POWER_1024, BIG_ORB, SMALL_ORB
        );
    }

    public static void initialize() {
        new CR7Mod();
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new Shoot());
        BaseMod.addCard(new Duel());
        BaseMod.addCard(new Header());
        BaseMod.addCard(new Reign());
        BaseMod.addCard(new BulletHeader());
        BaseMod.addCard(new SilenceCampNou());
        BaseMod.addCard(new DragonCurve());
        BaseMod.addCard(new MadridistaStance());
        BaseMod.addCard(new HatTrick());
        BaseMod.addCard(new BicycleKick());
        BaseMod.addCard(new ButterflyOverSea());
        BaseMod.addCard(new Adversity());
        BaseMod.addCard(new Ronalander());
        BaseMod.addCard(new ArabCup());
        BaseMod.addCard(new WarriorSpirit());
        BaseMod.addCard(new StrikeInstinct());
        BaseMod.addCard(new RedDevil());
        BaseMod.addCard(new BianconeriGlory());
        BaseMod.addCard(new Roar());
        BaseMod.addCard(new Penalty());
        BaseMod.addCard(new RedCard());
        BaseMod.addCard(new YellowCard());
        BaseMod.addCard(new Backheel());
        BaseMod.addCard(new Offside());
        BaseMod.addCard(new SIUUUUU());
        BaseMod.addCard(new CounterAttack());
        BaseMod.addCard(new HideBall());
        BaseMod.addCard(new Dive());
        BaseMod.addCard(new AcrobaticShoot());
        BaseMod.addCard(new BananaShoot());
        BaseMod.addCard(new HeavyShoot());
        BaseMod.addCard(new VolleyShoot());
        BaseMod.addCard(new UnbalancedShoot());
        BaseMod.addCard(new LowScream());
        BaseMod.addCard(new LuxembourgReaper());
        BaseMod.addCard(new FollowUpShoot());
        BaseMod.addCard(new Dribble());
        BaseMod.addCard(new SpeedUp());
        BaseMod.addCard(new BestNo7());
        BaseMod.addCard(new Tackle());
        BaseMod.addCard(new WillPower());
        BaseMod.addCard(new Antony());
        BaseMod.addCard(new BloodSuck());
        BaseMod.addCard(new FishTank());
        BaseMod.addCard(new PublicHero());
        BaseMod.addCard(new BargeReferee());
        BaseMod.addCard(new OneTouchShoot());
        BaseMod.addCard(new DoubleKing());
        BaseMod.addCard(new KnockOut());
        BaseMod.addCard(new RonaldoRushdown());
        BaseMod.addCard(new Mbappe());
        BaseMod.addCard(new MyHero());
        BaseMod.addCard(new Morocco());
        BaseMod.addCard(new Ramos());
        BaseMod.addCard(new Bale());
        BaseMod.addCard(new Modric());
        BaseMod.addCard(new ThreePeat());
        BaseMod.addCard(new Benzema());
        BaseMod.addCard(new Pepe());
        BaseMod.addCard(new KingOfDesert());
        BaseMod.addCard(new BallonDor());
        BaseMod.addCard(new GOAT());
        BaseMod.addCard(new StepOver());
        BaseMod.addCard(new TurnAround());
        BaseMod.addCard(new ThreeKick());
        BaseMod.addCard(new Chess());
        BaseMod.addCard(new Charge());
        BaseMod.addCard(new Bruno());
        BaseMod.addCard(new Laoba());
        BaseMod.addCard(new Zhen());
        BaseMod.addCard(new Daikuan());
        BaseMod.addCard(new WWE());
        BaseMod.addCard(new Kaka());
        BaseMod.addCard(new Circle());
        BaseMod.addCard(new Ferguson());
        BaseMod.addCard(new Unyield());
        BaseMod.addCard(new BigCock());
        BaseMod.addCard(new Jump());
        BaseMod.addCard(new Hired());
        BaseMod.addCard(new Angry());
        BaseMod.addCard(new XYY());
        BaseMod.addCard(new Young());
        BaseMod.addCard(new ButMe());
        BaseMod.addCard(new Dimaria());
        BaseMod.addCard(new Mourinho());
        BaseMod.addCard(new Rooney());
        BaseMod.addCard(new Marcelo());
    }

    @Override
    public void receiveEditStrings() {
        String lang = "ZHS";
        BaseMod.loadCustomStringsFile(
            CardStrings.class,
            "CR7ModResources/localization/" + lang + "/cards.json"
        );
        BaseMod.loadCustomStringsFile(
            CharacterStrings.class,
            "CR7ModResources/localization/" + lang + "/characters.json"
        );
        BaseMod.loadCustomStringsFile(
            RelicStrings.class,
            "CR7ModResources/localization/" + lang + "/relics.json"
        );
        BaseMod.loadCustomStringsFile(
            PowerStrings.class,
            "CR7ModResources/localization/" + lang + "/powers.json"
        );
        BaseMod.loadCustomStringsFile(
            PotionStrings.class,
            "CR7ModResources/localization/" + lang + "/potions.json"
        );
        BaseMod.loadCustomStringsFile(
            UIStrings.class,
            "CR7ModResources/localization/" + lang + "/UIStrings.json"
        );
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "zhs";
        String json = Gdx.files.internal("CR7ModResources/localization/Keywords_" + lang + ".json")
            .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword("cr7mod", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(
            new CR7Character(CardCrawlGame.playerName),
            CHARACTER_BUTTON,
            CHARACTER_PORTRAIT,
            CR7Character.Enums.CR7_CHARACTER
        );
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new LuoJieJing(), CR7Character.Enums.CR7_COLOR);
        BaseMod.addRelicToCustomPool(new Bacteria(), CR7Character.Enums.CR7_COLOR);
        BaseMod.addRelicToCustomPool(new Tichun(), CR7Character.Enums.CR7_COLOR);
        BaseMod.addRelicToCustomPool(new Whistle(), CR7Character.Enums.CR7_COLOR);
        BaseMod.addRelicToCustomPool(new GoldShoe(), CR7Character.Enums.CR7_COLOR);
        BaseMod.addRelicToCustomPool(new Contract(), CR7Character.Enums.CR7_COLOR);
        BaseMod.addRelicToCustomPool(new ShinGuard(), CR7Character.Enums.CR7_COLOR);
        BaseMod.addRelicToCustomPool(new Captain(), CR7Character.Enums.CR7_COLOR);
    }

    @Override
    public void receivePostInitialize() {
        // 注册药水（仅供 CR7 角色）
        BaseMod.addPotion(BallonDorPotion.class, Color.GOLD, Color.WHITE, Color.CLEAR, BallonDorPotion.POTION_ID, CR7Character.Enums.CR7_CHARACTER);
        BaseMod.addPotion(CocaColaPotion.class, Color.BROWN, Color.CLEAR, Color.CLEAR, CocaColaPotion.POTION_ID, CR7Character.Enums.CR7_CHARACTER);
        BaseMod.addPotion(CharacterPotion.class, Color.RED, Color.GREEN, Color.CLEAR, CharacterPotion.POTION_ID, CR7Character.Enums.CR7_CHARACTER);
        System.out.println("CR7Mod loaded successfully!");
    }

}
