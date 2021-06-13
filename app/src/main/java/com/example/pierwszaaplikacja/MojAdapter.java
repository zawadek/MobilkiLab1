package com.example.pierwszaaplikacja;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MojAdapter extends RecyclerView.Adapter<MojAdapter.OcenyViewHolder> {

    private List<ModelOceny> mListaOcen;
    private LayoutInflater mPompka;


    public MojAdapter(Activity kontekst, List<ModelOceny> listaOcen){
        mPompka = kontekst.getLayoutInflater();
        this.mListaOcen = listaOcen;
    }

    @NonNull
    @Override
    public OcenyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //pobranie wiersza do recycleview (i wstawienie go?)
        View wiersz = mPompka.inflate(R.layout.wiersz_listy_ocen,null);
        return new OcenyViewHolder(wiersz);
    }

    @Override
    public void onBindViewHolder(@NonNull OcenyViewHolder holder, int position) {
        ModelOceny mo = mListaOcen.get(position);

        holder.nazwaPrzedmiotu.setTag(position);
        holder.nazwaPrzedmiotu.setText(mo.getNazwa());

        holder.przyciski.setTag(position);
        switch(mo.getOcena()){
            case 2:
                holder.przyciski.check(R.id.radioButton);break;
            case 3:
                holder.przyciski.check(R.id.radioButton2);break;
            case 4:
                holder.przyciski.check(R.id.radioButton3);break;
            default:
                holder.przyciski.check(R.id.radioButton4);break;
        }
    }

    @Override
    public int getItemCount() {
        return mListaOcen.size();
    }


class OcenyViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {

    TextView nazwaPrzedmiotu;
    RadioGroup przyciski;

    public OcenyViewHolder(@NonNull View itemView) {
        super(itemView);
        nazwaPrzedmiotu = itemView.findViewById(R.id.nazwaPrzedmiotu);
        przyciski = itemView.findViewById(R.id.grupaPrzyciskow);
        przyciski.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        RadioButton rb = group.findViewById(checkedId);
        int wartoscPrzycisku;
        //uzupelnienie wartosci ocen na podstawie zaznaczonego przycisku
        if (null != rb) {
            wartoscPrzycisku = Integer.parseInt(rb.getText().toString());
            int index = (Integer) przyciski.getTag();
            ModelOceny mo = mListaOcen.get(index);
            mo.setOcena(wartoscPrzycisku);
        }

    }
}
}

