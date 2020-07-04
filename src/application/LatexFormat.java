package application;

import java.awt.image.BufferedImage;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;


public class LatexFormat {

    
    private TeXFormula  lenguajeTex;

	public LatexFormat ( ) {
		this.lenguajeTex = new TeXFormula();
	}
	
	
	public Image getLnguaje(String texto) {

		this.lenguajeTex = new TeXFormula( texto );
		java.awt.Image awtImage = lenguajeTex.createBufferedImage(TeXConstants.STYLE_TEXT, 20, java.awt.Color.BLACK, null);
	    Image fxImage = SwingFXUtils.toFXImage((BufferedImage) awtImage, null);
	    return fxImage;
		
	}
}
