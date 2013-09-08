/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.university.tdi.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;

import org.dvb.ui.DVBColor;
import org.havi.ui.HComponent;
import org.havi.ui.HContainer;
import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;
import org.havi.ui.HSceneTemplate;
import org.havi.ui.HStaticText;

/**
 * 
 * @author Johan Tique
 */
public class AppXlet extends HComponent implements Xlet, KeyListener {

	private XletContext context;
	private HScene scene;
	private String messageToDisplay;
	private HStaticText text;

	public AppXlet() {
		super();
		messageToDisplay = "Hello MHP World";
	}

	public void initXlet(XletContext ctx) throws XletStateChangeException {
		//Obtenemos y especificamos el contexto
		this.context = ctx;
		//creamos la escena
		create_scene();
	}

	public void pauseXlet() {
		scene.setVisible(false);
	}

	public void startXlet() throws XletStateChangeException {
		//anadimos elementos graficos
		add_graphic_elements();
		scene.setVisible(true);
		scene.repaint();
	}

	public void destroyXlet(boolean unconditional)
			throws XletStateChangeException {
		if (unconditional) {
			if (scene != null) {
				scene.removeKeyListener(this);
				scene.remove(text);
				scene.remove(this);
				scene.setVisible(false);
				HSceneFactory.getInstance().dispose(scene);
				scene = null;
			}
			context.notifyDestroyed();
		} else {/*
				 * We have had a polite request to die, so we can refuse this
				 * request if we want.
				 */
			throw new XletStateChangeException("Please don't let me die!");
		}
	}

	private void create_scene() {
		//Creando un SceneFactory
		HSceneFactory factory = HSceneFactory.getInstance();
		//Fijando las caracteristicas de la plantilla de la escena
		HSceneTemplate hst = new HSceneTemplate();
		hst.setPreference(HSceneTemplate.SCENE_SCREEN_DIMENSION,
						new org.havi.ui.HScreenDimension(1, 1),
						HSceneTemplate.REQUIRED);
		hst.setPreference(HSceneTemplate.SCENE_SCREEN_LOCATION,
				new org.havi.ui.HScreenPoint(0, 0), HSceneTemplate.REQUIRED);
		//Obteniendo la escena con las caracteristicas especificadas
		scene = factory.getBestScene(hst);
		
		//Estableciendo las dimensiones
		Rectangle rect = scene.getBounds();
		
		//configurando la escena
		scene.setBounds(rect);
		scene.setBackgroundMode(HScene.BACKGROUND_FILL);
		scene.setBackground(Color.black);
		
		//estableciendo las dimensiones de la clase padre HComponent, metodo valido para trabajar graficos de bajo nivel AWT
		//una vez cestablecido el anterior parametro, procedemos a anadir el Hcomponent a la escena como argumento grafico 
		setBounds(rect);
		scene.add(this);
		
		//Fijamos los listener del control remoto
		scene.addKeyListener(this);
	}

	private void add_graphic_elements() {
		//este metodo anade elementos graficos basandose en la libreria grafica de nivel 2 HAVi
		text = new HStaticText("Libreria HAVi: " + messageToDisplay, 70, 120,
				580, 40);
		text.setBordersEnabled(false);
		text.setForeground(Color.white);
		text.setFont(new Font("Tiresias", Font.BOLD, 24));
		scene.add(text);
	}

	public void paint(Graphics g) {
		//este metodo anade elementos canvas usando la libreria AWT, este es invocado cada vez que se llama el metodo repaint.
		System.out.println("Estoy en Paint");
		g.setColor(new DVBColor(255, 255, 255, 255));
		Font font = new Font("Tiresias", 0, 25);
		g.setFont(font);
		g.drawString("Libreria AWT: " + messageToDisplay, 180, 407);
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}
}
