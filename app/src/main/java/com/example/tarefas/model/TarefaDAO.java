package com.example.tarefas.model;

import java.util.ArrayList;

public class TarefaDAO {
    private final static ArrayList<Tarefa> tarefas = new ArrayList<>();

    public void save(Tarefa tarefa) { tarefas.add(tarefa); }

    public ArrayList<Tarefa> findAll() {
        return new ArrayList<>(tarefas);
    };

    public Tarefa findTarefaByID(Tarefa t) {
        for (Tarefa tarefa : tarefas) {
            if (tarefa.getId() == t.getId()) {
                return tarefa;
            }
        }
        return null;
    };

    public void edit(Tarefa tarefa) {
        Tarefa edit = findTarefaByID(tarefa);

        if (edit != null) {
            int posicao = tarefas.indexOf(edit);
            tarefas.set(posicao, tarefa);
        }
    }

    public void remove(Tarefa tarefa) {
        Tarefa remove = findTarefaByID(tarefa);
        if (remove != null) {
            tarefas.remove(remove);
        }
    }
}
