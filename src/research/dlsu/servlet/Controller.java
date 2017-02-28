package research.dlsu.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import research.dlsu.model.Crab;
import research.dlsu.model.CrabUpdate;
import research.dlsu.model.OnSiteUser;
import research.dlsu.service.CrabService;
import research.dlsu.service.CrabUpdateService;
import research.dlsu.service.OnSiteUserService;

import com.google.gson.Gson;

/**
 * Servlet implementation class Controller
 */
@WebServlet({"/Controller", "/insertcrab", "/insertcrabupdate", "/getcrab", "/insertonsiteuser", "/getresults"})
@MultipartConfig
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String FAIL = "FAIL";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		process(request, response);
	}

	public void process(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String servletPath = request.getServletPath();
		switch(servletPath){
			case "/insertonsiteuser":{
					OnSiteUser osu = new OnSiteUser();
					osu.setSerialNumber(request.getParameter(OnSiteUser.SERIALNUMBER));
					new OnSiteUserService().insertOnSiteUser(osu);
				}
				break;
			case "/insertcrab":{
					long id = insertNewCrab(request);
					if(id > 0){
						// return id so app can save it in its db
						response.getWriter().print(id);
					}else{
						response.getWriter().print(FAIL);
					}
				}
				break;
			case "/insertcrabupdate":{
//					long idcrab = Long.parseLong(request.getParameter(CrabUpdate.PARAMETER_SERVERIDCRAB));
					// Now make sure that app has crab entry before passing crabupdate
//					if(idcrab == -1){
//						// no crab entry yet, so make one
//						idcrab = insertNewCrab(request);
//					}
					
//					if(idcrab != -1){
//						// if all went well (crab already exists)
//						CrabUpdate cu = new CrabUpdate();
//						
//						String path = saveImage(request);
//						if(path != null){
//							cu.setPath(path);
//							cu.setDate(new Date(Long.parseLong(request.getParameter(CrabUpdate.DATE))));
//							cu.setPhoneidcrabupdate(Integer.parseInt(request.getParameter(CrabUpdate.PARAMETER_PHONEIDCRABUPDATE)));
//							cu.setPhoneidcrab(Integer.parseInt(request.getParameter(CrabUpdate.PARAMETER_PHONEIDDCRAB)));
//							cu.setIdcrab(idcrab);
//							
//							System.out.println(cu.toString());
//							long id = new CrabUpdateService().insertCrabUpdate(cu);
//							response.getWriter().print(id);
//						}
//						// return id so app can save it in its db
//					}else{
//						response.getWriter().print(FAIL);
//					}
				
					System.out.println("now receiving");
					CrabUpdate cu = new CrabUpdate();
					
					int idonsiteuser = new OnSiteUserService().getIdFromIMEI(request.getParameter(OnSiteUser.SERIALNUMBER));
					
					System.out.println("idonsiteuser is " + idonsiteuser);
					
					if(idonsiteuser < 0){
						idonsiteuser = new OnSiteUserService().insertOnSiteUser(request.getParameter(OnSiteUser.SERIALNUMBER));
					}

					System.out.println("idonsiteuser is " + idonsiteuser);
					
					String path = saveImage(request);
					if(path != null){
						cu.setPath(path);
						cu.setDate(new Date(Long.parseLong(request.getParameter(CrabUpdate.DATE))));
						cu.setPhoneidcrabupdate(Integer.parseInt(request.getParameter(CrabUpdate.PARAMETER_PHONEIDCRABUPDATE)));
//						cu.setPhoneidcrab(Integer.parseInt(request.getParameter(CrabUpdate.PARAMETER_PHONEIDDCRAB)));
						cu.setType(request.getParameter(CrabUpdate.TYPE));
						
						cu.setIdonsiteuser(idonsiteuser);
						
						// CHESKA'S EDIT
						// CRAB UPDATE
						int idcrabupdate = Math.toIntExact(cu.getPhoneidcrabupdate());
						
						String result = null;
						Process p = Runtime.getRuntime().exec("C:\\crab\\detect.exe " + path + " C:\\crab\\svm16.mat");
						BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
						System.out.println("CRAB SPECIES: ");
						result = input.readLine();
						cu.setResult("result");
						// END CHESKA'S EDIT
						
						
						System.out.println(cu.toString());
						long id = new CrabUpdateService().insertCrabUpdate(cu);
						
						// TO DO : Cheska. Will identify the crab species here.
						//CrabUpdateService.getCrabUpdate(idcrabupdate);
						//CrabUpdateService.updateCrabUpdateResult(result, idcrabupdate);
						
						response.getWriter().print(id);
					}else{
						response.getWriter().print(FAIL);
					}
					
				}
				break;
			case "/getresults":{
					String serialNumber = request.getParameter(OnSiteUser.SERIALNUMBER);
					String phoneidcrabString = request.getParameter(CrabUpdate.IDCRABSTRING);
//					
//					ArrayList<Integer> phoneidcrabList = new ArrayList<Integer>();
//					String[] phoneidcrabArray = phoneidcrabString.split(",");
//					for(String s: phoneidcrabArray){
//						phoneidcrabList.add(Integer.parseInt(s));
//					}
//					
					ArrayList<CrabUpdate> crabResults = new CrabUpdateService().getAvailableResultsFromSNAndListPhoneIdCrab(serialNumber, phoneidcrabString);
					Gson g = new Gson();
					String jsonString = g.toJson(crabResults);
					response.getWriter().write(jsonString);
					
					System.out.println(jsonString);
				}
				break;
		}
	}
	
	public long insertNewCrab(HttpServletRequest request){
		Crab crab = new Crab();
		int idonsiteuser = new OnSiteUserService().getIdFromIMEI(request.getParameter(OnSiteUser.SERIALNUMBER));
		
		System.out.println("idonsiteuser is " + idonsiteuser);
		
		if(idonsiteuser < 0){
			idonsiteuser = new OnSiteUserService().insertOnSiteUser(request.getParameter(OnSiteUser.SERIALNUMBER));
		}

		System.out.println("idonsiteuser is " + idonsiteuser);
		
		crab.setIdonsiteuser(idonsiteuser);
		crab.setCity(request.getParameter(Crab.CITY));
		crab.setFarm(request.getParameter(Crab.FARM));
		crab.setLatitude(Double.parseDouble(request.getParameter(Crab.LATITUDE)));
		crab.setLongitude(Double.parseDouble(request.getParameter(Crab.LONGITUDE)));
		crab.setWeight(Double.parseDouble(request.getParameter(Crab.WEIGHT)));
		crab.setPhoneidcrab(Integer.parseInt(request.getParameter(Crab.PHONEIDCRAB)));

		System.out.println("phoneidcrab is " + request.getParameter(Crab.PHONEIDCRAB));
		
		System.out.println(crab.toString());
		
		long id = new CrabService().insertNewCrab(crab);
		return id;
	}
	
	public String saveImage(HttpServletRequest request){
		// Get the file Part, the image we want to receive
		Part part;
		try {
			part = request.getPart("image");
		
			// Make a fileName for the file to save
			// We can use System.currentTimeMillis() to make the image file name unique, but this will eventually fail if you are expecting high traffic in uploads
			// If expecting many uploads, consider appending the user's ID, or using a universally unique identifier (UUID) generator
			String fileName = System.currentTimeMillis() + "-image.jpg";
			
			// You may also get other parts of the request
			String title = request.getParameter("title");
			System.out.println("title : " + title);
			
			// Create our file name with FOLDER as the parent repository 
		    File file = new File(FileServlet.FOLDER, fileName);
		    
			// Save the file (will replace existing files with similar file name)
		    InputStream fileInputStream = part.getInputStream();
			Files.copy(fileInputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			fileInputStream.close();
			
		    // This is where the file was saved
			System.out.println("Success! File saved in : " + file.getAbsolutePath());
			
			return file.getAbsolutePath();
		} catch (IllegalStateException | IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
