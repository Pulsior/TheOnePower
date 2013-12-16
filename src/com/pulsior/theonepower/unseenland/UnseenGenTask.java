package com.pulsior.theonepower.unseenland;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.scheduler.BukkitRunnable;

import com.pulsior.theonepower.TheOnePower;

public class UnseenGenTask extends BukkitRunnable{

	Logger log = Bukkit.getLogger();

	@Override
	public void run() {
		
		File file = new File("tel'aran'riod/uid.dat");
		file.delete();
		file = new File("tel'aran'riod");
		file.delete();
		
		try {
			copyDirectory(new File("world"), file);
			new File("tel'aran'riod/uid.dat").delete();
			
			TheOnePower.unseenLand = new UnseenLand();
		} catch (IOException e) {
			System.out.println("Error generating Tel'aran'riod");
		}
		
		Bukkit.getServer().createWorld(new WorldCreator("tel'aran'riod"));
		
	}

	public void copyDirectory(File srcPath, File dstPath) throws IOException{

		if (srcPath.isDirectory()){

			if (!dstPath.exists()){
				dstPath.mkdir();
			}
			String files[] = srcPath.list();

			for(int i = 0; i < files.length; i++){
				copyDirectory(new File(srcPath, files[i]), 
						new File(dstPath, files[i]));
			}
		}
		else{
			if(!srcPath.exists()){
				System.exit(0);
			}
			else{

				InputStream in = new FileInputStream(srcPath);
				OutputStream out = new FileOutputStream(dstPath); 
				// Transfer bytes from in to out
				byte[] buf = new byte[1024];

				int len;

				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				in.close();
				out.close();

			}

		}
	}

}
