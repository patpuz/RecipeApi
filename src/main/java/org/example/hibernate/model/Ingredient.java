package org.example.hibernate.model;

import javax.persistence.*;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "ingredient_name", nullable = false, length = 100)
    private String ingredientName;

    public Long getId() {
        return id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
}
