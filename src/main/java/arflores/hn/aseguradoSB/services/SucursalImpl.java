/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arflores.hn.aseguradoSB.services;

import arflores.hn.aseguradoSB.models.Sucursal;
import arflores.hn.aseguradoSB.repository.SucursalRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author AllanRamiro
 */
public class SucursalImpl implements ISucursal {

    @Autowired
    private SucursalRepository ejecutar;
    
    @Override
    public List<Sucursal> listarSurcusal() {
        return (List<Sucursal>) ejecutar.findAll();
    }
    
}
