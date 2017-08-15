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

import com.microlabs.trallet.presenter.AddCurrencyActivityPresenter;
import com.microlabs.trallet.repo.DatabaseBookRepository;
import com.microlabs.trallet.view.AddCurrencyActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCurrencyActivity extends AppCompatActivity implements AddCurrencyActivityView {

    @BindView(R.id.txtCurrName)
    TextInputEditText txtCurrName;
    @BindView(R.id.txtCurrValue)
    TextInputEditText txtCurrValue;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private AddCurrencyActivityPresenter presenter;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_currency);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if ((id = getIntent().getIntExtra("currId", -1)) != -1) {
            txtCurrName.setText(getIntent().getStringExtra("currName"));
            txtCurrValue.setText(String.valueOf(getIntent().getDoubleExtra("currValue", -1)));
            getSupportActionBar().setTitle("Edit Currency");
        }

        presenter = new AddCurrencyActivityPresenter(this, new DatabaseBookRepository());

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
            finish();
        } else if (item.getItemId() == R.id.menuDelete) {
            presenter.validateDeleteCurrency(id);
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnSave)
    public void onClick() {
        // if new Currency is created
        if (txtCurrName.getText().toString().isEmpty() || txtCurrValue.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Input all Data", Toast.LENGTH_SHORT).show();
        } else {
            if (id == -1) {
                presenter.insertNewCurrency(txtCurrName.getText().toString(), Double.valueOf(txtCurrValue.getText().toString()));
            } else {
                // if edit Currency
                presenter.updateCurrency(id, txtCurrName.getText().toString(), Double.valueOf(txtCurrValue.getText().toString()));
                finish();
            }
        }
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Fail to save Currency", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void done() {
        finish();
    }

    @Override
    public void showDuplicateError() {
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

    @Override
    public void validateDeleteCurrency() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to delete this currency?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteCurrency(id);
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void showFailDeleteCurrency() {
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

    @Override
    protected void onDestroy() {
        presenter.close();
        super.onDestroy();
    }
}
