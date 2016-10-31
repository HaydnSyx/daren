package com.syx.taobao.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.controller.common.AbstractController;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.Product;
import com.syx.taobao.service.ProductService;
import com.syx.taobao.vo.ResultVo;

@Controller
@RequestMapping("product")
public class ProductController extends AbstractController {
	@Autowired
	private ProductService productService;

	@RequestMapping("list")
	public String list(HttpServletRequest request, ModelMap map) {
		List<Product> list = null;
		try {
			list = productService.queryAllProduct();
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (list == null) {
			list = new ArrayList<Product>();
		}
		map.addAttribute("list", list);
		return "work/product/list";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addview(Integer parentId, HttpServletRequest request, ModelMap map) {
		return "work/product/add";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo add(Product pt, HttpServletRequest request, ModelMap map) {
		try {
			productService.addProduct(pt);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, null);
	}

	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateview(@RequestParam("id") int id, HttpServletRequest request, ModelMap map) {
		Product pt = null;
		try {
			pt = productService.queryProduct(id);
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (pt == null) {
			pt = new Product();
		}
		map.addAttribute("bean", pt);
		return "work/product/update";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo update(Product pt, HttpServletRequest request, ModelMap map) {
		try {
			productService.updateProduct(pt);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, null);
	}

	@RequestMapping("delete")
	public String delete(@RequestParam("id") int id, HttpServletRequest request, ModelMap map) {
		try {
			productService.deleteProduct(id);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return "redirect:/product/list.htm";
	}

}
