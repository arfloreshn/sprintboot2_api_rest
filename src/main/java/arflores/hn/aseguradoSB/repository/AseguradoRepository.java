/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arflores.hn.aseguradoSB.repository;

import arflores.hn.aseguradoSB.models.Asegurado;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AllanRamiro
 */
@Repository
public interface AseguradoRepository extends CrudRepository<Asegurado, Long> {
    
    @Query("select c from Asegurado c where c.fec_ingreso >= :fec_desde and c.fec_ingreso <= :fec_hasta")
    public List<Asegurado> BuscarRangoFechas(@Param("fec_desde") Date fec_desde, @Param("fec_hasta") Date fec_hasta);
    
}
