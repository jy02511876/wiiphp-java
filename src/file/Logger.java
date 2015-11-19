package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Logger {
	
	//文件的大小
	private long filesize = 0;
	
	//上次的文件大小
	private long lastFilesize = 0;
	
	//当前文件名
	private String filename = "";
	
	//上一次执行的文件名
	private String tmpFilename = "";
	
	//延迟的文件名
	private String lastFilename = "";
	
	//文件生成的时间差
	private final int delayMinutes = 10;	
	
	/*
	 * 实时读取日志
	 */
	public void rsync(String path,String logFilePre) throws FileNotFoundException{		
		final String filePath = path;
		final String filePre = logFilePre; 
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
		exec.scheduleWithFixedDelay(new Runnable() {
			public void run() {
				try {
					//日志文件的时间后缀
					SimpleDateFormat dateFormat =  new SimpleDateFormat("mm",Locale.CHINA);
					Calendar cal = Calendar.getInstance();
					System.out.println(Calendar.MINUTE);
					//取得当前的分钟
					int minute = Integer.parseInt(dateFormat.format(cal.getTime()));
					//处理延迟的日志数据
					//if(minute <= delayMinutes){
					if(true){
						//cal.set(Calendar.HOUR,Calendar.HOUR-1);
						cal.set(Calendar.MINUTE,Calendar.MINUTE -1);
						lastFilename = filePre + dateFormat.format(cal.getTime());
						System.out.println(lastFilename);
						File lastFile = new File(filePath + lastFilename);
						if(lastFile.exists()){
							RandomAccessFile lastRandomFile = new RandomAccessFile(lastFile,"r");
							lastRandomFile.seek(lastFilesize);
							String tmp = "";
							while((tmp = lastRandomFile.readLine()) != null){
								System.out.println(tmp);
							}
							lastFilesize = lastRandomFile.length();
						}
					}else{
						lastFilesize = filesize; 
					}
					
					filename = filePre + dateFormat.format(cal.getTime());
					System.out.println(filename);
					if(!filename.equals(tmpFilename))
						filesize = 0;
					File file = new File(filePath + filename);
					if(!file.exists()) return;
					RandomAccessFile randomFile = new RandomAccessFile(file,"r");
					randomFile.seek(filesize);
					String tmp = "";
					while((tmp = randomFile.readLine()) != null){
						System.out.println(tmp);
					}
					filesize = randomFile.length();
					tmpFilename = filename;
					
					//System.out.println(filesize);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 0, 1, TimeUnit.SECONDS);
		
		
	}
	
	
	public static void main(String[] args) {
		Logger logger = new Logger();
		try {
			logger.rsync("/tmp/logger/","log.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
