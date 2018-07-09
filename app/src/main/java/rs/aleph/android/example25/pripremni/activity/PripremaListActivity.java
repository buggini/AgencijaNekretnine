package rs.aleph.android.example25.pripremni.activity;


import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import rs.aleph.android.example25.pripremni.adapters.DrawerListAdapter;
import rs.aleph.android.example25.pripremni.model.NavigationItem;
import rs.aleph.android.example25.R;
import rs.aleph.android.example25.pripremni.db.PripremaORMLightHelper;
import rs.aleph.android.example25.pripremni.db.model.Nekretnina;
import rs.aleph.android.example25.pripremni.dilog.AboutDialog;
import rs.aleph.android.example25.pripremni.preferences.PripremaPrefererences;

public class PripremaListActivity extends AppCompatActivity {

    private PripremaORMLightHelper databaseHelper;
    private SharedPreferences prefs;

    public static String ACTOR_KEY = "ACTOR_KEY";
    public static String NOTIF_TOAST = "notif_toast";
    public static String NOTIF_STATUS = "notif_statis";

////    /* The click listner for ListView in the navigation drawer */
//       private class DrawerItemClickListener implements ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            if (position == 0) {
//                // TODO
//
//            } else if (position == 1) {
//                Intent settings = new Intent(PripremaListActivity.this,PripremaPrefererences.class);
//                startActivity(settings);
//            } else if (position == 2) {
//                if (dialog == null){
//                    dialog = new AboutDialog(PripremaListActivity.this).prepareDialog();
//                } else {
//                    if (dialog.isShowing()) {
//                        dialog.dismiss();
//                    }
//                }
//                // TODO: SImplify
//                // To not create dialog class
//                // call it from here.
//
//                dialog.show();
//            }
//
//            drawerList.setItemChecked(position, true);
//            setTitle(drawerItems.get(position).getTitle());
//            drawerLayout.closeDrawer(drawerPane);
//
//        }
//    }
//
//    // Attributes used by NavigationDrawer
//    private DrawerLayout drawerLayout;
//    private ListView drawerList;
//    private ActionBarDrawerToggle drawerToggle;
//    private RelativeLayout drawerPane;
//    private CharSequence drawerTitle;
//    private ArrayList<NavigationItem> drawerItems = new ArrayList<NavigationItem>();
//
//    // Attributes used by Dialog
//    private AlertDialog dialog;



    private int itemId = 0; // Selected item ID



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priprema_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
//        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//
//        if(actionBar !=null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.show();
//        }
//        // Manages NavigationDrawer
//
//        // Populates a list of NavigationDrawer items
//        drawerItems.add(new NavigationItem(getString(R.string.drawer_home), getString(R.string.drawer_home_long), R.drawable.ic_action_product));
//        drawerItems.add(new NavigationItem(getString(R.string.drawer_settings), getString(R.string.drawer_Settings_long), R.drawable.ic_action_settings));
//        drawerItems.add(new NavigationItem(getString(R.string.drawer_about), getString(R.string.drawer_about_long), R.drawable.ic_action_about));
//
//        drawerTitle = getTitle();
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//        drawerList = (ListView) findViewById(R.id.navList);
//
//        // Populates NavigtionDrawer with options
//        drawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
//        DrawerListAdapter adapter = new DrawerListAdapter(this, drawerItems);
//
//        // Sets a custom shadow that overlays the main content when NavigationDrawer opens
//        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//        drawerList.setOnItemClickListener(new DrawerItemClickListener());
//        drawerList.setAdapter(adapter);
//
//        drawerToggle = new ActionBarDrawerToggle(
//                this,                           /* host Activity */
//                drawerLayout,                   /* DrawerLayout object */
//                toolbar,                        /* nav drawer image to replace 'Up' caret */
//                R.string.drawer_open,           /* "open drawer" description for accessibility */
//                R.string.drawer_close           /* "close drawer" description for accessibility */
//        ) {
//            public void onDrawerClosed(View view) {
//                getSupportActionBar().setTitle(drawerTitle);
//
//                invalidateOptionsMenu();        // Creates call to onPrepareOptionsMenu()
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                getSupportActionBar().setTitle(drawerTitle);
//                invalidateOptionsMenu();        // Creates call to onPrepareOptionsMenu()
//            }
//        };

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        final ListView listView = (ListView) findViewById(R.id.nekretnina_list);

        try {
            List<Nekretnina> list = getDatabaseHelper().getNekretninaDao().queryForAll();

            ListAdapter adapterr = new ArrayAdapter<>(PripremaListActivity.this, R.layout.list_item, list);
            listView.setAdapter(adapterr);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Nekretnina p = (Nekretnina) listView.getItemAtPosition(position);

                    Intent intent = new Intent(PripremaListActivity.this, PripremaDetail.class);
                    intent.putExtra(ACTOR_KEY, p.getmId());
                    startActivity(intent);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.nekretnina_list);

        if (listview != null){
            ArrayAdapter<Nekretnina> adapter = (ArrayAdapter<Nekretnina>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Nekretnina> list = getDatabaseHelper().getNekretninaDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Metoda koja komunicira sa bazom podataka
    public PripremaORMLightHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, PripremaORMLightHelper.class);
        }
        return databaseHelper;
    }


    private void showStatusMesage(String message){
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("Agencija za Nekretnine");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_add);

        mBuilder.setLargeIcon(bm);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.priprema_add_new_nekretnina:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.priprema_add_nekretnina_layout);

                Button add = (Button) dialog.findViewById(R.id.add_nekretnina_btn_add);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText edName = (EditText) dialog.findViewById(R.id.nekretnina_name_add);
                        EditText edOpis = (EditText) dialog.findViewById(R.id.nekretnina_opis_add);
                        EditText edAdresa = (EditText) dialog.findViewById(R.id.nekretnina_adresa_add);
                        EditText edTelefon = (EditText) dialog.findViewById(R.id.nekretnina_telefon_add);
                        EditText edKvadratura = (EditText) dialog.findViewById(R.id.nekretnina_kvadratura_add);
                        EditText edBrojSobe = (EditText) dialog.findViewById(R.id.nekretnina_broj_soba_add);
                        EditText edCena = (EditText) dialog.findViewById(R.id.nekretnina_cena_add);

                        Nekretnina n = new Nekretnina();

                        n.setmName(edName.getText().toString());
                        n.setmOpis(edOpis.getText().toString());
                        n.setmAdresa(edAdresa.getText().toString());
                        n.setmTelefon(Integer.parseInt(edTelefon.getText().toString()));
                        n.setmKvadratura(Double.parseDouble(edKvadratura.getText().toString()));
                        n.setmBrojSoba(Integer.parseInt(edBrojSobe.getText().toString()));
                        n.setmCena(Double.parseDouble(edCena.getText().toString()));

                        try {
                            getDatabaseHelper().getNekretninaDao().create(n);
                            //provera podesenja
                            boolean toast = prefs.getBoolean(NOTIF_TOAST, false);
                            boolean status = prefs.getBoolean(NOTIF_STATUS, false);

                            if (toast){
                                Toast.makeText(PripremaListActivity.this, "Added new Nekretnina", Toast.LENGTH_SHORT).show();
                            }

                            if (status){
                                showStatusMesage("Added new Nekretnina");
                            }

                            //REFRESH
                            refresh();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                    }
                });
                Button cancel = (Button) dialog.findViewById(R.id.cancel_nekretnina_btn_add);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();
                break;
            case R.id.priprema_about:

                AlertDialog alertDialog = new AboutDialog(this).prepareDialog();
                alertDialog.show();
                break;
            case R.id.priprema_preferences:
                startActivity(new Intent(PripremaListActivity.this, PripremaPrefererences.class));
                break;

        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // nakon rada sa bazo podataka potrebno je obavezno
        //osloboditi resurse!
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
