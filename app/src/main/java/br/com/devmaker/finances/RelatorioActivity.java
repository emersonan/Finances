package br.com.devmaker.finances;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.devmaker.finances.adapter.RelatorioAdapter;
import br.com.devmaker.finances.dao.local.sqlite.FinancesDao;
import br.com.devmaker.finances.model.Finance;

public class RelatorioActivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView lista;
    FinancesDao dao;
    ArrayList<Finance> finances = new ArrayList<>();
    public RelatorioAdapter adapter;
    TextView total;
    Double totalValor = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Gastos");
            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
            toolbar.setNavigationIcon(R.drawable.back);
            setSupportActionBar(toolbar);
        }

        total = (TextView) findViewById(R.id.txtTotal);
        dao = new FinancesDao(this);
        lista = (ListView) findViewById(R.id.listView);
        finances = dao.getAll();

        for(Finance f : finances){
            totalValor += f.getValor();
        }
        total.setText("R$ " + String.valueOf(totalValor));
        adapter = new RelatorioAdapter(this,finances);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Finance f = (Finance) parent.getItemAtPosition(position);
                Intent it = new Intent(RelatorioActivity.this,DescricaoActivity.class);
                it.putExtra("model",f);
                startActivity(it);

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
