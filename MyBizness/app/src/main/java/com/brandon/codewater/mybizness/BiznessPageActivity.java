package com.brandon.codewater.mybizness;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;

/**
 * Created by brand on 2017-09-07.
 */

public class BiznessPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private Boolean newUser = true; //this will be intent that is read from MainActivity which comes from the DB
    private Dialog overHeadTutorial, middleDialog;
    private UtilityFunctions utilObj;
    private static int tutorialNo;
    private View headTutView;
    private Button mNextStep, mConfirmTut;
    private EditText mCompanyText, mDescriptionText;
    private ImageButton mUploadImage;
    private int locations[];
    private int rookie = 0; //intent
    private TabLayout mTab;
    private TextView mStepOne, mStepTwo, mStepThree;
    private FragmentManager fManager;
    private FragmentTransaction fAction;
    private boolean fillCompany, fillDescription; //Booleans that will be used to activate the "Continue tutorial" button

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        tutorialNo++;
        super.onWindowFocusChanged(hasFocus);
        //location 0,1,2,3,4,5 will be the x and ys of elements for the tutorial dialogs
        if(hasFocus && rookie != 0){
            //do nothing. User already has an account and has gone through the tutorial
        }
        else if(tutorialNo == 1){
            initHeaderTutorial();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bizpage_layout);

        fillCompany = false;
        fillDescription = false;
        mTab = (TabLayout) findViewById(R.id.tab_lay);
        mConfirmTut = (Button) findViewById(R.id.confirm_tutorial);
        mConfirmTut.setEnabled(false);
        mCompanyText = (EditText) findViewById(R.id.company_title);
        mDescriptionText = (EditText) findViewById(R.id.bizpage_description);
        mUploadImage = (ImageButton) findViewById(R.id.company_pic_button);

        initTabFragments();
        initBar();
        tutorialNo = 0;

        mConfirmTut.setOnClickListener(this);

        fManager = getFragmentManager();
        fAction = fManager.beginTransaction();

        //TUTORIAL USE ONLY
        mCompanyText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    fillCompany = true;
                }
                else {
                    fillCompany = false;
                }
                continueCheck(fillCompany, fillDescription);
            }
        });

        //TUTORIAL USE ONLY
        mDescriptionText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    fillDescription = true;
                }
                else {
                    fillDescription = false;
                }
                continueCheck(fillCompany, fillDescription);
            }
        });

        //extras retrieved from MainActivity will let us know if user is new or not. If so...
    }

    /**
     * TUTORIAL USE ONLY
     */
    private void initHeaderTutorial() {
        locations = new int[2];
        utilObj = new UtilityFunctions();

        final LayoutInflater headTutInflater, middleInflater;
        headTutInflater = getLayoutInflater();
        middleInflater = getLayoutInflater();
        headTutView = headTutInflater.inflate(R.layout.top_left_dialog, null);
        View v = middleInflater.inflate(R.layout.middle_top_dialog, null);

        mNextStep = (Button) headTutView.findViewById(R.id.next_ok);
        overHeadTutorial = new Dialog(this, R.style.Theme_Transparent_Custom);
        middleDialog = new Dialog(this, R.style.Theme_Transparent_Custom);
        overHeadTutorial.setCanceledOnTouchOutside(false);
        overHeadTutorial.setContentView(headTutView);
        middleDialog.setContentView(v);

        mCompanyText.getLocationOnScreen(locations);
        if(tutorialNo == 1){
            utilObj.setDialogLocation(overHeadTutorial, locations[0], locations[1]);
        }

        mNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (tutorialNo){
                    case 2:
                        overHeadTutorial.dismiss();
                        mDescriptionText.getLocationOnScreen(locations);
                        utilObj.setDialogLocation(overHeadTutorial, locations[0], locations[1]);

                        break;

                    case 4:
                        overHeadTutorial.dismiss();
                        mUploadImage.getLocationOnScreen(locations);
                        WindowManager.LayoutParams wp = middleDialog.getWindow().getAttributes();
                        wp.y = mUploadImage.getBaseline();
                        middleDialog.show();

                        break;
                }
            }
        });

        Button okay = (Button) v.findViewById(R.id.next_ok);
        okay.setText("OK");
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                middleDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(newUser == true){
            Toast.makeText(this, "Please finish tutorial before returning", Toast.LENGTH_SHORT).show();
        }
        else {
            super.onBackPressed();
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    private void initTabFragments(){

        mTab.addTab(mTab.newTab().setText("News"));
        mTab.addTab(mTab.newTab().setText("Shop"));
        mTab.addTab(mTab.newTab().setText("Gallery"));
        mTab.addTab(mTab.newTab().setText("Promotions"));

        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabChosen = tab.getText().toString();
                if(tabChosen.equals("News")){
                    Toast.makeText(getApplicationContext(), "News is now", Toast.LENGTH_SHORT).show();
                }
                else if (tabChosen.equals("Shop")){
                    Toast.makeText(getApplicationContext(), "Shop is now", Toast.LENGTH_SHORT).show();
                }
                else if (tabChosen.equals("Gallery")){
                    Toast.makeText(getApplicationContext(), "Gall is now", Toast.LENGTH_SHORT).show();
                }
                else if (tabChosen.equals("Promotions")){
                    Toast.makeText(getApplicationContext(), "Promo is now", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * TUTORIAL USE ONLY
     * @param checkOne
     * @param checkTwo
     */
    private void continueCheck(boolean checkOne, boolean checkTwo){
        if(checkOne == true && checkTwo == true){
            mConfirmTut.setTextColor(Color.WHITE);
            mConfirmTut.setEnabled(true);
        }
        else{
            mConfirmTut.setTextColor(Color.GRAY);
            mConfirmTut.setEnabled(false);
        }
    }


    /**
     * Methods below are for the navigation drawer.
     */
    private void initBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        String company, description;
        company = mCompanyText.getText().toString();
        description = mDescriptionText.getText().toString();

        if(v == mConfirmTut){
            Toast.makeText(this, company + " " + company.length() + " " + description + " " + description.length(), Toast.LENGTH_SHORT).show();

            if(company.length() == 0 || description.length() == 0 ){
                Toast.makeText(this, "Please give us your Company name and a brief description.", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "You bless", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
