/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ucm.si.Checker.LogicPosibilidad;

import com.sun.xml.internal.bind.v2.model.core.EnumLeafInfo;

/**
 *
 * @author Pilar
 */
public class Posibilidad 
{
    private enum  LogicEnum { TRUE, FALSE, MAYBETRUE, MAYBEFALSE };
    
    private LogicEnum valor;
    
    public static int TRUE = 0;
    public static int FALSE = 1;
    public static int MAYBETRUE = 2;
    public static int MAYBEFALSE = 3;

    public Posibilidad(LogicEnum valor) {
        this.valor = valor;
    }
    
    public Posibilidad(int valor)
    {
        switch(valor)
        {
                case(0): this.valor = LogicEnum.TRUE ;break;
                case(1): this.valor = LogicEnum.FALSE; break;
                case(2): this.valor = LogicEnum.MAYBETRUE; break;
                case(3): this.valor = LogicEnum.MAYBEFALSE; break;
                default: this.valor = LogicEnum.TRUE; break;
        }
    }
    
    
    public Posibilidad(String valor)
    {
        if(valor.equals("TRUE"))
            this.valor = LogicEnum.TRUE ;
        else if (valor.equals("FALSE"))
            this.valor = LogicEnum.FALSE;
        else if(valor.equals("MAYBETRUE"))
            this.valor = LogicEnum.MAYBETRUE;
        else if(valor.equals("MAYBEFALSE"))
            this.valor= LogicEnum.MAYBEFALSE;
        else
           this.valor = LogicEnum.TRUE ; 
    }
   
    
    public boolean and(Posibilidad  p)
    {
        if(p.getValor().equals(LogicEnum.TRUE) || p.getValor().equals(MAYBETRUE))
            if(this.valor.equals(LogicEnum.TRUE) || this.valor.equals(MAYBETRUE))
                return true;
        return false;
    }
    
    public boolean or(Posibilidad  p)
    {
        if(p.getValor().equals(LogicEnum.TRUE) || p.getValor().equals(MAYBETRUE)
                || this.valor.equals(LogicEnum.TRUE) || this.valor.equals(MAYBETRUE))
                return true;
        return false;
    }
    
    public boolean not()
    {
        if(this.valor.equals(LogicEnum.TRUE) || this.valor.equals(MAYBETRUE))
            return false;
        if(this.valor.equals(LogicEnum.FALSE) || this.valor.equals(MAYBEFALSE))
            return true;
        return false;
    }
    
    /*public Posibilidad and(Posibilidad  p)
    {
        if(p.getValor().equals(LogicEnum.TRUE) && this.valor.equals(LogicEnum.TRUE))
                return new Posibilidad(LogicEnum.TRUE);
        else if(p.getValor().equals(LogicEnum.FALSE) || this.valor.equals(LogicEnum.FALSE))
            return new Posibilidad(LogicEnum.FALSE);
        else if( ( p.getValor().equals(LogicEnum.MAYBETRUE) && 
                (this.valor.equals(LogicEnum.MAYBETRUE) || this.valor.equals(LogicEnum.TRUE)) )
                || (p.getValor().equals(LogicEnum.TRUE) ) || this.valor
                )
                return new Posibilidad(LogicEnum.TRUE);
                
            || p.getValor().equals(MAYBETRUE))
            if(this.valor.equals(LogicEnum.TRUE) || this.valor.equals(MAYBETRUE))
                return true;
        return false;
    }
    
    public boolean or(Posibilidad  p)
    {
        if(p.getValor().equals(LogicEnum.TRUE) || p.getValor().equals(MAYBETRUE)
                || this.valor.equals(LogicEnum.TRUE) || this.valor.equals(MAYBETRUE))
                return true;
        return false;
    }
    
    public boolean not()
    {
        if(this.valor.equals(LogicEnum.TRUE) || this.valor.equals(MAYBETRUE))
            return false;
        if(this.valor.equals(LogicEnum.FALSE) || this.valor.equals(MAYBEFALSE))
            return true;
        return false;
    }*/
    
    
    
    
    
    
    
    
    
    
    public LogicEnum getValor() {
        return valor;
    }

    public void setValor(LogicEnum valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Posibilidad other = (Posibilidad) obj;
        if (this.valor != other.valor) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.valor != null ? this.valor.hashCode() : 0);
        return hash;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
        
    public static void main(String[] args)
    {
        System.out.println("Hola");
        LogicEnum.values();
        
        for(LogicEnum c : LogicEnum.values())
            System.out.println(c);
        
    }
    

}
