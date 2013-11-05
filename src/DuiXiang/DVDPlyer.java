package DuiXiang;

public class DVDPlyer {
	
	boolean canRecord = false;
	
	void recordDVD() {
		System.out.println("DVD recording");
	}

}

class DVDPlayerTesDrive {
	public static void main(String[] args) {
		
		DVDPlayer d = new DVDPlayer();
		d.canRecord = true;
		d.playDVD();
		
		if(d.canRecord == true) {
			d.recordDVD();
		}
	}
}
