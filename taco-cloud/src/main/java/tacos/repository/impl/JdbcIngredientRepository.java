package tacos.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacos.domain.Ingredient;
import tacos.repository.IngredientRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("select id, name, type from ingredient", this::mapRowToIngredient);
    }

    @Override
    public Ingredient findById(String id) {
        return jdbcTemplate.queryForObject("select id, name, type from ingredient where id = ?",
                this::mapRowToIngredient, id);
    }

    /**
     * 映射成Java对象
     */
    private Ingredient mapRowToIngredient(ResultSet resultSet, int rowNum) throws SQLException {
        return new Ingredient(resultSet.getString("id"), resultSet.getString("name"),
                Ingredient.Type.valueOf(Ingredient.Type.class, resultSet.getString("type")));
    }

    @Override
    public void save(Ingredient ingredient) {
        jdbcTemplate.update("insert into ingredient (id, name, type) values (?, ?, ?)",
        ingredient.getId(), ingredient.getName(), ingredient.getType().toString());
    }
}
