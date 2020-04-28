/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arflores.hn.aseguradoSB.Controllers;

import arflores.hn.aseguradoSB.models.Asegurado;
import arflores.hn.aseguradoSB.models.Sucursal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author AllanRamiro
 */
@Controller
@RequestMapping("/asegurados")
public class clienteController {

    private final static String hostURL = "http://localhost:8080/rest/asegurado/";

    private HttpHeaders headers = new HttpHeaders();
    private RestTemplate restTemplate = new RestTemplate();
    private List<Asegurado> tb = null;
    private List<Sucursal> lstSuc = null;
    
    
    public List<Asegurado> getTb() {
        return tb;
    }

    public void setTb(List<Asegurado> tb) {
        this.tb = tb;
    }

    public List<Sucursal> getLstSuc() {
        return lstSuc;
    }

    public void setLstSuc(List<Sucursal> lstSuc) {
        this.lstSuc = lstSuc;
    }

    public void InicializoHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();

    }

    @GetMapping("/listado")
    public String listarAsegurados(Model mod) {

        InicializoHeaders();
        String url = hostURL;
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Asegurado[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Asegurado[].class);

        // Declaro un arreglo, que recibe la respuesta del contenido del body o cuerpo de la peticion
        Asegurado[] body = responseEntity.getBody();

        // Creo un bucle for, para recorrer el contenido del cuerpo y lo asigno a la variable var_asegurado de tipo Asegurado
        this.tb = new ArrayList<Asegurado>();
        this.tb.clear();
        for (Asegurado obj : body) {
            // lleno la lista de asegurados
            tb.add(obj);
        }

        mod.addAttribute("titulo", "Listado de Asegurados");
        mod.addAttribute("asegurados", tb);

        return "/pages/listado";
    }

    public void sucursales() {

        InicializoHeaders();
        String urlSucursales = "http://localhost:8080/rest/asegurado/sucursal";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Sucursal[]> responseEntity = restTemplate.exchange(urlSucursales, HttpMethod.GET, requestEntity, Sucursal[].class);

        // Declaro un arreglo, que recibe la respuesta del contenido del body o cuerpo de la peticion
        Sucursal[] body = responseEntity.getBody();

        // Creo un bucle for, para recorrer el contenido del cuerpo y lo asigno a la variable var_asegurado de tipo Asegurado
        this.lstSuc = new ArrayList<Sucursal>();
        this.lstSuc.clear();
        for (Sucursal obj : body) {
            // lleno la lista de asegurados
            lstSuc.add(obj);
        }

    }

    @GetMapping("/listado/{id}")
    public String buscar_cliente(@PathVariable("id") Long idAsegurado, RedirectAttributes attribute, Model mod) {

        InicializoHeaders();
        String url = this.hostURL + "{id}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

        ResponseEntity<Asegurado> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Asegurado.class, idAsegurado);

        // Recibo el contido del cuerpo
        Asegurado var_asegurado = responseEntity.getBody();

        mod.addAttribute("titulo", "Formulario: Editar asegurado");
        mod.addAttribute("asegurado", var_asegurado);

        return "/pages/frmasegurado";

    }

    @GetMapping("/nuevo")
    public String cmd_agregarAsegurado(Model mod) {

        InicializoHeaders();

        String url = hostURL;
        Asegurado var_asegurado = new Asegurado();

        sucursales();
        mod.addAttribute("titulo", "Nuevo asegurado");
        mod.addAttribute("asegurado", var_asegurado);
        mod.addAttribute("suc", lstSuc);

        return "/pages/frmasegurado_new";
    }

    @GetMapping("/edit/{id}")
    public String cmd_editarAsegurado(@PathVariable("id") Long idAsegurado, Model mod) {

        sucursales();
        InicializoHeaders();

        String url = this.hostURL + "{id}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

        ResponseEntity<Asegurado> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Asegurado.class, idAsegurado);

        // Recibo el contido del cuerpo
        Asegurado var_asegurado = responseEntity.getBody();

        mod.addAttribute("titulo", "Formulario: Editar Asegurado");
        mod.addAttribute("asegurado", var_asegurado);
        mod.addAttribute("suc", lstSuc);

        return "/pages/frmasegurado_udp";
    }

    @PostMapping("/save")
    private String cmdSave(@ModelAttribute Asegurado asegurado) {

        String url = "http://localhost:8080/rest/asegurado/crear";
        InicializoHeaders();

        HttpEntity<Asegurado> requestEntity = new HttpEntity<Asegurado>(asegurado, headers);
        URI uri = restTemplate.postForLocation(url, requestEntity);

        return "redirect:/asegurados/listado";
    }

    @RequestMapping("/put")
    private String cmdPut(@ModelAttribute Asegurado asegurado) {

        String url = "http://localhost:8080/rest/asegurado/edit/" + asegurado.getId();

        InicializoHeaders();

        HttpEntity<Asegurado> putAsegurado = new HttpEntity<Asegurado>(asegurado, headers);
        restTemplate.put(url, putAsegurado);
        return "redirect:/asegurados/listado";
    }

    
    @DeleteMapping("/delete/{id}")
    public String cmd_borrar(@PathVariable("id") Long idAsegurado) {

        InicializoHeaders();

        String url = hostURL + "{id}";
        HttpEntity<Asegurado> requestEntity = new HttpEntity<Asegurado>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, idAsegurado);
        return "redirect:/asegurados/listado";

    }
    


    @RequestMapping("/rango")
    public String lstRangoFechas(@RequestParam String txtdesde, @RequestParam String txthasta, @ModelAttribute Asegurado asegurado,Model modelo) {
       
        
        InicializoHeaders();

        String url = "";
        url = "http://localhost:8080/rest/asegurado/buscarRangoFechas/?ini=" + txtdesde + "&fin=" + txthasta;
        
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Asegurado[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Asegurado[].class);

        
         // Declaro un arreglo, que recibe la respuesta del contenido del body o cuerpo de la peticion
        Asegurado[] body = responseEntity.getBody();

        // Creo un bucle for, para recorrer el contenido del cuerpo y lo asigno a la variable var_asegurado de tipo Asegurado
        this.tb = new ArrayList<Asegurado>();
        this.tb.clear();
        for (Asegurado obj : body) {
            // lleno la lista de asegurados
            tb.add(obj);
        }

        modelo.addAttribute("titulo", "Listado de Asegurados");
        modelo.addAttribute("asegurados", tb);
       
        return "/pages/frmbuscarPorRangoFechas";
        
    }
 
    
    
    
 
}
