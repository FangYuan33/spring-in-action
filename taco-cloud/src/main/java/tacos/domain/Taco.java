package tacos.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Taco {

    @NotNull
    @Size(min = 3, message = "请输入大于仨字儿的名儿")
    private String name;

//    @Size(min = 1, message = "你总得选一个食材吧？")
    private List<String> ingredients;
}
