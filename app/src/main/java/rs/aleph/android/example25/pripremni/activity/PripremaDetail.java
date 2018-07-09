package rs.aleph.android.example25.pripremni.activity;


import android.Manifest;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import rs.aleph.android.example25.R;
import rs.aleph.android.example25.pripremni.db.PripremaORMLightHelper;
import rs.aleph.android.example25.pripremni.db.model.Nekretnina;

import static rs.aleph.android.example25.pripremni.activity.PripremaListActivity.NOTIF_STATUS;
import static rs.aleph.android.example25.pripremni.activity.PripremaListActivity.NOTIF_TOAST;

public class PripremaDetail extends AppCompatActivity {

    private PripremaORMLightHelper databaseHelper;
    private SharedPreferences prefs;
    private Nekretnina nekretnina;

    private TextView tvName;
    private TextView tvOpis;
    private TextView tvAdresa;
    private ImageView tvSlika;
    private TextView tvTelefon;
    private TextView tvKvadratura;
    private TextView tvBrojSobe;
    private TextView tvCena;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priprema_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int key = getIntent().getExtras().getInt(PripremaListActivity.ACTOR_KEY);

        try {
            nekretnina = getDatabaseHelper().getNekretninaDao().queryForId(key);

            tvName = (TextView) findViewById(R.id.nekretnina_name);
            tvOpis = (TextView) findViewById(R.id.nekretnina_opis);
            tvAdresa = (TextView) findViewById(R.id.nekretnina_adresa);
            tvSlika = (ImageView) findViewById(R.id.nekretnina_slika);
            tvKvadratura = (TextView) findViewById(R.id.nekretnina_kvadratura);
            tvBrojSobe = (TextView) findViewById(R.id.nekretnina_broj_soba);
            tvCena = (TextView) findViewById(R.id.nekretnina_cena);

            tvTelefon = (TextView) findViewById(R.id.nekretnina_telefon);
            tvTelefon.setText(String.valueOf(nekretnina.getmTelefon()));

            tvTelefon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String broj = String.valueOf(tvTelefon.getText());
                    call(v, broj);
                }
            });

            tvName.setText(nekretnina.getmName());
            tvOpis.setText(nekretnina.getmOpis());
            tvAdresa.setText(nekretnina.getmAdresa());
            tvKvadratura.setText(String.valueOf(nekretnina.getmKvadratura()));
            tvBrojSobe.setText(String.valueOf(nekretnina.getmBrojSoba()));
            tvCena.setText(String.valueOf(nekretnina.getmCena()));
            InputStream is = null;
            try {
                is = getAssets().open("panorama.jpg");
                Drawable drawable = Drawable.createFromStream(is, null);
                tvSlika.setImageDrawable(drawable);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void call(View v, String broj) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse(broj));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        startActivity(callIntent);
    }

    //Metoda koja komunicira sa bazom podataka
    public PripremaORMLightHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, PripremaORMLightHelper.class);
        }
        return databaseHelper;
    }

    private void showStatusMesage(String message) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("Agencija za Nekretnine");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_add);

        mBuilder.setLargeIcon(bm);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    private void showMessage(String message) {
        //provera
        boolean toast = prefs.getBoolean(NOTIF_TOAST, false);
        boolean status = prefs.getBoolean(NOTIF_STATUS, false);

        if (toast) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        if (status) {
            showStatusMesage(message);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.priprema_edit:

                final Dialog dialogEdit = new Dialog(this);
                dialogEdit.setContentView(R.layout.edit_nekretnina_layout);
                dialogEdit.setCanceledOnTouchOutside(false);

                Button cancel = (Button) dialogEdit.findViewById(R.id.cancel_nekretnina_btn_edit);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogEdit.dismiss();
                    }
                });

                Button edit = (Button) dialogEdit.findViewById(R.id.add_nekretnina_btn_edit);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {


                            EditText edName = (EditText) dialogEdit.findViewById(R.id.nekretnina_name_edit);
                            EditText edOpis = (EditText) dialogEdit.findViewById(R.id.nekretnina_opis_edit);
                            EditText edAdresa = (EditText) dialogEdit.findViewById(R.id.nekretnina_adresa_edit);
                            EditText edTelefon = (EditText) dialogEdit.findViewById(R.id.nekretnina_telefon_edit);
                            EditText edKvadratura = (EditText) dialogEdit.findViewById(R.id.nekretnina_kvadratura_edit);
                            EditText edBrojSobe = (EditText) dialogEdit.findViewById(R.id.nekretnina_broj_soba_edit);
                            EditText edCena = (EditText) dialogEdit.findViewById(R.id.nekretnina_cena_edit);

                            nekretnina.setmName(edName.getText().toString());
                            nekretnina.setmOpis(edOpis.getText().toString());
                            nekretnina.setmAdresa(edAdresa.getText().toString());
                            nekretnina.setmTelefon(Integer.parseInt(edTelefon.getText().toString()));
                            nekretnina.setmKvadratura(Double.parseDouble(edKvadratura.getText().toString()));
                            nekretnina.setmBrojSoba(Integer.parseInt(edBrojSobe.getText().toString()));
                            nekretnina.setmCena(Double.parseDouble(edCena.getText().toString()));


                            tvName.setText(nekretnina.getmName());
                            tvOpis.setText(nekretnina.getmOpis());
                            tvAdresa.setText(nekretnina.getmAdresa());
                            tvTelefon.setText(String.valueOf(nekretnina.getmTelefon()));
                            tvKvadratura.setText(String.valueOf(nekretnina.getmKvadratura()));
                            tvBrojSobe.setText(String.valueOf(nekretnina.getmBrojSoba()));
                            tvCena.setText(String.valueOf(nekretnina.getmCena()));


                            getDatabaseHelper().getNekretninaDao().update(nekretnina);
                            showMessage("Nekretnina detail updated");
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                        dialogEdit.dismiss();
                        finish();
                    }

                });

                dialogEdit.show();
                break;
            case R.id.priprema_remove:
                final Dialog dialogRemove = new Dialog(this);
                dialogRemove.setContentView(R.layout.remove_nekretina_layout);
                dialogRemove.setCanceledOnTouchOutside(false);

                TextView textView =(TextView) findViewById(R.id.text_dialog);

                Button deleteDialog = (Button) dialogRemove.findViewById(R.id.delete_actor_btn_dialog);
                deleteDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            getDatabaseHelper().getNekretninaDao().delete(nekretnina);
                            showMessage("Nekretnina Deleted");
                            finish();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
//

//
                    }
                });
                Button cancelDialog = (Button) dialogRemove.findViewById(R.id.cancel_actor_btn_dialog);
                cancelDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogRemove.dismiss();

                        finish(); //moramo pozvati da bi se vratili na prethodnu aktivnost
                    }
                });
                dialogRemove.show();
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
