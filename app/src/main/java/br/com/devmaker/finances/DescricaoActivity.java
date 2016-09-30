package br.com.devmaker.finances;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import br.com.devmaker.finances.dao.local.sqlite.FinancesDao;
import br.com.devmaker.finances.model.Finance;
import br.com.devmaker.finances.util.MoneyTextWatcher;

public class DescricaoActivity extends AppCompatActivity {
    EditText nome;
    EditText valor;
    ImageView add;
    Finance finance;
    Toolbar toolbar;
    TextView btnOk;
    TextView btnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Descrição");
            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
            toolbar.setNavigationIcon(R.drawable.back);
            setSupportActionBar(toolbar);
        }


        nome = (EditText) findViewById(R.id.editNome);
        valor = (EditText) findViewById(R.id.editValor);
        valor.addTextChangedListener(new MoneyTextWatcher(valor));
        btnDel = (TextView) findViewById(R.id.delTxt);
        btnOk = (TextView) findViewById(R.id.okBtn);

        Intent it = getIntent();
        finance = (Finance) it.getSerializableExtra("model");
        nome.setText(finance.getNome());
        valor.setText(String.valueOf(finance.getValor()));

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valueString = valor.getText().toString();
                String[] separated = valueString.split("\\$");
                valueString = separated[1];
                Double d = Double.parseDouble(valueString.replaceAll(",","."));
                finance.setValor(d);
                finance.setNome(nome.getText().toString());
                FinancesDao dao = new FinancesDao(DescricaoActivity.this);
                if (dao.update(finance)) {
                    Toast.makeText(getBaseContext(), "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
//                    RelatorioActivity r = new RelatorioActivity();
//                    r.adapter.notifyDataSetChanged();
//                    onBackPressed();
                    Intent it = new Intent(DescricaoActivity.this,MainActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(getBaseContext(), "Erro ao atualizar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FinancesDao dao = new FinancesDao(DescricaoActivity.this);
                if(dao.delete(finance)){
                    Toast.makeText(getBaseContext(), "Deletado com sucesso", Toast.LENGTH_SHORT).show();
//                    RelatorioActivity r = new RelatorioActivity();
//                    r.adapter.notifyDataSetChanged();
//                    onBackPressed();
                    Intent it = new Intent(DescricaoActivity.this,MainActivity.class);
                    startActivity(it);
                }else {
                    Toast.makeText(getBaseContext(), "Erro ao deletar", Toast.LENGTH_SHORT).show();
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
}
