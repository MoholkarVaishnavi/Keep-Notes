package com.practise.myapplication.models;

public class NoteModel {
    long id;
    String name;
    String details;

    public NoteModel() {
    }

    public NoteModel(long id, String name, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
