package asavovic.courseProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CourseProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseProjectApplication.class, args);
	}

}
