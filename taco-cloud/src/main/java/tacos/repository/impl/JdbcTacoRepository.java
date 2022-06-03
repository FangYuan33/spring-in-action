package tacos.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import tacos.domain.Taco;
import tacos.repository.TacoRepository;

import java.sql.*;
import java.time.LocalDateTime;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Taco save(Taco taco) {
        saveTacoInfo(taco);

        for (String ingredientId : taco.getIngredients()) {
            saveIngredientToTaco(ingredientId, taco.getId());
        }

        return taco;
    }

    @SuppressWarnings("all")
    private void saveTacoInfo(Taco taco) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement psst = con
                        .prepareStatement("insert into taco (name, createdAt) values (?, ?)",  new String[] {"id"});
                psst.setString(1, taco.getName());
                psst.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                return psst;
            }
        }, keyHolder);

        taco.setId(keyHolder.getKey().longValue());
    }

    private void saveIngredientToTaco(String ingredientId, long tacoId) {
        jdbcTemplate.update(
                "insert into taco_ingredients (taco, ingredient) values (?, ?)",
                tacoId, ingredientId);
    }
}
