package local.bwg.ipd;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    TextView publicipout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Fragment fragment = null;
        Class fragmentClass = InfoFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Вставляем фрагмент, заменяя текущий фрагмент
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        allIPUpdate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                allIPUpdate();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /////////////// LEFT BAR

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        Class fragmentClass = null;

        int id = item.getItemId();

        if (id == R.id.nav_info) {
            fragmentClass = InfoFragment.class;
        } else if (id == R.id.nav_tools) {
            fragmentClass = ToolsFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Вставляем фрагмент, заменяя текущий фрагмент
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        // Выделяем выбранный пункт меню в шторке
        item.setChecked(true);



        if (id == R.id.nav_info) {
            allIPUpdate();
        } else if (id == R.id.nav_tools) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * all ips update
     */
    private void allIPUpdate() {
        AllIPUpdateTask allIPUpdateTask = new AllIPUpdateTask();
        allIPUpdateTask.execute();
    }
    /**
     * AllIPUpdateTask task
     */
    @SuppressLint("StaticFieldLeak")
    private class AllIPUpdateTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            TextView localipout = (TextView) findViewById(R.id.localipout);
            TextView macaddrout = (TextView) findViewById(R.id.macaddrout);
            publicipout = (TextView) findViewById(R.id.publicipout);

            localipout.setText("n/d");
            publicipout.setText("n/d");
            macaddrout.setText("n/d");


            localipout.setText(localIPUpdate());
            macaddrout.setText(macAddrUpdate());

            publicIPUpdate();
        }
    }
    /**
     * local ip update
     * @return ip
     */
    private String localIPUpdate() {
        return new IPAddrLocal().getIpAddr();
    }

    /**
     * mac address update
     * @return mac
     */
    private String macAddrUpdate() {
        return new IPAddrLocal().getMacAddr();
    }

    /**
     * public ip update
     */
    private void publicIPUpdate() {
        GetPublicIPTask gpipt = new GetPublicIPTask();
        gpipt.execute();
    }

    /**
     * Get public ip task
     */
    @SuppressLint("StaticFieldLeak")
    private class GetPublicIPTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return new IPAddrPublic().getIpAddr();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            publicipout.setText(result);
        }
    }
}
