package br.com.alura.roomapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.delegate.ProvasDelegate;
import br.com.alura.roomapplication.fragment.FormularioProvasFragment;
import br.com.alura.roomapplication.fragment.ListaProvasFragment;
import br.com.alura.roomapplication.models.Prova;

public class ProvasActivity extends AppCompatActivity implements ProvasDelegate {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        exibe(new ListaProvasFragment(), false);
    }

    private void exibe(Fragment fragment, boolean empilhado) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.provas_frame, fragment);
        if(empilhado) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void lidaComClickFAB() {
        exibe(new FormularioProvasFragment() , true);
    }

    @Override
    public void voltaParaTelaAnterior() {
        onBackPressed();
    }

    @Override
    public void alteraNomeDaActivity(String materia) {
        setTitle(materia);
    }

    @Override
    public void lidaComProvaSelecionado(Prova prova) {
        FormularioProvasFragment formulario = new FormularioProvasFragment();
        Bundle argumentos = new Bundle();
        argumentos.putSerializable("Prova", prova);
        formulario.setArguments(argumentos);
        exibe(formulario, true);
    }

}
