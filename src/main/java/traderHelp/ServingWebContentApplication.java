package traderHelp;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import traderHelp.gpio.IndicateController;
import traderHelp.repository.ToolsRepository;
import traderHelp.services.StockMarketService;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ServingWebContentApplication {

	@Autowired
    Environment environment;
	
	@Autowired
	static ToolsRepository  repository;
	public static void main(String[] args) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				if (args.length > 0) {
					IndicateController indicateController = new IndicateController();
					for (int i = 0; i < 10; i++) {
						indicateController.indikate();
					}
				}

			}
		}).start();
		System.out.println(repository);
		//new Thread(new StockMarketService(repository)).start();
		SpringApplication.run(ServingWebContentApplication.class, args);
		
	}
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return (args) -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            String[] var4 = beanNames;
            int var5 = beanNames.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String beanName = var4[var6];
                System.out.println(beanName);
            }

            String port = this.environment.getProperty("local.server.port");
            System.out.println(port);
        };
    }

}
