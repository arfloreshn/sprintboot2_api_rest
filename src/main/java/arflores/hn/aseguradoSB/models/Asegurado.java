/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arflores.hn.aseguradoSB.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author AllanRamiro
 */
@Entity
@Table(name = "asegurados")
public class Asegurado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String email;

  
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="dd-MM-YYYY")
    private Date fec_ingreso;

            
    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal suc;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFec_ingreso() {
        return fec_ingreso;
    }

    public void setFec_ingreso(Date fec_ingreso) {
       
        try {
            
          //America/Tegucigalpa  
         String str = fec_ingreso.toString();
         
         //SimpleDateFormat formatter = new SimpleDateFormat("EE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH);
         //TimeZone tz = TimeZone.getTimeZone("America/Tegucigalpa");
        
         TimeZone tz = TimeZone.getTimeZone("UTC");
         
         DateFormat df = new SimpleDateFormat("EE MMM dd HH:mm:ss zz yyyy", Locale.ENGLISH);
         df.setTimeZone(tz);
         Date fecha = (Date) df.parse(str);
         
         //java.sql.Timestamp timeStampDate = new Timestamp(fecha.getTime());
         
         // formatter.parse(str);
            
            this.fec_ingreso = fecha;
        } catch (ParseException ex) {
            Logger.getLogger(Asegurado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
   public String right(String cadena, int pos) 
   {
     return cadena.substring(cadena.length()-4);
   }
    
    
    
    public Sucursal getSuc() {
        return suc;
    }

    public void setSuc(Sucursal suc) {
        this.suc = suc;
    }

    
    
    @Override
    public String toString() {
        return "Asegurado{" + "id=" + id + ", nombres=" + nombres + ", apellidos=" + apellidos + ", telefono=" + telefono + ", email=" + email + ", fec_ingreso=" + fec_ingreso + '}';
    }

}
