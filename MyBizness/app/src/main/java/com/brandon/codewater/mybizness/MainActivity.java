package com.brandon.codewater.mybizness;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionItemTarget;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import org.w3c.dom.Text;

import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.OnViewInflateListener;

/**
 * Main menu after sign in.
 * Pre: SignInActivity
 * Commincation:
 *
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Button mBiznessPage;
    private ImageButton mProfileSettings;
    private FancyShowCaseView fancyView;
    private RelativeLayout mProfileLayout;
    private Dialog tutorialDialog;
    private FrameLayout frame;

    private void initBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //frame = (FrameLayout) findViewById(R.id.frame);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mBiznessPage = (Button) findViewById(R.id.mybiz_page_button);
        mProfileSettings = (ImageButton) findViewById(R.id.profile_settings);
        mProfileLayout = (RelativeLayout) findViewById(R.id.main_profile_layout);

        mProfileSettings.setOnClickListener(this);
        mBiznessPage.setOnClickListener(this);
        mProfileLayout.setOnClickListener(this);

        initBar();
//        if usertype == buziness owner && newcomer
        initTutorial();
    }

    /**
     * The tutorial to setup a bizness page will be started from here.
     * When you press "OK", which is defined here from the button, it will go onto
     * myBiznessActivity
     */
    private void initTutorial(){
        LayoutInflater tutInflater = getLayoutInflater();
        View tutView = tutInflater.inflate(R.layout.middle_top_dialog, null);
        TextView mOkay = (TextView) tutView.findViewById(R.id.next_ok);
        mOkay.setText("OK");

        tutorialDialog = new Dialog(this, R.style.Theme_Transparent_Custom);
        tutorialDialog.setCanceledOnTouchOutside(false);
        tutorialDialog.setContentView(tutView);

        UtilityFunctions obj = new UtilityFunctions();
        obj.tutorialDialogSettings(tutorialDialog, 75);
        tutorialDialog.show();

        mOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BiznessPageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        Intent intent;
        if(v == mBiznessPage){
            intent = new Intent(MainActivity.this, BiznessPageActivity.class);
            startActivity(intent);
        }
        else if (v == mProfileLayout){
            intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }
}
