/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arflores.hn.aseguradoSB.repository;

import arflores.hn.aseguradoSB.models.Sucursal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AllanRamiro
 */
@Repository
public interface SucursalRepository  extends CrudRepository<Sucursal, Long>
{
    
}
