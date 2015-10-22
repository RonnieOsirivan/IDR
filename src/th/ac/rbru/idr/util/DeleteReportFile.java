package th.ac.rbru.idr.util;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;

public class DeleteReportFile {
	
	@Scheduled(cron="0 0 1 ? * *")
	public void printTest(){
		
		File file = new File(StaticValue.ABSULUTEPATH+"/trash");
		try {
			FileUtils.cleanDirectory(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
