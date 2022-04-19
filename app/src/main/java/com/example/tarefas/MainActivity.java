package com.example.tarefas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.tarefas.model.Tarefa;
import com.example.tarefas.model.TarefaDAO;

public class MainActivity extends AppCompatActivity {

    private ListView listaTarefas;
    private FloatingActionButton fabAddTarefas;
    final static TarefaDAO dao = new TarefaDAO();
    private ArrayAdapter<Tarefa> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadWidgets();
        configFABAddTask();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configListTarefa();
    }

    private  void loadWidgets() {
        listaTarefas = findViewById(R.id.lista_tarefas);
        fabAddTarefas = findViewById(R.id.fab_add_tarefa);
    }

    private void configFABAddTask() {
        fabAddTarefas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(
                        MainActivity.this,
                        FormTarefaActivity.class
                ));
            }
        });
    }

    private void configListTarefa() {
        configAdapterList();
        recoveryAdapter();
        configActionClickList();
    }

    private void configAdapterList() {
        adapter = new ArrayAdapter<Tarefa>(
                MainActivity.this,
                android.R.layout.simple_list_item_1
        );
        listaTarefas.setAdapter(adapter);
    }

    private void recoveryAdapter() {
        adapter.clear();
        adapter.addAll(dao.findAll());
    }

    private void configActionClickList() {
        listaTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tarefa selectTarefa = (Tarefa) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(
                        MainActivity.this,
                        FormTarefaActivity.class
                );
                intent.putExtra("tarefaSelecionada", selectTarefa);
                startActivity(intent);
            }
        });
    }
}