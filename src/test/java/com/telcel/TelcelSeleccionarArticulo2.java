package com.telcel;

import org.junit.Test;

public class TelcelSeleccionarArticulo2 extends TelcelParent2 {
    @Test
    public void TelcelSeleccionarArticulo() {

        verificarLandingPage();
        listarTelefonos();
        seleccionarEstado("Jalisco");
        verificarPaginaResultados();
        Celular primerCelular;
        primerCelular = capturarDatosCelular(1);
        seleccionarCelular(1);
        validarDatosCelular(primerCelular);

    }


}

