package com.example.pierwszaaplikacja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class WpisanieOcen extends AppCompatActivity {
    private int liczba;

    Button button;
    RecyclerView mListaOcen;
    String[] nazwyPrzedmiotow;
    ArrayList<ModelOceny> mDane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //pobranie tablicy z nazwami przedmiotow ze strings.xml
        nazwyPrzedmiotow=getResources().getStringArray(R.array.tablicaOceny);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wpisanie_ocen);
        button = (Button)findViewById(R.id.button2);
        button.setText(R.string.buttonSrednia);
        //pobranie danych przekazanych do intencji
        Bundle paczka = getIntent().getExtras();
        liczba = paczka.getInt("liczba");
        //wypelnienie tablicy danych
        mDane = new ArrayList<ModelOceny>();
        for(int i = 0; i < liczba; i++){
            mDane.add(new ModelOceny(nazwyPrzedmiotow[i],2));
        }
        //stworzenie adaptera z tablicy mDane
        MojAdapter adapter = new MojAdapter(this, mDane);
        //ustawienie adaptera dla recyclerview
        mListaOcen = (RecyclerView)findViewById(R.id.listaOcenrv);
        mListaOcen.setAdapter(adapter);
        mListaOcen.setLayoutManager(new LinearLayoutManager(this));
    }

    public void policzSrednia(View view) {
        double srednia;
        int sumaOcen=0;
        for (ModelOceny val :mDane) {
            sumaOcen += val.getOcena();
        }
        srednia = sumaOcen*1.0 / liczba*1.0;
        //stworzenie intencji i przekazanie wynikow to pierwszej aktywnosci
        Intent intent = new Intent();
        intent.putExtra("wynik",srednia);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //zapisanie wartosci dla radiobuttons
        super.onSaveInstanceState(outState);
        //tablica przechowujaca wartosci ocen z mDane
        int[] oceny = new int[mDane.size()];
        int i=0;
        //wypelnienie tablicy ocenami
        for (ModelOceny val :mDane) {
            oceny[i] = val.getOcena();
            i++;
        }
        outState.putIntArray("oceny",oceny);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        //odczytanie wartosci dla radiobuttons
        super.onRestoreInstanceState(savedInstanceState);
        //pobranie tablicy z ocenami
        int [] oceny = savedInstanceState.getIntArray("oceny");
        //wyczyszczenie listy mDane
        mDane = new ArrayList<ModelOceny>();
        //wypelnianie listy mDane od nowa
        for(int i = 0; i < oceny.length; i++){
            mDane.add(new ModelOceny(nazwyPrzedmiotow[i],oceny[i]));
        }
        //stowrzenie adaptera z odczytanych danych
        MojAdapter adapter = new MojAdapter(this, mDane);
        //ustawienie nowego adaptera
        mListaOcen = (RecyclerView)findViewById(R.id.listaOcenrv);
        mListaOcen.setAdapter(adapter);
        mListaOcen.setLayoutManager(new LinearLayoutManager(this));
    }
}