package br.com.alura.roomapplication.database;


import android.arch.persistence.room.Room;
import android.content.Context;

public class GeradorDeBancoDeDados {

    public AluraDataBase gera(Context context) {
        AluraDataBase aluraDB = Room
                .databaseBuilder(context, AluraDataBase.class, "AluraDB")
                .allowMainThreadQueries()
                .build();

        return aluraDB;
    }
}

