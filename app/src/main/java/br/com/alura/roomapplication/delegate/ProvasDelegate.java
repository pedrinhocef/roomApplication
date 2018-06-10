package br.com.alura.roomapplication.delegate;

import br.com.alura.roomapplication.models.Prova;

public interface ProvasDelegate {

    void lidaComClickFAB();

    void voltaParaTelaAnterior();

    void alteraNomeDaActivity(String materia);

    void lidaComProvaSelecionado(Prova prova);
}
