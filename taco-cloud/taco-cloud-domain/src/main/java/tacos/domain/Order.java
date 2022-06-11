package tacos.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("taco_order")
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {

    private String orderName;

    private String address;

    private String state;

    private String phone;

}
