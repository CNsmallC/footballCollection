package cn.smallc.footballcollection;

import cn.smallc.footballcollection.main.Test;
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
//		Test.addTeam("E:\\smallC\\workspace\\footballCollection\\src\\main\\resources\\Test.txt");



//		System.out.println("run");
	}

}
