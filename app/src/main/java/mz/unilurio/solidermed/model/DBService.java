package mz.unilurio.solidermed.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mz.unilurio.solidermed.AddParturientActivity;
import mz.unilurio.solidermed.MainActivity;

public class DBService  extends SQLiteOpenHelper {
    public  boolean isNotificationAlerte;
    private Notificacao notificacao;
    private static String nomeDB = "BDHospital.db";
    private static int versao = 1;

    private String[] sql = {
            // "CREATE TABLE AllacessUser(id INTEGER NOT NULL UNIQUE, acess boolean NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE AuxParturientes(id INTEGER NOT NULL UNIQUE, " +
                    "name TEXT NOT NULL," +
                    "apelido TEXT NOT NULL," +
                    "isAtendido boolean NOT NULL," +
                    "tipoAtendimento TEXT NOT NULL," +
                    "idade TEXT NOT NULL," +
                    "sinaisDePatologia TEXT NOT NULL, " +
                    "horaExpulsoDoFeto TEXT NOT NULL, " +
                    "idAuxParturiente TEXT NOT NULL," +
                    "dilatacao TEXT NOT NULL," +
                    "paridade TEXT NOT NULL," +
                    "isTrasferido boolean NOT NULL," +
                    "isTrasferenciaForaDaUnidade boolean NOT NULL," +
                    "HoraEntrada TEXT NOT NULL, " +
                    "HoraAtendimento TEXT NOT NULL," +
                    "origemTrasferencia TEXT NOT NULL," +
                    "motivoOrigemTrasferencia TEXT NOT NULL," +
                    "destinoTrasferencia TEXT NOT NULL," +
                    "motivosDestinoTrasferencia TEXT NOT NULL," +
                    "parturienteEmprocesso boolean NOT NULL," +
                    "idadeGestacional TEXT NOT NULL, " +
                    "isDisparo boolean NOT NULL," +
                    "isEditDilatation boolean NOT NULL," +
                    "PRIMARY KEY(id AUTOINCREMENT ));",

            "CREATE TABLE Atendidos(id INTEGER NOT NULL UNIQUE," +
                    "name TEXT NOT NULL," +
                    "apelido TEXT NOT NULL," +
                    "isAtendido boolean NOT NULL," +
                    "tipoAtendimento TEXT NOT NULL," +
                    "idade TEXT NOT NULL," +
                    "sinaisDePatologia TEXT NOT NULL, " +
                    "horaExpulsoDoFeto TEXT NOT NULL, " +
                    "idAuxParturiente TEXT NOT NULL," +
                    "dilatacao TEXT NOT NULL," +
                    "paridade TEXT NOT NULL," +
                    "isTrasferido boolean NOT NULL," +
                    "isTrasferenciaForaDaUnidade boolean NOT NULL," +
                    "HoraEntrada TEXT NOT NULL, " +
                    "HoraAtendimento TEXT NOT NULL," +
                    "origemTrasferencia TEXT NOT NULL," +
                    "motivoOrigemTrasferencia TEXT NOT NULL," +
                    "destinoTrasferencia TEXT NOT NULL," +
                    "motivosDestinoTrasferencia TEXT NOT NULL," +
                    "parturienteEmprocesso boolean NOT NULL," +
                    "idadeGestacional TEXT NOT NULL, " +
                    "isDisparo boolean NOT NULL," +
                    "isEditDilatation boolean NOT NULL," +
                    "PRIMARY KEY(id AUTOINCREMENT ));",

            "CREATE TABLE Transferidos(id INTEGER NOT NULL UNIQUE," +
                    "name TEXT NOT NULL," +
                    "apelido TEXT NOT NULL," +
                    "isAtendido boolean NOT NULL," +
                    "tipoAtendimento TEXT NOT NULL," +
                    "idade TEXT NOT NULL," +
                    "sinaisDePatologia TEXT NOT NULL, " +
                    "horaExpulsoDoFeto TEXT NOT NULL, " +
                    "idAuxParturiente TEXT NOT NULL," +
                    "dilatacao TEXT NOT NULL," +
                    "paridade TEXT NOT NULL," +
                    "isTrasferido boolean NOT NULL," +
                    "isTrasferenciaForaDaUnidade boolean NOT NULL," +
                    "HoraEntrada TEXT NOT NULL, " +
                    "HoraAtendimento TEXT NOT NULL," +
                    "origemTrasferencia TEXT NOT NULL," +
                    "motivoOrigemTrasferencia TEXT NOT NULL," +
                    "destinoTrasferencia TEXT NOT NULL," +
                    "motivosDestinoTrasferencia TEXT NOT NULL," +
                    "parturienteEmprocesso boolean NOT NULL," +
                    "idadeGestacional TEXT NOT NULL, " +
                    "isDisparo boolean NOT NULL," +
                    "isEditDilatation boolean NOT NULL," +
                    "PRIMARY KEY(id AUTOINCREMENT ));",


            "CREATE TABLE Parturientes(id INTEGER NOT NULL UNIQUE," +

                    "name TEXT NOT NULL," +
                    "apelido TEXT NOT NULL," +
                    "isAtendido boolean NOT NULL," +
                    "tipoAtendimento TEXT NOT NULL," +
                    "idade TEXT NOT NULL," +
                    "sinaisDePatologia TEXT NOT NULL, " +
                    "horaExpulsoDoFeto TEXT NOT NULL, " +
                    "idAuxParturiente TEXT NOT NULL," +
                    "dilatacao TEXT NOT NULL," +
                    "paridade TEXT NOT NULL," +
                    "isTrasferido boolean NOT NULL," +
                    "isTrasferenciaForaDaUnidade boolean NOT NULL," +
                    "HoraEntrada TEXT NOT NULL, " +
                    "HoraAtendimento TEXT NOT NULL," +
                    "origemTrasferencia TEXT NOT NULL," +
                    "motivoOrigemTrasferencia TEXT NOT NULL," +
                    "destinoTrasferencia TEXT NOT NULL," +
                    "motivosDestinoTrasferencia TEXT NOT NULL," +
                    "parturienteEmprocesso boolean NOT NULL," +
                    "idadeGestacional TEXT NOT NULL, " +
                    "isDisparo boolean NOT NULL," +
                    "isEditDilatation boolean NOT NULL," +
                    "PRIMARY KEY(id AUTOINCREMENT ));",

            "CREATE TABLE Notificacao(id INTEGER NOT NULL UNIQUE," +
                    "horaDoEnvioDaMensagem TEXT NOT NULL," +
                    "idParturiente TEXT NOT NULL ," +
                    "fullname TEXT NOT NULL," +
                    "HoraNotification TEXT NOT NULL," +
                    "isOpen boolean NOT NULL," +
                    "inProcess boolean NOT NULL," +
                    "cor INTEGER NOT NULL, PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE privilegiosDoctor(id INTEGER NOT NULL UNIQUE, acessoTotal boolean NOT NULL, PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE UserDoctor(id INTEGER NOT NULL UNIQUE, username TEXT NOT NULL,fullname TEXT NOT NULL,tellDoctor TEXT NOT NULL, pass TEXT NOT NULL, PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE UserNurse(id INTEGER NOT NULL UNIQUE, username TEXT NOT NULL ,fullname TEXT NOT NULL ,tellNurse TEXT NOT NULL, pass TEXT NOT NULL, PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE HospitalSelect(id INTEGER NOT NULL UNIQUE, hospital TEXT NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE Hospitais(id INTEGER NOT NULL UNIQUE, hospital TEXT NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE OpcoesParidade(id INTEGER NOT NULL UNIQUE, valor TEXT NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE IdadeGestacional(id INTEGER NOT NULL UNIQUE, txtIdadeGestacional TEXT NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE Dilatacao(id INTEGER NOT NULL UNIQUE, dilatation TEXT NOT NULL, horas TEXT NOT NULL , minutes TEXT NOT NULL  , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE Alert(id INTEGER NOT NULL UNIQUE, horas TEXT NOT NULL , minutes TEXT NOT NULL  , PRIMARY KEY(id AUTOINCREMENT ));",

    };
    private int tempoRestante;

    public DBService(@Nullable Context context) {
        super(context, nomeDB, null, versao);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int i = 0; i < sql.length; i++) {
            db.execSQL(sql[i]);
        }
    }

    public long addDoctorPrivilegios(boolean isAcessoTotal) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("acessoTotal",isAcessoTotal);
        return db.insert(" privilegiosDoctor", null, cv);

    }
    public long updadeDoctorPrivilegios(boolean isAcessoTotal) {
        String  id="1";
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("acessoTotal",isAcessoTotal);
        return db.update("privilegiosDoctor", cv, "id=?", new String[]{String.valueOf(id)});
    }

    public boolean getPrivilegios() {
        String id = "1";
        boolean privilegios = false;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM privilegiosDoctor WHERE id = ?",
                new String[]{id});
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
               privilegios = ((c.getInt(c.getColumnIndex("acessoTotal"))== 1)? true : false);
            } while (c.moveToNext());
        }
        return privilegios;
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
    public  void addParturiente(Parturient parturient) throws ParseException {
        if(!parturient.getSinaisDePatologia().equalsIgnoreCase("Nenhum")){
            envioDaNotificacao(parturient);
            addAuxParturiente(parturient);
        }else{
            addParturienteInDB(parturient);
        }
    }


    public long addParturienteInDB(Parturient parturient) throws ParseException {


        initializeCountDownTimer(parturient,getTimerDilatation(parturient.getReason()+"")); // inicializa a contagem
        addAuxParturiente(parturient);  /// coloca os parturintes numa lista auxiliar
        DBManager.getInstance().getParturients().add(parturient);


        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name ", parturient.getName());
        cv.put("apelido", parturient.getSurname());
        cv.put("isAtendido", parturient.isAtendido());
        cv.put("tipoAtendimento", parturient.getTipoAtendimento());
        cv.put("horaExpulsoDoFeto", parturient.getHoraExpulsoDoFeto());
        cv.put("sinaisDePatologia", parturient.getSinaisDePatologia());
        cv.put("idAuxParturiente ", parturient.getIdAuxParturiente());
        cv.put("isEditDilatation", parturient.isEditDilatation());
        cv.put("isDisparo",parturient.isDisparoAlerta());
        cv.put("idade", parturient.getAge());
        cv.put("isTrasferido",parturient.isTransfered());
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
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("cor",cor);
        return db.update("Notificacao", cv, "idParturiente=?", new String[]{String.valueOf(id)});
    }


    public long addAuxParturiente(Parturient parturient) {
        DBManager.getInstance().getAuxlistNotificationParturients().add(parturient);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name ", parturient.getName());
        cv.put("apelido", parturient.getSurname());
        cv.put("isAtendido", parturient.isAtendido());
        cv.put("tipoAtendimento", parturient.getTipoAtendimento());
        cv.put("horaExpulsoDoFeto", parturient.getHoraExpulsoDoFeto());
        cv.put("sinaisDePatologia", parturient.getSinaisDePatologia());
        cv.put("idAuxParturiente ", parturient.getIdAuxParturiente());
        cv.put("isEditDilatation", parturient.isEditDilatation());
        cv.put("isDisparo",parturient.isDisparoAlerta());
        cv.put("idade", parturient.getAge());
        cv.put("isTrasferido",parturient.isTransfered());
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


    public void initializeListNotification() throws ParseException {

        DBManager.getInstance().getNotifications().removeAll(DBManager.getInstance().getNotifications());

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Notificacao", null);
        c.moveToFirst();
        if (c.getCount()> 0) {
            do {
                Notificacao no=new Notificacao();
                no.setId(c.getInt(c.getColumnIndex("id"))+"");
                no.setTime(c.getString(c.getColumnIndex("HoraNotification")));
                no.setIdAuxParturiente(c.getString(c.getColumnIndex("idParturiente")));
                no.setHoraDoEnvioDaMensagem(c.getString(c.getColumnIndex("horaDoEnvioDaMensagem")));
                no.setNome(c.getString(c.getColumnIndex("fullname")));
                no.setOpen(((c.getInt(c.getColumnIndex("isOpen")))== 1)? true : false);
                no.setColour(c.getInt(c.getColumnIndex("cor")));
                no.setInProcess((c.getInt(c.getColumnIndex("inProcess"))== 1)? true : false);
                tempoRestante = getTempoRestanteEmSeguntosDaNotificacao(no); // tras o tempo restante para terminar com a contagem

               if(!isConten(no)){
                   if(isFinishTimeNotification2(no)){
                       no.setViewTimerTwo(" Alerta disparado ");
                       ordeList();
                       DBManager.getInstance().getNotifications().add(no);
                       ordeList();
                   }else{
                       ordeList();
                       DBManager.getInstance().getNotifications().add(no);
                       ordeList();
                       initializeCountDownTimerNotification(no,tempoRestante);
                   }
               }

            } while (c.moveToNext());
            c.close();
        }
    }





    public void initializeListParturiente() throws ParseException {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Parturientes", null);
        DBManager.getInstance().getAuxlistNotificationParturients().removeAll( DBManager.getInstance().getAuxlistNotificationParturients());
        DBManager.getInstance().getParturients().removeAll(DBManager.getInstance().getParturients());
        getListAuxParturiente();
        c.moveToFirst();
        if (c.getCount()> 0) {
            do {

                Parturient parturient=new Parturient();

                parturient.setId(c.getInt(c.getColumnIndex("id")));
                parturient.setReason(c.getInt(c.getColumnIndex("dilatacao")));
                parturient.setHoraEntrada(c.getString(c.getColumnIndex("HoraEntrada")));
                parturient.setTipoAtendimento(c.getString(c.getColumnIndex("tipoAtendimento")));
                if(!c.getString(c.getColumnIndex("HoraAtendimento")).isEmpty()) {
                    parturient.setHoraAtendimento(c.getString(c.getColumnIndex("HoraAtendimento")));
                }
                parturient.setSinaisDePatologia(c.getString(c.getColumnIndex("sinaisDePatologia")));
                parturient.setHoraExpulsoDoFeto(c.getString(c.getColumnIndex("horaExpulsoDoFeto")));
                parturient.setEditDilatation(false);
                parturient.setIdAuxParturiente(c.getString(c.getColumnIndex("idAuxParturiente")));
                parturient.setName(c.getString(c.getColumnIndex("name")));
                parturient.setSurname(c.getString(c.getColumnIndex("apelido")));
                parturient.setInProcess(((c.getInt(c.getColumnIndex("parturienteEmprocesso")))== 1)? true : false);
                parturient.setOrigemTransferencia(c.getString(c.getColumnIndex("origemTrasferencia")));
                parturient.setDestinoTrasferencia(c.getString(c.getColumnIndex("destinoTrasferencia")));
                parturient.setMotivosDestinoDaTrasferencia(c.getString(c.getColumnIndex("motivosDestinoTrasferencia")));
                parturient.setPara(c.getInt(c.getColumnIndex("paridade")));
                parturient.setAge(c.getInt(c.getColumnIndex("idade")));
                parturient.setInProcess((c.getInt(c.getColumnIndex("parturienteEmprocesso"))== 1)? true : false);
                parturient.setGestatinalRange(c.getString(c.getColumnIndex("idadeGestacional")));
                parturient.setMotivosDaTrasferencia(c.getString(c.getColumnIndex("motivoOrigemTrasferencia")));
                parturient.setTrasferidoParaForaDoHospital((c.getInt(c.getColumnIndex("isTrasferenciaForaDaUnidade"))== 1)? true : false);
                parturient.setTransfered((c.getInt(c.getColumnIndex("isTrasferido"))== 1)? true : false);
                parturient.setAtendido((c.getInt(c.getColumnIndex("isAtendido"))== 1)? true : false);
                tempoRestante = getTempoRestanteEmSeguntos(parturient); // tras o tempo restante para terminar com a contagem
                if(isFinishTime(parturient)){
                    if(parturient.isInProcess()){
                        parturient.setTempoRes("Parturiente Em Processo");
                        DBManager.getInstance().getParturients().add(parturient);
                        DBManager.getInstance().addAuxListNotificationParturient(parturient);

                    }else {
                        if(getIdNotification(parturient.getIdAuxParturiente())==-1)
                            envioDaNotificacao(parturient);
                            removeInBD(parturient);
                    }
                }else{
                    initializeCountDownTimer(parturient,tempoRestante);
                    DBManager.getInstance().getParturients().add(parturient);
                    DBManager.getInstance().addAuxListNotificationParturient(parturient);
                }

            } while (c.moveToNext());
            c.close();
        }
    }

    private void envioDaNotificacao(Parturient parturient) throws ParseException {

        Notificacao notifi=new Notificacao();
        notifi.setAtendido(false);
        notifi.setNome(notifi.getMessage());
        notifi.setHoraDoEnvioDaMensagem(getTempoLimiteDoEnvioMensagem(getTimerAlertEmergenceDilatation()));
        notifi.setIdAuxParturiente(parturient.getIdAuxParturiente());
        notifi.setInProcess(false);
        notifi.setOpen(false);
        notifi.setNome(parturient.getName()+ " "+parturient.getSurname());
        notifi.setTime(format(new Date()));
        notifi.setColour(Color.YELLOW+Color.BLACK);
       /// DBManager.getInstance().addAuxListNotification(notifi);

        if(!isConten(notifi)){
            if(!parturient.getSinaisDePatologia().equalsIgnoreCase("Nenhum")){
                notifi.setColour( Color.rgb(248, 215, 218));
                sendMensageEmergenceOfPatologia(notifi, parturient);
                notifi.setViewTimerTwo("Alerta disparado");
                ordeList();
                DBManager.getInstance().getNotifications().add(notifi);
                ordeList();
            }else{
                initializeCountDownTimerNotification(notifi,getTimerAlertEmergenceDilatation());
                ordeList();
                DBManager.getInstance().getNotifications().add(notifi);
                ordeList();
            }
            addNotificacao(notifi);
        }

    }

    public void ordeList(){ // o primeiro a entrar fica no topo
        ArrayList<Notificacao> list = new ArrayList<>();
        for(int i=(DBManager.getInstance().getNotifications().size()-1);i>=0;i--){
            list.add(DBManager.getInstance().getNotifications().get(i));
        }
        DBManager.getInstance().getNotifications().removeAll(DBManager.getInstance().getNotifications());
        DBManager.getInstance().getNotifications().addAll(list);
    }

    public boolean isConten(Notificacao notificaca){
           for(Notificacao noti: DBManager.getInstance().getNotifications()){
               if(noti.getIdAuxParturiente().equalsIgnoreCase(notificaca.getIdAuxParturiente())){
                   return true;
               }else {
                   return false;
               }
           }
           return false;
    }


    private void initializeCountDownTimerNotification(Notificacao notifica, int seconds) {
        initializeListParturientesTransferidos();
        new CountDownTimer(seconds*1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

                if(iscontemInTransferido(notifica.getIdAuxParturiente())){
                    cancel();
                }

                if(notifica.isInProcess()){
                    notifica.setViewTimerTwo(" Emprocesso de parto ");
                    cancel();
                }

                notifica.setViewTimerTwo("Tempo restante : " + String.format("%02d", hours)
                        + ":" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds)+" ");
            }

            public void onFinish() {
                notifica.setAlertaDisparada(true);
                notifica.setColour(Color.rgb(248, 215, 218));
                AddParturientActivity addParturientActivity=new AddParturientActivity();
                addParturientActivity.isFireAlert=true;
                sendMensageEmergence(notifica,getParturiente(notifica.getIdAuxParturiente()));
                cancel();
                notifica.setViewTimerTwo("Alerta Disparado ");
            }
        }.start();

    }

    public Parturient getParturiente(String idParturiente){
                for(Parturient parturient: getListAuxParturiente()){
                    if(parturient.getIdAuxParturiente().equalsIgnoreCase(idParturiente)){
                        return parturient;
                    }
                }
                return null;
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
                parturient.setReason(c.getInt(c.getColumnIndex("dilatacao")));
                parturient.setHoraEntrada(c.getString(c.getColumnIndex("HoraEntrada")));
                parturient.setTipoAtendimento(c.getString(c.getColumnIndex("tipoAtendimento")));
                if(!c.getString(c.getColumnIndex("HoraAtendimento")).isEmpty()) {
                    parturient.setHoraAtendimento(c.getString(c.getColumnIndex("HoraAtendimento")));
                }
                parturient.setSinaisDePatologia(c.getString(c.getColumnIndex("sinaisDePatologia")));
                parturient.setHoraExpulsoDoFeto(c.getString(c.getColumnIndex("horaExpulsoDoFeto")));
                parturient.setEditDilatation(false);
                parturient.setIdAuxParturiente(c.getString(c.getColumnIndex("idAuxParturiente")));
                parturient.setName(c.getString(c.getColumnIndex("name")));
                parturient.setSurname(c.getString(c.getColumnIndex("apelido")));
                parturient.setInProcess(((c.getInt(c.getColumnIndex("parturienteEmprocesso")))== 1)? true : false);
                parturient.setOrigemTransferencia(c.getString(c.getColumnIndex("origemTrasferencia")));
                parturient.setDestinoTrasferencia(c.getString(c.getColumnIndex("destinoTrasferencia")));
                parturient.setMotivosDestinoDaTrasferencia(c.getString(c.getColumnIndex("motivosDestinoTrasferencia")));
                parturient.setPara(c.getInt(c.getColumnIndex("paridade")));
                parturient.setAge(c.getInt(c.getColumnIndex("idade")));
                parturient.setInProcess((c.getInt(c.getColumnIndex("parturienteEmprocesso"))== 1)? true : false);
                parturient.setGestatinalRange(c.getString(c.getColumnIndex("idadeGestacional")));
                parturient.setMotivosDaTrasferencia(c.getString(c.getColumnIndex("motivoOrigemTrasferencia")));
                parturient.setTrasferidoParaForaDoHospital((c.getInt(c.getColumnIndex("isTrasferenciaForaDaUnidade"))== 1)? true : false);
                parturient.setTransfered((c.getInt(c.getColumnIndex("isTrasferido"))== 1)? true : false);
                parturient.setAtendido((c.getInt(c.getColumnIndex("isAtendido"))== 1)? true : false);
                arrayList.add(parturient);
            } while (c.moveToNext());

        }
        return (ArrayList<Parturient>) arrayList;
    }



    public void initializeListParturientesTransferidos() {

        DBManager.getInstance().getListaTransferidos().removeAll(DBManager.getInstance().getListaTransferidos());
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Transferidos", null);
        List<Parturient> arrayList = new ArrayList<>();

        c.moveToFirst();
        if (c.getCount()> 0) {
            do {
                Parturient parturient=new Parturient();

                parturient.setId(c.getInt(c.getColumnIndex("id")));
                parturient.setReason(c.getInt(c.getColumnIndex("dilatacao")));
                parturient.setHoraEntrada(c.getString(c.getColumnIndex("HoraEntrada")));
                parturient.setTipoAtendimento(c.getString(c.getColumnIndex("tipoAtendimento")));
                if(!c.getString(c.getColumnIndex("HoraAtendimento")).isEmpty()) {
                    parturient.setHoraAtendimento(c.getString(c.getColumnIndex("HoraAtendimento")));
                }
                parturient.setSinaisDePatologia(c.getString(c.getColumnIndex("sinaisDePatologia")));
                parturient.setHoraExpulsoDoFeto(c.getString(c.getColumnIndex("horaExpulsoDoFeto")));
                parturient.setEditDilatation(false);
                parturient.setIdAuxParturiente(c.getString(c.getColumnIndex("idAuxParturiente")));
                parturient.setName(c.getString(c.getColumnIndex("name")));
                parturient.setSurname(c.getString(c.getColumnIndex("apelido")));
                parturient.setInProcess(((c.getInt(c.getColumnIndex("parturienteEmprocesso")))== 1)? true : false);
                parturient.setOrigemTransferencia(c.getString(c.getColumnIndex("origemTrasferencia")));
                parturient.setDestinoTrasferencia(c.getString(c.getColumnIndex("destinoTrasferencia")));
                parturient.setMotivosDestinoDaTrasferencia(c.getString(c.getColumnIndex("motivosDestinoTrasferencia")));
                parturient.setPara(c.getInt(c.getColumnIndex("paridade")));
                parturient.setAge(c.getInt(c.getColumnIndex("idade")));
                parturient.setInProcess((c.getInt(c.getColumnIndex("parturienteEmprocesso"))== 1)? true : false);
                parturient.setGestatinalRange(c.getString(c.getColumnIndex("idadeGestacional")));
                parturient.setMotivosDaTrasferencia(c.getString(c.getColumnIndex("motivoOrigemTrasferencia")));
                parturient.setTrasferidoParaForaDoHospital((c.getInt(c.getColumnIndex("isTrasferenciaForaDaUnidade"))== 1)? true : false);
                parturient.setTransfered((c.getInt(c.getColumnIndex("isTrasferido"))== 1)? true : false);
                parturient.setAtendido((c.getInt(c.getColumnIndex("isAtendido"))== 1)? true : false);

                DBManager.getInstance().getListaTransferidos().add(parturient);

            } while (c.moveToNext());

        }
    }


    public void initializeListParturientesAtendidos() {

        DBManager.getInstance().getListParturientesAtendidos().removeAll(DBManager.getInstance().getListParturientesAtendidos());
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Atendidos", null);
        List<Parturient> arrayList = new ArrayList<>();

        c.moveToFirst();
        if (c.getCount()> 0) {
            do {
                Parturient parturient=new Parturient();

                parturient.setId(c.getInt(c.getColumnIndex("id")));
                parturient.setReason(c.getInt(c.getColumnIndex("dilatacao")));
                parturient.setHoraEntrada(c.getString(c.getColumnIndex("HoraEntrada")));
                parturient.setTipoAtendimento(c.getString(c.getColumnIndex("tipoAtendimento")));
                if(!c.getString(c.getColumnIndex("HoraAtendimento")).isEmpty()) {
                    parturient.setHoraAtendimento(c.getString(c.getColumnIndex("HoraAtendimento")));
                }
                parturient.setSinaisDePatologia(c.getString(c.getColumnIndex("sinaisDePatologia")));
                parturient.setHoraExpulsoDoFeto(c.getString(c.getColumnIndex("horaExpulsoDoFeto")));
                parturient.setEditDilatation(false);
                parturient.setIdAuxParturiente(c.getString(c.getColumnIndex("idAuxParturiente")));
                parturient.setName(c.getString(c.getColumnIndex("name")));
                parturient.setSurname(c.getString(c.getColumnIndex("apelido")));
                parturient.setInProcess(((c.getInt(c.getColumnIndex("parturienteEmprocesso")))== 1)? true : false);
                parturient.setOrigemTransferencia(c.getString(c.getColumnIndex("origemTrasferencia")));
                parturient.setDestinoTrasferencia(c.getString(c.getColumnIndex("destinoTrasferencia")));
                parturient.setMotivosDestinoDaTrasferencia(c.getString(c.getColumnIndex("motivosDestinoTrasferencia")));
                parturient.setPara(c.getInt(c.getColumnIndex("paridade")));
                parturient.setAge(c.getInt(c.getColumnIndex("idade")));
                parturient.setInProcess((c.getInt(c.getColumnIndex("parturienteEmprocesso"))== 1)? true : false);
                parturient.setGestatinalRange(c.getString(c.getColumnIndex("idadeGestacional")));
                parturient.setMotivosDaTrasferencia(c.getString(c.getColumnIndex("motivoOrigemTrasferencia")));
                parturient.setTrasferidoParaForaDoHospital((c.getInt(c.getColumnIndex("isTrasferenciaForaDaUnidade"))== 1)? true : false);
                parturient.setTransfered((c.getInt(c.getColumnIndex("isTrasferido"))== 1)? true : false);
                parturient.setAtendido((c.getInt(c.getColumnIndex("isAtendido"))== 1)? true : false);

                DBManager.getInstance().getListParturientesAtendidos().add(parturient);

            } while (c.moveToNext());

        }
    }

    public long deleteParturienteInAuxList(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("AuxParturientes", "idAuxParturiente=?", new String[]{String.valueOf(id)});

    }
    public long deleteParturiente(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("Parturientes", "idAuxParturiente=?", new String[]{String.valueOf(id)});

    }

    public long deleteNotification(Notificacao notificacao) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("Notificacao", "idParturiente=?", new String[]{String.valueOf(notificacao.getIdAuxParturiente())});
    }

    //....................NOTIFICACAO.................................
    public long addNotificacao(Notificacao notificacao) {

            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("horaDoEnvioDaMensagem", notificacao.getHoraDoEnvioDaMensagem());
            cv.put("fullname", notificacao.getMessage());
            cv.put("idParturiente", notificacao.getIdAuxParturiente());
            cv.put("HoraNotification", format(new Date()));
            cv.put("isOpen", notificacao.isOpen());
            cv.put("cor", notificacao.getColour());
            cv.put("inProcess", notificacao.isInProcess());
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
        String id=parturient.getIdAuxParturiente();

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name ", parturient.getName());
        cv.put("apelido", parturient.getSurname());
        cv.put("isAtendido", parturient.isAtendido());
        cv.put("tipoAtendimento", parturient.getTipoAtendimento());
        cv.put("horaExpulsoDoFeto", parturient.getHoraExpulsoDoFeto());
        cv.put("sinaisDePatologia", parturient.getSinaisDePatologia());
        cv.put("idAuxParturiente ", parturient.getIdAuxParturiente());
        cv.put("isEditDilatation", parturient.isEditDilatation());
        cv.put("isDisparo",parturient.isDisparoAlerta());
        cv.put("idade", parturient.getAge());
        cv.put("isTrasferido",parturient.isTransfered());
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
        return db.update("AuxParturientes", cv, "idAuxParturiente=?", new String[]{String.valueOf(id)});
    }


    public long updadeAllDadeParturiente(Parturient parturient) {
        String id=parturient.getIdAuxParturiente();
        updadeAllDadeAuxParturiente(parturient);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name ", parturient.getName());
        cv.put("apelido", parturient.getSurname());
        cv.put("isAtendido", parturient.isAtendido());
        cv.put("tipoAtendimento", parturient.getTipoAtendimento());
        cv.put("horaExpulsoDoFeto", parturient.getHoraExpulsoDoFeto());
        cv.put("sinaisDePatologia", parturient.getSinaisDePatologia());
        cv.put("idAuxParturiente ", parturient.getIdAuxParturiente());
        cv.put("isEditDilatation", parturient.isEditDilatation());
        cv.put("isDisparo",parturient.isDisparoAlerta());
        cv.put("idade", parturient.getAge());
        cv.put("isTrasferido",parturient.isTransfered());
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
        return db.update("Parturientes", cv, "idAuxParturiente=?", new String[]{String.valueOf(id)});
    }

    public int getIdNotification( String idParturiente) {
        int id=-1;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Notificacao WHERE idParturiente = ?",
                new String[]{idParturiente});
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                id = c.getInt(c.getColumnIndex("idParturiente"));
            } while (c.moveToNext());
            c.close();
        }
        return id;
    }

    //.................ATENDIMENTO...............................
    public long addAtendimento(Parturient parturient) {
        updadeAllDadeAuxParturiente(parturient);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name ", parturient.getName());
        cv.put("apelido", parturient.getSurname());
        cv.put("isAtendido", parturient.isAtendido());
        cv.put("tipoAtendimento", parturient.getTipoAtendimento());
        cv.put("horaExpulsoDoFeto", parturient.getHoraExpulsoDoFeto());
        cv.put("sinaisDePatologia", parturient.getSinaisDePatologia());
        cv.put("idAuxParturiente ", parturient.getIdAuxParturiente());
        cv.put("isEditDilatation", parturient.isEditDilatation());
        cv.put("isDisparo",parturient.isDisparoAlerta());
        cv.put("idade", parturient.getAge());
        cv.put("isTrasferido",parturient.isTransfered());
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

    public long addTransferidos(Parturient parturient) {
        updadeAllDadeAuxParturiente(parturient);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name ", parturient.getName());
        cv.put("apelido", parturient.getSurname());
        cv.put("isAtendido", parturient.isAtendido());
        cv.put("tipoAtendimento", parturient.getTipoAtendimento());
        cv.put("horaExpulsoDoFeto", parturient.getHoraExpulsoDoFeto());
        cv.put("sinaisDePatologia", parturient.getSinaisDePatologia());
        cv.put("idAuxParturiente ", parturient.getIdAuxParturiente());
        cv.put("isEditDilatation", parturient.isEditDilatation());
        cv.put("isDisparo",parturient.isDisparoAlerta());
        cv.put("idade", parturient.getAge());
        cv.put("isTrasferido",parturient.isTransfered());
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
        return db.insert("Transferidos", null, cv);
    }

    private String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        return dateFormat.format(date);
    }

    public void removeInBD(Parturient parturient) {
        deleteParturiente(parturient.getIdAuxParturiente());
    }

    public void removParturiente(Parturient prt){
        for(Parturient parturient: DBManager.getInstance().getParturients()){
            if(parturient.getIdAuxParturiente()==prt.getIdAuxParturiente()){
                DBManager.getInstance().getParturients().remove(parturient);
                break;
            }
        }
        AddParturientActivity addParturientActivity =new AddParturientActivity();
        addParturientActivity.isFireAlert=true;

    }



    public boolean iscontemInTransferido(String id)
    {
        for(Parturient parturient: DBManager.getInstance().getListaTransferidos()){
            if(parturient.getIdAuxParturiente().equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
    }


    public void initializeCountDownTimer(Parturient parturient,int seconds) {

        initializeListParturientesTransferidos();

        new CountDownTimer(seconds*1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);

                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);


                if(iscontemInTransferido(parturient.getIdAuxParturiente())){
                    DBManager.getInstance().getParturients().remove(parturient);
                    cancel();
                }

                System.out.println("Tempo Restante : [ " + String.format("%02d", hours)
                        + ":" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds)+" ]");
                if(parturient.isEditDilatation()){// caso
                    cancel();
                    updateHoraExpulsoFeto(parturient);
                    initializeCountDownTimer(parturient,getTimerDilatation(parturient.getReason()+""));
                    parturient.setEditDilatation(false);
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
                        parturient.setTempoRes("Alerta Disparado ");
                        try {
                                removeInBD(parturient);
                                removParturiente(parturient);
                                envioDaNotificacao(parturient);
                                cancel();

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        addParturientActivity.isFireAlert=true;

           }
        }.start();

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
    public long updadeInProcessParturiente(Parturient parturient) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("parturienteEmprocesso",true);
        return db.update("Parturientes", cv, "idAuxParturiente=?", new String[]{String.valueOf(parturient.getIdAuxParturiente())});
    }

    public long updadeInProcessNotification(Notificacao notificacao) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("inProcess",true);
        return db.update("Notificacao", cv, "idParturiente=?", new String[]{String.valueOf(notificacao.getIdAuxParturiente())});
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
        // System.out.println(" ===========x======x===========  "+ isInprocess);
        return isInprocess;
    }


    public boolean isFinishTimeNotification2(Notificacao notificacao) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        Calendar horaEnvioMensagem=Calendar.getInstance();
        Calendar horaActual=Calendar.getInstance();

        horaEnvioMensagem.setTime(dateFormat.parse(notificacao.getHoraDoEnvioDaMensagem()));
        horaActual.setTime(dateFormat.parse(dateFormat.format(new Date())));
        if(horaActual.after(horaEnvioMensagem)){
            return true;
        }else{
            return false;
        }
    }
    public boolean isFinishTime(Parturient parturient) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        Calendar horaExpulso=Calendar.getInstance();
        Calendar horaActual=Calendar.getInstance();

        horaExpulso.setTime(dateFormat.parse(parturient.getHoraExpulsoDoFeto()));
        horaActual.setTime(dateFormat.parse(dateFormat.format(new Date())));
        if(horaActual.after(horaExpulso)){
            return true;
        }else{
            return false;
        }
    }
    public int getTempoRestanteEmSeguntosDaNotificacao(Notificacao notificacao) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        Calendar horaEnvioMensagem = Calendar.getInstance();
        horaEnvioMensagem.setTime(dateFormat.parse(notificacao.getHoraDoEnvioDaMensagem()));
        horaEnvioMensagem.add(Calendar.SECOND,-getSeguntosHoraActual());

        return horaEnvioMensagem.getTime().getHours()*3600+
                horaEnvioMensagem.getTime().getMinutes()*60+
                horaEnvioMensagem.getTime().getSeconds();
    }
    public  String getTempoLimiteDoEnvioMensagem(int seguntos) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND,seguntos);
        return formatd(calendar.getTime());
    }

    private String formatd(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");

        return dateFormat.format(date);
    }


    public long updateHoraExpulsoFeto(Parturient parturient) {
        String id=parturient.getIdAuxParturiente();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("horaExpulsoDoFeto",parturient.getHoraExpulsoDoFeto());
        return db.update("Parturientes", cv, "idAuxParturiente=?", new String[]{String.valueOf(id)});
    }
    public int getTempoRestanteEmSeguntos(Parturient parturient) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        Calendar horaExpulso = Calendar.getInstance();
        horaExpulso.setTime(dateFormat.parse(parturient.getHoraExpulsoDoFeto()));
        horaExpulso.add(Calendar.SECOND,-getSeguntosHoraActual());
        return horaExpulso.getTime().getHours()*3600+horaExpulso.getTime().getMinutes()*60+horaExpulso.getTime().getSeconds();
    }

    public int getSeguntosHoraActual() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(dateFormat.format(new Date())));
        return calendar.getTime().getHours()*3600+calendar.getTime().getMinutes()*60+calendar.getTime().getSeconds();
    }



    private void sendMensageEmergenceOfPatologia(Notificacao notificacao, Parturient parturient) {
        String mensagem = "";
        String mensagem2 = "";
        mensagem =abrevStr(getHospitalSelect())+": ("+ parturient.getName()+" "+parturient.getSurname()+") , idade :"+parturient.getAge()+",Entrada :"+parturient.getHoraEntrada()+", necessita de cuidados medico";
        mensagem2="Patologia : "+parturient.getSinaisDePatologia()+", unidade gestacional: "+parturient.getGestatinalRange()+" paridade : "+parturient.getPara();
        updadeCorIsDispareNotification(Color.rgb(248, 215, 218), notificacao.getIdAuxParturiente());


                for (UserDoctor userDoctor : getListDoctor()) {
                    sendSMS(userDoctor.getContacto(), mensagem);
                    sendSMS(userDoctor.getContacto(), mensagem2);
                }
    }
    private void sendMensageEmergence(Notificacao notificacao, Parturient parturient) {
        String mensagem = "";
        String mensagem2 = "";
        mensagem = abrevStr(getHospitalSelect())+" : ("+ parturient.getName()+" "+parturient.getSurname()+") , idade :"+parturient.getAge()+", Entrada :"+parturient.getHoraEntrada()+", necessita de cuidados medico";
        mensagem2="Patologia : "+parturient.getSinaisDePatologia()+", unidade gestacional: "+parturient.getGestatinalRange()+" paridade : "+parturient.getPara();
        updadeCorIsDispareNotification(Color.rgb(248, 215, 218), notificacao.getIdAuxParturiente());

        if(parturient.getReason()>3) {
            if (!notificacao.isInProcess()) {
                for (UserDoctor userDoctor : getListDoctor()) {
                    sendSMS(userDoctor.getContacto(), mensagem);
                    sendSMS(userDoctor.getContacto(), mensagem2);
                    //sendSMS(userDoctor.getContacto(), mensagem3);
                }
            }
        }
}


    public  String abreviacaoDeNomes(String nome){
        String newNome="";
        int posicao=0;
        int tamanho=nome.length();

                     for(int i=0;i< nome.length();i++){
                         if(nome.charAt(i)==' ')
                             posicao=i;
                     }

                 newNome=nome.substring(posicao,tamanho);

        return newNome;
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


    private String abrevStr(String str){

        if(str.equalsIgnoreCase("Hospital Distrital de Chiúre")){
            return "C.S DE CHIURE";
        }
        if(str.equalsIgnoreCase("Centro de saúde de Catapua")){
            return "C.S DE CATAPUA";
        }
        if(str.equalsIgnoreCase("Centro de saúde de Ocua")){
            return "C.S DE OCUA";
        }
        if(str.equalsIgnoreCase("Centro de saúde de Chiúre Velho")){
            return "C.S DE CHIURE VELHO";
        }
        if(str.equalsIgnoreCase("Centro de saúde de Mmala")){
            return "C.S DE MMALA";
        }
        if(str.equalsIgnoreCase("Centro de saúde de Marera")){
            return "C.S DE MARERA";
        }
        if(str.equalsIgnoreCase("Centro de saúde de Mazeze")){
            return "C.S DE MAZEZE";
        }
        if(str.equalsIgnoreCase("Centro de saúde de Muege")){
            return "C.S DE MUEGE";
        }
        if(str.equalsIgnoreCase("Centro de saúde de Nakoto")){
            return "C.S DE MAKOTO";
        }
        if(str.equalsIgnoreCase("Centro de saúde de Namogeliua")){
            return "C.S DE NAMOGELIUA";
        }
        if(str.equalsIgnoreCase("Centro de saúde de Samora Machel")){
            return "C.S DE SAMORA MACHEL";
        }
        if(str.equalsIgnoreCase("Centro de saúde de Bilibiza")){
            return "C.S DE BILIBIZA";
        }

        return "C.S";
    }


}
