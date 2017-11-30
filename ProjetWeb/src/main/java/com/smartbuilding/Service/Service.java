package com.smartbuilding.Service;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.smartbuilding.VueController;

@Component
@Path("/")
public class Service {


	/*
	 * 
	 * methode pour traiter les données recus du formulaire sur l'index
	 * 
	 * 
	 */
	@POST
	@Path(value="/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response recevoir(@FormParam("location") String location,
                                @FormParam("le") String le,
                                @FormParam("li") String li,
                                @FormParam("temp") String temp,
                                @FormParam("air") String air, 
                                @FormParam("secu") String secu
                                ) throws FileNotFoundException, URISyntaxException {
		
		 this.location = location;
		 if(!le.equals("")) this.le=Double.parseDouble(le) ;
		 if(!li.equals("")) this.li= Double.parseDouble(li);
		 if(!air.equals("")&& air !=null) this.air =Double.parseDouble(air) ;
		 if(!temp.equals(""))  this.temp=Double.parseDouble(temp) ;
		  this.secu=Boolean.valueOf(secu);
		  URI index = new URI("http://localhost:8070/ProjetWeb/");
		
        return Response.seeOther(index).build();
    }
	

	/*
	 * 
	 * methode pour traiter les données recus du formulaire sur listeService
	 * 
	 * 
	 */
	@POST
	@Path(value="/listeService")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response donneesService (@FormParam("servicelumiere") String servicelumiere,
            @FormParam("serviceCanicule") String serviceCanicule,
            @FormParam("serviceEconomie") String serviceEconomie,
            @FormParam("serviceAire") String serviceAire,
            @FormParam("sln") String sln, 
            @FormParam("sla") String sla,
            @FormParam("sca") String sca,
            @FormParam("sch") String sch, 
            @FormParam("sle1") String sle1, 
            @FormParam("sech") String sech, 
            @FormParam("sqa") String sqa,
            @FormParam("serviceSecu")String serviceSecu
            ) throws URISyntaxException {
		
		 this.serviceCanicule=serviceCanicule;
		 this.servicelumiere= servicelumiere;
		 this.serviceEconomie=serviceEconomie;
		 this.serviceAire=serviceAire;
		 this.sln=sln;
		 this.sla=sla;
		 this.sca=sca;
		 this.sch=sch;
		 this.sle1=sle1;
		 this.sech=sech;
		 this.sqa=sqa;
		 this.serviceSecu=serviceSecu;
		 URI listeService = new URI("http://localhost:8070/ProjetWeb/listeService");
		return   Response.seeOther(listeService).build();
		
	}
	

	/*
	 * 
	 * methode pour recuperer  les données traités du formulaire sur l'index
	 *  elle seront utiliser sur freedomotic via l'url:http://localhost:8070/ProjetWeb/service/capteur/donnees
	 * 
	 * 
	 */
	@GET
	@Path(value="/capteur/donnees")
	@Produces("application/json")
	public Map<String, Object> donnees() {
		Map<String, Object> donnes = new HashMap<String,Object>();
		donnes.put("location", this.location);
		donnes.put("li", this.li);
		donnes.put("le", this.le);
		donnes.put("air", this.air);
		donnes.put("temp", this.temp);
		donnes.put("secu", this.secu);
		
		return  donnes;
	}
	
	
	/*
	 * 
	 * methode pour recuperer  les données traités du formulaire sur listeService
	 *  elle seront utiliser sur freedomotic via l'url:http://localhost:8070/ProjetWeb/service/listeService/donnees
	 * 
	 * 
	 */
	@GET
	@Path(value="/listeService/donnees")
	@Produces("application/json")
	public Map services() {
		
		Map<String,Object> listService = new HashMap<String,Object> ();
		
		
		
		
		
		if (!servicelumiere.equals("")) {
			Map<String,Object> serviceLumier = new HashMap<String,Object> ();
			serviceLumier.put("priorite", this.servicelumiere);
			if (!sln.equals("")) { serviceLumier.put("sln", this.sln);}
			if (!sla.equals("")) {serviceLumier.put("sla", this.sla);}
			
			 
			 listService.put("servicelumiere", serviceLumier);
			}
		if (!serviceCanicule.equals("")) {
			Map<String,Object> serviceCan= new HashMap<String,Object> ();
			serviceCan.put("priorite",this.serviceCanicule);
			if (!sca.equals("")) { serviceCan.put("sca", this.sca);}
			if (sch !=null && !sch.equals("")) { serviceCan.put("sch", this.sch);}
			
			 listService.put("serviceCanicule",serviceCan ) ;
		}
	
		if (serviceEconomie !=null && !serviceEconomie.equals("")) {
			Map<String,Object> serviceEco= new HashMap<String,Object> ();
			serviceEco.put("priorite",this.serviceEconomie);
			if (!sle1.equals("")) {serviceEco.put("sle1", this.sle1);}
			if (!sech.equals("")) {serviceEco.put("sech", this.sech);}
			 listService.put("serviceEconomie",serviceEco );
		}
		
		if (serviceAire !=null && !serviceAire.equals("")) {
			Map<String,Object> serviceair= new HashMap<String,Object> ();
			serviceair.put("priorite",this.serviceAire);
			if (!sqa.equals("")) {serviceair.put("sqa", this.sqa);}
			 listService.put("serviceAire", serviceair) ;
		}
		if (serviceSecu !=null && !serviceSecu.equals("")) {
			Map<String,Object> servicesecu= new HashMap<String,Object> ();
			servicesecu.put("priorite",0);
			 listService.put("servicesecu", servicesecu) ;
		}
		
		
		return  listService;
	}
	//variables pour initialisé les prioritées 
	private static String serviceCanicule="";
	private static String servicelumiere="";
	private static String serviceEconomie="";
	private static String serviceAire="";
	private static String sln="";
	private static String sla="";
	private static String sca="";
	private static String sch="";
	private static String sle1="";
	private static String sech="";
	private static String sqa="";
	private static String serviceSecu="";
	private static Boolean secu=false;
	//variables pour initialisé les capteurs  
	private static String location="2";
	private static double le=550;
	private static double li=350;
	private static double temp=20;
	private static double air=500;
	
}
