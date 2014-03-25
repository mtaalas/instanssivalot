
import javax.sound.sampled.*;

import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import edu.emory.mathcs.jtransforms.fft.*;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		VSJqueryMixers.showMixers();
		
		AudioFormat format = new AudioFormat(44100, 16, 2, true, true);

	    TargetDataLine line = null;
	    DataLine.Info info = new DataLine.Info(TargetDataLine.class,
	            format); 
	    if (!AudioSystem.isLineSupported(info)) {

	    }

	    try {
	        line = (TargetDataLine) AudioSystem.getLine(info);
	        line.open(format);
	    } catch (LineUnavailableException ex) {
	        // Handle the error ...
	    }

	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    int numBytesRead;
	    System.out.println(line.getBufferSize());
	    byte[] data = new byte[line.getBufferSize()/50];


	    line.start();

	    while (true){
	    	//RealFFTUtils_2D fft = new RealFFTUtils_2D(arg0, arg1)
	        numBytesRead = line.read(data, 0, data.length);
	        // Save this chunk of data.
	        out.write(data, 0, numBytesRead);
	        for(int i=0; i<numBytesRead; i+=1) {
	            //System.out.println("Sample " + i +" " + Byte.toString(data[i]));
	        	
	        }
	    }
	    
		//JavaSoundRecorder recorder= new JavaSoundRecorder();
		//recorder.test();
	}
}

	/**final ByteArrayOutputStream out = new ByteArrayOutputStream();
	float sampleRate = 8000;
	int sampleSizeInBits = 8;
	int channels = 1;
	boolean signed = true;
	boolean bigEndian = true;
	final AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
			bigEndian);
	DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
	final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
	line.open(format);
	line.start();
	Runnable runner = new Runnable() {
		int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
	
		byte buffer[] = new byte[bufferSize];
	
		public void run() {
			try {
	
				int count = line.read(buffer, 0, buffer.length);
				if (count > 0) {
					out.write(buffer, 0, count);
				}
	
				out.close();
			} catch (IOException e) {
				System.err.println("I/O problems: " + e);
				System.exit(-1);
			}
		}
	};
	Thread captureThread = new Thread(runner);
	captureThread.start();
	
	byte audio[] = out.toByteArray();
	InputStream input = new ByteArrayInputStream(audio);
	final SourceDataLine line1 = (SourceDataLine) AudioSystem.getLine(info);
	final AudioInputStream ais = new AudioInputStream(input, format, audio.length
			/ format.getFrameSize());
	line1.open(format);
	line1.start();
	
	runner = new Runnable() {
		int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
	
		byte buffer[] = new byte[bufferSize];
	
		public void run() {
			try {
				int count;
				while ((count = ais.read(buffer, 0, buffer.length)) != -1) {
					if (count > 0) {
						line1.write(buffer, 0, count);
					}
				}
				line1.drain();
				line1.close();
			} catch (IOException e) {
				System.err.println("I/O problems: " + e);
				System.exit(-3);
			}
		}
	};
	Thread playThread = new Thread(runner);
	playThread.start();
	*/
