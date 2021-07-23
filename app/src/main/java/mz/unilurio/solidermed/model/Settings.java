package mz.unilurio.solidermed.model;

import android.widget.ImageView;

public class Settings {
    private String setting;
    private int idSetting;
    private int imagem;

    public Settings(String setting, int idSetting,int imagem) {
        this.setting = setting;
        this.idSetting = idSetting;
        this.imagem=imagem;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public int getIdSetting() {
        return idSetting;
    }

    public void setIdSetting(int idSetting) {
        this.idSetting = idSetting;
    }
}
