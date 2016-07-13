package com.example.adam.androidtestapplication.rotk;

/**
 * Created by adam on 13/7/16.
 */
public class RotkCharacter {

    String _name = "";
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
