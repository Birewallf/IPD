package local.bwg.ipd;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
            case R.id.action_favorite:
                allIPUpdate();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_info) {
            // Handle the camera action
        } else if (id == R.id.nav_tools) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * all ips update
     */
    private void allIPUpdate() {
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
