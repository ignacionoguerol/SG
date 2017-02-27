
package Model;

import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color3f;

/**
 *
 * @author fvelasco
 */

/// Un enumerado para los distintos aspectos de las figuras
public enum Appearances {
  Color(Internal.Color), 
  Texture(Internal.Texture);
  
  private enum Internal { Color, Texture };
  
  private final Appearance ap;
  
  private Appearances(Internal i) {
    ap = new Appearance();
    switch (i) {
      case Color :
        ap.setMaterial(new Material (
            new Color3f (0.20f, 0.20f, 0.20f),   // Color ambiental
            new Color3f (0.00f, 0.00f, 0.00f),   // Color emisivo
            new Color3f (0.49f, 0.34f, 0.00f),   // Color difuso
            new Color3f (0.89f, 0.79f, 0.00f),   // Color especular
            17.0f ));                            // Brillo
        break;
        
      case Texture :
        Texture aTexture = new TextureLoader ("imgs/tierra.jpg", null).getTexture();
        ap.setTexture (aTexture);
        ap.setMaterial (new Material (
            new Color3f (0.20f, 0.20f, 0.20f),   // Color ambiental
            new Color3f (0.00f, 0.00f, 0.00f),   // Color emisivo
            new Color3f (0.50f, 0.50f, 0.50f),   // Color difuso
            new Color3f (0.70f, 0.70f, 0.70f),   // Color especular
            17.0f ));                            // Brillo
        TextureAttributes ta = new TextureAttributes();
        ta.setTextureMode(TextureAttributes.MODULATE);
        ap.setTextureAttributes(ta);
        break;
    }
  }
  
  public Appearance getAppearance () {
    return ap;
  }
}
