package br.com.devmaker.finances;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import br.com.devmaker.finances.dao.local.sqlite.FinancesDao;
import br.com.devmaker.finances.model.Finance;
import br.com.devmaker.finances.util.MoneyTextWatcher;

public class MainActivity extends AppCompatActivity {

    EditText nome;
    EditText valor;
    ImageView add;
    Finance finance;
    Toolbar toolbar;
    RelativeLayout botaoRelatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Cadastro de Gastos");
            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
            setSupportActionBar(toolbar);
        }
        FinancesDao dao = new FinancesDao(getBaseContext());

        nome = (EditText) findViewById(R.id.editNome);
        valor = (EditText) findViewById(R.id.editValor);
        valor.addTextChangedListener(new MoneyTextWatcher(valor));
        add = (ImageView) findViewById(R.id.imageView);
        botaoRelatorio = (RelativeLayout) findViewById(R.id.relativeLayout);


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
                    if(dao.save(finance)){
                        nome.setText("");
                        valor.setText("");
                        Toast.makeText(getBaseContext(),"Novo gasto inserido! Cuidado para não gastar demais!!!!!",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getBaseContext(),"OPS, houve um erro. Parece ser um sinal divino, ein?!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(),"OPS, Você esqueceu de preencher algum campo. Pode ir com calma :)",Toast.LENGTH_SHORT).show();
                }
            }
        });


        botaoRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this,RelatorioActivity.class);
                startActivity(it);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        FinancesDao dao = new FinancesDao(getBaseContext());

        if(id == R.id.deletar){
           if(dao.deleteAll()){
               Toast.makeText(getBaseContext(),"Tabela de dados agora está vazia!",Toast.LENGTH_LONG).show();
           }else{
               Toast.makeText(getBaseContext(),"Ops, ocorreu um erro! E a culpa deve ser do nosso estágiario.",Toast.LENGTH_LONG).show();
           }
        }

        if(id == R.id.cadastro){
          startActivity(new Intent(getBaseContext(),PlanejamentoActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }
}
