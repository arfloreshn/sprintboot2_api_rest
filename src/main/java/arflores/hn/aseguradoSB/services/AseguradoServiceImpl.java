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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import arflores.hn.aseguradoSB.repository.AseguradoRepository;
import arflores.hn.aseguradoSB.repository.SucursalRepository;

/**
 *
 * @author AllanRamiro
 */
@Service
public class AseguradoServiceImpl implements IAseguradoService {

    
    @Autowired
    AseguradoRepository aseguradoDao;
    
    @Autowired
    SucursalRepository sucursalDao;
    
    @Override
    public List<Asegurado> listarAsegurados() {
        return (List<Asegurado>) aseguradoDao.findAll();
    }

    
    @Override
    public List<Sucursal> listarSecursales() {
        return (List<Sucursal>) sucursalDao.findAll();
    }

    
    @Override
    public void guardarAsegurado(Asegurado aseg) {
      aseguradoDao.save(aseg);
    }

    @Override
    public Asegurado Buscar_registro (Long id)
    {
       Asegurado aseg = new Asegurado();
       aseg = aseguradoDao.findById(id).orElse(null);
       return aseg;
    };
    
    @Override
    public void modificarAsegurado(Asegurado aseg) {
     aseguradoDao.save(aseg);
    };

    @Override
    public void eliminarAsegurado(Long id) {
     aseguradoDao.deleteById(id);
    }
    
    @Override
    public void eliminarTodo(){
    aseguradoDao.deleteAll();
    };
    
  
    @Override
    public List<Asegurado> BuscarRangoFechas(@Param("pfec_desde") Date fec_ini, @Param("pfec_hasta") Date fec_fin) 
    {
      return (List<Asegurado>) aseguradoDao.BuscarRangoFechas(fec_ini, fec_fin);
    };
  
}
