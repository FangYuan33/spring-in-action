package tacos.dto;

import lombok.Data;

import java.util.List;

@Data
public class TacoDto {

    private String name;

    private List<String> ingredients;
}
