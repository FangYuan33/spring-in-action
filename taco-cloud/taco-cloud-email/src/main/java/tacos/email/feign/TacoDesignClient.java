package tacos.email.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import tacos.dto.TacoDto;

/**
 * 这个Feign调用真的很酷啊
 * <p>
 * FeignClient("taco-cloud")指定服务名，之后具体的调用像SpringMVC里Controller层写的一样啊
 */
@FeignClient("taco-cloud")
public interface TacoDesignClient {

    @PostMapping("/taco/design")
    void designTaco(TacoDto tacoDto);
}
