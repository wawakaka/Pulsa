package com.example.mubarak.pulsa;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText nomorTujuan;
    EditText nomorOperator;
    EditText nomorMember;
    RadioButton rb;
    RadioGroup rg;
    Time today= new Time(Time.getCurrentTimezone());
    DBAdapter myDB;
    String PREFS_NAME="MYTEXT";
    String PREFS_NAMEA="MYTEXTA";
    String nO,nM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set the fragment initially
        ReportListFragment fragment = new ReportListFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        openDB();


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
            setTitle("Pengaturan");
            //Set the fragment initially
            SettingsFragment fragment = new SettingsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.laporan) {

            setTitle("Laporan");
            //Set the fragment initially
            ReportListFragment fragment = new ReportListFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();


        } else if (id == R.id.isi) {
            setTitle("Isi");
            //Set the fragment initially
            RechargeFragment fragment = new RechargeFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.pengaturan) {
            setTitle("Pengaturan");
            //Set the fragment initially
            SettingsFragment fragment = new SettingsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } /*else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sendSMS()
    {
        nomorTujuan = (EditText)findViewById(R.id.editText);
        rg=(RadioGroup)findViewById(R.id.radioGroup);
        int nominalPulsa=rg.getCheckedRadioButtonId();
        rb = (RadioButton)findViewById(nominalPulsa);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String mytext = settings.getString("mytext", "");
        nO=mytext;

        SharedPreferences settingsa = getSharedPreferences(PREFS_NAMEA, 0);
        String mytexta = settingsa.getString("mytexta", "");
        nM=mytexta;

        today.setToNow();
        String timeStamp=today.format("%d-%m-%Y %H:%M");
        String nT=nomorTujuan.getText().toString();



        if(rg.getCheckedRadioButtonId()==-1 || nT.matches("")){
            Toast.makeText(getApplicationContext(),"nomor tujuan dan nominal pulsa tidak boleh kosong !!!",Toast.LENGTH_LONG).show();
        }
        else {
            myDB.insertRow(nT,timeStamp,false);
            String message = "ISI "+nT+" "+rb.getText()+" "+nM;

            SmsManager sm = SmsManager.getDefault();
            String msg = message;
            sm.sendTextMessage(nO, null, msg, null, null);
            Toast.makeText(getApplicationContext(),"Pulsa telah dikirim "+msg,Toast.LENGTH_LONG).show();
        }


    }
    public void kirim(View view){
        sendSMS();

    }
    public void set(View view){
        nomorOperator = (EditText)findViewById(R.id.editTextOperator);
        nomorMember = (EditText)findViewById(R.id.editTextIdMember);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("mytext", nomorOperator.getText().toString());
        editor.commit();
        SharedPreferences settingsa = getSharedPreferences(PREFS_NAMEA, 0);
        SharedPreferences.Editor editora = settingsa.edit();
        editora.putString("mytexta", nomorMember.getText().toString());
        editora.commit();

    }

    private void openDB(){
        myDB = new DBAdapter(this);
        myDB.open();
    }

    public void populateListView(){
        Cursor cursor = myDB.getAllRows();
        String[] fromFiledNames = new String[]{DBAdapter.KEY_ROWID,DBAdapter.KEY_NOMOR,
                DBAdapter.KEY_DATE,DBAdapter.KEY_STATUS};
        int[] toViewIDs = new int[]{R.id.textView2,R.id.textView3,R.id.textView4,R.id.textView5,};
        SimpleCursorAdapter myCursorAdapter= new SimpleCursorAdapter(getBaseContext(),
                R.layout.list_adapter,cursor,fromFiledNames,toViewIDs,0);
        ListView ls = (ListView)findViewById(R.id.listView);
        ls.setAdapter(myCursorAdapter);
    }

    public void refresh(View view){
        myDB.open();
        populateListView();
        myDB.close();
    }







}
