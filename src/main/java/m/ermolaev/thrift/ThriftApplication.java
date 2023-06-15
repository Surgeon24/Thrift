package m.ermolaev.thrift;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationServiceException;

@SpringBootApplication
public class ThriftApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThriftApplication.class, args);
	}

}
