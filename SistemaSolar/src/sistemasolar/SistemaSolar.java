/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemasolar;

import GUI.ControlWindow;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.Viewer;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.awt.Color;
import static java.awt.Color.white;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.media.j3d.Texture;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author Nacho
 */
public class SistemaSolar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Se obtiene la configuración gráfica del sistema y se crea el Canvas3D que va a mostrar la imagen
        Canvas3D canvas = new Canvas3D (SimpleUniverse.getPreferredConfiguration());
        // Se le da el tamaño deseado
        canvas.setSize(1920, 1080);
        
        //Se declara el origen del arbol
        BranchGroup root = new BranchGroup();
        
        
        //*********************************************************************
        //*************************Background**********************************
        //*********************************************************************
         
        //Creo el fondo y defino el area de influencia con forma de esfera. 
        Background background = new Background ();
        background.setApplicationBounds (new BoundingSphere 
        (new Point3d (0.0, 0.0, 0.0), 100.0));
        
        //Defino un nuevo aspecto
        Appearance appearance = new Appearance ();
        //Cargo una textura y se la añado al aspecto creado. 
        Texture texture = new TextureLoader ("imgs/back.jpg", null).getTexture();
        appearance.setTexture (texture);
        
        //Creo la esfera que va a ser utilizada de fondo. 
        //    - Se generan las coordenadas de textura.
        //    - Se generan las normales hacia dentro, ya que queremos que la textura
        //      se vea en el interior. 
        //    - Se le da el aspecto creado antes. 
        
        Primitive sphere = new Sphere (1.0f, Primitive.GENERATE_TEXTURE_COORDS 
                | Primitive.GENERATE_NORMALS_INWARD, 64, appearance);
        
        // Se crea la rama para la geometría del fondo, 
        BranchGroup bgGeometry = new BranchGroup ();
        // Se le añade la esfera
        bgGeometry.addChild (sphere);
        // Y se establece como geometría del objeto background
        background.setGeometry (bgGeometry);
        
        //Creo un branchgroup para el fondo y le añado el fondo como hijo.
        BranchGroup bgbranch = new BranchGroup();
        bgbranch.addChild(background);
       
        //Añado el branchgroupo del fondo como hijo de root.
        root.addChild(bgbranch);
        
        //*********************************************************************
        //*********************END Background**********************************
        //*********************************************************************
        
        //*********************************************************************
        //*********************Lights******************************************
        //*********************************************************************
        
        //Creo el BranchGroup para las luces
        BranchGroup branchLight = new BranchGroup();
        
        // Se crea la luz ambiente
        Light aLight = new AmbientLight (new Color3f (0.2f, 0.2f, 0.2f));
        aLight.setInfluencingBounds (new BoundingSphere (new Point3d 
        (0.0, 0.0, 0.0), 100.0));
        aLight.setEnable(true);
        
        //Se añade la luz luz al branch de las luces
        branchLight.addChild(aLight);
        
        // Se crea la luz puntual.
        //Se declara el color y la dirección de la luz.
        Color3f white;
        Vector3f direction;
        
        white = new Color3f (1.0f, 1.0f, 1.0f);
        direction = new Vector3f (-4.0f, -2.0f, -3.0f);
        
        //Se configura la luz como luz direccional.
        aLight = new DirectionalLight (white, direction);
        aLight.setInfluencingBounds (new BoundingSphere (new Point3d 
        (0.0, 0.0, 0.0), 100.0));
        aLight.setCapability(Light.ALLOW_STATE_WRITE);
       aLight.setEnable (true);
        
        //Se añade la luz luz al branch de las luces
        branchLight.addChild(aLight);
        
        //Se añade lel branch de las luces a la raiz. 
        root.addChild(branchLight);
        
        //*********************************************************************
        //*********************END Lights**************************************
        //*********************************************************************
        
        
        //*********************************************************************
        //*********************ESCENA******************************************
        //*********************************************************************
        
        //Creacion SOL
        //Parametros: tamaño, velocidad rotacion, distancia origen, imagen
        Estrella Sol = new Estrella(4, 10000, 0, "imgs/sol.jpg");
        root.addChild(Sol);
        
        //Creacion Mercurio
        //Parametros: tamaño, velocidad rotacion, distancia origen, imagen, velocidad traslacion
        Planeta mercurio = new Planeta((float)0.48, 2400, (float) 5.7, "imgs/mercurio.jpg", 10000);
        root.addChild(mercurio);
        
        //Creacion Venus
        //Parametros: tamaño, velocidad rotacion, distancia origen, imagen, velocidad traslacion
        Planeta venus = new Planeta((float)1.2, 5860, (float) 10.8, "imgs/venus.jpg",20000);
        root.addChild(venus);
        
        //Creacion Tierra
        //Parametros: tamaño, velocidad rotacion, distancia origen, imagen, velocidad traslacion
        Planeta tierra = new Planeta((float)1.2, 1000, (float) 14.9, "imgs/tierra.jpg", 30000);
        Satelite luna = new Satelite((float)0.2, 1000, 2, "imgs/luna.jpg");
        tierra.addSatelite(luna, 10000);
        root.addChild(tierra);
        
        //Creacion Marte
        //Parametros: tamaño, velocidad rotacion, distancia origen, imagen, velocidad traslacion
        Planeta marte = new Planeta((float)0.6, 5860, (float) 18.8, "imgs/marte.jpg", 40000);
        
        //Creacion Satelite Fobos
        //Parametros: tamaño, velocidad rotacion, distancia origen, imagen
        Satelite Fobos = new Satelite((float)0.2, 1000, 2, "imgs/fobos.jpg");
        //Adicion de satelite a planeta
        //Parametros: satelite, velocidad traslacion
        marte.addSatelite(Fobos, 10000);

        //Creacion Satelite Deimos
        //Parametros: tamaño, velocidad rotacion, distancia origen, imagen
        Satelite Deimos = new Satelite((float)0.2, 2000, 3, "imgs/deimos.jpg");
         //Adicion de satelite a planeta
        //Parametros: satelite, velocidad traslacion
        marte.addSatelite(Deimos, 20000);
        root.addChild(marte);
        
        //Creacion Jupiter
        //Parametros: tamaño, velocidad rotacion, distancia origen, imagen, velocidad traslacion
        Planeta jupiter = new Planeta((float)2, 5860, (float) 24.8, "imgs/jupiter.jpg", 50000);
        
        //Creacion Satelite io
        //Parametros: tamaño, velocidad rotacion, distancia origen, imagen
         Satelite io = new Satelite((float)0.2, 1000, 3, "imgs/io.jpg");
          //Adicion de satelite a planeta
        //Parametros: satelite, velocidad traslacion
        jupiter.addSatelite(io, 10000);
        
        //Creacion Satelite europa
        //Parametros: tamaño, velocidad rotacion, distancia origen, imagen
         Satelite europa = new Satelite((float)0.2, 2000, 4, "imgs/europa.jpg");
          //Adicion de satelite a planeta
        //Parametros: satelite, velocidad traslacion
        jupiter.addSatelite(europa, 20000);
        
        //Creacion Satelite calisto
        //Parametros: tamaño, velocidad rotacion, distancia origen, imagen
         Satelite calisto = new Satelite((float)0.2, 3000, 5, "imgs/calisto.jpg");
          //Adicion de satelite a planeta
        //Parametros: satelite, velocidad traslacion
        jupiter.addSatelite(calisto, 30000);
        root.addChild(jupiter);
        
        
        //*********************************************************************
        //*********************Creación Universo*******************************
        //*********************************************************************
            // Se crea el universo simple. La parte de la vista
            SimpleUniverse universe = createUniverse (canvas);
            
             // Se optimiza la escena y se cuelga del universo
            root.compile();
            universe.addBranchGraph(root);
    
        ControlWindow controlWindow = new ControlWindow (canvas, universe);
        // Se muestra la ventana principal de la aplicación
        controlWindow.showWindow ();
    
    }
    
    private static SimpleUniverse createUniverse (Canvas3D canvas) {
    // Se crea manualmente un ViewingPlatform para poder personalizarlo y
    //asignárselo al universo
    ViewingPlatform viewingPlatform = new ViewingPlatform();
    
    // La transformación de vista, dónde se está, a dónde se mira, Vup
    TransformGroup viewTransformGroup = viewingPlatform.getViewPlatformTransform();
    Transform3D viewTransform3D = new Transform3D();
    viewTransform3D.lookAt (new Point3d (50,50,50), new Point3d (0,0,0), new 
        Vector3d (0,1,0));
    viewTransform3D.invert();
    viewTransformGroup.setTransform (viewTransform3D);

    // El comportamiento, para mover la camara con el raton
    OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ALL);
    orbit.setSchedulingBounds(new BoundingSphere(new Point3d (0.0f, 0.0f, 0.0f), 
            500.0f));
    orbit.setZoomFactor (5.0f);
    viewingPlatform.setViewPlatformBehavior(orbit);
    
    // Se establece el angulo de vision a 45 grados y el plano de recorte trasero
    Viewer viewer = new Viewer (canvas);
    View view = viewer.getView();
    view.setFieldOfView(Math.toRadians(45));
    view.setBackClipDistance(50.0);

    // Se construye y devuelve el Universo con los parametros definidos
    return new SimpleUniverse (viewingPlatform, viewer);
  }
    
}
