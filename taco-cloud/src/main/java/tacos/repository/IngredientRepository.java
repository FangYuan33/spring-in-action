package tacos.repository;

import tacos.domain.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Ingredient findById(String id);

    void save(Ingredient ingredient);
}
