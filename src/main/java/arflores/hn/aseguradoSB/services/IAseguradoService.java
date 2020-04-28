/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arflores.hn.aseguradoSB.services;

import arflores.hn.aseguradoSB.models.Asegurado;
import arflores.hn.aseguradoSB.models.Sucursal;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author AllanRamiro
 */
public interface IAseguradoService {

    public List<Asegurado> listarAsegurados();

    public void guardarAsegurado(Asegurado cliente);

    public Asegurado Buscar_registro(Long id);
    
    public void modificarAsegurado(Asegurado cliente);

    public void eliminarAsegurado(Long id);
    
    public void eliminarTodo();
    
    public List<Asegurado> BuscarRangoFechas(@Param("pfec_desde") Date fec_ini, @Param("pfec_hasta") Date fec_fin);
    
    public List<Sucursal> listarSecursales();

    
}
