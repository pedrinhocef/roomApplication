package br.com.alura.roomapplication.delegate;

import br.com.alura.roomapplication.models.Aluno;

public interface AlunosDelegate {

    void lidaComClickFAB();

    void voltaParaTelaAnterior();

    void alteraNomeDaActivity(String nome);

    void lidaComAlunoSelecionado(Aluno aluno);
}
