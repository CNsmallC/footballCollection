package cn.smallc.footballcollection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FootballCollectionApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FootballCollectionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("run");
	}

}
