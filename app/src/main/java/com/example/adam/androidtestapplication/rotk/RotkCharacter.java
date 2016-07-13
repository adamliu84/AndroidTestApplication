package com.example.adam.androidtestapplication.rotk;

/**
 * Created by adam on 13/7/16.
 */
public class RotkCharacter {

    String _name = "";
    String _avatarurl = "http://25.media.tumblr.com/tumblr_m4lj6lbENX1r2h6ioo1_500.jpg"; //Default
    int _atk = 0;
    int _def = 0;

    public RotkCharacter() {
    }

    public String get_name() {
        return _name;
    }

    public int get_atk() {
        return _atk;
    }

    public int get_def() {
        return _def;
    }

    public String get_avatarurl() {
        return _avatarurl;
    }

    public void set_avatarurl(String _avatarurl) {
        this._avatarurl = _avatarurl;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_atk(int _atk) {
        this._atk = _atk;
    }

    public void set_def(int _def) {
        this._def = _def;
    }

    @Override
    public String toString() {
        return "RotkCharacter{" +
                "_name='" + _name + '\'' +
                ", _atk=" + _atk +
                ", _def=" + _def +
                '}';
    }
}
