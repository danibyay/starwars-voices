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

public class Driver2 {
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
			System.out.println("hola1");
			//FFT fft = new FFT(1024, wavFileObj.getSampleRate());
			FFT fft = new FFT(1024, 44100);

			float[] samples = new float[1024];
			float[] spectrum = new float[1024 / 2 + 1];
			float[] lastSpectrum = new float[1024 / 2 + 1];
			List<Float> spectralFlux = new ArrayList<Float>();
			
			decoder.readSamples(samples);
			
			fft.forward(samples);
			
			
			
			
			//Espectro de frecuencias
			Plot plot2 = new Plot( "Note A Spectrum", 512, 512);
			plot2.plot(fft.getSpectrum(), 1, Color.WHITE );
			
			/*float[] inverso1=new float[fft.getSpectrum().length];
			for(int i=0;i<inverso1.length;i++){
				inverso1[i]=fft.getSpectrum()[i];
			}
			fft.inverse(inverso1);
			
			Plot plot = new Plot( "Spectral Flux", 512, 512 );
			plot.plot( inverso1, 1, Color.WHITE );*/
			
			
			//Escalar amplitud en rango de frecuencias
			for (int i=fft.freqToIndex(400);i<fft.freqToIndex(4000);i++){
				fft.scaleBand(i, 0.5f);
			}
			Plot plo4 = new Plot( "Spectral Flux", 512, 512 );
			plo4.plot( fft.getSpectrum(), 1, Color.BLUE );
			
			float[] inverso=new float[fft.getSpectrum().length];
			for(int i=0;i<inverso.length;i++){
				inverso[i]=fft.getSpectrum()[i];
			}
			fft.inverse(inverso);
			
			Plot plo3 = new Plot( "Spectral Flux", 512, 512 );
			plo3.plot( inverso, 1, Color.BLUE );
			
			
			
			System.out.println(fft.getBandWidth());
			System.out.println(fft.timeSize());
			System.out.println(fft.specSize());
			
			new PlaybackVisualizer(plot2,1,decoder);
			
			
			File out = new File("Grabacionmod.wav");
			
			
			
			
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
