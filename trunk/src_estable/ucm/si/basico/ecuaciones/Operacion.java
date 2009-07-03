package ucm.si.basico.ecuaciones;


/**
 *  @author Niko, Jose Antonio, Ivan
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.057CEE6A-9B4E-FA5F-B072-0244CDC669A2]
// </editor-fold> 
public abstract class Operacion extends Formula {
    protected Formula[] operandos;
    
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.59F3FCDB-DBCF-D611-BF66-A7BC8400EF1F]
    // </editor-fold> 
    public Formula[] getOperandos (){
        return this.operandos;
    }
    
    public Formula getOperando(int i){
        if ((i>=0)&&(i<operandos.length))
            return this.operandos[i];
        else{
            System.err.println("Indice " + i + "esta fuera de rango.");
            return null;
        }
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.60E5C8D9-962B-1560-3189-2A3D674AF258]
    // </editor-fold> 
    public void setOperandos (Formula[] operandos){
        this.operandos = operandos;
    }

}

