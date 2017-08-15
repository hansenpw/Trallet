package com.microlabs.trallet;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.microlabs.trallet.base.RealmHelper;
import com.microlabs.trallet.model.Currency;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class AddNewCurrencyActivity extends AppCompatActivity {

    @BindView(R.id.txtCurrName)
    TextInputEditText txtCurrName;
    @BindView(R.id.txtCurrValue)
    TextInputEditText txtCurrValue;
    int id;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_currency);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        realm = Realm.getDefaultInstance();

//        id = getIntent().getIntExtra("currId", -1);

        if ((id = getIntent().getIntExtra("currId", -1)) != -1) {
            txtCurrName.setText(getIntent().getStringExtra("currName"));
            txtCurrValue.setText(String.valueOf(getIntent().getDoubleExtra("currValue", -1)));
            getSupportActionBar().setTitle("Edit Currency");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (id != -1) {
            getMenuInflater().inflate(R.menu.menu_delete, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            realm.close();
            finish();
        } else if (item.getItemId() == R.id.menuDelete) {
            if (RealmHelper.canCurrencyDelete(realm, id)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure want to delete this currency?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realm.beginTransaction();
                        realm.where(Currency.class).equalTo(Currency.fId, id).findFirst().deleteFromRealm();
                        realm.commitTransaction();
                        realm.close();
                        dialog.dismiss();
                        finish();
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("This currency is currently being used by some expense data. Please change the expense currency, to be able to remove this currency.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnSave)
    public void onClick() {
        // if new Currency is created
        if (txtCurrName.getText().toString().isEmpty() || txtCurrValue.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Input all Data", Toast.LENGTH_SHORT).show();
        } else {
            if (id == -1) {
                if (realm.where(Currency.class).equalTo(Currency.fName, txtCurrName.getText().toString()).findFirst() == null) {
                    realm.beginTransaction();
                    Currency currency = realm.createObject(Currency.class);
                    currency.setId(realm);
                    currency.setName(txtCurrName.getText().toString());
                    currency.setValue(Double.parseDouble(txtCurrValue.getText().toString()));
                    realm.commitTransaction();
                    realm.close();
                    finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Currency Name has exists. Please change the currency name to prevent duplication.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            } else {
                // if edit Currency
                realm.beginTransaction();
                Currency currency = realm.where(Currency.class).equalTo(Currency.fId, id).findFirst();
                currency.setName(txtCurrName.getText().toString());
                currency.setValue(Double.parseDouble(txtCurrValue.getText().toString()));
                realm.commitTransaction();
                realm.close();
                finish();
            }
        }
    }
}
