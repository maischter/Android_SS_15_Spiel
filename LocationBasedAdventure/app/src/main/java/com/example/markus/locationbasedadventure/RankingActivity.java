package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.markus.locationbasedadventure.AsynchronTasks.LoadRankingTask;
import com.example.markus.locationbasedadventure.Database.MySqlDatabase;
import com.example.markus.locationbasedadventure.Items.RankingItem;

import java.util.ArrayList;

/**
 * Created by Markus on 24.08.2015.
 */
public class RankingActivity extends Activity implements LoadRankingTask.RankingTaskListener {

    TextView rankText;
    TextView characternameText;
    TextView levelText;
    TextView expText;

    TextView rank1;
    TextView charactername1;
    TextView level1;
    TextView exp1;

    TextView rank2;
    TextView charactername2;
    TextView level2;
    TextView exp2;

    TextView rank3;
    TextView charactername3;
    TextView level3;
    TextView exp3;

    TextView rank4;
    TextView charactername4;
    TextView level4;
    TextView exp4;

    TextView rank5;
    TextView charactername5;
    TextView level5;
    TextView exp5;

    TextView rank6;
    TextView charactername6;
    TextView level6;
    TextView exp6;

    TextView rankLeer;
    TextView characternameLeer;
    TextView levelLeer;
    TextView expLeer;
    MySqlDatabase db;



    TableRow tablerowText;
    TableRow tablerow1;
    TableRow tablerow2;
    TableRow tablerow3;
    TableRow tablerow4;
    TableRow tablerow5;
    TableRow tablerow6;
    TableRow tablerowLeer;
    private String address = "http://sruball.de/game/getRanking.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        initDB();
        initTextViews();
        new LoadRankingTask(this,this).execute(address,db.getEmail());

    }




    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

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
    }

    private void initTextViewText() {
        rankText = (TextView) findViewById(R.id.textViewRankText);
        characternameText = (TextView) findViewById(R.id.textViewCharacternameText);
        levelText = (TextView) findViewById(R.id.textViewLevelText);
        expText = (TextView) findViewById(R.id.textViewExpText);
    }
    private void initTextViewRow1() {
        rank1 = (TextView) findViewById(R.id.textViewRank1);
        charactername1 = (TextView) findViewById(R.id.textViewCharactername1);
        level1 = (TextView) findViewById(R.id.textViewLevel1);
        exp1 = (TextView) findViewById(R.id.textViewExp1);
    }
    private void initTextViewRow2() {
        rank2 = (TextView) findViewById(R.id.textViewRank2);
        charactername2 = (TextView) findViewById(R.id.textViewCharactername2);
        level2 = (TextView) findViewById(R.id.textViewLevel2);
        exp2 = (TextView) findViewById(R.id.textViewExp2);
    }
    private void initTextViewRow3() {
        rank3 = (TextView) findViewById(R.id.textViewRank3);
        charactername3 = (TextView) findViewById(R.id.textViewCharactername3);
        level3 = (TextView) findViewById(R.id.textViewLevel3);
        exp3 = (TextView) findViewById(R.id.textViewExp3);
    }
    private void initTextViewRow4() {
        rank4 = (TextView) findViewById(R.id.textViewRank4);
        charactername4 = (TextView) findViewById(R.id.textViewCharactername4);
        level4 = (TextView) findViewById(R.id.textViewLevel4);
        exp4 = (TextView) findViewById(R.id.textViewExp4);
    }
    private void initTextViewRow5() {
        rank5 = (TextView) findViewById(R.id.textViewRank5);
        charactername5 = (TextView) findViewById(R.id.textViewCharactername5);
        level5 = (TextView) findViewById(R.id.textViewLevel5);
        exp5 = (TextView) findViewById(R.id.textViewExp5);
    }
    private void initTextViewRow6() {
        rank6 = (TextView) findViewById(R.id.textViewRank6);
        charactername6 = (TextView) findViewById(R.id.textViewCharactername6);
        level6 = (TextView) findViewById(R.id.textViewLevel6);
        exp6 = (TextView) findViewById(R.id.textViewExp6);
    }
    private void initTextViewLeer() {
        rankLeer = (TextView) findViewById(R.id.textViewRankLeer);
        characternameLeer = (TextView) findViewById(R.id.textViewCharacternameLeer);
        levelLeer = (TextView) findViewById(R.id.textViewLevelLeer);
        expLeer = (TextView) findViewById(R.id.textViewExpLeer);
    }

    private void setTextViewRow1(int rank, String charactername, int level, int exp){
        rank1.setText(""+rank+".");
        charactername1.setText(charactername);
        level1.setText(""+level);
        exp1.setText(""+exp);
    }
    private void setTextViewRow2(int rank, String charactername, int level, int exp){
        rank2.setText(""+rank+".");
        charactername2.setText(charactername);
        level2.setText(""+level);
        exp2.setText(""+exp);
    }
    private void setTextViewRow3(int rank, String charactername, int level, int exp){
        rank3.setText(""+rank+".");
        charactername3.setText(charactername);
        level3.setText(""+level);
        exp3.setText(""+exp);
    }
    private void setTextViewRow4(int rank, String charactername, int level, int exp){
        rank4.setText(""+rank+".");
        charactername4.setText(charactername);
        level4.setText(""+level);
        exp4.setText(""+exp);
    }
    private void setTextViewRow5(int rank, String charactername, int level, int exp){
        rank5.setText(""+rank+".");
        charactername5.setText(charactername);
        level5.setText(""+level);
        exp5.setText(""+exp);
    }
    private void setTextViewRow6(int rank, String charactername, int level, int exp){
        rank6.setText(""+rank+".");
        charactername6.setText(charactername);
        level6.setText(""+level);
        exp6.setText(""+exp);
    }


    private void initDB(){
        db = new MySqlDatabase(this);
        db.open();
    }

    @Override
    public void rankingDataRetrieved(ArrayList<RankingItem> rankingList, int persusernr) {
        setTextViewRow1(rankingList.get(0).getRank(), rankingList.get(0).getCharactername(), rankingList.get(0).getLevel(), rankingList.get(0).getExp());
        setTextViewRow2(rankingList.get(1).getRank(), rankingList.get(1).getCharactername(), rankingList.get(1).getLevel(), rankingList.get(1).getExp());
        setTextViewRow3(rankingList.get(2).getRank(),rankingList.get(2).getCharactername(),rankingList.get(2).getLevel(),rankingList.get(2).getExp());
        setTextViewRow4(rankingList.get(3).getRank(),rankingList.get(3).getCharactername(),rankingList.get(3).getLevel(),rankingList.get(3).getExp());
        setTextViewRow5(rankingList.get(4).getRank(),rankingList.get(4).getCharactername(),rankingList.get(4).getLevel(),rankingList.get(4).getExp());
        setTextViewRow6(rankingList.get(5).getRank(), rankingList.get(5).getCharactername(), rankingList.get(5).getLevel(), rankingList.get(5).getExp());
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

    private void setBackgroundRow1Normal(){
        tablerow1.setBackgroundColor(getResources().getColor(R.color.background_material_dark));
    }
    private void setBackgroundRow2Normal(){
        tablerow2.setBackgroundColor(getResources().getColor(R.color.background_material_dark));
    }
    private void setBackgroundRow3Normal(){
        tablerow3.setBackgroundColor(getResources().getColor(R.color.background_material_dark));
    }
    private void setBackgroundRow5Highlight() {
        tablerow5.setBackgroundColor(getResources().getColor(R.color.background_material_light));
    }
    private void setBackgroundRow5Normal() {
        tablerow5.setBackgroundColor(getResources().getColor(R.color.background_material_dark));
    }
    private void setBackgroundRow(int persusernr){
        setBackgroundRow1Normal();
        setBackgroundRow2Normal();
        setBackgroundRow3Normal();
        switch(persusernr){
            case 1: tablerow1.setBackgroundColor(getResources().getColor(R.color.background_material_dark));break;
            case 2: tablerow2.setBackgroundColor(getResources().getColor(R.color.background_material_dark));break;
            case 3: tablerow3.setBackgroundColor(getResources().getColor(R.color.background_material_dark));break;
        }
    }
}
