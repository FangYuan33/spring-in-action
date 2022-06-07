package tacos.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Order {

    private Long id;

    @NotBlank(message="Name is required")
    private String orderName;

    @NotBlank(message="Street is required")
    private String street;

    private String city;

    private String state;

    private String zip;

    private String ccNumber;

    private String ccExpiration;

    private String ccCVV;

    private LocalDateTime placedAt;

    private Long userId;

    /**
     * 订单中保存多个taco
     */
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        tacos.add(taco);
    }
}
