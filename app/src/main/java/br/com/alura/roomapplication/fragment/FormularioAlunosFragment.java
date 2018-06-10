package br.com.alura.roomapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.GeradorDeBancoDeDados;
import br.com.alura.roomapplication.database.dao.AlunoDAO;
import br.com.alura.roomapplication.delegate.AlunoDelegate;
import br.com.alura.roomapplication.models.Aluno;

public class FormularioAlunosFragment extends Fragment {

    private Aluno aluno = new Aluno();
    private EditText campoNome;
    private EditText campoEmail;
    private AlunoDelegate delegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = (AlunoDelegate) getActivity();
        delegate.alteraNomeDaActivity("Cadastro de Aluno");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_alunos, container, false);

        configuraComponontesDa(view);
        colocaAlunoNaTelaSeNecessario();

        return view;
    }

    private void colocaAlunoNaTelaSeNecessario() {
        Bundle argumentos = getArguments();
        if (argumentos != null) {
            this.aluno = (Aluno) argumentos.getSerializable("Aluno");
            campoNome.setText(aluno.getNome());
            campoEmail.setText(aluno.getEmail());
        }
    }

    private void configuraComponontesDa(View view) {
        this.campoNome = view.findViewById(R.id.formulario_alunos_nome);
        this.campoEmail = view.findViewById(R.id.formulario_alunos_email);

        Button cadastrarAluno = view.findViewById(R.id.formulario_alunos_cadastrar);
        cadastrarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaInformacoesDoAluno();

                persisteAluno();
                delegate.voltaParaTelaAnterior();
            }
        });
    }

    private void persisteAluno() {
        GeradorDeBancoDeDados gerador = new GeradorDeBancoDeDados();
        AlunoDAO alunoDAO = gerador.gera(getContext()).getAlunoDAO();

        if (ehAlunoNovo()) {
            alunoDAO.insere(aluno);
        } else {
            alunoDAO.altera(aluno);
        }
    }

    private boolean ehAlunoNovo() {
        return aluno.getId() == null;
    }

    private void atualizaInformacoesDoAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEmail(campoEmail.getText().toString());
    }
}
