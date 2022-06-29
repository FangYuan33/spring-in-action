package tacos.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 我的Info Actuator配置器
 */
@Component
public class MyInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("MyHeart", "CL");

        builder.withDetails(hashMap);
    }
}
