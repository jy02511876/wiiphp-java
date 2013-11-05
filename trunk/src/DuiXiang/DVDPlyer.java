package DuiXiang;

public class DVDPlyer {
	
	boolean canRecord = false;
	
	void recordDVD() {
		System.out.println("DVD recording");
	}


	void playDVD() {
		System.out.println("DVD playing");
	}
}

class DVDPlayerTesDrive {
	public static void main(String[] args) {
		
		DVDPlyer d = new DVDPlyer();
		d.canRecord = true;
		d.playDVD();
		
		if(d.canRecord == true) {
			d.recordDVD();
		}
	}
}
