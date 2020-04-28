package arflores.hn.aseguradoSB;

import java.util.Date;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class aseguradoSbApplication {

    
     @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));   // It will set UTC timezone
    }
        
	public static void main(String[] args) {
  		SpringApplication.run(aseguradoSbApplication.class, args);
	}

}
