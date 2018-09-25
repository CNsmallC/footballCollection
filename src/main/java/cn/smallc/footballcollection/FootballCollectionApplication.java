package cn.smallc.footballcollection;

import cn.smallc.footballcollection.extractor.tag.Insert_CrawlerDeal_500MixMatch_New;
import cn.smallc.footballcollection.extractor.tag.TeamDeal;
import cn.smallc.footballcollection.main.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FootballCollectionApplication implements CommandLineRunner {

	public static void main(String[] args) {
		System.setProperty("log4j2Filename","notInsertTeamIndex_log");

		SpringApplication.run(FootballCollectionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Test.addTeam("E:\\smallC\\workspace\\footballCollection\\src\\main\\resources\\Test.txt");

//		TeamDeal.main(args);

		TeamDeal.main(args);

//		System.out.println("run");
	}

}
