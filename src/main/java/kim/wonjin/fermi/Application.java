package kim.wonjin.fermi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// TODO 데이터
@SpringBootApplication
//@EnableJpaRepositories(basePackages = "kim.wonjin", entityManagerFactoryRef = "entityManagerFactory")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
