package ucm.si.animadorGUI.laberinto;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import ucm.si.Laberinto.Laberinto;
import ucm.si.Laberinto.Posicion;
public class PanelLaberinto<S> extends Panel<S>{
	
        

	public PanelLaberinto(Contexto cnt)
        {
            super.contexto = cnt;
        }
	/*public PanelLaberinto(Laberinto l) {
		lab = l;
	}*/
	@Override
	public void pintaEstado(S p) {
		this.drawer.pintaEstado(p, this);
	
	}

	@Override
	public void rePinta(S p) 
        {

		this.drawer.rePinta(p, this);
		
	}

    @Override
    public void setDrawer(Drawer<S> dw) {
        super.drawer = dw;
        super.drawer.setContexto(contexto);
        
    }
    
    @Override
    public void setContexto(Contexto cntxt)
    {
        super.contexto = cntxt;
    }

   
    

    
}
