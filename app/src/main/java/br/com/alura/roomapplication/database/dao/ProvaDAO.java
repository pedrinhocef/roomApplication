package br.com.alura.roomapplication.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Calendar;
import java.util.List;

import br.com.alura.roomapplication.models.Prova;

@Dao
public interface ProvaDAO {

    @Insert
    void insere(Prova prova);

    @Query("select * from Prova")
    List<Prova> buscaProva();

    @Query("select * from Prova where dataRealizacao between :inicio and :fim")
    List<Prova> buscaPeloPeriodo(Calendar inicio, Calendar fim);

    @Update
    void altera(Prova prova);

    @Delete
    void deleta(Prova prova);
}
