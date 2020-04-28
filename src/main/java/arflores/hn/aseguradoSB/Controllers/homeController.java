package arflores.hn.aseguradoSB.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {

	
	@GetMapping({"/","/index","/home"})
	public String index() 
	{
		return "home";
	}
        
        @GetMapping({"/pageRangofechas"})
	public String pageRangofechas() 
	{
	  return "pages/frmbuscarPorRangoFechas";
	}
}
