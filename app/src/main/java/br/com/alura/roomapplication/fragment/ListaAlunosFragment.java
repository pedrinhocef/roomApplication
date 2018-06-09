package br.com.alura.roomapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.AlunoDAO;
import br.com.alura.roomapplication.database.GeradorDeBancoDeDados;
import br.com.alura.roomapplication.delegate.AlunosDelegate;
import br.com.alura.roomapplication.models.Aluno;

public class ListaAlunosFragment extends Fragment {

    private AlunosDelegate delegate;
    private FloatingActionButton botaoAdd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = (AlunosDelegate) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        final ListView lista = view.findViewById(R.id.fragment_lista);

        final AlunoDAO alunoDAO = new GeradorDeBancoDeDados().gera(getContext()).getAlunoDAO();
        List<Aluno> alunos = alunoDAO.buscaAluno();

        final ArrayAdapter<Aluno> adapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, alunos);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Aluno aluno = (Aluno) lista.getItemAtPosition(position);

                delegate.lidaComAlunoSelecionado(aluno);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Aluno aluno = (Aluno) lista.getItemAtPosition(position);

                String mensagem = "Excluir Aluno " + aluno.getNome() + " ?";
                Snackbar.make(botaoAdd, mensagem ,Snackbar.LENGTH_LONG)
                        .setAction("Sim", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alunoDAO.deleta(aluno);
                                adapter.remove(aluno);
                            }
                        })
                        .show();

                return true;
            }
        });

        configuraFAB(view);

        return view;

    }

    private void configuraFAB(View view) {
        botaoAdd = view.findViewById(R.id.fragment_lista_fab);
        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.lidaComClickFAB();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        delegate.alteraNomeDaActivity("Lista de Alunos");
    }
}
