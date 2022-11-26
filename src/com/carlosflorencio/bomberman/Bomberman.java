package com.carlosflorencio.bomberman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.carlosflorencio.bomberman.exceptions.BombermanException;
import com.carlosflorencio.bomberman.gui.Frame;

public class Bomberman {
	static ArrayList<Double> configOpciones = new ArrayList<Double>(6);
	static int BOMBRATE_ = 1;
	static int BOMBRADIUS_ = 1;
	static double PLAYERSPEED_ = 1.0;
	static int TIME_ = 200;
	static int POINTS_ = 0;
	static int LIVES_ = 3;

	static public void leerConfig() {
		boolean reintentar = false;

		do {
			String linea = new String();

			File configFile = new File("config.ini");
			try {
				if (configFile.createNewFile() || reintentar) {
					FileWriter defaultConfig = new FileWriter("config.ini");

					defaultConfig.write("bomb_rate=1\nbomb_radius=1\nplayer_speed=1.0\ntime=200\npoints=0\nlives=3");
					defaultConfig.close();
					reintentar = false;
				} else {

					if (configFile.exists()) {
						RandomAccessFile configFileRandom = new RandomAccessFile("config.ini", "r");

						for (int i = 0; i < 6; i++) {
							linea = configFileRandom.readLine();
							StringTokenizer token = new StringTokenizer(linea, "=");
							token.nextToken();

							configOpciones.add(Double.valueOf(token.nextToken())); // Lo convierte a Double
						}
						configFileRandom.close();

						BOMBRATE_ = configOpciones.get(0).intValue(); // Lo convierte a int
						BOMBRADIUS_ = configOpciones.get(1).intValue(); // Lo convierte a int
						PLAYERSPEED_ = (double) configOpciones.get(2);
						TIME_ = configOpciones.get(3).intValue(); // Lo convierte a int
						POINTS_ = configOpciones.get(4).intValue(); // Lo convierte a int
						LIVES_ = configOpciones.get(5).intValue(); // Lo convierte a int

						reintentar = false;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println(
						"Ha habido un problema con el archivo de configuración config.ini, creando uno nuevo...");
				reintentar = true;
			}

		} while (reintentar);

	}

	public static void main(String[] args) throws BombermanException {
		leerConfig();
		Frame mainwindow = new Frame();
	}
}
