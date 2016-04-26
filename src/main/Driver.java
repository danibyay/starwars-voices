package main;

import io.*;
import record.Grabar;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JFileChooser;

import analysis.*;
import visualization.*;

public class Driver {
	public static void main(String[] argv){
		new Grabar();
		WaveDecoder decoder;
		/*String rutaAudio;
		JFileChooser fc= new JFileChooser();
		int valorRetorno = fc.showOpenDialog(null);
		if (valorRetorno == JFileChooser.APPROVE_OPTION){                
			File archivoEntrada = fc.getSelectedFile();         
			rutaAudio = archivoEntrada.getPath();
			System.out.println("Ruta del audio = "+rutaAudio);*/


		try {
			decoder = new WaveDecoder(new FileInputStream("Grabacion.wav"));
		
			//FFT fft = new FFT(1024, wavFileObj.getSampleRate());
			FFT fft = new FFT(1024, 44100);

			float[] samples = new float[1024];
			float[] spectrum = new float[1024 / 2 + 1];
			float[] lastSpectrum = new float[1024 / 2 + 1];
			List<Float> spectralFlux = new ArrayList<Float>();

			while (decoder.readSamples(samples) > 0) {
				fft.forward(samples);
				System.arraycopy(spectrum, 0, lastSpectrum, 0, spectrum.length);
				System.arraycopy(fft.getSpectrum(), 0, spectrum, 0, spectrum.length);

				float flux = 0;
				for (int i = 0; i < spectrum.length; i++)
					flux += (spectrum[i] - lastSpectrum[i]);
				spectralFlux.add(flux);
			}
			
			
			
			Plot plot = new Plot( "Spectral Flux", 1024, 512 );
			plot.plot( spectralFlux, 1, Color.red );
			
			
			Plot plot2 = new Plot( "Note A Spectrum", 512, 512);
			plot2.plot(fft.getSpectrum(), 1, Color.WHITE );
			
			float[] inverso=fft.getSpectrum();
			System.out.println(inverso.length);
			fft.inverse(inverso);
			
			Plot plo3 = new Plot( "Spectral Flux", 1024, 512 );
			plo3.plot( inverso, 1, Color.BLUE );
			
			System.out.println(inverso.length);
			System.out.println(fft.timeSize());
			
			
			
		
			
			System.out.println(fft.getBandWidth());
			System.out.println(fft.timeSize());
			System.out.println(fft.specSize());
			
			new PlaybackVisualizer(plot2,1,decoder);
			
			
			/*byte byteArray[] = new byte[inverso.length*4];

			// wrap the byte array to the byte buffer
			ByteBuffer byteBuf = ByteBuffer.wrap(byteArray);

			// create a view of the byte buffer as a float buffer
			FloatBuffer floatBuf = byteBuf.asFloatBuffer();

			// now put the float array to the float buffer,
			// it is actually stored to the byte array
			floatBuf.put (inverso);
			
			File out = new File("Grabacionmod.wav");
			AudioFormat format = new AudioFormat((float)44100, 16, 1, true, false);
		    ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
		    AudioInputStream audioInputStream;
		    audioInputStream = new AudioInputStream(bais, format,inverso.length);
		    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
		    audioInputStream.close();*/
			
			
			
			
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("hola2");
		/*} else {                
			System.out.println("Acción de abrir cancelada por el usuario\n");
		}*/


	}
}
