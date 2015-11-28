package Utility;

import java.io.File;

import javax.swing.ImageIcon;

public class Media {
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	private static String local;
	
	public static void setLocal() {
		if(OS.indexOf("nux") >= 0) {
			local = "/";
		}
		else {
			local = "\\";
		}
	}
	
	public static ImageIcon[] getAnimation() {
		setLocal();
		ImageIcon[] images = new ImageIcon[30];
		for(int i = 0; i < images.length; i++) {
			images[i] = new ImageIcon("animation"+local+"frame"+i+".gif");
		}
		return images;
	}
	
	public static ImageIcon getImageMenu() {
		setLocal();
		return new ImageIcon("img"+local+"space_Menu.gif");
	}
	
	public static ImageIcon getImagePause() {
		setLocal();
		return new ImageIcon("img"+local+"space_Pause.gif");
	}
	
	public static ImageIcon getImageEnd() {
		setLocal();
		return new ImageIcon("img"+local+"space_End.gif");
	}
	
	public static File getThemeMusic() {
		setLocal();
		return new File("music"+local+"Skydive.wav");
	}
	
	public static File getInGameMusic() {
		setLocal();
		return new File("music"+local+"InGameMusic.wav");
	}

}
