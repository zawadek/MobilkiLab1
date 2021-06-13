package com.example.pierwszaaplikacja;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static int KOD = 997;

    Button buttonOceny, buttonKoniec;
    EditText imie, nazwisko, oceny;
    TextView imie2, nazwisko2, oceny2, srednia;
    final String KLUCZ1 = "imieTekstKlucz";
    final String KLUCZ2 = "nazwiskoTekstKlucz";
    final String KLUCZ3 = "ocenyTekstKlucz";

    boolean i=false,n=false,o=false;
    boolean zdal,wylacz=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonOceny = (Button)findViewById(R.id.button);
        buttonOceny.setText(R.string.buttonOceny);
        buttonOceny.setVisibility(View.INVISIBLE);

        buttonKoniec = (Button)findViewById(R.id.buttonKoniec);

        imie = (EditText)findViewById(R.id.editTextTextPersonName);
        imie.setHint(R.string.imiehint);

        imie2 = (TextView)findViewById(R.id.imie);
        imie2.setText(getText(R.string.imietv));

        nazwisko = (EditText)findViewById(R.id.editTextTextPersonSurname);
        nazwisko.setHint(R.string.nazwiskohint);

        nazwisko2 = (TextView)findViewById(R.id.nazwisko);
        nazwisko2.setText(getText(R.string.nazwiskotv));

        oceny = (EditText)findViewById(R.id.editTextNumber);
        oceny.setHint(R.string.ocenyhint);

        oceny2 = (TextView)findViewById(R.id.oceny);
        oceny2.setText(getText(R.string.ocenytv));

        srednia = (TextView)findViewById(R.id.srednia);
        srednia.setVisibility(View.GONE);

        imie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(!"".equals(imie.getText().toString())){
                    i=true;
                }else i = false;

                checkButton(i,n,o);
            }
        });
        imie.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && "".equals(imie.getText().toString())){
                    Toast.makeText(MainActivity.this, R.string.imieTost, Toast.LENGTH_SHORT).show();
                    imie.setError(getText(R.string.imieTost));
                }
            }
        });
        nazwisko.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(!"".equals(nazwisko.getText().toString())){
                    n=true;
                }else n = false;

                checkButton(i,n,o);
            }
        });
        nazwisko.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && "".equals(nazwisko.getText().toString())) {
                    Toast.makeText(MainActivity.this, R.string.nazwiskoTost, Toast.LENGTH_SHORT).show();
                    nazwisko.setError(getText(R.string.nazwiskoTost));
                }
            }
        });
        oceny.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String temp = oceny.getText().toString();
                if(!"".equals(temp)){
                    int value = 0;
                    value = Integer.parseInt(temp);

                    if ((value < 5 || value > 15)) {
                        Toast.makeText(MainActivity.this, R.string.ocenyTost1, Toast.LENGTH_SHORT).show();
                        oceny.setError(getText(R.string.ocenyTost1));
                        o=false;
                    }else {
                        o = true;
                        //oceny.setError(null);
                    }
                }
                checkButton(i,n,o);
            }
        });
        oceny.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && "".equals(oceny.getText().toString())) {
                    Toast.makeText(MainActivity.this, R.string.ocenyTost2, Toast.LENGTH_SHORT).show();
                    oceny.setError(getText(R.string.ocenyTost2));
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        buttonOceny = (Button)findViewById(R.id.button);
        buttonKoniec = (Button)findViewById(R.id.buttonKoniec);
        srednia = (TextView)findViewById(R.id.srednia);
        //zapisanie stanu przyciskow oraz etykiety

        outState.putInt("buttonOcenyVisibility",buttonOceny.getVisibility());

        outState.putInt("buttonKoniecVisibility",buttonKoniec.getVisibility());
        outState.putString("buttonKoniecText",buttonKoniec.getText().toString());

        outState.putInt("sredniaVisibility",srednia.getVisibility());
        outState.putString("sredniaText", srednia.getText().toString());
        //dodanie boola do tosta zamykajacego aplikacje
        outState.putBoolean("zdal",zdal);

        //edycja pol tekstowych
        imie = (EditText)findViewById(R.id.editTextTextPersonName);
        nazwisko = (EditText)findViewById(R.id.editTextTextPersonSurname);
        oceny = (EditText)findViewById(R.id.editTextNumber);

        outState.putBoolean("imieEnabled",imie.isEnabled());
        outState.putBoolean("nazwiskoEnabled",nazwisko.isEnabled());
        outState.putBoolean("ocenyEnabled",oceny.isEnabled());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        buttonOceny = (Button)findViewById(R.id.button);
        buttonKoniec = (Button)findViewById(R.id.buttonKoniec);
        srednia = (TextView)findViewById(R.id.srednia);
        //odczytanie stanow przyciskow oraz etykiety

        buttonOceny.setVisibility(savedInstanceState.getInt("buttonOcenyVisibility"));

        buttonKoniec.setVisibility(savedInstanceState.getInt("buttonKoniecVisibility"));
        buttonKoniec.setText(savedInstanceState.getString("buttonKoniecText"));

        srednia.setVisibility(savedInstanceState.getInt("sredniaVisibility"));
        srednia.setText(savedInstanceState.getString("sredniaText"));
        //odczytanie boola do tosta
        zdal = savedInstanceState.getBoolean("zdal");

        //edycja pol tekstowych
        imie = (EditText)findViewById(R.id.editTextTextPersonName);
        nazwisko = (EditText)findViewById(R.id.editTextTextPersonSurname);
        oceny = (EditText)findViewById(R.id.editTextNumber);

        imie.setEnabled(savedInstanceState.getBoolean("imieEnabled"));
        nazwisko.setEnabled(savedInstanceState.getBoolean("nazwiskoEnabled"));
        oceny.setEnabled(savedInstanceState.getBoolean("ocenyEnabled"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            Bundle paczka = data.getExtras();
            //pobranie wyniku zwroconego z druiej aktywnosci
            double pom = paczka.getDouble("wynik");
            //wyswietlenie wyniku i wlaczenie widoku etykiety
            //stworzenie lancucha znakow podajac argument uzupelniajacy
            String wyswietl = getString(R.string.sredniatv, pom);
            srednia.setText(wyswietl);
            srednia.setVisibility(View.VISIBLE);
            //zamiana przyciskow
            buttonOceny.setVisibility(View.GONE);
            buttonKoniec.setVisibility(View.VISIBLE);

            //wylaczenie mozliwosci edytowania pol tekstowych
            imie.setEnabled(false);
            nazwisko.setEnabled(false);
            oceny.setEnabled(false);

            if (pom >= 3.0) {//wyswietlenie odpowiedniego tekstu w zaleznosci od sredniej
                buttonKoniec.setText(R.string.buttonKoniecSukces);
                zdal = true;
            } else {
                buttonKoniec.setText(R.string.buttonKoniecFail);
                zdal = false;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        imie = (EditText)findViewById(R.id.editTextTextPersonName);
        nazwisko = (EditText)findViewById(R.id.editTextTextPersonSurname);
        oceny = (EditText)findViewById(R.id.editTextNumber);
        //odczytanie sharedpreferences i stworzenie edytora
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //zapis tekstu w polach do shared preferences
        editor.putString(KLUCZ1,imie.getText().toString());
        editor.putString(KLUCZ2,nazwisko.getText().toString());
        editor.putString(KLUCZ3,oceny.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        imie = (EditText)findViewById(R.id.editTextTextPersonName);
        nazwisko = (EditText)findViewById(R.id.editTextTextPersonSurname);
        oceny = (EditText)findViewById(R.id.editTextNumber);
        //odczytanie shared preferences
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        //odczytanie wartosci o konkretnych kluczach
        imie.setText(preferences.getString(KLUCZ1,""));
        nazwisko.setText(preferences.getString(KLUCZ2,""));
        oceny.setText(preferences.getString(KLUCZ3,""));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(wylacz) {
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            //czyszczenie pamieci sharedpreferences przy niszczeniu aplikacji
            editor.clear();
            editor.commit();
        }
    }

    public void checkButton(boolean i, boolean n, boolean o){
        //sprawdzenie czy wszystkie pola sa dobrze uzupelnione
        if(i && n && o)
            buttonOceny.setVisibility(View.VISIBLE);
        else buttonOceny.setVisibility(View.INVISIBLE);
    }

    public void buttonOnClick(View view) {
        Intent intent = new Intent(this, WpisanieOcen.class);
        //dodanie liczby ocen do intencji tworzacej nowa aktywnosc
        int liczba = Integer.parseInt(oceny.getText().toString());
        intent.putExtra("liczba",liczba);
        startActivityForResult(intent,KOD);
    }

    public void buttonOnClickExit(View view) {
        if(zdal)
            Toast.makeText(MainActivity.this, R.string.toastKoniecSukces, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, R.string.toastKoniecFail, Toast.LENGTH_SHORT).show();
        wylacz=true;
        finish();
    }
}