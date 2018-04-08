package com.dn.recipeapp.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;
    @Lob
    private String recipeNotes;


    public Notes(String recipeNotes,Recipe recipe) {
        this.recipe = recipe;
        this.recipeNotes = recipeNotes;
    }

    public Notes() {
    }

}
