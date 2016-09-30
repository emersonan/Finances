package br.com.devmaker.finances.dao.local.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.devmaker.finances.model.Finance;


/**
 * Created by DevMaker on 3/23/16.
 */
public class FinancesDao {
    private Context context;

    public FinancesDao(Context context) {
        this.context = context;
    }


    public ArrayList<Finance> getAll() {
        OpenHelper db = new OpenHelper(context);

        SQLiteDatabase dbl = db.getReadableDatabase();
        Cursor cursor = dbl.rawQuery("select * from finances ", null);
        ArrayList<Finance> properties = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Finance finance = new Finance();
                finance.setNome(cursor.getString(0));
                finance.setValor(Double.parseDouble(cursor.getString(1)));
                finance.setId(cursor.getInt(2));
                properties.add(finance);
            } while (cursor.moveToNext());

        }
        dbl.close();
        return properties;
    }

    public ArrayList<Finance> getAllGastos() {
        OpenHelper db = new OpenHelper(context);

        SQLiteDatabase dbl = db.getReadableDatabase();
        Cursor cursor = dbl.rawQuery("select * from gastos ", null);
        ArrayList<Finance> properties = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Finance finance = new Finance();
                finance.setNome(cursor.getString(0));
                finance.setValor(Double.parseDouble(cursor.getString(1)));
                finance.setId(cursor.getInt(2));
                properties.add(finance);
            } while (cursor.moveToNext());

        }
        dbl.close();
        return properties;
    }

    public List<Finance> getById(String id) {
        OpenHelper db = new OpenHelper(context);

        SQLiteDatabase dbl = db.getReadableDatabase();
        Cursor cursor = dbl.rawQuery("select * from abastecimento where _id = ?", new String[]{id});
        List<Finance> properties = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Finance abastecimento = new Finance();
                properties.add(abastecimento);
            } while (cursor.moveToNext());

        }
        dbl.close();
        return properties;
    }


    public boolean save(Finance finance) {
        try {
            OpenHelper db = new OpenHelper(context);
            SQLiteDatabase dbl = db.getWritableDatabase();


            dbl.execSQL("insert into finances(nome,valor) values (?,?);",
                    new Object[]{
                            finance.getNome(),
                            finance.getValor(),
                    });

            dbl.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    public boolean savePlanejamento(Finance finance) {
        try {
            OpenHelper db = new OpenHelper(context);
            SQLiteDatabase dbl = db.getWritableDatabase();


            dbl.execSQL("insert into gastos (nome,valor) values (?,?);",
                    new Object[]{
                            finance.getNome(),
                            finance.getValor(),
                    });

            dbl.close();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }

    }

    public boolean deleteAll() {
        try {
            OpenHelper db = new OpenHelper(context);
            SQLiteDatabase dbl = db.getWritableDatabase();

            dbl.execSQL("delete from finances");
            dbl.execSQL("delete from gastos");
            dbl.close();
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public boolean delete(Finance f) {
        try {
            OpenHelper db = new OpenHelper(context);
            SQLiteDatabase dbl = db.getWritableDatabase();

            dbl.execSQL("delete from finances where _id = ?", new Object[]{f.getId()});
            dbl.close();
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public boolean update(Finance finance) {
        try {
           OpenHelper db = new OpenHelper(context);
           SQLiteDatabase dbl = db.getWritableDatabase();
//
//            dbl.execSQL("update finances set nome=? and valor=? where _id = ?", new Object[]{finance.getNome(), finance.getValor(), finance.getId()});
//            dbl.close();

            ContentValues valores = new ContentValues();
            valores.put("nome", finance.getNome());
            valores.put("valor", finance.getValor());

            dbl.update("finances", valores, "_id = ?", new String[]{"" + finance.getId()});

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
