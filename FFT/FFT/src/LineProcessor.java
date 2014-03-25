import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;
import java.util.Arrays;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Enymind Oy
 */
public class LineProcessor implements Runnable {

  FFTInterruptable parent;
  TargetDataLine line;

  public LineProcessor(FFTInterruptable parent, TargetDataLine line) {
    this.parent = parent;
    this.line = line;
  }

  @Override
  public void run() {
    int bufferLen = line.getBufferSize() / 1024;
    System.out.println("Starting thread with buffer size of: " + bufferLen);

    int bytesRead;
    byte[] data = new byte[bufferLen];
    double[] ddata = new double[bufferLen];
    double[] spectrum = new double[bufferLen/2];
    DoubleFFT_1D fft = new DoubleFFT_1D(bufferLen);

    while (!Thread.interrupted()) {
      bytesRead = line.read(data, 0, data.length);
      for (int i = 0; i < bufferLen - 1; i++) {
        ddata[i] = data[i];
      }

      fft.realForward(ddata);

      for (int k = 0; k < (bufferLen / 2); k++) {
        spectrum[k] = Math.sqrt(Math.pow(ddata[2 * k], 2) + Math.pow(ddata[2 * k + 1], 2));
      }

      this.parent.fftInterrupt(spectrum);
    }
  }
}
