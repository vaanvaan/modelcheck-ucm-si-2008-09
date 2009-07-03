package ucm.si.Checker;

import java.util.List;

/**
 *
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
import ucm.si.Checker.util.StateLabeledList;
// #[regen=yes,id=DCE.AB379A1E-67C3-9733-6F60-5FA18BC31E6E]
// </editor-fold>
/**
 * Interfaz a apartir de la cual se contruyen los interpretes
 * @author Niko, Jose Antonio, Ivan Antonio
 * @param <S>
 */
public interface Interprete<S> {

    /**
     *  Debe devolver el conjunto de posibles estados iniciales.
     * @return
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E3EA2941-4A75-6F2C-15E4-98944D2EB3C1]
    // </editor-fold> 
    public List<S> iniciales ();

    /**
     * Funcion principal de los intepretes.<p>
     * De un estado proporcionado deve deolver una lista con los posibles estados
     * a los que se podria transitar
     * @param state estado sobre elq eu se pregunta
     * @return  Lista de estados a los que se puede transitar
     */
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.2088AE4E-6D7D-BD80-A3D7-21C2583346FF]
    // </editor-fold> 
    public List<S> transitar (S state);

    /**
     * Funcion Principal de los interpretes.
     * Es como <code>transitar(S state)</code> solo que esta vez devulve la trasicion estiqeutada.
     * Para almacenar esta informacion se usa el objeto StateLabeledLis. Que es basicamente una lista de estados S y de String que
     * en la que puedes buscar el nombre de la transicion que lleva a un estado. O Para el nombre de una transicion a que estado va.
     * @param state Estado a aprtir del cual se transita.
     * @return lista de los nombres (etiquetas) de las transiciones.
     */
    public StateLabeledList<S> transitarConEtiqueta(S state);

    /**
     * Enumera todas las transicones posibles para el modelo a interpretar
     * @return
     */
    public List<String> dameTransiciones();

}

