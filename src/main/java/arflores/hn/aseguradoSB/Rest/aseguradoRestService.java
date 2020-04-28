/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arflores.hn.aseguradoSB.Rest;

import arflores.hn.aseguradoSB.models.Asegurado;
import arflores.hn.aseguradoSB.models.Sucursal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import arflores.hn.aseguradoSB.services.IAseguradoService;
import java.text.DateFormat;

/**
 *
 * @author AllanRamiro
 */
@RestController
@RequestMapping(value = "/rest", produces = {MediaType.APPLICATION_JSON_VALUE})
public class aseguradoRestService {

    
    @Autowired
    private IAseguradoService aseguradoDao;

    
    @RequestMapping(value = "/asegurado/" , method = RequestMethod.GET)
    public ResponseEntity<List<Asegurado>> listarAsegurados() {
        List<Asegurado> var_asegurado = (List<Asegurado>) aseguradoDao.listarAsegurados();

        if (var_asegurado.isEmpty()) {
            // En caso no haber datos en la tabla hay que devolver que no hay contenido
            return new ResponseEntity<List<Asegurado>>(HttpStatus.NO_CONTENT);
        }
        
        
        return new ResponseEntity<List<Asegurado>>(var_asegurado, HttpStatus.OK);
    }

     @RequestMapping(value = "/asegurado/sucursal" , method = RequestMethod.GET)
    public ResponseEntity<List<Sucursal>> listarSucursales() {
        List<Sucursal> var_sucursal = (List<Sucursal>) aseguradoDao.listarSecursales();

        if (var_sucursal.isEmpty()) {
            // En caso no haber datos en la tabla hay que devolver que no hay contenido
            return new ResponseEntity<List<Sucursal>>(HttpStatus.NO_CONTENT);
        }
        
        
        return new ResponseEntity<List<Sucursal>>(var_sucursal, HttpStatus.OK);
    }

   
    //----------------   Crear un nuevo asegurado  ---------------------------------------------
    
    @RequestMapping(value = "/asegurado/crear", method = RequestMethod.POST)
    private ResponseEntity<Void> crear(@RequestBody Asegurado asegurado,    UriComponentsBuilder ucBuilder) {
      
        Long idAsegurado;
        idAsegurado = asegurado.getId();
                
        Asegurado snExisteAsegurado = aseguradoDao.Buscar_registro(idAsegurado);
      
        if (snExisteAsegurado != null) {
            System.out.println("El asegurado ya existe.:" + asegurado.getId() );
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
 
        aseguradoDao.guardarAsegurado(asegurado);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/asegurado/{id}").buildAndExpand(asegurado.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        
      }

    //----------------   Modificar un asegurado en especifico  ---------------------------------------------

    @RequestMapping(value = "/asegurado/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Asegurado> actualizar(@PathVariable("id") long id, @RequestBody Asegurado asegurado) {

        Asegurado var_asegurado =  aseguradoDao.Buscar_registro(id);
         
        if (var_asegurado==null) {
            System.out.println("El asegurado " + id + " no se encontro");
            return new ResponseEntity<Asegurado>(HttpStatus.NOT_FOUND);
        }
  
        var_asegurado.setNombres(asegurado.getNombres());
        var_asegurado.setApellidos(asegurado.getApellidos());
        var_asegurado.setTelefono(asegurado.getTelefono());
        var_asegurado.setEmail(asegurado.getEmail());
        var_asegurado.setFec_ingreso(asegurado.getFec_ingreso());
        var_asegurado.setSuc(asegurado.getSuc());
        
        
        aseguradoDao.modificarAsegurado(var_asegurado);
        return new ResponseEntity<Asegurado>(var_asegurado, HttpStatus.OK);
        
    }


   //----------------   Borrar un asegurado en especifico  ---------------------------------------------

    @RequestMapping(value = "/asegurado/borrar/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Asegurado> deleteUser(@PathVariable("id") long id) {

         Asegurado var_asegurado =  aseguradoDao.Buscar_registro(id);

        if (var_asegurado == null) {
            System.out.println("El registro no pudo ser borrado con este Id " + id + "  o nose encontro");
            return new ResponseEntity<Asegurado>(HttpStatus.NOT_FOUND);
        }
 
        aseguradoDao.eliminarAsegurado(id);
        return new ResponseEntity<Asegurado>(HttpStatus.NO_CONTENT);
        }

    
      //------------------- Borrar todos los asegurados-------------------------------------------
     
    @RequestMapping(value = "/asegurado/deleteall", method = RequestMethod.DELETE)
    public ResponseEntity<Asegurado> borrarTodo() {
        System.out.println("Borrar todos los registros");
 
        aseguradoDao.eliminarTodo();
        return new ResponseEntity<Asegurado>(HttpStatus.NO_CONTENT);
    }
    

     
    //----------------   Buscar un asegurado en especifico por id  ---------------------------------------------
    @RequestMapping(value = "/asegurado/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Asegurado> getAsegurado(@PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);
        Asegurado var_asegurado = aseguradoDao.Buscar_registro(id);
        if (var_asegurado == null) {
            System.out.println("Asegurado no encontrado.: " + id);
            return new ResponseEntity<Asegurado>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Asegurado>(var_asegurado, HttpStatus.OK);
    }

    //Busque por una fecha en especefico
    
    @RequestMapping(value = "/asegurado/bfecha/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Asegurado>> BuscarporFecha(@RequestParam(value="fecha", required=false) String fecIni) {
        
        List<Asegurado> var_asegurado = null;
        
        try {
        
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Date fec_ini = formatter.parse(fecIni);
            Date fec_fin = formatter.parse(fecIni);
    
             var_asegurado = (List<Asegurado>) aseguradoDao.BuscarRangoFechas(fec_ini, fec_fin);
            
            if (var_asegurado.isEmpty()) {
                // En caso no haber datos en la tabla hay que devolver que no hay contenido
                return new ResponseEntity<List<Asegurado>>(HttpStatus.NO_CONTENT);
            }
    
        } catch (ParseException ex) {
            Logger.getLogger(aseguradoRestService.class.getName()).log(Level.SEVERE, null, ex);
        }
 
            
            return new ResponseEntity<List<Asegurado>>(var_asegurado, HttpStatus.OK);
    }

    
        //------------------ LISTAR POR RANGO DE FECHAS ---------------------------
    
    @RequestMapping(value = "/asegurado/buscarRangoFechas/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Asegurado>> BuscarporFechas(@RequestParam(value="ini", required=false) String fecIni
                , @RequestParam(value="fin", required=false) String fecFin) {
        
        List<Asegurado> var_asegurado = null;
        
        try {
        
            DateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
            
            Date fec_ini = formato.parse(fecIni);
            Date fec_fin = formato.parse(fecFin);
    
             var_asegurado = (List<Asegurado>) aseguradoDao.BuscarRangoFechas(fec_ini, fec_fin);
            
            if (var_asegurado.isEmpty()) {
                // En caso no haber datos en la tabla hay que devolver que no hay contenido
                return new ResponseEntity<List<Asegurado>>(HttpStatus.NO_CONTENT);
            }
    
        } catch (ParseException ex) {
            Logger.getLogger(aseguradoRestService.class.getName()).log(Level.SEVERE, null, ex);
        }
 
            
            return new ResponseEntity<List<Asegurado>>(var_asegurado, HttpStatus.OK);
    }


    
}
