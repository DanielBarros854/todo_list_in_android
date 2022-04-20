package com.example.tarefas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tarefas.model.Tarefa;
import com.example.tarefas.model.TarefaDAO;

public class FormTarefaActivity extends AppCompatActivity {

    EditText etTitleTarefa, etDescriptionTarefa;
    Button btnAdicionar;
    private static final TarefaDAO dao = new TarefaDAO();
    Tarefa tarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tarefa);
        setTitle("Cadastrar Tarefa");
        loadWidgets();
        configButtonPersist();
        verifyExtraIntent();
    }

    private void loadWidgets() {
        etTitleTarefa = findViewById(R.id.et_add_title_tarefa);
        etDescriptionTarefa = findViewById(R.id.et_add_description_tarefa);
        btnAdicionar = findViewById(R.id.btn_add_tarefa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_tarefa_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.form_tarefa_menu_salvar:
                validFields();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configButtonPersist() {
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validFields();
            }
        });
    }

    private void validFields() {
        if (fieldsNotEmpty()) {
            persistTarefa();
        } else {
            showMessage("Campo invalido");
        }
    }

    private void persistTarefa() {
        populateTarefa();

        if (editTarefa()) {
            edit();
        } else {
            save();
        }

        finish();
    }

    private boolean fieldsNotEmpty() {
        return !etTitleTarefa.getText().toString().isEmpty()
                && !etDescriptionTarefa.getText().toString().isEmpty();
    }

    private void verifyExtraIntent() {
        if (editTarefa()) {
            setTitle("Editar Tarefa");

            tarefa = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

            etDescriptionTarefa.setText(tarefa.getDescription());
            etTitleTarefa.setText(tarefa.getTitulo());
            btnAdicionar.setText("Editar");
        }
    }

    private void populateTarefa() {
        if (editTarefa()) {
            tarefa.setTitulo(etTitleTarefa.getText().toString());
            tarefa.setDescription((etDescriptionTarefa.getText().toString()));
        } else {
            tarefa = new Tarefa(
                    etTitleTarefa.getText().toString(),
                    etDescriptionTarefa.getText().toString()
            );
        }
    }

    private boolean editTarefa() {
        return getIntent().hasExtra("tarefaSelecionada");
    }

    private void save() {
        dao.save(tarefa);
        showMessage("Tarefa Adicionada");
    }

    private void edit() {
        dao.edit(tarefa);
        showMessage("Tarefa Editada");
    }

    private void showMessage(String msg) {
        Toast.makeText(
                FormTarefaActivity.this,
                msg,
                Toast.LENGTH_SHORT
        ).show();
    }
}