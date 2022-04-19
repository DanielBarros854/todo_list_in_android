package com.example.tarefas.model;

import java.io.Serializable;

public class Tarefa implements Serializable {
    private static int contId = 1;
    private String titulo;
    private String description;
    private int id;

    public Tarefa(String titulo, String description) {
        this.titulo = titulo;
        this.description = description;
        this.id = contId++;
    }

    public static int getContId() {
        return contId;
    }

    public static void setContId(int contId) {
        Tarefa.contId = contId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() { return "id: " + this.id + "\nTitulo: " + this.titulo + "\nDescrição: " + this.description; }
}
