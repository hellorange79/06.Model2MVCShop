package com.model2.mvc.web.product;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
public class ProductController {
	
	
	//Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping("/addProductView.do")
	public String addProuctView() throws Exception{
		
		System.out.println("addProductView.do");
		
		return "forward:/product/addProductView.jsp";
	}
	@RequestMapping("/addProduct.do")
	public String addProduct(@ModelAttribute("product") Product product) throws Exception{
		
		System.out.println("/addProduct.do");
		
		//System.out.println(product);
		productService.addProduct(product);
		
		return "forward:/product/addProduct.jsp";
	}
	@RequestMapping("/listProduct.do")
	public String getProductList(@ModelAttribute("search") Search search, Model model, HttpServletRequest request)throws Exception {
		
		System.out.println("/listProduct.do");
		
		String menu = request.getParameter("menu");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map= productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), 
				((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model °ú View ¿¬°á
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		
		return "forward:/product/listProduct.jsp?menu="+menu;
	}
	@RequestMapping("/getProduct.do")
	public String getProduct(@RequestParam("prodNo") int prodNo, Model model)throws Exception {
		
		System.out.println("/getProduct.do");
		
		Product product= productService.getProduct(prodNo);
		
		model.addAttribute("prodNo", prodNo);
		
		return "forward:/product/getProduct.jsp";
	}
}
