package br.com.devmaker.finances;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.devmaker.finances.adapter.RelatorioAdapter;
import br.com.devmaker.finances.dao.local.sqlite.FinancesDao;
import br.com.devmaker.finances.model.Finance;
import br.com.devmaker.finances.util.MoneyTextWatcher;

public class PlanejamentoActivity extends AppCompatActivity {

    EditText nome;
    EditText valor;
    ImageView add;
    Finance finance;
    Toolbar toolbar;
    RelativeLayout botaoRelatorio;
    ListView listView;
    TextView txtValor;
    ArrayList<Finance> finances = new ArrayList<>();
    Double totalValor = 0.0;
    public RelatorioAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planejamento);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Planejamento de Gastos");
            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
            toolbar.setNavigationIcon(R.drawable.back);
            setSupportActionBar(toolbar);
        }

        FinancesDao dao = new FinancesDao(getBaseContext());

        nome = (EditText) findViewById(R.id.editNome);
        valor = (EditText) findViewById(R.id.editValor);
        valor.addTextChangedListener(new MoneyTextWatcher(valor));
        add = (ImageView) findViewById(R.id.imageView);
        botaoRelatorio = (RelativeLayout) findViewById(R.id.relativeLayout);
        listView = (ListView) findViewById(R.id.listView2);
        txtValor = (TextView) findViewById(R.id.txtValor);

        finances = dao.getAllGastos();

        for(Finance f : finances){
            totalValor += f.getValor();
        }
        txtValor.setText("R$ " + String.valueOf(totalValor));
        adapter = new RelatorioAdapter(this,finances);
        listView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nome.getText().toString().length() != 0 && valor.getText().toString().length() != 0 ){

                    FinancesDao dao = new FinancesDao(getBaseContext());
                    finance = new Finance();
                    finance.setNome(nome.getText().toString());
                    String valueString = valor.getText().toString();
                    String[] separated = valueString.split("\\$");
                    valueString = separated[1];
                    Double d = Double.parseDouble(valueString.replaceAll(",","."));

                    finance.setValor(d);
                    if(dao.savePlanejamento(finance)){
                        nome.setText("");
                        valor.setText("");
                        finances.clear();
                        finances.addAll(dao.getAllGastos());
                        adapter.notifyDataSetChanged();


                        for(Finance f : finances){
                            totalValor += f.getValor();
                        }
                        txtValor.setText("R$ " + String.valueOf(totalValor));

                        Toast.makeText(getBaseContext(),"Novo gasto inserido! Cuidado para não gastar demais!!!!!",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getBaseContext(),"OPS, houve um erro. Parece ser um sinal divino, ein?!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(),"OPS, Você esqueceu de preencher algum campo. Pode ir com calma :)",Toast.LENGTH_SHORT).show();
                }
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

//        public void atualizaList(){
//            FinancesDao dao = new FinancesDao(getBaseContext());
//
//            finances = dao.getAll();
//
//            for(Finance f : finances){
//                totalValor += f.getValor();
//            }
//            txtValor.setText("R$ " + String.valueOf(totalValor));
//            adapter = new RelatorioAdapter(this,finances);
//            listView.setAdapter(adapter);
//        }

}
