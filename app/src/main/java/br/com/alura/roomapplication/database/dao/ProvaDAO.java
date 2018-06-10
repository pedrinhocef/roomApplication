package br.com.alura.roomapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.alura.roomapplication.models.Prova;

@Dao
public interface ProvaDAO {

    @Insert
    void insere(Prova prova);

    @Query("select * from prova")
    List<Prova> buscaProva();

    @Update
    void altera(Prova prova);

    @Delete
    void deleta(Prova prova);
}
