package tacos.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

// 配置启用Feign 断路器Hystrix
@EnableHystrix
@EnableFeignClients
@SpringBootApplication
public class TacoEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoEmailApplication.class, args);
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
	  return new RestTemplate();
	}
	
}
