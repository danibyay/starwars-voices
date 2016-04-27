package main;

import io.*;
import record.Grabar;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import analysis.*;
import visualization.*;

public class Driver4 {
	private int sel;
	
	public Driver4(int sel){
		//seleccionar efecto, 
		this.sel = sel;
	}
	
	public Driver4(){
		this.sel=1;
	}
	
	public void reproducirFourier(){
		try {
			WaveDecoder decoder = new WaveDecoder(new FileInputStream("Grabacion.wav"));//Abrir archivo de audio grabado
			FFT fft = new FFT(1024, 44100);

			float[][] samples = new float[204][1024];
			float[] temp=new float[513];
			float[][] inversa=new float[204][1024];


			for(int i=1;i<204;i++){
				
				decoder.readSamples( samples[i] );						//Pasar audio a un arreglo
				//fft.window(FourierTransform.HAMMING);
				
				fft.forward(samples[i]);								//Hacer Fourier a dicho arreglo
				
				for (int j=fft.freqToIndex(0);j<fft.freqToIndex(100);j++){
					fft.scaleBand(j, 0);											//Modificar banda de frecuencias
				}
				if(sel==1){//Darth Vader
					for (int j=0;j<fft.specSize();j++){
						float ampli=fft.getBand(j);
						fft.setBand((int)(j*0.7), ampli*2);
					}
				}else if(sel==2){//Storm Trooper
					for (int j=0;j<fft.specSize();j++){
						float ampli=fft.getBand(j);
						fft.setBand((int)(j*0.9), ampli*2);
					}
				}else if(sel==3){//C3P0
					for (int j=(int)(fft.specSize()/2.5);j>fft.specSize()/40;j--){
						float ampli=fft.getBand(j);
						fft.setBand((int)(j*2.4), ampli*2);
					}
				}else if(sel==4){//R2D2
					for (int j=(int)(fft.specSize()/5);j>fft.specSize()/100;j--){
						float ampli=fft.getBand(j);
						fft.setBand((int)(j*4.9), ampli*2);
					}
				}else{//YODA
					for (int j=(int)(fft.specSize()/10);j>fft.specSize()/100;j--){
						float ampli=fft.getBand(j);
						fft.setBand((int)(j*2), ampli*2);
					}
				}
				
				temp = fft.getSpectrum();
				//Guardar espectro de frecuencias en arreglo
				for(int j=0;j<temp.length;j++){		//Guardar espectro de frecuencias en arreglo
					inversa[i][j]=temp[j];			
				}			
				
				fft.inverse(inversa[i]);			//Transformada Inversa de Fourier(Regresar al dominio del tiempo)

				
				/*Plot plot0 = new Plot( "Samples", 1024, 512 );  // descomentar si se desea ver la gr�fica
				plot0.plot( samples[i], 1, Color.YELLOW );
				plot0.plot( inversa[i], 1, Color.BLUE );

				System.out.println(i);*/
			}
			
			
			//Reproducir audio modificado
			AudioDevice device=new AudioDevice();
			for(int i=0;i<204;i++){
				device.writeSamples(inversa[i]);
			}


		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	public void grabarAudio(){
		new Grabar(); //Grabar archivo de audio localmente(Grabacion.wav)
	}
	
	
	public int getSel() {
		return sel;
	}

	public void setSel(int sel) {
		this.sel = sel;
	}

	public static void main(String[] argv){
		Driver4 d = new Driver4();
		d.reproducirFourier();
		
	}
}
