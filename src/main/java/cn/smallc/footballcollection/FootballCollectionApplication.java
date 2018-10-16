package cn.smallc.footballcollection;

import cn.smallc.footballcollection.main.Main_500MixMatch_New;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class FootballCollectionApplication implements CommandLineRunner {

	public static void main(String[] args) {
		System.setProperty("log4j2Filename","notInsertTeamIndex_log");

		SpringApplication.run(FootballCollectionApplication.class, args);
	}

	@Override
	public void run(String[] args) throws Exception {
//		Test.addTeam("E:\\smallC\\workspace\\footballCollection\\src\\main\\resources\\Test.txt");

		int sleeptime = Integer.valueOf(600000);// 抓取时间间隔(毫秒)
		String urlfrom = "500New";// 站点"qiumiwu"
		String logFileName = "500New";// 日志文件

//		sleeptime = Integer.valueOf(args[0]);// 抓取时间间隔(毫秒)
//		urlfrom = args[1];// 站点"qiumiwu"
//		logFileName = args[2];// 日志文件



		boolean loopflag = true;

//		TeamDeal.main(args);

		while (loopflag){

			Long startTime = new Date().getTime();


			if ("500New".equals(urlfrom)){
				Main_500MixMatch_New.main(args);
			}



			if (sleeptime > 0) {
				System.out.println("wait.......");
				try {
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date endTime = new Date();
                    System.out.println("结束时间:" + sdf.format(endTime) + "\n" + "本次消耗时间:" + (endTime.getTime() - startTime)/1000 + "秒");
					Thread.sleep(sleeptime);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

//		System.out.println("run");
	}

}
