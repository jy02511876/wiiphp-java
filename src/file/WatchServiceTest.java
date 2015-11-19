package file;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


/**
 * 
 * @author zhoubin
 *	能监控目录下文件的变化，新增文件，有作用
 */
public class WatchServiceTest {
	
	private static boolean shutdown = false; 
	
	public static void main(String[] args) {
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path dir = FileSystems.getDefault().getPath("/var/www/miwa/protected/runtime");
			WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
			while(!shutdown){
				key = watcher.take();
				for(WatchEvent<?> event : key.pollEvents()){
					if(event.kind() == StandardWatchEventKinds.ENTRY_MODIFY){
						System.out.println("changed");
					}
				}
				key.reset();
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
