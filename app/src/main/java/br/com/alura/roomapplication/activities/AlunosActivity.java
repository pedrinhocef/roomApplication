package br.com.alura.roomapplication.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.delegate.AlunosDelegate;
import br.com.alura.roomapplication.fragment.FormularioAlunosFragment;
import br.com.alura.roomapplication.fragment.ListaAlunosFragment;

public class AlunosActivity extends AppCompatActivity implements AlunosDelegate {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);

        exibe(new ListaAlunosFragment(), false);
    }

    private void exibe(Fragment fragment, boolean empilhado) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.alunos_frame, fragment);
        if(empilhado) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }


    @Override
    public void lidaComClickFAB() {
        exibe(new FormularioAlunosFragment() , true);
    }
}
