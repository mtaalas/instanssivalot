import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class VSJqueryMixers {
	
	/**
	 * Constructor
	 */
	public VSJqueryMixers() {
		 
	}
	
	/**
	 * gives all mixers available on this system
	 */
	public static void showMixers() {
		//ArrayList<Mixer.Info> mixInfos = new ArrayList<Mixer.Info>(Arrays.asList(AudioSystem.getMixerInfo()));
		
		List<Mixer.Info> mixers = new ArrayList<Mixer.Info>(Arrays.asList(AudioSystem.getMixerInfo()));
		
		Line.Info sourceDLInfo = new Line.Info(SourceDataLine.class);
		Line.Info targetDLInfo = new Line.Info(TargetDataLine.class);

		Line.Info clipInfo = new Line.Info(Clip.class);
		Line.Info portInfo = new Line.Info(Port.class);

		String support;
		for (Mixer.Info mixInfo: mixers) {
			Mixer mixer = AudioSystem.getMixer(mixInfo);
			support = ", supports ";
			if (mixer.isLineSupported(sourceDLInfo))
				support += "SourceDataLine ";
			if (mixer.isLineSupported(clipInfo))
				support += "Clip ";
			if (mixer.isLineSupported(targetDLInfo))
				support +="TargetDataLine ";
			if (mixer.isLineSupported(portInfo))
				support += "Port ";
			System.out.println("Mixer: " + mixInfo.getName() + support + ", " + mixInfo.getDescription());
		}
	}
}