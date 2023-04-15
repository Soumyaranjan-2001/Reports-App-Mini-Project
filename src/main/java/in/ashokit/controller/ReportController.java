package in.ashokit.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.entity.CitizenPlan;
import in.ashokit.request.SearchRequest;
import in.ashokit.service.ReportService;

@Controller
public class ReportController {

	@Autowired
	private ReportService service;
	@GetMapping("/pdf")
	public void pdfExport(HttpServletResponse response,Model model) throws Exception {	
		response.setContentType("application/pdf");
		response.addHeader("content-Disposition", "attachment;filename=plans.pdf");
		service.exportPdf(response);
	}

	@GetMapping("/excel")
	public void excelExport(HttpServletResponse response) throws Exception {	
		response.setContentType("application/octet-stream");
		response.addHeader("content-Disposition", "attachment;filename=plans.xls");
		service.exportExcel(response);
	}

	@PostMapping("/searchData")
	public String handleSearch(@ModelAttribute("search") SearchRequest search, Model model) {


		List<CitizenPlan> plans = service.search(search);
		model.addAttribute("plans", plans);
		model.addAttribute("search", search);

		init(model);

		return "index";
	}

	/*
	 * 
	 * This method is used to load form
	 * 
	 * @Param Model
	 * 
	 * @return String
	 * 
	 */

	@GetMapping("/")
	public String indexPage(Model model) {
		// SearchRequest searchObj = new SearchRequest();

		model.addAttribute("search", new SearchRequest());

		init(model);

		return "index";
	}

	private void init(Model model) {
		model.addAttribute("names", service.getPlanNames());
		model.addAttribute("status", service.getPlanStatuses());
	}

}
