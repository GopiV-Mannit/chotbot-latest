package com.mannit.chatbot.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mannit.chatbot.model.CurrentPatient;
import com.mannit.chatbot.model.Noappointment;
import com.mannit.chatbot.model.QueriedPatient;
import com.mannit.chatbot.repository.Currentpatientsrepo;
import com.mannit.chatbot.repository.Noappointmentrepo;
import com.mannit.chatbot.repository.QuriedpRepo;

@Controller
public class Displaypage {

	  @Autowired
	  private  Currentpatientsrepo repo;
	  @Autowired
	  private  Noappointmentrepo no_app_repo;
	  @Autowired
	  private  QuriedpRepo quried_repo;
	
	
@GetMapping(value="/")	
public String getcurrentpatients(Model model) {
	 List<CurrentPatient> cp= repo.findAll();
	 List<QueriedPatient> qp= quried_repo.findAll();
	 List<Noappointment>np =no_app_repo.findAll();
	 System.out.println(np);
	 model.addAttribute("currentpatients",cp);
	 model.addAttribute("queriedpatients",qp);
	 model.addAttribute("noappointment",np);
	return "index.html";
}

@GetMapping(value = "/getbydate")
public String getbydate(@RequestParam("date") String date, Model model) {
	 String formattedDate = convertDateFormat(date, "yyyy-MM-dd", "MM/dd/yyyy");
	List<CurrentPatient> cp = repo.findByDate(formattedDate);
	List<QueriedPatient> qp = quried_repo.findByDate(formattedDate);
	List<Noappointment> np = no_app_repo.findByDate(formattedDate);
	System.out.println(np);
	model.addAttribute("currentpatients", cp);
	model.addAttribute("queriedpatients", qp);
	model.addAttribute("noappointment", np);
	return "index.html";
}
public String convertDateFormat(String inputDateStr, String inputFormat, String outputFormat) {
    DateFormat inputDateFormat = new SimpleDateFormat(inputFormat);
    DateFormat outputDateFormat = new SimpleDateFormat(outputFormat);
    try {
        Date date = inputDateFormat.parse(inputDateStr);
        return outputDateFormat.format(date);
    } catch (ParseException e) {
        e.printStackTrace();
        return null;
    }
}
		
}
