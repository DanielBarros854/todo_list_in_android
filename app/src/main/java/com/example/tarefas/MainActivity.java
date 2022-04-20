package com.example.tarefas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.list_tarefa_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        selectMenuContext(item);
        return super.onContextItemSelected(item);
    }

    private void selectMenuContext(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.list_tarefa_deletar:
                removeTarefa(item);
                break;

            case R.id.list_tarefa_info:
                showMessage("Nada feito hoje");
                break;
        }
    }

    private void removeTarefa(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Tarefa selectTarefa = adapter.getItem(menuInfo.position);
        dao.remove(selectTarefa);
        adapter.remove(selectTarefa);
        adapter.notifyDataSetChanged();

        showMessage("Tarefa deletada");
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
        configShortClick();
        configLongClick();
    }

    private  void configLongClick() {
        registerForContextMenu(listaTarefas);
    }

    private void configShortClick() {
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

    private void showMessage(String msg) {
        Toast.makeText(
                MainActivity.this,
                msg,
                Toast.LENGTH_SHORT
        ).show();
    }
}