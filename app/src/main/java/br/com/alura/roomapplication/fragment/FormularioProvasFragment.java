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
import br.com.alura.roomapplication.database.conversor.ConversorDeData;
import br.com.alura.roomapplication.database.dao.ProvaDAO;
import br.com.alura.roomapplication.delegate.ProvasDelegate;
import br.com.alura.roomapplication.models.Prova;

public class FormularioProvasFragment extends Fragment{

    private Prova prova = new Prova();
    private EditText campoMateria;
    private EditText campoData;
    private ProvasDelegate delegate;
    private Button cadastrarProva;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = (ProvasDelegate) getActivity();
        delegate.alteraNomeDaActivity("Cadastro de Prova");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_formulario_provas, container, false);

        configuraComponontesDa(view);
        colocaProvaNaTelaSeNecessario();

        return view;
    }

    private void colocaProvaNaTelaSeNecessario() {
        Bundle argumentos = getArguments();
        if (argumentos != null) {
            this.prova = (Prova) argumentos.getSerializable("Prova");
            campoMateria.setText(prova.getMateria());
            campoData.setText(ConversorDeData.converteDo(prova.getDataRealizacao()));
        }
    }

    private void configuraComponontesDa(View view) {
        buscaCamposDa(view);
        cadastrarProva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaInformacoesDaProva();

                persisteProva();
                delegate.voltaParaTelaAnterior();
            }
        });
    }

    private void buscaCamposDa(View view) {
        this.campoMateria = view.findViewById(R.id.formulario_prova_materia);
        this.campoData = view.findViewById(R.id.formulario_prova_data);
        cadastrarProva = view.findViewById(R.id.formulario_prova_cadastrar);
    }

    private void persisteProva() {
        GeradorDeBancoDeDados gerador = new GeradorDeBancoDeDados();
        ProvaDAO provaDAO = gerador.gera(getContext()).getProvaDAO();

        if (ehProvaNova()) {
            provaDAO.insere(prova);
        } else {
            provaDAO.altera(prova);
        }
    }

    private boolean ehProvaNova() {
        return prova.getId() == null;
    }

    private void atualizaInformacoesDaProva() {
        prova.setMateria(campoMateria.getText().toString());
        prova.setDataRealizacao(ConversorDeData.converte(campoData.getText().toString()));
    }
}
