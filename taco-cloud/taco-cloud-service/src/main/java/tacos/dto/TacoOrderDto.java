package tacos.dto;

import lombok.Data;

import java.util.List;

@Data
public class TacoOrderDto {

    private String orderName;

    private String address;

    private String phone;

    private List<Long> tacoIds;
}
