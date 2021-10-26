package mz.unilurio.solidermed.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBService  extends SQLiteOpenHelper {
    private static String nomeDB = "BDHospital.db";
    private static int versao = 1;

    private String[] sql = {
            "CREATE TABLE Atendidos(id INTEGER NOT NULL UNIQUE, fullname TEXT NOT NULL,apelido TEXT NOT NULL,idade TEXT NOT NULL,dilatacao TEXT NOT NULL,paridade TEXT NOT NULL,isTrasferencia boolean NOT NULL,isTrasferenciaForaDaUnidade boolean NOT NULL,HoraEntrada TXT NOT NULL,HoraAtendimento TXT NOT NULL,origemTrasferencia TXT NOT NULL,motivoOrigemTrasferencia TXT NOT NULL,destinoTrasferencia TXT NOT NULL,motivosDestinoTrasferencia TXT NOT NULL,parturienteEmprocesso boolean NOT NULL,idadeGestacional TXT NOT NULL, PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE Parturientes(id INTEGER NOT NULL UNIQUE, fullname TEXT NOT NULL,apelido TEXT NOT NULL,idade TEXT NOT NULL,dilatacao TEXT NOT NULL,paridade TEXT NOT NULL,isTrasferencia boolean NOT NULL,isTrasferenciaForaDaUnidade boolean NOT NULL,HoraEntrada TXT NOT NULL, horaParte INTEGER NOT NULL, minutoParte INTEGER NOT NULL,segundoParte INTEGER NOT NULL,HoraAtendimento TXT NOT NULL,origemTrasferencia TXT NOT NULL,motivoOrigemTrasferencia TXT NOT NULL,destinoTrasferencia TXT NOT NULL,motivosDestinoTrasferencia TXT NOT NULL,parturienteEmprocesso boolean NOT NULL,idadeGestacional TXT NOT NULL, PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE Notificacao(id INTEGER NOT NULL UNIQUE, fullname TEXT NOT NULL,HoraNotification TXT NOT NULL,isOpen boolean NOT NULL,inProcess boolean NOT NULL,cor INTEGER NOT NULL, PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE UserDoctor(id INTEGER NOT NULL UNIQUE, username TEXT NOT NULL,fullname TEXT NOT NULL,tellDoctor TEXT NOT NULL, pass TEXT NOT NULL, PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE UserNurse(id INTEGER NOT NULL UNIQUE, username TEXT NOT NULL ,fullname TEXT NOT NULL ,tellNurse TEXT NOT NULL, pass TEXT NOT NULL, PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE HospitalSelect(id INTEGER NOT NULL UNIQUE, hospital TEXT NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE Hospitais(id INTEGER NOT NULL UNIQUE, hospital TEXT NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE OpcoesParidade(id INTEGER NOT NULL UNIQUE, valor TEXT NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE IdadeGestacional(id INTEGER NOT NULL UNIQUE, txtIdadeGestacional TEXT NOT NULL , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE Dilatacao(id INTEGER NOT NULL UNIQUE, dilatation TEXT NOT NULL, horas TEXT NOT NULL , minutes TEXT NOT NULL  , PRIMARY KEY(id AUTOINCREMENT ));",
            "CREATE TABLE Alert(id INTEGER NOT NULL UNIQUE, horas TEXT NOT NULL , minutes TEXT NOT NULL  , PRIMARY KEY(id AUTOINCREMENT ));",

    };

    public DBService(@Nullable Context context) {
        super(context, nomeDB, null, versao);
    }



    public DBService(Parturient parturient) {
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
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("horaParte", parturient.getHoraParte());
        cv.put("minutoParte ", parturient.getMinutoParte());
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
        return db.insert("Parturientes", null, cv);
    }

    public void updadeListParturiente() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Parturientes", null);
        List<Parturient> arrayList = new ArrayList<>();
        DBManager.getInstance().getAuxlistNotificationParturients().removeAll(DBManager.getInstance().getAuxlistNotificationParturients());
        DBManager.getInstance().getParturients().removeAll(DBManager.getInstance().getParturients());

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
                parturient.setMotivosDaTrasferencia(c.getString(c.getColumnIndex("motivoOrigemTrasferencia")));
                parturient.setTrasferidoParaForaDoHospital((c.getInt(c.getColumnIndex("isTrasferenciaForaDaUnidade"))== 1)? true : false);
                parturient.setTransfered((c.getInt(c.getColumnIndex("isTrasferencia"))== 1)? true : false);


                System.out.println(" agora vamos mosttrar as horas");
                System.out.println(" hora 1 : "+formatHoras(new Date())+" == "+parturient.getHoraParte());
                System.out.println(" minuto 1 : "+formatMinuto(new Date())+" == "+parturient.getMinutoParte());
                System.out.println(" segundo 1 : "+formatSegundo(new Date())+" == "+parturient.getSegundoParte());



                int segundosHoraAtual=Integer.parseInt(formatHoras(new Date()))*3600+Integer.parseInt(formatMinuto(new Date()))*60+Integer.parseInt(formatSegundo(new Date()));
                int segundosHoraEntrada=parturient.getHoraParte()*3600+parturient.getMinutoParte()*60+parturient.getSegundoParte();
                int sub=segundosHoraAtual-segundosHoraEntrada;
                int tempoRestante=getTimerDilatation(parturient.getReason())-sub;

                if(tempoRestante>0){
                    parturient.initializeCountDownTimer(tempoRestante);
                    DBManager.getInstance().getParturients().add(parturient);
                    DBManager.getInstance().addAuxListNotificationParturient(parturient);
                }else {
                    deleteParturiente(parturient.getId());
                }

            } while (c.moveToNext());
            c.close();
        }
    }

    public long deleteParturiente(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("Parturientes", "id=?", new String[]{String.valueOf(id)});
    }

    //....................NOTIFICACAO.................................
    public long addNotificacao(Notification notification) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fullname", notification.getMessage());
        cv.put("HoraNotification",format(new Date()));
        cv.put("isOpen", notification.isOpen());
        cv.put("cor",notification.getColour());
        cv.put("inProcess",notification.isInProcess());
        return db.insert("Notificacao", null, cv);
    }


    public long updadeCorNotification(int id, int cor) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put("username", username);
        cv.put("cor", cor);
        return db.update("Notificacao", cv, "id=?", new String[]{String.valueOf(id)});
    }
    public void updadeListNotification() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Notificacao", null);

        DBManager.getInstance().getNotifications().removeAll(DBManager.getInstance().getNotifications());

        c.moveToFirst();
        if (c.getCount()> 0) {
            do {
                System.out.println("barata");
                Notification notification=new Notification();
                notification.setId(c.getInt(c.getColumnIndex("id"))+"");
                notification.setInProcess(((c.getInt(c.getColumnIndex("inProcess")))== 1)? true : false);
                notification.setNome(c.getString(c.getColumnIndex("fullname")));
                notification.setOpen(((c.getInt(c.getColumnIndex("isOpen")))== 1)? true : false);
                notification.setTime(c.getString(c.getColumnIndex("HoraNotification")));
                notification.setColour(c.getInt(c.getColumnIndex("cor")));
                DBManager.getInstance().addNewNotification(notification);
                System.out.println("ffffffffffffffffffff fffffffff "+DBManager.getInstance().getNotifications());

            } while (c.moveToNext());

        }
    }




    //.................ATENDIMENTO...............................
    public long addAtendimento(String nome,String apelido, String idade, String dilatacao,
                               boolean isTrasferencia,String paridade, boolean isTrasForaDaUnidade,
                               String horaEtrada, String horaAtendimento,String origemTrasferencia,
                               String motivosOrigemTrasferencia, String destinoTrasferencia,
                               String motivosDestinoTrasferencia, boolean isParturienteEmProcesso) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fullname ", nome);
        cv.put("apelido", apelido);
        cv.put("idade", idade);
        cv.put("isTrasferencia",isTrasferencia);
        cv.put("isTrasferenciaForaDaUnidade",isTrasForaDaUnidade);
        cv.put("HoraEntrada ",horaEtrada);
        cv.put("paridade",paridade);
        cv.put("HoraAtendimento ",horaAtendimento);
        cv.put("motivosDestinoTrasferencia",motivosDestinoTrasferencia);
        cv.put("motivoOrigemTrasferencia",motivosOrigemTrasferencia);
        cv.put("destinoTrasferencia",destinoTrasferencia);
        cv.put("origemTrasferencia",origemTrasferencia);
        cv.put("parturienteEmprocesso",isParturienteEmProcesso);
        cv.put("dilatacao ",dilatacao);
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
}


