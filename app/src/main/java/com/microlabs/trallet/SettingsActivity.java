package com.microlabs.trallet;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btnDeleteAll, R.id.btnAbout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDeleteAll:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure want to delete all data?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.deleteAll();
                        realm.commitTransaction();
                        Toast.makeText(SettingsActivity.this, "All data deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setTitle("Delete all data?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            case R.id.btnAbout:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("About Us");
                builder1.setMessage("Made by MicroLabs Developers\nAJ\nNA\nNH\nHP");
                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.create().show();
                break;
        }
    }
}
