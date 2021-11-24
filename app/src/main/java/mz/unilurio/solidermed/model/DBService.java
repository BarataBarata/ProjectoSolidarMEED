package mz.unilurio.solidermed.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.AddParturientActivity;
import mz.unilurio.solidermed.MainActivity;

public class DBService  extends SQLiteOpenHelper {
    public  boolean isNotificationAlerte;
    private Notification notification;
    private static String nomeDB = "BDHospital.db";
    private static int versao = 1;

    private String[] sql = {
           // "CREATE TABLE AllacessUser(id INTEGER NOT NULL UNIQUE, acess boolean NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE AuxParturientes(id INTEGER NOT NULL UNIQUE, " +
                    "fullname TEXT NOT NULL," +
                    "apelido TEXT NOT NULL," +
                    "idade TEXT NOT NULL," +
                    "idAuxParturiente TEXT NOT NULL," +
                    "setYearDayMonthNotification TEXT NOT NULL," +
                    "dilatacao TEXT NOT NULL," +
                    "paridade TEXT NOT NULL," +
                    "isTrasferencia boolean NOT NULL," +
                    "isTrasferenciaForaDaUnidade boolean NOT NULL," +
                    "HoraEntrada TXT NOT NULL, " +
                    "horaParte INTEGER NOT NULL, " +
                    "minutoParte INTEGER NOT NULL," +
                    "segundoParte INTEGER NOT NULL," +
                    "HoraAtendimento TXT NOT NULL," +
                    "origemTrasferencia TXT NOT NULL," +
                    "motivoOrigemTrasferencia TXT NOT NULL," +
                    "destinoTrasferencia TXT NOT NULL," +
                    "motivosDestinoTrasferencia TXT NOT NULL," +
                    "parturienteEmprocesso boolean NOT NULL," +
                    "idadeGestacional TXT NOT NULL, " +
                    "PRIMARY KEY(id AUTOINCREMENT ));",

            "CREATE TABLE Atendidos(id INTEGER NOT NULL UNIQUE," +
                    "fullname TEXT NOT NULL," +
                    "apelido TEXT NOT NULL," +
                    "idade TEXT NOT NULL," +
                    "tipoAtendimento TEXT NOT NULL," +
                    "idAuxParturiente TEXT NOT NULL," +
                    "setYearDayMonthNotification TEXT NOT NULL," +
                    "dilatacao TEXT NOT NULL," +
                    "paridade TEXT NOT NULL," +
                    "isTrasferencia boolean NOT NULL," +
                    "isTrasferenciaForaDaUnidade boolean NOT NULL," +
                    "HoraEntrada TXT NOT NULL, " +
                    "horaParte INTEGER NOT NULL, " +
                    "minutoParte INTEGER NOT NULL," +
                    "segundoParte INTEGER NOT NULL," +
                    "HoraAtendimento TXT NOT NULL," +
                    "origemTrasferencia TXT NOT NULL," +
                    "motivoOrigemTrasferencia TXT NOT NULL," +
                    "destinoTrasferencia TXT NOT NULL," +
                    "motivosDestinoTrasferencia TXT NOT NULL," +
                    "parturienteEmprocesso boolean NOT NULL," +
                    "idadeGestacional TXT NOT NULL, " +
                    "PRIMARY KEY(id AUTOINCREMENT ));",

            "CREATE TABLE Parturientes(id INTEGER NOT NULL UNIQUE," +
                    " fullname TEXT NOT NULL," +
                    "apelido TEXT NOT NULL," +
                    "idade TEXT NOT NULL," +
                    "dilatacao TEXT NOT NULL," +
                    "paridade TEXT NOT NULL," +
                    "setYearDayMonthNotification TEXT NOT NULL," +
                    "idAuxParturiente TEXT NOT NULL," +
                    "dia INTEGER NOT NULL," +
                    "isTrasferencia boolean NOT NULL," +
                    "isTrasferenciaForaDaUnidade boolean NOT NULL," +
                    "HoraEntrada TXT NOT NULL, " +
                    "horaParte INTEGER NOT NULL," +
                    "minutoParte INTEGER NOT NULL," +
                    "segundoParte INTEGER NOT NULL," +
                    "allSegundos INTEGER NOT NULL," +
                    "HoraAtendimento TXT NOT NULL,origemTrasferencia TXT NOT NULL," +
                    "motivoOrigemTrasferencia TXT NOT NULL," +
                    "destinoTrasferencia TXT NOT NULL," +
                    "motivosDestinoTrasferencia TXT NOT NULL," +
                    "parturienteEmprocesso boolean NOT NULL," +
                    "idadeGestacional TXT NOT NULL, " +
                    "isDisparo boolean NOT NULL," +
                    "isEditDilatation boolean NOT NULL," +
                    "PRIMARY KEY(id AUTOINCREMENT ));",

            "CREATE TABLE Notificacao(id INTEGER NOT NULL UNIQUE," +
                    "idParturiente INTEGER NOT NULL UNIQUE," +
                    "hora INTEGER NOT NULL," +
                    "minuto INTEGER NOT NULL," +
                    "segundo INTEGER NOT NULL, " +
                    "allSegundos INTEGER NOT NULL, " +
                    "setYearDayMonthNotification TEXT NOT NULL,"+
                    "fullname TEXT NOT NULL," +
                    "HoraNotification TXT NOT NULL," +
                    "isOpen boolean NOT NULL," +
                    "inProcess boolean NOT NULL," +
                    "cor INTEGER NOT NULL, PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE UserDoctor(id INTEGER NOT NULL UNIQUE, username TEXT NOT NULL,fullname TEXT NOT NULL,tellDoctor TEXT NOT NULL, pass TEXT NOT NULL, PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE UserNurse(id INTEGER NOT NULL UNIQUE, username TEXT NOT NULL ,fullname TEXT NOT NULL ,tellNurse TEXT NOT NULL, pass TEXT NOT NULL, PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE HospitalSelect(id INTEGER NOT NULL UNIQUE, hospital TEXT NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE Hospitais(id INTEGER NOT NULL UNIQUE, hospital TEXT NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE OpcoesParidade(id INTEGER NOT NULL UNIQUE, valor TEXT NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE IdadeGestacional(id INTEGER NOT NULL UNIQUE, txtIdadeGestacional TEXT NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE Dilatacao(id INTEGER NOT NULL UNIQUE, dilatation TEXT NOT NULL, horas TEXT NOT NULL , minutes TEXT NOT NULL  , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE Alert(id INTEGER NOT NULL UNIQUE, horas TEXT NOT NULL , minutes TEXT NOT NULL  , PRIMARY KEY(id AUTOINCREMENT ));",

    };
    private int valorTimer;
    private int timerAlert;

    public DBService(@Nullable Context context) {
        super(context, nomeDB, null, versao);
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

    public long updadeDoctorUserAndPassword(int id, String pass) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put("username", username);
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
            c.close();
            return true;
        }
        c.close();
        return false;
    }

    //SELECT LOGIN
    public boolean isDoctorLogin(String username, String pass) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM UserDoctor WHERE username = ? AND pass = ?",
                new String[]{username, pass});
        c.moveToFirst();
        if (c.getCount()>0) {
            return true;
        }
        return false;
    }
    public String getFullNameDoctorLogin(String username, String pass) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM UserDoctor WHERE username = ? AND pass = ?",
                new String[]{username, pass});
        c.moveToFirst();
        if (c.getCount() == 1) {
            return c.getString(c.getColumnIndex("fullname"));
        }
        c.close();
        return "";
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
            c.close();
        }
        return listaUtilizadores;
    }

    @Override
    public




    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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

    public long updadeNurseUserAndPassword(int id, String pass) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
       // cv.put("username", username);
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
            c.close();
            return true;

        }
        c.close();
        return false;
    }

    //SELECT LOGIN
    public boolean isNurseLogin(String username, String pass) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM UserNurse WHERE username = ? AND pass = ?",
                new String[]{username, pass});
        c.moveToFirst();
        if (c.getCount() == 1) {
            c.close();
            return true;
        }
        c.close();
        return false;
    }

    public String getFullNameNurseLogin(String username, String pass) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM UserNurse WHERE username = ? AND pass = ?",
                new String[]{username, pass});
        c.moveToFirst();
        if (c.getCount() == 1) {
            return c.getString(c.getColumnIndex("fullname"));
        }
        return "";
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
        if (c.getCount()>0) {

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
            db.close();
        }
        return listaUtilizadores;
    }
//................................IdadeGestacional.............................//



    public long addIdadeGestacional(String idadeGestacional) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("txtIdadeGestacional", idadeGestacional);
        return db.insert("IdadeGestacional", null, cv);

    }
    public long updadeIdadeGestacional(int id, String idadeGestacional) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("txtIdadeGestacional", idadeGestacional);
        return db.update("IdadeGestacional", cv, "id=?", new String[]{String.valueOf(id)});
    }


    public List<IdadeGestacional> getListIdadeGestacional() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM IdadeGestacional", null);
        List<IdadeGestacional> listaIdadeGestacional = new ArrayList<>();
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                int id = c.getInt(c.getColumnIndex("id"));
                String txtIdadeGestacional = c.getString(c.getColumnIndex("txtIdadeGestacional"));
                listaIdadeGestacional.add(new IdadeGestacional(txtIdadeGestacional, id + ""));

            } while (c.moveToNext());
            db.close();
        }
        return listaIdadeGestacional;
    }

    public long deleteIdadeGestacional(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("IdadeGestacional", "id=?", new String[]{String.valueOf(id)});
    }

    //......................opcoes paridade...........................//

    public int getOpcoesParidadeMaximoValor() {
        String id="1";
        String valor;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM OpcoesParidade WHERE id = ?",
                new String[]{id});
        c.moveToFirst();
        valor=c.getString(c.getColumnIndex("valor"));

        return Integer.parseInt(valor);
    }

    public long addOpcoesParidade(String valor) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("valor", valor);
        return db.insert("OpcoesParidade", null, cv);

    }

    public long updadeOpcoesParidade(String valor) {
        int id=1;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("valor", valor);
        return db.update("OpcoesParidade", cv, "id=?", new String[]{String.valueOf(id)});
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
            c.close();
            return true;
        }
        c.close();
        return false;
    }
    public int getTimerSecAlertNotification() {
        int allSegundos=getHourasAlert()*3600+getMinutesAlert()*60;
        return allSegundos;
    }


    public int getTimerDilatation(String dilatation) {
        int horas = 0;
        int minutos=0;
        int secundos=0;

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
            c.close();
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
            c.close();
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
            c.close();
        }
        return arrayList;
    }
    //..........................HospitalSelect...................//

    public long addHospitalSelect(String hospital) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("hospital", hospital);

        return db.insert("HospitalSelect", null, cv);
    }

    public boolean isHospitalSelect() {
        String id="1";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM HospitalSelect WHERE id= ?",
                new String[]{id});
        c.moveToFirst();
        if (c.getCount()>0) {
            c.close();
            return true;
        }else {
            c.close();
            return false;
        }
    }

    public String getHospitalSelect() {
        String id="1";
        String nameHospital;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM HospitalSelect WHERE id = ?",
                new String[]{id});
        c.moveToFirst();
        nameHospital=c.getString(c.getColumnIndex("hospital"));
        c.close();
        return nameHospital;
    }

    /// ......................Alert......................................

    public long addAlertDilatation(int timerDilatationHours, int timerDilatationMinutes) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("horas", timerDilatationHours);
        cv.put("minutes", timerDilatationMinutes);
        return db.insert("Alert", null, cv);
    }
    public boolean isExistTimer(String minutes) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Alert WHERE minutes = ?",
                new String[]{minutes});
        c.moveToFirst();
        if (c.getCount() == 1) {
            return true;
        }
        c.close();
        return false;
    }

    public int getHourasAlert() {
        int timerDilatationHours=0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Alert", null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                timerDilatationHours = c.getInt(c.getColumnIndex("horas"));
            } while (c.moveToNext());
            c.close();
        }
        return timerDilatationHours;
    }

    public int getMinutesAlert() {
        int timerDilatationMinutes=0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Alert", null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                timerDilatationMinutes = c.getInt(c.getColumnIndex("minutes"));
            } while (c.moveToNext());
            c.close();
        }
        return timerDilatationMinutes;
    }

    public long updadeAlertDilatation(int id,int timerDilatationHours, int timerDilatationMinutes) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("horas", timerDilatationHours);
        cv.put("minutes", timerDilatationMinutes);
        return db.update("Alert", cv, "id=?", new String[]{String.valueOf(id)});
    }

    public int getTimerAlertEmergenceDilatation() {
        String dilatation ="1";
        int horas=0;
        int minutes=0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Alert WHERE id = ?",
                new String[]{dilatation});
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                horas = c.getInt(c.getColumnIndex("horas"));
                minutes = c.getInt(c.getColumnIndex("minutes"));
            } while (c.moveToNext());
            c.close();
        }
        return horas*3600+minutes*60;
    }

    public int getIdAlertDilatation() {
        String dilatation ="1";
        int id = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Alert WHERE id = ?",
                new String[]{dilatation});
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                id = c.getInt(c.getColumnIndex("id"));
            } while (c.moveToNext());
            c.close();
        }
        return id;
    }

    ///............................add parturiente ...........................////

    public long addParturiente(Parturient parturient) {
        addAuxParturiente(parturient);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("horaParte", parturient.getHoraParte());
        cv.put("idAuxParturiente ", parturient.getIdAuxParturiente());
        cv.put("minutoParte ", parturient.getMinutoParte());
        cv.put("segundoParte", parturient.getSegundoParte());
        cv.put("fullname ", parturient.getName());
        cv.put("allSegundos", parturient.getAllSegundos());
        cv.put("setYearDayMonthNotification", parturient.getSetYearDayMonthNotification());
        cv.put("dia", parturient.getDiaRegisto());
        cv.put("isEditDilatation", parturient.isEditDilatation());
        cv.put("isDisparo", parturient.isAtendimento());
        cv.put("apelido", parturient.getSurname());
        cv.put("idade", parturient.getAge());
        cv.put("isTrasferencia",parturient.isTransfered());
        cv.put("isTrasferenciaForaDaUnidade",parturient.isTrasferidoParaForaDoHospital());
        cv.put("HoraEntrada ",parturient.getHoraEntrada());
        cv.put("paridade",parturient.getPara());
        cv.put("HoraAtendimento ",parturient.getHoraAtendimento());
        cv.put("motivosDestinoTrasferencia",parturient.getMotivosDestinoDaTrasferencia());
        cv.put("motivoOrigemTrasferencia",parturient.getMotivosDaTrasferencia());
        cv.put("destinoTrasferencia",parturient.getDestinoTrasferencia());
        cv.put("origemTrasferencia",parturient.getOrigemTransferencia());
        cv.put("parturienteEmprocesso",parturient.isInProcess());
        cv.put("dilatacao ",parturient.getReason());
        cv.put("idadeGestacional ",parturient.getGestatinalRange());
        return db.insert("Parturientes", null, cv);
    }

    public long updadeCorIsDispareParturiente(boolean isDispare, int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("isDisparo",isDispare);
        return db.update("Parturientes", cv, "id=?", new String[]{String.valueOf(id)});
    }

    public long updadeCorIsDispareNotification(int cor, String id) {
        System.out.println(" =================== : "+cor+"  ======= +"+id);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("cor",cor);
        return db.update("Notificacao", cv, "idParturiente=?", new String[]{String.valueOf(id)});
    }







    public long addAuxParturiente(Parturient parturient) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("horaParte", parturient.getHoraParte());
        cv.put("minutoParte ", parturient.getMinutoParte());
        cv.put("setYearDayMonthNotification", parturient.getSetYearDayMonthNotification());
        cv.put("idAuxParturiente ", parturient.getIdAuxParturiente());
        cv.put("segundoParte", parturient.getSegundoParte());
        cv.put("fullname ", parturient.getName());
        cv.put("apelido", parturient.getSurname());
        cv.put("idade", parturient.getAge());
        cv.put("isTrasferencia",parturient.isTransfered());
        cv.put("isTrasferenciaForaDaUnidade",parturient.isTrasferidoParaForaDoHospital());
        cv.put("HoraEntrada ",parturient.getHoraEntrada());
        cv.put("paridade",parturient.getPara());
        cv.put("HoraAtendimento ",parturient.getHoraAtendimento());
        cv.put("motivosDestinoTrasferencia",parturient.getMotivosDestinoDaTrasferencia());
        cv.put("motivoOrigemTrasferencia",parturient.getMotivosDaTrasferencia());
        cv.put("destinoTrasferencia",parturient.getDestinoTrasferencia());
        cv.put("origemTrasferencia",parturient.getOrigemTransferencia());
        cv.put("parturienteEmprocesso",parturient.isInProcess());
        cv.put("dilatacao ",parturient.getReason());
        cv.put("idadeGestacional ",parturient.getGestatinalRange());
        return db.insert("AuxParturientes", null, cv);
    }

    public void updadeListParturiente() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Parturientes", null);
        c.moveToFirst();
        if (c.getCount()> 0) {
            do {
                Parturient parturient=new Parturient();
                parturient.setId(c.getInt(c.getColumnIndex("id")));
                parturient.setReason(c.getString(c.getColumnIndex("dilatacao")));
                parturient.setHoraEntrada(c.getString(c.getColumnIndex("HoraEntrada")));

                if(!c.getString(c.getColumnIndex("HoraAtendimento")).isEmpty()) {
                    parturient.setHoraAtendimento(c.getString(c.getColumnIndex("HoraAtendimento")));
                }

                parturient.setIdAuxParturiente(c.getString(c.getColumnIndex("setYearDayMonthNotification")));

                parturient.setIdAuxParturiente(c.getString(c.getColumnIndex("idAuxParturiente")));
                parturient.setHoraParte(c.getInt(c.getColumnIndex("horaParte")));
                parturient.setEditDilatation((c.getInt(c.getColumnIndex("isEditDilatation"))== 1)? true : false);
                parturient.setMinutoParte(c.getInt(c.getColumnIndex("minutoParte")));
                parturient.setSegundoParte(c.getInt(c.getColumnIndex("segundoParte")));
                parturient.setName(c.getString(c.getColumnIndex("fullname")));
                parturient.setSurname(c.getString(c.getColumnIndex("apelido")));
                parturient.setInProcess(((c.getInt(c.getColumnIndex("parturienteEmprocesso")))== 1)? true : false);
                parturient.setOrigemTransferencia(c.getString(c.getColumnIndex("origemTrasferencia")));
                parturient.setDestinoTrasferencia(c.getString(c.getColumnIndex("destinoTrasferencia")));
                parturient.setMotivosDestinoDaTrasferencia(c.getString(c.getColumnIndex("motivosDestinoTrasferencia")));
                parturient.setPara(c.getInt(c.getColumnIndex("paridade")));
                parturient.setAge(c.getInt(c.getColumnIndex("idade")));
                parturient.setAllSegundos(c.getInt(c.getColumnIndex("allSegundos")));
                parturient.setDiaRegisto(c.getInt(c.getColumnIndex("dia")));
                System.out.println(c.getInt(c.getColumnIndex("idade"))+" : patdttdt idade : "+parturient.getAge());
                parturient.setMotivosDaTrasferencia(c.getString(c.getColumnIndex("motivoOrigemTrasferencia")));
                parturient.setTrasferidoParaForaDoHospital((c.getInt(c.getColumnIndex("isTrasferenciaForaDaUnidade"))== 1)? true : false);
                parturient.setTransfered((c.getInt(c.getColumnIndex("isTrasferencia"))== 1)? true : false);


                if(isToday(parturient)){
                    if(isExistParturiente(parturient.getId())){
                        initializeCountDownTimer(parturient,getTimerDilatation(parturient.getReason()));
                        DBManager.getInstance().getParturients().add(parturient);
                        DBManager.getInstance().addAuxListNotificationParturient(parturient);
                    }
                }

            } while (c.moveToNext());
            c.close();
        }
    }


    public void initializeListParturiente() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Parturientes", null);
        DBManager.getInstance().getAuxlistNotificationParturients().removeAll( DBManager.getInstance().getAuxlistNotificationParturients());
        getListAuxParturiente();

        c.moveToFirst();
        if (c.getCount()> 0) {
            do {
                Parturient parturient=new Parturient();
                parturient.setId(c.getInt(c.getColumnIndex("id")));
                parturient.setReason(c.getString(c.getColumnIndex("dilatacao")));
                parturient.setHoraEntrada(c.getString(c.getColumnIndex("HoraEntrada")));

                if(!c.getString(c.getColumnIndex("HoraAtendimento")).isEmpty()) {
                    parturient.setHoraAtendimento(c.getString(c.getColumnIndex("HoraAtendimento")));
                }
                parturient.setEditDilatation((c.getInt(c.getColumnIndex("isEditDilatation"))== 1)? true : false);
                parturient.setIdAuxParturiente(c.getString(c.getColumnIndex("setYearDayMonthNotification")));
                parturient.setIdAuxParturiente(c.getString(c.getColumnIndex("idAuxParturiente")));
                parturient.setHoraParte(c.getInt(c.getColumnIndex("horaParte")));
                parturient.setMinutoParte(c.getInt(c.getColumnIndex("minutoParte")));
                parturient.setSegundoParte(c.getInt(c.getColumnIndex("segundoParte")));
                parturient.setName(c.getString(c.getColumnIndex("fullname")));
                parturient.setSurname(c.getString(c.getColumnIndex("apelido")));
                parturient.setInProcess(((c.getInt(c.getColumnIndex("parturienteEmprocesso")))== 1)? true : false);
                parturient.setOrigemTransferencia(c.getString(c.getColumnIndex("origemTrasferencia")));
                parturient.setDestinoTrasferencia(c.getString(c.getColumnIndex("destinoTrasferencia")));
                parturient.setMotivosDestinoDaTrasferencia(c.getString(c.getColumnIndex("motivosDestinoTrasferencia")));
                parturient.setPara(c.getInt(c.getColumnIndex("paridade")));
                parturient.setAge(c.getInt(c.getColumnIndex("idade")));
                parturient.setDiaRegisto(c.getInt(c.getColumnIndex("dia")));

                parturient.setGestatinalRange(c.getString(c.getColumnIndex("idadeGestacional")));
                System.out.println(c.getInt(c.getColumnIndex("idade"))+" : patdttdt idade : "+parturient.getAge());
                parturient.setMotivosDaTrasferencia(c.getString(c.getColumnIndex("motivoOrigemTrasferencia")));
                parturient.setTrasferidoParaForaDoHospital((c.getInt(c.getColumnIndex("isTrasferenciaForaDaUnidade"))== 1)? true : false);
                parturient.setTransfered((c.getInt(c.getColumnIndex("isTrasferencia"))== 1)? true : false);

                valorTimer = getValueUpdadeAndSaveTimer(parturient);
                if(isToday(parturient)){
                    if(valorTimer>0 ||!parturient.isAtendimento() ){
                        System.out.println("**************** : "+valorTimer);
                        if(valorTimer>0){
                            initializeCountDownTimer(parturient,valorTimer);
                        }else {
                            if(isInProcessParturiente(parturient)){
                                parturient.setTempoRes("Parturiente Em Processo");
                            }else {
                                parturient.setTempoRes("Alerta Disparado");
                            }

                        }
                        if(isExistParturiente(parturient.getId())){
                            DBManager.getInstance().getParturients().add(parturient);
                            DBManager.getInstance().addAuxListNotificationParturient(parturient);
                        }

                    }else {
                        System.out.println(" nome : "+parturient.getName());
                        parturient.setAtendimento(true);
                        updadeCorIsDispareParturiente(true,parturient.getId());
                        //deleteParturiente(parturient.getId());
                    }

                }

            } while (c.moveToNext());
            c.close();
        }
    }


    public ArrayList<Parturient> getListAuxParturiente() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM AuxParturientes", null);
        List<Parturient> arrayList = new ArrayList<>();

        c.moveToFirst();
        if (c.getCount()> 0) {
            do {
                Parturient parturient=new Parturient();
                parturient.setId(c.getInt(c.getColumnIndex("id")));

                parturient.setReason(c.getString(c.getColumnIndex("dilatacao")));
                parturient.setHoraEntrada(c.getString(c.getColumnIndex("HoraEntrada")));
                parturient.setGestatinalRange(c.getString(c.getColumnIndex("idadeGestacional")));

                if(!c.getString(c.getColumnIndex("HoraAtendimento")).isEmpty()) {
                    parturient.setHoraAtendimento(c.getString(c.getColumnIndex("HoraAtendimento")));
                }
                parturient.setIdAuxParturiente(c.getString(c.getColumnIndex("setYearDayMonthNotification")));
                parturient.setIdAuxParturiente(c.getString(c.getColumnIndex("idAuxParturiente")));
                parturient.setHoraParte(c.getInt(c.getColumnIndex("horaParte")));
                parturient.setMinutoParte(c.getInt(c.getColumnIndex("minutoParte")));
                parturient.setSegundoParte(c.getInt(c.getColumnIndex("segundoParte")));
                parturient.setName(c.getString(c.getColumnIndex("fullname")));
                parturient.setSurname(c.getString(c.getColumnIndex("apelido")));
                parturient.setInProcess(((c.getInt(c.getColumnIndex("parturienteEmprocesso")))== 1)? true : false);
                parturient.setOrigemTransferencia(c.getString(c.getColumnIndex("origemTrasferencia")));
                parturient.setDestinoTrasferencia(c.getString(c.getColumnIndex("destinoTrasferencia")));
                parturient.setMotivosDestinoDaTrasferencia(c.getString(c.getColumnIndex("motivosDestinoTrasferencia")));
                parturient.setPara(c.getInt(c.getColumnIndex("paridade")));
                parturient.setAge(c.getInt(c.getColumnIndex("idade")));
                parturient.setMotivosDaTrasferencia(c.getString(c.getColumnIndex("motivoOrigemTrasferencia")));
                parturient.setTrasferidoParaForaDoHospital((c.getInt(c.getColumnIndex("isTrasferenciaForaDaUnidade"))== 1)? true : false);
                parturient.setTransfered((c.getInt(c.getColumnIndex("isTrasferencia"))== 1)? true : false);
                DBManager.getInstance().getAuxlistNotificationParturients().add(parturient);
                arrayList.add(parturient);

            } while (c.moveToNext());

        }
        return (ArrayList<Parturient>) arrayList;
    }



    public void initializeListParturientesAtendidos() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Atendidos", null);
        List<Parturient> arrayList = new ArrayList<>();

        c.moveToFirst();
        if (c.getCount()> 0) {
            do {
                Parturient parturient=new Parturient();
                parturient.setId(c.getInt(c.getColumnIndex("id")));

                parturient.setReason(c.getString(c.getColumnIndex("dilatacao")));
                parturient.setHoraEntrada(c.getString(c.getColumnIndex("HoraEntrada")));
                parturient.setGestatinalRange(c.getString(c.getColumnIndex("idadeGestacional")));

                if(!c.getString(c.getColumnIndex("HoraAtendimento")).isEmpty()) {
                    parturient.setHoraAtendimento(c.getString(c.getColumnIndex("HoraAtendimento")));
                }

                parturient.setTipoAtendimento(c.getString(c.getColumnIndex("tipoAtendimento")));
                parturient.setIdAuxParturiente(c.getString(c.getColumnIndex("setYearDayMonthNotification")));
                parturient.setIdAuxParturiente(c.getString(c.getColumnIndex("idAuxParturiente")));
                parturient.setHoraParte(c.getInt(c.getColumnIndex("horaParte")));
                parturient.setMinutoParte(c.getInt(c.getColumnIndex("minutoParte")));
                parturient.setSegundoParte(c.getInt(c.getColumnIndex("segundoParte")));
                parturient.setName(c.getString(c.getColumnIndex("fullname")));
                parturient.setSurname(c.getString(c.getColumnIndex("apelido")));
                parturient.setInProcess(((c.getInt(c.getColumnIndex("parturienteEmprocesso")))== 1)? true : false);
                parturient.setOrigemTransferencia(c.getString(c.getColumnIndex("origemTrasferencia")));
                parturient.setDestinoTrasferencia(c.getString(c.getColumnIndex("destinoTrasferencia")));
                parturient.setMotivosDestinoDaTrasferencia(c.getString(c.getColumnIndex("motivosDestinoTrasferencia")));
                parturient.setPara(c.getInt(c.getColumnIndex("paridade")));
                parturient.setAge(c.getInt(c.getColumnIndex("idade")));
                parturient.setMotivosDaTrasferencia(c.getString(c.getColumnIndex("motivoOrigemTrasferencia")));
                parturient.setTrasferidoParaForaDoHospital((c.getInt(c.getColumnIndex("isTrasferenciaForaDaUnidade"))== 1)? true : false);
                parturient.setTransfered((c.getInt(c.getColumnIndex("isTrasferencia"))== 1)? true : false);

                if(!isContem(parturient)){
                    DBManager.getInstance().getListParturientesAtendidos().add(parturient);
                }


            } while (c.moveToNext());

        }
    }

    private boolean isContem(Parturient parturient) {
         for(Parturient parturient1: DBManager.getInstance().getListParturientesAtendidos()){
             if(parturient.getIdAuxParturiente().equals(parturient1.getIdAuxParturiente())){
                 return false;
             }else {
                 return true;
             }
         }
         return false;
    }


    public long deleteParturiente(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("Parturientes", "id=?", new String[]{String.valueOf(id)});

    }

    public long deleteNotification(Notification notification) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("Notificacao", "idParturiente=?", new String[]{String.valueOf(notification.getIdAuxParturiente())});
    }

    //....................NOTIFICACAO.................................
    public long addNotificacao(Notification notification) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fullname", notification.getMessage());
        cv.put("setYearDayMonthNotification", notification.yearDayMonthNotification);
        cv.put("idParturiente", notification.getIdAuxParturiente());
        cv.put("HoraNotification",format(new Date()));
        cv.put("hora",notification.getHoras());
        cv.put("minuto",notification.getMinutos());
        cv.put("segundo",notification.getSegundo());
        cv.put("allSegundos",notification.getAllSegundos());
        cv.put("isOpen", notification.isOpen());
        cv.put("cor",notification.getColour());
        cv.put("inProcess",notification.isInProcess());
        return db.insert("Notificacao", null, cv);
    }


    public long updadeCorNotification(String idParturiente, int cor) {
        int id=getIdNotification(idParturiente);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put("username", username);
        cv.put("cor", cor);
        return db.update("Notificacao", cv, "id=?", new String[]{String.valueOf(id)});
    }

    public long updadeAllDadeAuxParturiente(Parturient parturient) {
        int id=parturient.getId();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("horaParte", parturient.getHoraParte());
        cv.put("minutoParte ", parturient.getMinutoParte());
        cv.put("idAuxParturiente ", parturient.getIdAuxParturiente());
        cv.put("segundoParte", parturient.getSegundoParte());
        cv.put("fullname ", parturient.getName());
        cv.put("apelido", parturient.getSurname());
        cv.put("idade", parturient.getAge());
        cv.put("isTrasferencia",parturient.isTransfered());
        cv.put("isTrasferenciaForaDaUnidade",parturient.isTrasferidoParaForaDoHospital());
        cv.put("HoraEntrada ",parturient.getHoraEntrada());
        cv.put("paridade",parturient.getPara());
        cv.put("HoraAtendimento ",parturient.getHoraAtendimento());
        cv.put("motivosDestinoTrasferencia",parturient.getMotivosDestinoDaTrasferencia());
        cv.put("motivoOrigemTrasferencia",parturient.getMotivosDaTrasferencia());
        cv.put("destinoTrasferencia",parturient.getDestinoTrasferencia());
        cv.put("origemTrasferencia",parturient.getOrigemTransferencia());
        cv.put("parturienteEmprocesso",parturient.isInProcess());
        cv.put("dilatacao ",parturient.getReason());
        cv.put("idadeGestacional ",parturient.getGestatinalRange());
        return db.update("AuxParturientes", cv, "id=?", new String[]{String.valueOf(id)});
    }


    public long updadeAllDadeParturiente(Parturient parturient) {
        int id=parturient.getId();
        updadeAllDadeAuxParturiente(parturient);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("horaParte", parturient.getHoraParte());
        cv.put("idAuxParturiente ", parturient.getIdAuxParturiente());
        cv.put("minutoParte ", parturient.getMinutoParte());
        cv.put("segundoParte", parturient.getSegundoParte());
        cv.put("fullname ", parturient.getName());
        cv.put("dia", parturient.getDiaRegisto());
        cv.put("isEditDilatation", parturient.isEditDilatation());
        cv.put("isDisparo", parturient.isAtendimento());
        cv.put("apelido", parturient.getSurname());
        cv.put("idade", parturient.getAge());
        cv.put("isTrasferencia",parturient.isTransfered());
        cv.put("isTrasferenciaForaDaUnidade",parturient.isTrasferidoParaForaDoHospital());
        cv.put("HoraEntrada ",parturient.getHoraEntrada());
        cv.put("paridade",parturient.getPara());
        cv.put("HoraAtendimento ",parturient.getHoraAtendimento());
        cv.put("motivosDestinoTrasferencia",parturient.getMotivosDestinoDaTrasferencia());
        cv.put("motivoOrigemTrasferencia",parturient.getMotivosDaTrasferencia());
        cv.put("destinoTrasferencia",parturient.getDestinoTrasferencia());
        cv.put("origemTrasferencia",parturient.getOrigemTransferencia());
        cv.put("parturienteEmprocesso",parturient.isInProcess());
        cv.put("dilatacao ",parturient.getReason());
        cv.put("idadeGestacional ",parturient.getGestatinalRange());

        return db.update("Parturientes", cv, "id=?", new String[]{String.valueOf(id)});
    }

    public int getIdNotification( String idParturiente) {
        int id=0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Notification WHERE idParturiente = ?",
                new String[]{idParturiente});
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                id = c.getInt(c.getColumnIndex("id"));
            } while (c.moveToNext());
            c.close();
        }
        return id;
    }


    public void initializeListNotification() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Notificacao", null);
        c.moveToFirst();
        if (c.getCount()> 0) {
            do {
                if(!isContenId(c.getInt(c.getColumnIndex("id")))) {
                    Notification notification = new Notification();
                    notification.setYearDayMonthNotification(c.getString(c.getColumnIndex("setYearDayMonthNotification")));
                    notification.setHoras(c.getInt(c.getColumnIndex("hora")));
                    notification.setMinutos(c.getInt(c.getColumnIndex("minuto")));
                    notification.setSegundo(c.getInt(c.getColumnIndex("segundo")));
                    notification.setIdAuxParturiente(c.getString(c.getColumnIndex("idParturiente")));
                    notification.setId(c.getInt(c.getColumnIndex("id"))+"");
                    notification.setInProcess(((c.getInt(c.getColumnIndex("inProcess"))) == 1) ? true : false);
                    notification.setNome(c.getString(c.getColumnIndex("fullname")));
                    notification.setOpen(((c.getInt(c.getColumnIndex("isOpen"))) == 1) ? true : false);
                    notification.setTime(c.getString(c.getColumnIndex("HoraNotification")));
                    notification.setColour(c.getInt(c.getColumnIndex("cor")));

                    if(isTodayNotification(notification)){

                       int valorCount=getValueUpdadeAndSaveTimerInNotification(notification);
                        if(valorCount>0){
                            alertaEmergence(notification,valorCount);
                            DBManager.getInstance().addNewNotification(notification);
                        }else{
                            DBManager.getInstance().addNewNotification(notification);
                        }
                    }
}
            } while (c.moveToNext());

        }
    }



    //.................ATENDIMENTO...............................
    public long addAtendimento(Parturient parturient) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("horaParte", parturient.getHoraParte());
        cv.put("idAuxParturiente ", parturient.getIdAuxParturiente());
        cv.put("minutoParte ", parturient.getMinutoParte());
        cv.put("segundoParte", parturient.getSegundoParte());
        cv.put("tipoAtendimento", parturient.getTipoAtendimento());
        cv.put("fullname ", parturient.getName());
        cv.put("setYearDayMonthNotification", parturient.getSetYearDayMonthNotification());
        cv.put("apelido", parturient.getSurname());
        cv.put("idade", parturient.getAge());
        cv.put("isTrasferencia",parturient.isTransfered());
        cv.put("isTrasferenciaForaDaUnidade",parturient.isTrasferidoParaForaDoHospital());
        cv.put("HoraEntrada ",parturient.getHoraEntrada());
        cv.put("paridade",parturient.getPara());
        cv.put("HoraAtendimento ",parturient.getHoraAtendimento());
        cv.put("motivosDestinoTrasferencia",parturient.getMotivosDestinoDaTrasferencia());
        cv.put("motivoOrigemTrasferencia",parturient.getMotivosDaTrasferencia());
        cv.put("destinoTrasferencia",parturient.getDestinoTrasferencia());
        cv.put("origemTrasferencia",parturient.getOrigemTransferencia());
        cv.put("parturienteEmprocesso",parturient.isInProcess());
        cv.put("dilatacao ",parturient.getReason());
        cv.put("idadeGestacional ",parturient.getGestatinalRange());
        return db.insert("Atendidos", null, cv);
    }

    ///.............................ccc..................................///
    private String formatMinuto(Date date){
        DateFormat dateFormat = new SimpleDateFormat("mm");
        return dateFormat.format(date);
    }
    private String formatSegundo(Date date){
        DateFormat dateFormat = new SimpleDateFormat("ss");
        return dateFormat.format(date);
    }

    private String formatHoras(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh");
        return dateFormat.format(date);
    }
    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(date);
    }



    public void removeInBD(Parturient parturient) {
        deleteParturiente(parturient.getId());
    }

    public void removParturiente(Parturient prt){
        for(Parturient parturient: DBManager.getInstance().getParturients()){
            if(parturient.getId()==prt.getId()){
                DBManager.getInstance().getParturients().remove(parturient);
                break;
            }
        }
        AddParturientActivity addParturientActivity =new AddParturientActivity();
       addParturientActivity.isFireAlert=true;

    }
    private void sendMensageEmergence(Notification notification){

        notification.setColour(Color.rgb(248, 215,218));
        updadeCorIsDispareNotification(Color.rgb(248, 215,218),notification.getIdAuxParturiente());
        String mensagem=notification.getMessage() +": Necessita  de cuidados medicos";
        System.out.println(mensagem);

        if(!notification.isInProcess()) {
            for (UserDoctor userDoctor : getListDoctor()) {
                sendSMS(userDoctor.getContacto(), mensagem);
            }
        }
    }

    void sendNotification(Parturient parturient){
        notification = new Notification();
        notification.setColour(Color.YELLOW+Color.BLACK);
        notification.setNome(parturient.getName()+" "+parturient.getSurname());
        notification.setIdAuxParturiente(parturient.getIdAuxParturiente());
        notification.setTime(format(new Date()));
        notification.setOpen(true);
        String auxData=new Date().getYear()+""+new Date().getMonth()+""+new Date().getDay();
        notification.setYearDayMonthNotification(auxData);
        notification.setId(parturient.getId()+"");
       // System.out.println("  yyyyyyyyyyyyyyyyyyyyyyyy= : "+parturient.getId());
        notification.setInProcess(false);
        alertaEmergence(notification,getTimerAlertEmergenceDilatation());
        DBManager.getInstance().getNotifications().add(notification);
        addNotificacao(notification);
        //updadeListNotification();
        System.out.println(" ======================"+parturient.getName()+"========================= ");
    }

    private void sendSMS(String phoneNumber, String message) {
        phoneNumber = phoneNumber.trim();
        message = message.trim();
        System.out.println(message);
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        } catch (Exception e) {
            Log.i("EXPECTION SMS", e.getMessage());
        }
    }


    private boolean isToday(Parturient parturient) {
             String auxData=new Date().getYear()+""+new Date().getMonth()+""+new Date().getDay();
            if(parturient.getSetYearDayMonthNotification().equals(auxData)){
                return false;
            }else{
                return true;
            }
    }
    private int getTempoRestanteEmergenceAlert(Notification notification) {
        int segundosHoraAtual=Integer.parseInt(formatHoras(new Date()))*3600+Integer.parseInt(formatMinuto(new Date()))*60+Integer.parseInt(formatSegundo(new Date()));
        int segundosHoraEntrada=notification.getHoras()*3600+notification.getMinutos()*60+notification.getSegundo();
        int sub=segundosHoraAtual-segundosHoraEntrada;

        System.out.println(" ==============ID================[ "+notification.getId());
        System.out.println(segundosHoraAtual+" ] =====  ["+segundosHoraEntrada);
        System.out.println(" SUBTRACAO  hAtual e entrada: "+sub);
        System.out.println(getTimerSecAlertNotification()+" Sss : "+sub);

        int tempoRestante= getTimerSecAlertNotification()-sub;
        System.out.println(" SUBTRACAO TOTAL : "+tempoRestante);
        return tempoRestante;
    }

    private int getTempoRestante(Parturient parturient) {
        int segundosHoraAtual=Integer.parseInt(formatHoras(new Date()))*3600+Integer.parseInt(formatMinuto(new Date()))*60+Integer.parseInt(formatSegundo(new Date()));
        int segundosHoraEntrada=parturient.getHoraParte()*3600+parturient.getMinutoParte()*60+parturient.getSegundoParte();
        int sub=segundosHoraAtual-segundosHoraEntrada;
        int tempoRestante=getTimerDilatation(parturient.getReason())-sub;
        return tempoRestante;
    }


    private boolean isExistParturiente(int id) {

        for(Parturient parturient: DBManager.getInstance().getParturients()){
            if(parturient.getId()==id){
                return false;
            }
        }
        return true;
    }
    private boolean isTodayNotification(Notification notification) {
        String auxData=new Date().getYear()+""+new Date().getMonth()+""+new Date().getDay();
        if(notification.getYearDayMonthNotification().equals(auxData)){
            return true;
        }else{
            return false;
        }
    }


    private boolean isContenId(int id) {
        for (Notification notification: DBManager.getInstance().getNotifications()){
            if(Integer.parseInt(notification.getId())==id){
                return true;
            }
        }
        return false;
    }


//    public long updadeInProcessParturiente(boolean isInProcess, int id) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("parturienteEmprocesso",isInProcess);
//        return db.update("Parturientes", cv, "id=?", new String[]{String.valueOf(id)});
//    }

    public long updadeAndSaveTimer(Parturient parturient, int segundos){
         SQLiteDatabase db = getWritableDatabase();
         ContentValues cv = new ContentValues();
         cv.put("horaParte",formatHoras(new Date()));
         cv.put("minutoParte",formatMinuto(new Date()));
         cv.put("segundoParte",formatSegundo(new Date()));
         cv.put("allSegundos",segundos);
         return db.update("Parturientes", cv, "idAuxParturiente=?", new String[]{String.valueOf(parturient.getIdAuxParturiente())});
     }


    public int getValueUpdadeAndSaveTimer(Parturient parturient){
        int horas = 0;
        int minutos=0;
        int segundos=0;
        int timerSave = 0;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Parturientes WHERE idAuxParturiente = ?",
                new String[]{parturient.getIdAuxParturiente()});
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                horas= c.getInt(c.getColumnIndex("horaParte"));
                minutos= c.getInt(c.getColumnIndex("minutoParte"));
                segundos= c.getInt(c.getColumnIndex("segundoParte"));
                timerSave=c.getInt(c.getColumnIndex("allSegundos"));
            } while (c.moveToNext());
            c.close();
        }

        int allTimerSave=horas*3600+minutos*60+segundos;
        int allCurenteTimer=Integer.parseInt(formatHoras(new Date()))*3600+
                Integer.parseInt(formatMinuto(new Date()))*60+
                Integer.parseInt(formatSegundo(new Date()));

        int totalTimer=allCurenteTimer-allTimerSave;

        return timerSave-totalTimer;
    }


    public void initializeCountDownTimer(Parturient parturient,int seconds) {


        new CountDownTimer(seconds*1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);
                int totalTimer=hours*3600+minutes*60+seconds;
                updadeAndSaveTimer(parturient,totalTimer);
                if(isVerificationEditDilatation(parturient)){
                    cancel();
                    initializeCountDownTimer(parturient,getTimerDilatation(getDilatationParturiente(parturient)));
                }
                if(isInProcessParturiente(parturient)){
                    parturient.setTempoRes("Em processo de parto...");
                    cancel();
                }else{
                    if(parturient.isAtendido()){
                        cancel();
                    }else {
                        parturient.setTempoRes("Tempo Restante : [ " + String.format("%02d", hours)
                                + ":" + String.format("%02d", minutes)
                                + ":" + String.format("%02d", seconds)+" ]");
                    }
                }
            }

            public void onFinish() {
                AddParturientActivity addParturientActivity=new AddParturientActivity();
                addParturientActivity.idParturienteNotification=parturient.getIdAuxParturiente();
                addParturientActivity.alertFireNotification=true;
                //System.out.println(" o valor total do parturiente eh : "+ mainActivity.getFunctionPreferenceSaveTimer(parturient));
                parturient.setTempoRes("Alerta Disparado");
                addParturientActivity.isFireAlert=true;
                sendNotification(parturient);

                // System.out.println("==================: "+dbService.getTimerAlertEmergenceDilatation());
            }
        }.start();

    }



   private boolean isVerificationEditDilatation(Parturient parturient){
        boolean isEdit=false;
       SQLiteDatabase db = getReadableDatabase();
       Cursor c = db.rawQuery("SELECT * FROM Parturientes WHERE idAuxParturiente = ?",
               new String[]{parturient.getIdAuxParturiente()});
       c.moveToFirst();
       if (c.getCount() > 0) {
           do {
               isEdit= ((c.getInt(c.getColumnIndex("isEditDilatation"))== 1)? true : false);
           } while (c.moveToNext());
           c.close();
       }
       updadeIsEditDilatation(parturient.getIdAuxParturiente());
       return isEdit;

   }
    public long updadeIsEditDilatation(String id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("isEditDilatation", false);
        return db.update("Parturientes", cv, "idAuxParturiente=?", new String[]{String.valueOf(id)});
    }

    String getDilatationParturiente(Parturient parturient){
        String dilatation = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Parturientes WHERE idAuxParturiente = ?",
                new String[]{parturient.getIdAuxParturiente()});
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                dilatation= c.getString(c.getColumnIndex("dilatacao"));
            } while (c.moveToNext());
            c.close();
        }
        return dilatation;
    }



    //// notification ///
    public long updadeAndSaveTimerInNotification(Notification notification, int segundos){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("hora",formatHoras(new Date()));
        cv.put("minuto",formatMinuto(new Date()));
        cv.put("segundo",formatSegundo(new Date()));
        cv.put("allSegundos",segundos);
        return db.update("Notificacao", cv, "idParturiente=?", new String[]{String.valueOf(notification.getIdAuxParturiente())});
    }


    public int getValueUpdadeAndSaveTimerInNotification(Notification notification){
        int horas = 0;
        int minutos=0;
        int segundos=0;
        int timerSave = 0;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Notificacao WHERE idParturiente = ?",
                new String[]{notification.getIdAuxParturiente()});
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                horas= c.getInt(c.getColumnIndex("hora"));
                minutos= c.getInt(c.getColumnIndex("minuto"));
                segundos= c.getInt(c.getColumnIndex("segundo"));
                timerSave=c.getInt(c.getColumnIndex("allSegundos"));
            } while (c.moveToNext());
            c.close();
        }

        int allTimerSave=horas*3600+minutos*60+segundos;
        int allCurenteTimer=Integer.parseInt(formatHoras(new Date()))*3600+
                Integer.parseInt(formatMinuto(new Date()))*60+
                Integer.parseInt(formatSegundo(new Date()));

        int totalTimer=allCurenteTimer-allTimerSave;

        return timerSave-totalTimer;
    }


    public long updadeInProcessParturiente(Parturient parturient) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("parturienteEmprocesso",true);
        return db.update("Parturientes", cv, "idAuxParturiente=?", new String[]{String.valueOf(parturient.getIdAuxParturiente())});
    }

    public long updadeInProcessNotification(Notification notification) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("inProcess",true);
        return db.update("Notificacao", cv, "idParturiente=?", new String[]{String.valueOf(notification.getIdAuxParturiente())});
    }

    public boolean isInProcessParturiente(Parturient parturient){
        boolean isInprocess=false;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Parturientes WHERE idAuxParturiente = ?",
                new String[]{parturient.getIdAuxParturiente()});
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                isInprocess=((c.getInt(c.getColumnIndex("parturienteEmprocesso"))== 1)? true : false);
            } while (c.moveToNext());
            c.close();
        }
        System.out.println(" ===========x======x===========  "+ isInprocess);
        return isInprocess;
    }

    public void alertaEmergence(Notification notification,int seconds) {


        new CountDownTimer(seconds * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);
                int allTimer=hours*3600+minutes*60+seconds;
                updadeAndSaveTimerInNotification(notification,allTimer);
                System.out.println(getValueUpdadeAndSaveTimerInNotification( notification));
                if(notification.isAtendido()){
                    cancel();
                }else {
                    if(notification.isInProcess()){
                        notification.setViewTimerTwo(" Em processo de parto ...");
                    }else{
                        notification.setViewTimerTwo(" [" + String.format("%02d", hours)
                                + ":" + String.format("%02d", minutes)
                                + ":" + String.format("%02d", seconds)+"]");
                    }
                }

            }

            public void onFinish() {

                notification.setViewTimerTwo(" Alerta Desparado");
                AddParturientActivity addParturientActivity =new AddParturientActivity();
                addParturientActivity.isFireAlert=true;

                for(Parturient parturient: DBManager.getInstance().getAuxlistNotificationParturients()){
                    if(parturient.getIdAuxParturiente().equals(notification.getIdAuxParturiente())){
                        removParturiente(parturient);
                        removeInBD(parturient);
                    }
                }

                sendMensageEmergence(notification);
            }
        }.start();
    }

}


