package br.com.alura.roomapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.alura.roomapplication.models.Aluno;

@Database(entities = {Aluno.class}, version = 1)
public abstract class AluraDataBase extends RoomDatabase{

    public abstract AlunoDAO getAlunoDAO();
}
