package com.example.mulyani.getsourcewebsite;


import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    EditText Edtxt;
    String url;
    Spinner spin;
    ProgressBar progb;
    TextView txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Edtxt = (EditText) findViewById(R.id.txtUrl);
        progb = (ProgressBar) findViewById(R.id.progbar);
        txtview = (TextView) findViewById(R.id.txtResult);
        spin = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.jenis,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        progb.setVisibility(View.GONE);
    }


    public void doSomething(View view) {
        url = spin.getSelectedItem() + Edtxt.getText().toString();
        boolean valid = Patterns.WEB_URL.matcher(url).matches();

        if (valid){
            getSupportLoaderManager().restartLoader(0,null,this);
            progb.setVisibility(View.VISIBLE);
            txtview.setVisibility(View.GONE);
        }
        else {
            Loader loader = getSupportLoaderManager().getLoader(0);
            if (loader != null){
                loader.cancelLoad();
            }
            txtview.setText("URL yang dimasukkan salah");
            progb.setVisibility(View.GONE);
            txtview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new ConnecttoInternet(this,url);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<String> loader, String data) {
        txtview.setText(data);
        progb.setVisibility(View.GONE);
        txtview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<String> loader) {

    }
}
