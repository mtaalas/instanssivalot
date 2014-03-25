package com.enymind.audio;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Enymind Oy
 */
public class LineInFFTtoUDP implements FFTInterruptable {

  public LineInFFTtoUDP() {
    // Searching for any input lines
    AudioLineFinder finder = new AudioLineFinder();
    List<Line> inputLines = finder.getInputLines();

    // If not any input lines found
    if (inputLines.size() < 1) {
      System.err.println("Not any input lines found on your system.");
      System.exit(1);
    }

    // Ask user to select desired input line
    System.out.println("Select one of the found audio input lines:");
    int lineCounter = 0;
    for (Line line : inputLines) {
      System.out.println("[" + lineCounter + "] " + line.getLineInfo().toString());
      lineCounter++;
    }
    Scanner input = new Scanner(System.in);
    System.out.print("Enter number of line selected: ");
    int lineIndex = input.nextInt();

    // Initialize selected input line
    TargetDataLine line = null;
    try {
      line = (TargetDataLine) inputLines.get(lineIndex);
      System.out.println("Selected: [" + lineIndex + "] " + line.getLineInfo().toString());
    } catch (ClassCastException cce) {
      System.err.println("Selected wrong type of input line: " + cce.getMessage());
      System.exit(1);
    }

    // If everything went well, open line
    if (line != null) {
      try {
        // AudioFormat format = line.getFormat();
        AudioFormat format = new AudioFormat(44100, 16, 1, true, true);
        line.open(format);
        line.start();
      } catch (LineUnavailableException lue) {
        System.err.println("Selected line unavailable: " + lue.getMessage());
        System.exit(1);
      }
    }

    // Start processing FFT in separate thread
    Thread processor = new Thread(new LineProcessor(this, line));
    processor.start();
  }

  @Override
  public void fftInterrupt(double[] spectrum) {
    // TODO: Implement UDP sending
    System.out.println(Arrays.toString(spectrum));
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    LineInFFTtoUDP liftu = new LineInFFTtoUDP();
  }
}
