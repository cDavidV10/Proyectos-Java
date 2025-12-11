package Controlador;

import Funciones.Funciones;
import Vista.VistaTabla;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;
import javax.swing.SwingUtilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControladorTablaTest {

    private VistaTabla view;
    private FuncionesSpy funciones;
    private ControladorTablaSpy controller;

    @BeforeClass
    public static void requireNotHeadless() {
        // VistaTabla is a JFrame. Skip tests in headless environments (CI, etc.).
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
    }

    @AfterClass
    public static void cleanupAwt() {
        // No global cleanup required; hook kept for future stability.
    }

    @Before
    public void setUp() throws Exception {
        funciones = new FuncionesSpy();
        view = createViewWithoutController(funciones);

        final ControladorTablaSpy[] holder = new ControladorTablaSpy[1];
        runOnEdt(() -> holder[0] = new ControladorTablaSpy(view, funciones));
        controller = holder[0];
    }

    @After
    public void tearDown() throws Exception {
        if (view != null) {
            runOnEdt(() -> view.dispose());
        }
    }

    @Test
    public void checkboxSelected_doesNotCallFiltro() throws Exception {
        runOnEdt(() -> {
            funciones.reset();
            // Ensure a deterministic toggle.
            view.getCheckBox().setSelected(false);
            view.getCheckBox().doClick();
        });

        assertFalse("Filtro() should not be called directly from the checkbox handler", funciones.filtroCalled);
        assertTrue("cargarDatos() should be called after selecting the checkbox", funciones.cargarDatosCalled);
        assertTrue("cargarDatos() should be called with filtroActivo=true", funciones.lastCargarDatosFiltro);
    }

    @Test
    public void nextClick_callsLimpiarBtn() throws Exception {
        runOnEdt(() -> {
            controller.limpiarBtnCalls = 0;
            view.getBtnSiguiente().doClick();
        });

        assertEquals("limpiarBtn() should be called once when clicking Next", 1, controller.limpiarBtnCalls);
    }

    @Test
    public void previousClick_callsLimpiarBtn() throws Exception {
        runOnEdt(() -> {
            controller.pagina = 2; // ensure we can go back safely
            controller.limpiarBtnCalls = 0;
            view.getBtnAnterior().doClick();
        });

        assertEquals("limpiarBtn() should be called once when clicking Previous", 1, controller.limpiarBtnCalls);
    }

    @Test
    public void nextClick_incrementsPage_andCallsSetPaginaActual() throws Exception {
        runOnEdt(() -> {
            controller.pagina = 1;
            funciones.paginasTotalesReturn = 3;
            funciones.resetSetPaginaActual();

            view.getBtnSiguiente().doClick();
        });

        assertEquals("Page should increment from 1 to 2", 2, controller.pagina);
        assertEquals("setPaginaActual should be called with 2", 2, funciones.lastSetPaginaActualArg);
        assertEquals("setPaginaActual should be called exactly once", 1, funciones.setPaginaActualCalls);
    }

    @Test
    public void previousClick_decrementsPage_andCallsSetPaginaActual() throws Exception {
        runOnEdt(() -> {
            controller.pagina = 2;
            funciones.paginasTotalesReturn = 3;
            funciones.resetSetPaginaActual();

            view.getBtnAnterior().doClick();
        });

        assertEquals("Page should decrement from 2 to 1", 1, controller.pagina);
        assertEquals("setPaginaActual should be called with 1", 1, funciones.lastSetPaginaActualArg);
        assertEquals("setPaginaActual should be called exactly once", 1, funciones.setPaginaActualCalls);
    }

    private static VistaTabla createViewWithoutController(Funciones funciones) throws Exception {
        final VistaTabla[] holder = new VistaTabla[1];
        runOnEdt(() -> {
            VistaTabla v = new VistaTabla(funciones);

            // VistaTabla's initComponents() wires some ActionListeners.
            // VistaTabla(Funciones) also constructs a ControladorTabla that adds more listeners.
            // Remove all button/checkbox listeners so we can attach ONLY the controller under test.
            removeAllActionListeners(v.getBtnCompletar());
            removeAllActionListeners(v.getBtnEliminar());
            removeAllActionListeners(v.getBtnSiguiente());
            removeAllActionListeners(v.getBtnAnterior());
            removeAllActionListeners(v.getCheckBox());

            holder[0] = v;
        });
        return holder[0];
    }

    private static void removeAllActionListeners(AbstractButton button) {
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
    }

    private static void runOnEdt(Runnable r) throws Exception {
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
            return;
        }
        SwingUtilities.invokeAndWait(r);
    }

    private static class ControladorTablaSpy extends ControladorTabla {
        int limpiarBtnCalls = 0;

        ControladorTablaSpy(VistaTabla viewTabla, Funciones funciones) {
            super(viewTabla, funciones);
        }

        @Override
        public void limpiarBtn() {
            limpiarBtnCalls++;
            super.limpiarBtn();
        }
    }

    private static class FuncionesSpy extends Funciones {
        boolean filtroCalled = false;
        boolean cargarDatosCalled = false;
        boolean lastCargarDatosFiltro = false;

        int paginasTotalesReturn = 3;

        int setPaginaActualCalls = 0;
        int lastSetPaginaActualArg = -1;

        void reset() {
            filtroCalled = false;
            cargarDatosCalled = false;
            lastCargarDatosFiltro = false;
            resetSetPaginaActual();
        }

        void resetSetPaginaActual() {
            setPaginaActualCalls = 0;
            lastSetPaginaActualArg = -1;
        }

        @Override
        public void Filtro() {
            filtroCalled = true;
            // Intentionally do NOT call super.Filtro() for a pure interaction test.
        }

        @Override
        public void cargarDatos(VistaTabla interfaz, boolean filtro) {
            cargarDatosCalled = true;
            lastCargarDatosFiltro = filtro;
            // Intentionally do nothing else (avoid Swing updates and avoid calling Filtro()).
        }

        @Override
        public void setPaginaActual(int pagina) {
            setPaginaActualCalls++;
            lastSetPaginaActualArg = pagina;
            // We don't need super.setPaginaActual() for these tests.
        }

        @Override
        public int paginasTotales() {
            return paginasTotalesReturn;
        }
    }
}
