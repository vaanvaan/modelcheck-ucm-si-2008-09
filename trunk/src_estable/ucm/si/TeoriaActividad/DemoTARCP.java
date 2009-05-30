/**
 * 
 */
package ucm.si.TeoriaActividad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import ucm.si.Checker.Resultado;
import ucm.si.TeoriaActividad.GUI.DrawerActividad;
import ucm.si.TeoriaActividad.Interprete.SistemaActividades;
import ucm.si.TeoriaActividad.actividad.Actividad;
import ucm.si.TeoriaActividad.actividad.ActividadGenerator;
import ucm.si.TeoriaActividad.actividad.Condition;
import ucm.si.TeoriaActividad.actividad.EstadoActividad;
import ucm.si.TeoriaActividad.actividad.ListaEstadosActividades;
import ucm.si.TeoriaActividad.estado.EstadoTA;
import ucm.si.TeoriaActividad.item.EstadoItem;
import ucm.si.TeoriaActividad.item.Item;
import ucm.si.TeoriaActividad.item.ItemGenerator;
import ucm.si.TeoriaActividad.item.ListaEstadosItems;
import ucm.si.TeoriaActividad.proposiciones.ProposicionActividad;
import ucm.si.TeoriaActividad.proposiciones.ProposicionItem;
import ucm.si.animadorGUI.AnimadorGrafico;
import ucm.si.animadorGUI.Drawer;
import ucm.si.animadorGUI.util.Launcher;
import ucm.si.basico.ecuaciones.AU;
import ucm.si.basico.ecuaciones.AX;
import ucm.si.basico.ecuaciones.And;
import ucm.si.basico.ecuaciones.EU;
import ucm.si.basico.ecuaciones.EX;
import ucm.si.basico.ecuaciones.Formula;
import ucm.si.basico.ecuaciones.Not;
import ucm.si.basico.ecuaciones.Or;
import ucm.si.basico.ecuaciones.Proposicion;
import ucm.si.util.Contexto;

/**
 * @author Ivan
 *
 */
public class DemoTARCP extends JFrame {

    private JPanel botonera;
    private ArrayList<JButton> botones;
    private SistemaActividades interprete;
    private ArrayList<Formula> propiedades;
    private Launcher<EstadoTA> launcher;
    private int tipoBotonera;

    public DemoTARCP() {
        this.botones = new ArrayList<JButton>();
        this.propiedades = new ArrayList<Formula>();
        this.botonera = new JPanel();
        this.botonera.setLayout(new BoxLayout(this.botonera, BoxLayout.Y_AXIS));
        this.setContentPane(this.botonera);
        this.tipoBotonera = AnimadorGrafico.BOTONERA_CLASICA;

        ActionListener listenerCambioBotonera = new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
               if (((JRadioButtonMenuItem)arg0.getSource()).getText()
                        .equalsIgnoreCase("ComboBox")){
                   tipoBotonera = AnimadorGrafico.BOTONERA_COMBO;
               } else {
                   tipoBotonera = AnimadorGrafico.BOTONERA_CLASICA;
               }
            }
        };
        ButtonGroup botoneraElegida = new ButtonGroup();
        JRadioButtonMenuItem botoneraClasica = new JRadioButtonMenuItem("Botonera Clásica");
        botoneraClasica.setSelected(true);
        botoneraClasica.setMnemonic(KeyEvent.VK_B);
        botoneraClasica.addActionListener(listenerCambioBotonera);
        botoneraElegida.add(botoneraClasica);

        JRadioButtonMenuItem botoneraComboBox = new JRadioButtonMenuItem("ComboBox");
        botoneraComboBox.setMnemonic(KeyEvent.VK_C);
        botoneraComboBox.addActionListener(listenerCambioBotonera);
        botoneraElegida.add(botoneraComboBox);

        JMenu opciones = new JMenu("Opciones");
        opciones.add(botoneraClasica);
        opciones.add(botoneraComboBox);
        JMenuBar barramenu = new JMenuBar();
        barramenu.add(opciones);
        this.setJMenuBar(barramenu);
        this.setTitle("Demo RCP");
        this.crearActividades();
        ActividadGenerator activGen = ActividadGenerator.getReference();
        ItemGenerator itemGen = ItemGenerator.getReference();
        String[] actividades = activGen.getConjunto().keySet().toArray(new String[0]);
        String[] items = itemGen.getItems();
        ListaEstadosActividades lEstAct = new ListaEstadosActividades();
        for (int i = 0; i < actividades.length; i++) {
            lEstAct.addEstado(actividades[i], EstadoActividad.Waiting);
        }
        ListaEstadosItems lEstItems = new ListaEstadosItems();
        for (int i = 0; i < items.length; i++) {
            if (items[i].equalsIgnoreCase("reanimador") ||
                    items[i].equalsIgnoreCase("victima") ||
                    items[i].equalsIgnoreCase("Telefono") ||
                    items[i].equalsIgnoreCase("Ayudante") ||
                    items[i].equalsIgnoreCase("Desfibrilador")) {
                lEstItems.addEstado(items[i], EstadoItem.FREE);
            } else {
                lEstItems.addEstado(items[i], EstadoItem.DISPOSED);
            }
        }
        EstadoTA estadoIni = new EstadoTA(lEstItems, lEstAct, new TreeMap<String, Set<String>>());
        List<EstadoTA> laux = new ArrayList<EstadoTA>();
        laux.add(estadoIni);
        this.interprete = new SistemaActividades(laux);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(200, 200, 300, 300);
        this.pack();
    }

    /**
     * @param args
     */
    private void crearActividades() {
        try {
            Item reanimador = new Item("Reanimador");
            Item victima = new Item("Victima");
            Item seguridad = new Item("Seguridad");
            Actividad garantizarSeguridad = new Actividad(
                    "Garantizar Seguridad", new Item[]{reanimador}, //nombre, sujetos
                    new Item[]{victima}, new Item[]{seguridad}, // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[0], new Item[]{seguridad}, //itemsTodispose, itemsToGenerate
                    new Condition[0]); // condiciones
            Item salvarVictima = new Item("Salvar Victima");
            Condition haySeguridad = new Condition() {

                public boolean Cumple(EstadoTA contx) {
                    return contx.getEstadoItem("Seguridad").equals(EstadoItem.FREE);
                }
            };
            Item analisisConciencia = new Item("Analisis Conciencia");
            Actividad comprobarConciencia = new Actividad(
                    "Comprobar Conciencia", new Item[]{reanimador}, //nombre, sujetos
                    new Item[0], new Item[0], // objetos, objetivos
                    new Item[0], new Item[]{analisisConciencia}, // herramientas, productos
                    new Item[0], new Item[]{analisisConciencia}, //itemsTodispose, itemsToGenerate
                    new Condition[]{haySeguridad}); // condiciones
            Item victimaRespondeI = new Item("Victima Responde");
            Item victimaNoRespondeI = new Item("Victima No Responde");
            Actividad victimaResponde = new Actividad(
                    "Victima Responde", new Item[]{victima}, //nombre, sujetos
                    new Item[]{analisisConciencia}, new Item[0], // objetos, objetivos
                    new Item[0], new Item[]{victimaRespondeI}, // herramientas, productos
                    new Item[]{analisisConciencia}, new Item[]{victimaRespondeI, salvarVictima}, //itemsTodispose, itemsToGenerate
                    new Condition[0]); // condiciones
            Actividad victimaNoResponde = new Actividad(
                    "Victima No Responde", new Item[]{victima}, //nombre, sujetos
                    new Item[]{analisisConciencia}, new Item[0], // objetos, objetivos
                    new Item[0], new Item[]{victimaNoRespondeI}, // herramientas, productos
                    new Item[]{analisisConciencia}, new Item[]{victimaNoRespondeI}, //itemsTodispose, itemsToGenerate
                    new Condition[0]); // condiciones
            Condition noResponde = new Condition() {

                public boolean Cumple(EstadoTA contx) {
                    return contx.getEstadoItem("Victima No Responde").equals(EstadoItem.FREE);
                }
            };
            Item analisisRespiracion = new Item("Analisis Respiracion");
            Actividad comprobarRespiracion = new Actividad(
                    "Comprobar Respiracion", new Item[]{reanimador}, //nombre, sujetos
                    new Item[0], new Item[0], // objetos, objetivos
                    new Item[0], new Item[]{analisisRespiracion}, // herramientas, productos
                    new Item[0], new Item[]{analisisRespiracion}, //itemsTodispose, itemsToGenerate
                    new Condition[]{haySeguridad, noResponde}); // condiciones
            Item victimaRespira = new Item("Victima Respira");
            Item victimaNoRespira = new Item("Victima No Respira");
            Actividad respira = new Actividad(
                    "Victima Respira", new Item[]{victima}, //nombre, sujetos
                    new Item[0], new Item[0], // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[]{analisisRespiracion}, new Item[]{victimaRespira}, //itemsTodispose, itemsToGenerate
                    new Condition[0]); // condiciones
            Actividad noRespira = new Actividad(
                    "Victima No Respira", new Item[]{victima}, //nombre, sujetos
                    new Item[0], new Item[0], // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[]{analisisRespiracion}, new Item[]{victimaNoRespira}, //itemsTodispose, itemsToGenerate
                    new Condition[0]); // condiciones
            Condition respiraC = new Condition() {

                public boolean Cumple(EstadoTA contx) {
                    return contx.getEstadoItem("Victima Respira").equals(EstadoItem.FREE);
                }
            };
            Condition victimaNoSalvada = new Condition() {

                public boolean Cumple(EstadoTA contx) {
                    return !contx.getEstadoItem("Salvar Victima").equals(EstadoItem.FREE);
                }
            };

            Actividad colocarPosicionSeguridad = new Actividad(
                    "Colocar en Posicion de Seguridad", new Item[]{reanimador}, //nombre, sujetos
                    new Item[]{victima}, new Item[]{salvarVictima}, // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[0], new Item[]{salvarVictima}, //itemsTodispose, itemsToGenerate
                    new Condition[]{haySeguridad, respiraC, victimaNoSalvada}); // condiciones
            Item telf = new Item("Telefono");
            Item ambulanciaAvisada = new Item("Ambulancia Avisada");
            Condition ambulanciaNoAvisada = new Condition() {

                public boolean Cumple(EstadoTA contx) {
                    return !contx.getEstadoItem("Ambulancia Avisada").equals(EstadoItem.FREE);
                }
            };
            Actividad llamar112 = new Actividad(
                    "Llamar 112", new Item[]{reanimador}, //nombre, sujetos
                    new Item[0], new Item[]{ambulanciaAvisada}, // objetos, objetivos
                    new Item[]{telf}, new Item[0], // herramientas, productos
                    new Item[0], new Item[]{ambulanciaAvisada}, //itemsTodispose, itemsToGenerate
                    new Condition[]{haySeguridad, respiraC, ambulanciaNoAvisada}); // condiciones
            Condition norespiraC = new Condition() {

                public boolean Cumple(EstadoTA contx) {
                    return contx.getEstadoItem("Victima No Respira").equals(EstadoItem.FREE);
                }
            };
            Actividad llamarInmed112 = new Actividad(
                    "Llamar inmediatamente al 112", new Item[]{reanimador}, //nombre, sujetos
                    new Item[0], new Item[]{ambulanciaAvisada}, // objetos, objetivos
                    new Item[]{telf}, new Item[0], // herramientas, productos
                    new Item[0], new Item[]{ambulanciaAvisada}, //itemsTodispose, itemsToGenerate
                    new Condition[]{haySeguridad, norespiraC}); // condiciones
            Item ayudante = new Item("Ayudante");
            Item prioridadAyuda = new Item("PrioridadAyuda");
            Condition ambulanciaC = new Condition() {

                public boolean Cumple(EstadoTA contx) {
                    return contx.getEstadoItem("Ambulancia Avisada").equals(EstadoItem.FREE);
                }
            };
            Actividad pedirAyuda = new Actividad(
                    "Pedir Ayuda", new Item[]{reanimador}, //nombre, sujetos
                    new Item[]{ayudante}, new Item[]{prioridadAyuda}, // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[0], new Item[]{prioridadAyuda}, //itemsTodispose, itemsToGenerate
                    new Condition[]{haySeguridad, norespiraC, ambulanciaC}); // condiciones
            Item tomarPulso = new Item("Tomar Pulso Carotideo");
            Condition prioridadAyudaC = new Condition() {

                public boolean Cumple(EstadoTA contx) {
                    return contx.getEstadoItem("PrioridadAyuda").equals(EstadoItem.FREE);
                }
            };
            Actividad buscarPulsoCarotideo = new Actividad(
                    "Buscar Pulso Carotideo", new Item[]{reanimador}, //nombre, sujetos
                    new Item[]{victima}, new Item[]{tomarPulso}, // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[0], new Item[]{prioridadAyuda, tomarPulso}, //itemsTodispose, itemsToGenerate
                    new Condition[]{haySeguridad, norespiraC, prioridadAyudaC}); // condiciones
            Item victimaTienePulso = new Item("Victima Tiene Pulso");
            Item victimaNoTienePulso = new Item("Victima No Tiene Pulso");
            Actividad tienePulso = new Actividad(
                    "Victima Tiene Pulso", new Item[]{victima}, //nombre, sujetos
                    new Item[0], new Item[0], // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[]{tomarPulso}, new Item[]{victimaTienePulso}, //itemsTodispose, itemsToGenerate
                    new Condition[0]); // condiciones
            Actividad notienePulso = new Actividad(
                    "Victima No Tiene Pulso", new Item[]{victima}, //nombre, sujetos
                    new Item[0], new Item[0], // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[]{tomarPulso}, new Item[]{victimaNoTienePulso}, //itemsTodispose, itemsToGenerate
                    new Condition[0]); // condiciones
            Condition tienePulsoC = new Condition() {

                public boolean Cumple(EstadoTA contx) {
                    return contx.getEstadoItem("Victima Tiene Pulso").equals(EstadoItem.FREE);
                }
            };
            Condition notienePulsoC = new Condition() {

                public boolean Cumple(EstadoTA contx) {
                    return contx.getEstadoItem("Victima No Tiene Pulso").equals(EstadoItem.FREE);
                }
            };
            Item viaAereaLibre = new Item("via Aerea Libre");
            Actividad limpiarViaAerea = new Actividad(
                    "Limpiar Via Aerea", new Item[]{reanimador}, //nombre, sujetos
                    new Item[]{victima}, new Item[]{viaAereaLibre}, // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[0], new Item[]{viaAereaLibre}, //itemsTodispose, itemsToGenerate
                    new Condition[]{haySeguridad, norespiraC, prioridadAyudaC, tienePulsoC}); // condiciones
            Condition viaAereaLibreC = new Condition() {

                public boolean Cumple(EstadoTA contx) {
                    return contx.getEstadoItem("via Aerea Libre").equals(EstadoItem.FREE);
                }
            };
            Actividad reanimarRespiracion = new Actividad(
                    "Reanimar Respiracion", new Item[]{reanimador}, //nombre, sujetos
                    new Item[]{victima}, new Item[]{salvarVictima}, // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[]{victimaNoRespira}, new Item[]{salvarVictima, victimaRespira}, //itemsTodispose, itemsToGenerate
                    new Condition[]{haySeguridad, norespiraC,
                        prioridadAyudaC, tienePulsoC, viaAereaLibreC}); // condiciones
            Item intentoResucitacion = new Item("Intento Resucitacion");
            Actividad rcp = new Actividad(
                    "Hacer RCP", new Item[]{reanimador}, //nombre, sujetos
                    new Item[]{victima}, new Item[]{intentoResucitacion}, // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[0], new Item[]{intentoResucitacion}, //itemsTodispose, itemsToGenerate
                    new Condition[]{haySeguridad, norespiraC, prioridadAyudaC, notienePulsoC}); // condiciones
            Actividad resucitaPorRCP = new Actividad(
                    "Resucita Por RCP", new Item[]{victima}, //nombre, sujetos
                    new Item[0], new Item[0], // objetos, objetivos
                    new Item[0], new Item[]{salvarVictima, victimaRespira, victimaTienePulso}, // herramientas, productos
                    new Item[]{intentoResucitacion, victimaNoRespira, victimaNoTienePulso}, new Item[]{salvarVictima, victimaRespira, victimaTienePulso}, //itemsTodispose, itemsToGenerate
                    new Condition[0]); // condiciones
            Actividad rcpFallida = new Actividad(
                    "RCP Fallida", new Item[]{victima}, //nombre, sujetos
                    new Item[0], new Item[0], // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[]{intentoResucitacion}, new Item[0], //itemsTodispose, itemsToGenerate
                    new Condition[0]); // condiciones
            Condition rcpFallidaC = new Condition() {

                public boolean Cumple(EstadoTA contx) {
                    return contx.getEstadoActividad("RCP Fallida").equals(EstadoActividad.Finalized);
                }
            };
            Item desfibrilador = new Item("Desfibrilador");
            Item intentoDesfibrilacion = new Item("IntentoDesfibrilacion");
            Actividad desfibrilar = new Actividad(
                    "Desfibrilar", new Item[]{reanimador}, //nombre, sujetos
                    new Item[]{victima}, new Item[]{intentoDesfibrilacion}, // objetos, objetivos
                    new Item[]{desfibrilador}, new Item[0], // herramientas, productos
                    new Item[0], new Item[]{intentoDesfibrilacion}, //itemsTodispose, itemsToGenerate
                    new Condition[]{haySeguridad, norespiraC, prioridadAyudaC, notienePulsoC, rcpFallidaC}); // condiciones
            Actividad resucitaPorDesfibrilador = new Actividad(
                    "Resucita Por Desfibrilador", new Item[]{victima}, //nombre, sujetos
                    new Item[0], new Item[0], // objetos, objetivos
                    new Item[0], new Item[]{salvarVictima, victimaRespira, victimaTienePulso}, // herramientas, productos
                    new Item[]{intentoDesfibrilacion, victimaNoRespira, victimaNoTienePulso}, new Item[]{salvarVictima, victimaRespira, victimaTienePulso}, //itemsTodispose, itemsToGenerate
                    new Condition[0]); // condiciones
            Actividad desfibrilacionFallida = new Actividad(
                    "Desfibrilacion Fallida", new Item[]{victima}, //nombre, sujetos
                    new Item[0], new Item[0], // objetos, objetivos
                    new Item[0], new Item[0], // herramientas, productos
                    new Item[]{intentoDesfibrilacion}, new Item[0], //itemsTodispose, itemsToGenerate
                    new Condition[0]); // condiciones


            // Aqui construir el arbol de actividades

            ActividadGenerator activGen = ActividadGenerator.getReference();
            ItemGenerator itemGen = ItemGenerator.getReference();
            itemGen.addItem(reanimador); // inicial
            itemGen.addItem(victima);  // inicial
            itemGen.addItem(seguridad);
            itemGen.addItem(salvarVictima);
            itemGen.addItem(analisisConciencia);
            itemGen.addItem(victimaRespondeI);
            itemGen.addItem(victimaNoRespondeI);
            itemGen.addItem(analisisRespiracion);
            itemGen.addItem(victimaRespira);
            itemGen.addItem(victimaNoRespira);
            itemGen.addItem(telf);   // inicial
            itemGen.addItem(ambulanciaAvisada);
            itemGen.addItem(ayudante);  // inicial
            itemGen.addItem(prioridadAyuda);
            itemGen.addItem(tomarPulso);
            itemGen.addItem(victimaTienePulso);
            itemGen.addItem(victimaNoTienePulso);
            itemGen.addItem(viaAereaLibre);
            itemGen.addItem(intentoResucitacion);
            itemGen.addItem(desfibrilador);  // inicial
            itemGen.addItem(intentoDesfibrilacion);

            activGen.addActividad(garantizarSeguridad);
            activGen.addActividad(comprobarConciencia);
            activGen.addActividad(victimaResponde);
            activGen.addActividad(victimaNoResponde);
            activGen.addActividad(comprobarRespiracion);
            activGen.addActividad(respira);
            activGen.addActividad(noRespira);
            activGen.addActividad(colocarPosicionSeguridad);
            activGen.addActividad(llamar112);
            activGen.addActividad(llamarInmed112);
            activGen.addActividad(pedirAyuda);
            activGen.addActividad(buscarPulsoCarotideo);
            activGen.addActividad(tienePulso);
            activGen.addActividad(notienePulso);
            activGen.addActividad(limpiarViaAerea);
            activGen.addActividad(reanimarRespiracion);
            activGen.addActividad(rcp);
            activGen.addActividad(resucitaPorRCP);
            activGen.addActividad(rcpFallida);
            activGen.addActividad(desfibrilar);
            activGen.addActividad(resucitaPorDesfibrilador);
            activGen.addActividad(desfibrilacionFallida);
        } catch (Exception ex) {
            Logger.getLogger(DemoTARCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        DemoTARCP demo = new DemoTARCP();
        // preparamos las proposiciones
        Formula noRespira = new ProposicionActividad("Victima No Respira", EstadoActividad.Executing);
        Formula noTienePulso = new ProposicionActividad("Victima No Tiene Pulso", EstadoActividad.Executing);
        Formula grave = new Or(noRespira, noTienePulso);
        Formula llamaAmbulancia = new ProposicionActividad("Llamar inmediatamente al 112", EstadoActividad.Executing);
        Formula siguienteLlamaAmbulancia = new AX(llamaAmbulancia);
        Formula respira = new ProposicionActividad("Victima Respira", EstadoActividad.Finalized);
        Formula tienePulso = new ProposicionActividad("Victima Tiene Pulso", EstadoActividad.Finalized);
        Formula incertidumbre = new Not(new Or(new Or(noRespira, respira),
                new Or(noTienePulso, tienePulso)));
        Formula graveynollama = new And(grave, new Not(siguienteLlamaAmbulancia));
        Formula formula = new Not(new EU(incertidumbre, graveynollama));
        demo.addPropiedad(formula, "Si una victima esta grave, se llama a una ambulancia.");

        Formula propSalvada = new ProposicionItem("Salvar Victima", EstadoItem.FREE);
        Formula nopropSalvada = new Not(propSalvada);
        Formula graveymastardesesalva = new And(grave, new EU(nopropSalvada, propSalvada));
        formula = new EU(incertidumbre, graveymastardesesalva);
        demo.addPropiedad(formula, "Es posible salvar la vida de una victima grave.");


        Formula respiraI = new ProposicionItem("Victima Respira", EstadoItem.FREE);
        Formula tienepulso = new ProposicionItem("Victima Tiene Pulso", EstadoItem.FREE);
        Formula signos = new And(respiraI, tienepulso);
        formula = new AU(nopropSalvada, signos); // siempre que se salva se analiza respiracion y pulso
        demo.addPropiedad(formula, "Siempre que se salva, se ha analizado respiracion y pulso.");

        demo.lanzar();
    }

    private void addPropiedad(Formula propiedad, String nombrePropiedad) {
        JButton boton = new JButton(nombrePropiedad);
        this.botones.add(boton);
        this.propiedades.add(propiedad);
        boton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                check(arg0.getSource());
            }
        });
        this.botonera.add(boton);
    }

    private void check(Object o) {
        int posicion = this.botones.indexOf(o);
        Formula f = this.propiedades.get(posicion);
        this.launcher = new Launcher<EstadoTA>(
                new Contexto() {
                }, this.interprete, f);
        Resultado<EstadoTA> res = launcher.runCheck();
        if (res.equals(Resultado.COD_TRUE)) {
            JOptionPane.showMessageDialog(this, "La formula es cierta");
        } else {
            JOptionPane.showMessageDialog(this, "La formula es falsa");
        }
        Drawer drw = new DrawerActividad(this.interprete);
        launcher.launchGrafico(drw, this.tipoBotonera);

    }

    private void lanzar() {
        this.setVisible(true);
        this.pack();
    }
}
