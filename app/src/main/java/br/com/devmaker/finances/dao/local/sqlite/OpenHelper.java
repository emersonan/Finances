package br.com.devmaker.finances.dao.local.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "financas.db";
	private final Context context;
	
	public OpenHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        version1(db);
	}

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       String drop = "DROP TABLE finances";
       String drop1 = "DROP TABLE gastos";
        db.execSQL(drop);
        db.execSQL(drop1);
       onCreate(db);
	}

    private void version1(SQLiteDatabase db) {
        String table = "create table finances " +
                "( nome varchar(500)," +
                "valor double," +
                "_id integer primary key autoincrement)";

        String table2 = "create table gastos " +
                "( nome varchar(500)," +
                "valor double," +
                "_id integer primary key autoincrement)";

        db.execSQL(table);
        db.execSQL(table2);
    }
}
