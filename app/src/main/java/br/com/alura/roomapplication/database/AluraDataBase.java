package br.com.alura.roomapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import br.com.alura.roomapplication.database.conversor.ConversorDeData;
import br.com.alura.roomapplication.database.dao.AlunoDAO;
import br.com.alura.roomapplication.database.dao.ProvaDAO;
import br.com.alura.roomapplication.models.Aluno;
import br.com.alura.roomapplication.models.Prova;

@Database(entities = {Aluno.class, Prova.class}, version = 3)
@TypeConverters(ConversorDeData.class)
public abstract class AluraDataBase extends RoomDatabase{

    public abstract AlunoDAO getAlunoDAO();
    public abstract ProvaDAO getProvaDAO();
}
