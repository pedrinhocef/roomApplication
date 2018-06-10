package br.com.alura.roomapplication.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Calendar;
import java.util.List;

import br.com.alura.roomapplication.R;
import br.com.alura.roomapplication.database.GeradorDeBancoDeDados;
import br.com.alura.roomapplication.database.conversor.ConversorDeData;
import br.com.alura.roomapplication.database.dao.ProvaDAO;
import br.com.alura.roomapplication.delegate.ProvasDelegate;
import br.com.alura.roomapplication.models.Prova;

public class ListaProvasFragment extends Fragment {

    private ProvasDelegate delegate;
    private FloatingActionButton botaoAdd;
    private ListView listagem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = (ProvasDelegate) getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.lista_provas_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_lista_prova_calendario) {
            final Context contexto = getContext();

            LinearLayout linearLayout = new LinearLayout(contexto);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            final EditText campoInicio = new EditText(contexto);
            campoInicio.setHint("Inicio");
            campoInicio.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);

            final EditText campoFim = new EditText(contexto);
            campoFim.setHint("Fim");
            campoFim.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);

            linearLayout.addView(campoInicio);
            linearLayout.addView(campoFim);

            new AlertDialog.Builder(contexto)
                    .setView(linearLayout)
                    .setMessage("Digite as datas para busca")
                    .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String dataInicioString = campoInicio.getText().toString();
                            String dataFimString = campoFim.getText().toString();

                            Calendar dataInicio = ConversorDeData.converte(dataInicioString);
                            Calendar dataFim = ConversorDeData.converte(dataFimString);

                            ProvaDAO provaDAO = new GeradorDeBancoDeDados().gera(contexto).getProvaDAO();
                            List<Prova> listProva = provaDAO.buscaPeloPeriodo(dataInicio, dataFim);

                            configuraAdapter(contexto, listProva);

                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);

        configuraListagem(view);

        configuraFAB(view);

        return view;

    }

    private void configuraListagem(View view) {
        listagem = view.findViewById(R.id.fragment_lista);

        Context contexto = getContext();
        final ProvaDAO provaDAO = new GeradorDeBancoDeDados().gera(contexto).getProvaDAO();
        List<Prova> provas = provaDAO.buscaProva();

        final ArrayAdapter<Prova> adapter = configuraAdapter(contexto, provas);

        listagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Prova prova = (Prova) listagem.getItemAtPosition(position);

                delegate.lidaComProvaSelecionado(prova);
            }
        });

        listagem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Prova prova = (Prova) listagem.getItemAtPosition(position);

                String mensagem = "Excluir prova " + prova.getMateria() + " ?";
                Snackbar.make(botaoAdd, mensagem ,Snackbar.LENGTH_LONG)
                        .setAction("Sim", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                provaDAO.deleta(prova);
                                adapter.remove(prova);
                            }
                        })
                        .show();

                return true;
            }
        });
    }

    @NonNull
    private ArrayAdapter<Prova> configuraAdapter(Context contexto, List<Prova> provas) {
        final ArrayAdapter<Prova> adapter = new ArrayAdapter<>(contexto, android.R.layout.simple_list_item_1, provas);
        listagem.setAdapter(adapter);
        return adapter;
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
        delegate.alteraNomeDaActivity("Lista de Provas");
    }
}
