package com.example.wayfinding.classes;

import java.io.Serializable;

/**
 * Class used to store and pass along user settings between activites
 */
public class UserSettings implements Serializable {
    //darkMode and audioMode
    // 1=enabled, 0=disabled
    protected boolean darkMode;
    protected boolean audioMode;
    protected boolean french;

    public UserSettings(boolean darkMode, boolean audioMode){
        this.darkMode = darkMode;
        this.audioMode = audioMode;
    }

    public void setDarkMode(boolean b){
        darkMode = b;
    }
    public void setAudioMode(boolean b){
        audioMode = b;
    }
    public void setFrench(boolean b){
        french = b;
    }

    public boolean getDarkMode(){
        return darkMode;
    }
    public boolean getAudioMode(){
        return audioMode;
    }
    public boolean getFrench(){
        return french;
    }
}
