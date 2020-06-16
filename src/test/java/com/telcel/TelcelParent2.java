package com.telcel;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TelcelParent2{
    WebDriver driver;
    WebDriverWait wait;
    String url= "https://www.telcel.com";

    @Before

        public void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:\\libs\\chromedriver.exe");
        driver = new ChromeDriver(); //carga el driver de Chrome
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //hace un delay de 10 segs
        wait = new WebDriverWait(driver, 10);
        driver.navigate().to(url); //abre la url solicitada
        System.out.println("Set up: Browser opened");
    }
    public void verificarLandingPage() {
        //verificar que existen estos elementos
        //logoTelcel:  css="[src='/content/dam/htmls/img/icons/logo-telcel.svg']"
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[src='/content/dam/htmls/img/icons/logo-telcel.svg']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-nombreboton='Tienda en linea superior']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#buscador-menu-input")));
    }
    public void listarTelefonos() {
        WebElement tiendaEnLinea = driver.findElement(By.cssSelector("[data-nombreboton='Tienda en linea superior']")); //verifica que exista el elemento tienda en linea
        tiendaEnLinea.click();//al elemento tienda en linea se le da click
        WebElement linkTelefonosCelulares = driver.findElement(By.cssSelector(".shortcut-container [data-nombreboton='Telefonos y smartphones']")); //valida que exista el elemento telefonos y smartphones
        linkTelefonosCelulares.click();//al elemento linkTelefonosCalulares le da click
    }
    public void seleccionarEstado(String nombreEstado) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal .chosen-single"))).click();  //verifica que exista el elemento del boton del dropdown
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".chosen-search input"))).sendKeys(nombreEstado); //verifica que exista el elemento de escritura para Estado
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".chosen-results li"))).click(); //verifica que exista la lista de estados
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("entrarPerfilador"))).click(); //verifica que exista el boton Entrar en el marco
    }
    public void verificarPaginaResultados() {
        List<WebElement> celulares = driver.findElements(By.cssSelector(".comp-telcel-mosaico-equipos li"));
        System.out.println(celulares.size());
        if (celulares.size() > 1) {
            System.out.println("La lista se desplego correctamente.");
        }
    }
    public Celular capturarDatosCelular(int i) {
        String mm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".telcel-mosaico-equipos-marca"))).getText();
        String nombreEquipo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".telcel-mosaico-equipos-nombre-equipo"))).getText();
        String precioEquipo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ng-scope > p.telcel-mosaico-equipos-precio"))).getText();
        precioEquipo = precioEquipo.replace(",","");
        precioEquipo = precioEquipo.replace("$","");
        double pe = Double.parseDouble(precioEquipo);
        String capacidadEquipo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".telcel-mosaico-equipos-capacidad-numero"))).getText();
        String[] datos = capacidadEquipo.split(" ");
        String capacidadString = datos[0];
        int numGigas = Integer.parseInt(capacidadString);
        return new Celular(mm, nombreEquipo, pe, numGigas);
    }
    public void seleccionarCelular(int numCelular) {
        List<WebElement> celulares = driver.findElements(By.cssSelector(".comp-telcel-mosaico-equipos li"));
        System.out.println(celulares.size());
        WebElement primerCelular = celulares.get(numCelular -1);
        primerCelular.click();
    }
    public void validarDatosCelular(Celular primerCelular) {
        WebElement textoMarcaModelo = driver.findElement(By.cssSelector(".ecommerce-ficha-tecnica-opciones-compra #ecommerce-ficha-tecnica-modelo"));
        String mm = textoMarcaModelo.getText();
        if(primerCelular.getMarcaModelo().equals(mm))
            System.out.println("La marca y modelo coinciden");
        else
            System.out.println("La marca y modelo no coinciden");
        WebElement textoNombre = driver.findElement(By.cssSelector(".ecommerce-ficha-tecnica-opciones-compra #ecommerce-ficha-tecnica-nombre"));
        String nombreEquipo = textoNombre.getText();
        if(primerCelular.getNombre().equals(nombreEquipo))
            System.out.println("El nombre coincide");
        else
            System.out.println("El nombre no  coincide");

        WebElement textoPrecio = driver.findElement(By.cssSelector(".ecommerce-ficha-tecnica-opciones-compra #ecommerce-ficha-tecnica-precio-obj"));
        String precioEquipo = textoPrecio.getText();
        precioEquipo = precioEquipo.replace(",", "");
        precioEquipo = precioEquipo.replace("$", "");
        double pe = Double.parseDouble(precioEquipo);
        if(primerCelular.getPrecio() == pe)
            System.out.println("El precio coincide");
        else
            System.out.println("El precio no coincide");

        WebElement textoCapacidad = driver.findElement(By.cssSelector(".ecommerce-ficha-tecnica-opciones-compra .ecommerce-ficha-tecnica-opciones-compra-caracteristicas-etiqueta"));
        String capacidadEquipo = textoCapacidad.getText();
        String[] datos = capacidadEquipo.split(" ");
        String capacidadString = datos[0];
        int numGigas = Integer.parseInt(capacidadString);
        if(primerCelular.getCapacidadGb() == numGigas)
            System.out.println("La capacidad coincide");
        else
            System.out.println("La capacidad no coincide");
    }

    @After
    //public void cerrarBrowser(){
    public void tearDown(){

        driver.quit();
        System.out.println("Tear Down: Browser closed");
    }
}