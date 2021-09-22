package mz.unilurio.solidermed.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBService  extends SQLiteOpenHelper {
    private static String nomeDB = "BDHospital.db";
    private static int versao = 1;

    private String[] sql = {
            "CREATE TABLE UserDoctor(id INTEGER NOT NULL UNIQUE, username TEXT NOT NULL,fullname TEXT NOT NULL,tellDoctor TEXT NOT NULL, pass TEXT NOT NULL, PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE UserNurse(id INTEGER NOT NULL UNIQUE, username TEXT NOT NULL ,fullname TEXT NOT NULL ,tellNurse TEXT NOT NULL, pass TEXT NOT NULL, PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE Hospitais(id INTEGER NOT NULL UNIQUE, hospital TEXT NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE Dilatacao(id INTEGER NOT NULL UNIQUE, dilatation TEXT NOT NULL, horas TEXT NOT NULL , minutes TEXT NOT NULL  , PRIMARY KEY(id AUTOINCREMENT ));",
    };

    public DBService(@Nullable Context context) {
        super(context, nomeDB, null, versao);

        if (!isDoctorLogin("barata", "123")) {
            addDoctor("barata", "123", "845740722", "Barata Estevao Mario Barata");
        }

        if (!isHospital("Hospital Distrital de Chiúre")) {
            addHospital("Hospital Distrital de Chiúre");
            addHospital("Centro de saúde de Catapua");
            addHospital("Centro de saúde de Ocua");
            addHospital("Centro de saúde de Chiúre Velho");
            addHospital("Centro de saúde de Mmala");
            addHospital("Centro de saúde de Marera");
            addHospital("Centro de saúde de Mazeze");
            addHospital("Centro de saúde de Muege");
            addHospital("Centro de saúde de Nakoto");
            addHospital("Centro de saúde de Namogeliua");
            addHospital("Centro de saúde de Samora Machel");
            addHospital("Centro de saúde de Bilibiza");
        }
        if (!isExistDilatation("1")) {
            addDilatation(1, 9, 0);
            addDilatation(2, 8, 0);
            addDilatation(3, 7, 0);
            addDilatation(4, 9, 0);
            addDilatation(5, 7, 30);
            addDilatation(6, 6, 0);
            addDilatation(7, 4, 30);
            addDilatation(8, 3, 0);
            addDilatation(9, 1, 30);
            addDilatation(10, 0, 30);
        }

    }

    public DBService() {
        super(null, nomeDB, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < sql.length; i++) {
            db.execSQL(sql[i]);
        }
    }

    public long addDoctor(String username, String pass, String tellDoctor, String fullName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("fullName", fullName);
        cv.put("tellDoctor", tellDoctor);
        cv.put("pass", pass);
        return db.insert("UserDoctor", null, cv);
    }

    public long updadeDoctor(int id, String username, String pass, String tellDoctor, String fullName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("fullname", fullName);
        cv.put("tellDoctor", tellDoctor);
        cv.put("pass", pass);
        return db.update("UserDoctor", cv, "id=?", new String[]{String.valueOf(id)});
    }

    public long updadeDoctorNameAndTellphone(int id, String tellDoctor, String fullName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fullname", fullName);
        cv.put("tellDoctor", tellDoctor);
        return db.update("UserDoctor", cv, "id=?", new String[]{String.valueOf(id)});
    }

    public long updadeDoctorUserAndPassword(int id, String username, String pass) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("pass", pass);
        return db.update("UserDoctor", cv, "id=?", new String[]{String.valueOf(id)});
    }

    //DELETE
    public long deleteDoctor(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("UserDoctor", "id=?", new String[]{String.valueOf(id)});
    }

    //TellPhone
    public boolean isTellDoctor(String tellPhone) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM UserDoctor WHERE tellDoctor = ?",
                new String[]{tellPhone});
        c.moveToFirst();
        if (c.getCount() == 1) {
            return true;
        }
        return false;
    }

    //SELECT LOGIN
    public boolean isDoctorLogin(String username, String pass) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM UserDoctor WHERE username = ? AND pass = ?",
                new String[]{username, pass});
        c.moveToFirst();
        if (c.getCount() == 1) {
            return true;
        }
        return false;
    }

    public int getIdDoctor(String tellDoctor) {
        int id = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM UserDoctor WHERE tellDoctor = ?",
                new String[]{tellDoctor});
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                id = c.getInt(c.getColumnIndex("id"));
            } while (c.moveToNext());
        }
        return id;
    }


    public List<UserDoctor> getListDoctor() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM UserDoctor", null);

        List<UserDoctor> listaUtilizadores = new ArrayList<>();
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {

                int id = c.getInt(c.getColumnIndex("id"));
                String username = c.getString(c.getColumnIndex("username"));
                String pass = c.getString(c.getColumnIndex("pass"));
                String tellDoctor = c.getString(c.getColumnIndex("tellDoctor"));
                String fullName = c.getString(c.getColumnIndex("fullname"));
                listaUtilizadores.add(new UserDoctor(fullName, username, pass, id, tellDoctor));

            } while (c.moveToNext());
        }
        return listaUtilizadores;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // ..............................TABLE NURSE.................................///


    public long addNurse(String fullname, String userNurse, String passworNurse, String tellNurse) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", userNurse);
        cv.put("fullname", fullname);
        cv.put("tellNurse", tellNurse);
        cv.put("pass", passworNurse);
        return db.insert("UserNurse", null, cv);
    }

    public long updadeNurse(int id, String fullname, String userNurse, String passworNurse, String tellNurse) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", userNurse);
        cv.put("fullname", fullname);
        cv.put("tellNurse", tellNurse);
        cv.put("pass", passworNurse);
        return db.update("UserNurse", cv, "id=?", new String[]{String.valueOf(id)});
    }

    public long updadeNurseNameAndTellphone(int id, String fullname, String tellNurse) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fullname", fullname);
        cv.put("tellNurse", tellNurse);
        return db.update("UserNurse", cv, "id=?", new String[]{String.valueOf(id)});
    }

    public long updadeNurseUserAndPassword(int id, String username, String pass) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("pass", pass);
        return db.update("UserNurse", cv, "id=?", new String[]{String.valueOf(id)});
    }

    //DELETE
    public long deleteNurse(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("UserNurse", "id=?", new String[]{String.valueOf(id)});
    }

    //TellPhone
    public boolean isTellNurse(String tellPhone) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM UserNurse WHERE tellDoctor = ?",
                new String[]{tellPhone});
        c.moveToFirst();
        if (c.getCount() == 1) {
            return true;
        }
        return false;
    }

    //SELECT LOGIN
    public boolean isNurseLogin(String username, String pass) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM UserNurse WHERE username = ? AND pass = ?",
                new String[]{username, pass});
        c.moveToFirst();
        if (c.getCount() == 1) {
            return true;
        }
        return false;
    }

    public int getIdNurse(String tellNurse) {
        int id = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM UserNurse WHERE tellNurse = ?",
                new String[]{tellNurse});
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                id = c.getInt(c.getColumnIndex("id"));
            } while (c.moveToNext());
        }
        return id;
    }


    public List<UserNurse> getListNurse() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM UserNurse", null);

        List<UserNurse> listaUtilizadores = new ArrayList<>();
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {

                int id = c.getInt(c.getColumnIndex("id"));
                String userNurse = c.getString(c.getColumnIndex("username"));
                String passworNurse = c.getString(c.getColumnIndex("pass"));
                String tellNurse = c.getString(c.getColumnIndex("tellNurse"));
                String fullname = c.getString(c.getColumnIndex("fullname"));
                listaUtilizadores.add(new UserNurse(fullname, userNurse, passworNurse, tellNurse, id));

            } while (c.moveToNext());
        }
        return listaUtilizadores;
    }
    // .................................TABLE HOSPITAL.................................//


    public long addHospital(String hospital) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("hospital", hospital);
        return db.insert("Hospitais", null, cv);
    }

    public boolean isHospital(String hospital) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Hospitais WHERE hospital = ?",
                new String[]{hospital});
        c.moveToFirst();
        if (c.getCount() == 1) {
            return true;
        }
        return false;
    }

    public List<Hospitais> getListHospitais() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Hospitais", null);

        List<Hospitais> listaUtilizadores = new ArrayList<>();
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {

                int id = c.getInt(c.getColumnIndex("id"));
                String hospital = c.getString(c.getColumnIndex("hospital"));

                listaUtilizadores.add(new Hospitais(hospital, id + ""));

            } while (c.moveToNext());
        }
        return listaUtilizadores;
    }

    //............................dilatacao........................................//

    public long addDilatation(int numberDilatation, int timerDilatationHours, int timerDilatationMinutes) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("dilatation", numberDilatation);
        cv.put("horas", timerDilatationHours);
        cv.put("minutes", timerDilatationMinutes);
        return db.insert("Dilatacao", null, cv);
    }

    public long updadeDilatation(int id, int numberDilatation, int timerDilatationHours, int timerDilatationMinutes) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("dilatation", numberDilatation);
        cv.put("horas", timerDilatationHours);
        cv.put("minutes", timerDilatationMinutes);
        return db.update("Dilatacao", cv, "id=?", new String[]{String.valueOf(id)});
    }

    //DELETE
    public long deleteDilatation(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("Dilatacao", "id=?", new String[]{String.valueOf(id)});
    }

    //TellPhone
    public boolean isExistDilatation(String dilatation) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Dilatacao WHERE dilatation = ?",
                new String[]{dilatation});
        c.moveToFirst();
        if (c.getCount() == 1) {
            return true;
        }
        return false;
    }

    public int getTimerDilatation(String dilatation) {
        int horas = 0;
        int minutos=0;
        int secundos=4;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Dilatacao WHERE dilatation = ?",
                new String[]{dilatation});
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                horas = c.getInt(c.getColumnIndex("horas"));
                minutos = c.getInt(c.getColumnIndex("minutes"));
                secundos=horas*3600+minutos*60;
            } while (c.moveToNext());
        }
        return secundos;
    }
    public int getIdDilatation(String dilatation) {
        int id = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Dilatacao WHERE dilatation = ?",
                new String[]{dilatation});
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                id = c.getInt(c.getColumnIndex("id"));
            } while (c.moveToNext());
        }
        return id;
    }


    public List<DilatationAndTimer> getListDilatation() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Dilatacao", null);

        List<DilatationAndTimer> arrayList = new ArrayList<>();
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {

                int id = c.getInt(c.getColumnIndex("id"));
                int numberDilatation = c.getInt(c.getColumnIndex("dilatation"));
                int timerDilatationHours = c.getInt(c.getColumnIndex("horas"));
                int timerDilatationMinutes = c.getInt(c.getColumnIndex("minutes"));
                arrayList.add(new DilatationAndTimer(numberDilatation, timerDilatationHours, timerDilatationMinutes, id));

            } while (c.moveToNext());
        }
        return arrayList;


    }
}
