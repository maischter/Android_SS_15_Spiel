package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.Adapter.AchievementListAdapter;
import com.example.markus.locationbasedadventure.Adapter.ItemListAdapter;
import com.example.markus.locationbasedadventure.AsynchronTasks.LoadRankingTask;
import com.example.markus.locationbasedadventure.AsynchronTasks.SyndicateStatsLocalToServerTask;
import com.example.markus.locationbasedadventure.Database.AchievementDatabase;
import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;
import com.example.markus.locationbasedadventure.Database.ItemDatabase;
import com.example.markus.locationbasedadventure.Database.StatsDatabase;
import com.example.markus.locationbasedadventure.Items.Achievement;
import com.example.markus.locationbasedadventure.Items.Item;
import com.example.markus.locationbasedadventure.Items.RankingItem;

import java.util.ArrayList;

/**
 * Created by Markus on 24.08.2015.
 */
public class RankingActivity extends Activity implements LoadRankingTask.RankingTaskListener, AchievementListAdapter.AchievementListener {

    private TextView rankText;
    private TextView characternameText;
    private TextView levelText;
    private TextView expText;

    private TextView rank1;
    private TextView charactername1;
    private TextView level1;
    private TextView exp1;

    private TextView rank2;
    private TextView charactername2;
    private TextView level2;
    private TextView exp2;

    private TextView rank3;
    private TextView charactername3;
    private TextView level3;
    private TextView exp3;

    private TextView rank4;
    private TextView charactername4;
    private TextView level4;
    private TextView exp4;

    private TextView rank5;
    private TextView charactername5;
    private TextView level5;
    private TextView exp5;

    private TextView rank6;
    private TextView charactername6;
    private TextView level6;
    private TextView exp6;

    private TextView rankLeer;
    private TextView characternameLeer;
    private TextView levelLeer;
    private TextView expLeer;

    private Button back;


    private GridView achievementGrid;
    private TextView achievementInfo;


    private TableRow tablerowText;
    private TableRow tablerow1;
    private TableRow tablerow2;
    private TableRow tablerow3;
    private TableRow tablerow4;
    private TableRow tablerow5;
    private TableRow tablerow6;
    private TableRow tablerowLeer;

    private ArrayList<Achievement> achievementList = new ArrayList<Achievement>();
    private AchievementListAdapter achievementListAdapter;

    private AchievementDatabase achievementDb;
    private CharacterdataDatabase characterdataDb;
    private StatsDatabase statsDb;
    private String address = "http://sruball.de/game/getRanking.php";
    private String address2 = "http://sruball.de/game/syndicateData.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        initDB();
        initTextViews();
        initButton();
        new SyndicateStatsLocalToServerTask(this).execute(address2,characterdataDb.getEmail(),""+statsDb.getLevel(),""+statsDb.getExp(),""+statsDb.getStamina(),""+statsDb.getStrength(),""+statsDb.getDexterity(),""+statsDb.getIntelligence());
        new LoadRankingTask(this,this).execute(address, characterdataDb.getEmail());
        initGridView();
        initListAdapter();

        updateList();
    }



    //updates List
    //clears List
    //adds Item to List
    //informs adapter about change

    private void updateList() {
        achievementList.clear();
        achievementList.addAll(achievementDb.getAllAchievementItems());
        achievementListAdapter.notifyDataSetChanged();
    }


    //initialises Adapter

    private void initListAdapter() {
        achievementGrid = (GridView) findViewById(R.id.gridViewAchievement);
        achievementListAdapter = new AchievementListAdapter(this,achievementList,this);
        achievementGrid.setAdapter(achievementListAdapter);
    }


    //initialises GridView

    private void initGridView() {

        achievementGrid = (GridView) findViewById(R.id.gridViewAchievement);
    }


    //closes Databases if Activity is destroyed

    @Override
    protected void onDestroy() {
        characterdataDb.close();
        achievementDb.close();
        statsDb.close();
        super.onDestroy();
    }

    //initialises button
    //buttonListener --> finish this activity

    private void initButton(){
        back = (Button) findViewById(R.id.buttonBackRanking);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //initialises TextViews by calling Methods to initialises the Rows of the Table

    private void initTextViews() {
        initTextViewText();
        initTextViewRow1();
        initTextViewRow2();
        initTextViewRow3();
        initTextViewRow4();
        initTextViewRow5();
        initTextViewRow6();
        initTextViewLeer();
        tablerowText = (TableRow) findViewById(R.id.tableRowText);
        tablerow1 = (TableRow) findViewById(R.id.tableRow1);
        tablerow2 = (TableRow) findViewById(R.id.tableRow2);
        tablerow3 = (TableRow) findViewById(R.id.tableRow3);
        tablerow4 = (TableRow) findViewById(R.id.tableRow4);
        tablerow5 = (TableRow) findViewById(R.id.tableRow5);
        tablerow6 = (TableRow) findViewById(R.id.tableRow6);
        tablerowLeer = (TableRow) findViewById(R.id.tableRowLeer);


        achievementInfo = (TextView) findViewById(R.id.textViewAchievementInfo);
    }

    //initialises TextViews

    private void initTextViewText() {
        rankText = (TextView) findViewById(R.id.textViewRankText);
        characternameText = (TextView) findViewById(R.id.textViewCharacternameText);
        levelText = (TextView) findViewById(R.id.textViewLevelText);
        expText = (TextView) findViewById(R.id.textViewExpText);
    }


    //initialises TextViews of row 1

    private void initTextViewRow1() {
        rank1 = (TextView) findViewById(R.id.textViewRank1);
        charactername1 = (TextView) findViewById(R.id.textViewCharactername1);
        level1 = (TextView) findViewById(R.id.textViewLevel1);
        exp1 = (TextView) findViewById(R.id.textViewExp1);
    }

    //initialises TextViews of row 2

    private void initTextViewRow2() {
        rank2 = (TextView) findViewById(R.id.textViewRank2);
        charactername2 = (TextView) findViewById(R.id.textViewCharactername2);
        level2 = (TextView) findViewById(R.id.textViewLevel2);
        exp2 = (TextView) findViewById(R.id.textViewExp2);
    }

    //initialises TextViews of row 3


    private void initTextViewRow3() {
        rank3 = (TextView) findViewById(R.id.textViewRank3);
        charactername3 = (TextView) findViewById(R.id.textViewCharactername3);
        level3 = (TextView) findViewById(R.id.textViewLevel3);
        exp3 = (TextView) findViewById(R.id.textViewExp3);
    }

    //initialises TextViews of row 4


    private void initTextViewRow4() {
        rank4 = (TextView) findViewById(R.id.textViewRank4);
        charactername4 = (TextView) findViewById(R.id.textViewCharactername4);
        level4 = (TextView) findViewById(R.id.textViewLevel4);
        exp4 = (TextView) findViewById(R.id.textViewExp4);
    }

    //initialises TextViews of row 5

    private void initTextViewRow5() {
        rank5 = (TextView) findViewById(R.id.textViewRank5);
        charactername5 = (TextView) findViewById(R.id.textViewCharactername5);
        level5 = (TextView) findViewById(R.id.textViewLevel5);
        exp5 = (TextView) findViewById(R.id.textViewExp5);
    }

    //initialises TextViews of row 6


    private void initTextViewRow6() {
        rank6 = (TextView) findViewById(R.id.textViewRank6);
        charactername6 = (TextView) findViewById(R.id.textViewCharactername6);
        level6 = (TextView) findViewById(R.id.textViewLevel6);
        exp6 = (TextView) findViewById(R.id.textViewExp6);
    }

    //initialises TextViews of ran emptyRow

    private void initTextViewLeer() {
        rankLeer = (TextView) findViewById(R.id.textViewRankLeer);
        characternameLeer = (TextView) findViewById(R.id.textViewCharacternameLeer);
        levelLeer = (TextView) findViewById(R.id.textViewLevelLeer);
        expLeer = (TextView) findViewById(R.id.textViewExpLeer);
    }

    //sets TextViews of row 1

    private void setTextViewRow1(int rank, String charactername, int level, int exp){
        rank1.setText(""+rank+".");
        charactername1.setText(charactername);
        level1.setText(""+level);
        exp1.setText(""+exp);
    }

    //sets TextViews of row 2

    private void setTextViewRow2(int rank, String charactername, int level, int exp){
        rank2.setText(""+rank+".");
        charactername2.setText(charactername);
        level2.setText(""+level);
        exp2.setText(""+exp);
    }

    //sets TextViews of row 3

    private void setTextViewRow3(int rank, String charactername, int level, int exp){
        rank3.setText(""+rank+".");
        charactername3.setText(charactername);
        level3.setText(""+level);
        exp3.setText(""+exp);
    }

    //sets TextViews of row 4

    private void setTextViewRow4(int rank, String charactername, int level, int exp){
        rank4.setText(""+rank+".");
        charactername4.setText(charactername);
        level4.setText(""+level);
        exp4.setText(""+exp);
    }

    //sets TextViews of row 5

    private void setTextViewRow5(int rank, String charactername, int level, int exp){
        rank5.setText(""+rank+".");
        charactername5.setText(charactername);
        level5.setText(""+level);
        exp5.setText(""+exp);
    }

    //sets TextViews of row 6

    private void setTextViewRow6(int rank, String charactername, int level, int exp){
        rank6.setText(""+rank+".");
        charactername6.setText(charactername);
        level6.setText(""+level);
        exp6.setText("" + exp);
    }

    //initialises Databases
    //opens Databases

    private void initDB(){
        characterdataDb = new CharacterdataDatabase(this);
        characterdataDb.open();
        achievementDb = new AchievementDatabase(this);
        achievementDb.open();
        statsDb = new StatsDatabase(this);
        statsDb.open();
    }


    //calls setMethods to setTextView for the Row
    //sets BackgroundColor to highlight user

    @Override
    public void rankingDataRetrieved(ArrayList<RankingItem> rankingList, int persusernr) {
        setTextViewRow1(rankingList.get(0).getRank(), rankingList.get(0).getCharactername(), rankingList.get(0).getLevel(), rankingList.get(0).getExp());
        setTextViewRow2(rankingList.get(1).getRank(), rankingList.get(1).getCharactername(), rankingList.get(1).getLevel(), rankingList.get(1).getExp());
        setTextViewRow3(rankingList.get(2).getRank(), rankingList.get(2).getCharactername(), rankingList.get(2).getLevel(), rankingList.get(2).getExp());
        setTextViewRow4(rankingList.get(3).getRank(), rankingList.get(3).getCharactername(), rankingList.get(3).getLevel(), rankingList.get(3).getExp());
        setTextViewRow5(rankingList.get(4).getRank(), rankingList.get(4).getCharactername(), rankingList.get(4).getLevel(), rankingList.get(4).getExp());
        if(rankingList.size()==6){
            setTextViewRow6(rankingList.get(5).getRank(), rankingList.get(5).getCharactername(), rankingList.get(5).getLevel(), rankingList.get(5).getExp());
        }
        if(persusernr>3){
            setBackgroundRow1Normal();
            setBackgroundRow2Normal();
            setBackgroundRow3Normal();
            setBackgroundRow5Highlight();
        }
        else{
            setBackgroundRow5Normal();
            setBackgroundRow(persusernr);
        }
    }

    //setsBackgroundColor of Row1 to Normal (==black)

    private void setBackgroundRow1Normal(){
        tablerow1.setBackgroundColor(getResources().getColor(R.color.background_material_dark));
    }

    //setsBackgroundColor of Row2 to Normal (==black)

    private void setBackgroundRow2Normal(){
        tablerow2.setBackgroundColor(getResources().getColor(R.color.background_material_dark));
    }

    //setsBackgroundColor of Row3 to Normal (==black)

    private void setBackgroundRow3Normal(){
        tablerow3.setBackgroundColor(getResources().getColor(R.color.background_material_dark));
    }

    //setsBackgroundColor of Row5 to Highlight (==white)

    private void setBackgroundRow5Highlight() {
        tablerow5.setBackgroundColor(getResources().getColor(R.color.background_material_light));
    }

    //setsBackgroundColor of Row5 to Normal (==black)

    private void setBackgroundRow5Normal() {
        tablerow5.setBackgroundColor(getResources().getColor(R.color.background_material_dark));
    }

    //setsBackgroundColor of Row1,2,3 to Normal (==black)

    private void setBackgroundRow(int persusernr){
        setBackgroundRow1Normal();
        setBackgroundRow2Normal();
        setBackgroundRow3Normal();
        switch(persusernr){
            case 1: tablerow1.setBackgroundColor(getResources().getColor(R.color.background_material_light));break;
            case 2: tablerow2.setBackgroundColor(getResources().getColor(R.color.background_material_light));break;
            case 3: tablerow3.setBackgroundColor(getResources().getColor(R.color.background_material_light));break;
        }
    }

    //sets TextView to showAchievementInfo

    @Override
    public void showAchievementInfo(int achievementTyp) {
        achievementInfo.setText(selectAchievement(achievementTyp));
    }


    //gets int itemTyp
    //returns String with Info about Achievement

    private String selectAchievement(int itemTyp) {
        switch (itemTyp) {
            case 1:
                return "Level 5 erreicht!";
            case 2:
                return "Level 15 erreicht!";
            case 3:
                return "Level 30 erreicht!";
            case 4:
                return "5 Kämpfe bestritten!";
            case 5:
                return "50 Kämpfe bestritten!";
            case 6:
                return "250 Kämpfe bestritten!";
            default: return "Leer";
        }
    }
}
