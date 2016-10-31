package com.syx.taobao.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.syx.taobao.common.AppConstant;
import com.syx.taobao.controller.common.AbstractController;
import com.syx.taobao.exception.BizException;
import com.syx.taobao.model.MUser;
import com.syx.taobao.model.Product;
import com.syx.taobao.model.WorkRecord;
import com.syx.taobao.model.common.Pager;
import com.syx.taobao.service.MUserService;
import com.syx.taobao.service.ProductService;
import com.syx.taobao.service.WorkRecordService;
import com.syx.taobao.util.DateEditor;
import com.syx.taobao.vo.ResultVo;
import com.syx.taobao.vo.WorkRecordVo;

@Controller
@RequestMapping("work/record")
public class WorkRecordController extends AbstractController {
	@Autowired
	private WorkRecordService workRecordService;
	@Autowired
	private ProductService productService;
	@Autowired
	private MUserService mUserService;

	@RequestMapping("list")
	public String list(WorkRecordVo vo, String orderby, String sort, HttpServletRequest request, ModelMap map) {
		Pager<WorkRecordVo> pager = null;

		try {
			pager = workRecordService.queryWorkRecordPagerByCustom(vo, orderby, sort, getPageIndex(request), 50);
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (pager == null) {
			pager = new Pager<WorkRecordVo>();
		}

		map.addAttribute("pager", pager);

		return "work/record/list";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addview(HttpServletRequest request, ModelMap map) {
		double tbSingleproductPrice = 0;
		double tbDetaillistPrice = 0;
		double tbCardPrice = 0;
		double tmSingleproductPrice = 0;
		double tmDetaillistPrice = 0;
		double tmCardPrice = 0;

		List<Product> productList = null;
		List<MUser> muserList = null;
		try {
			productList = productService.queryAllProduct();
			muserList = mUserService.queryMUserListByRoleId(AppConstant.ROLE_PARTJOB);
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (productList == null) {
			productList = new ArrayList<>();
		}
		if (muserList == null) {
			muserList = new ArrayList<>();
		}
		for (Product o : productList) {
			switch (o.getProductType()) {
			case 1:
				tbSingleproductPrice = o.getProductPrice();
				break;
			case 2:
				tbDetaillistPrice = o.getProductPrice();
				break;
			case 3:
				tbCardPrice = o.getProductPrice();
				break;
			case 4:
				tmSingleproductPrice = o.getProductPrice();
				break;
			case 5:
				tmDetaillistPrice = o.getProductPrice();
				break;
			case 6:
				tmCardPrice = o.getProductPrice();
				break;
			default:
				break;
			}
		}
		map.addAttribute("tbSingleproductPrice", tbSingleproductPrice);
		map.addAttribute("tbDetaillistPrice", tbDetaillistPrice);
		map.addAttribute("tbCardPrice", tbCardPrice);
		map.addAttribute("tmSingleproductPrice", tmSingleproductPrice);
		map.addAttribute("tmDetaillistPrice", tmDetaillistPrice);
		map.addAttribute("tmCardPrice", tmCardPrice);
		map.addAttribute("currentDate", new Date());
		map.addAttribute("muserList", muserList);

		return "work/record/add";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo add(WorkRecord o, HttpServletRequest request, ModelMap map) {
		try {
			workRecordService.addWorkRecord(o);
		} catch (BizException e) {
			return new ResultVo(AppConstant.RESULT_STATUS_ERROR, e.getMessage(), null);
		}
		return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, null);
	}

	@RequestMapping(value = "update", method = RequestMethod.GET)
	public String updateview(@RequestParam("id") int id, HttpServletRequest request, ModelMap map) {
		WorkRecord o = null;
		List<Product> productList = null;
		List<MUser> muserList = null;
		try {
			o = workRecordService.queryWorkRecord(id);
			productList = productService.queryAllProduct();
			muserList = mUserService.queryMUserListByRoleId(AppConstant.ROLE_PARTJOB);
		} catch (BizException e) {
			e.printStackTrace();
		}
		if (o == null) {
			o = new WorkRecord();
		}
		double tbSingleproductPrice = 0;
		double tbDetaillistPrice = 0;
		double tbCardPrice = 0;
		double tmSingleproductPrice = 0;
		double tmDetaillistPrice = 0;
		double tmCardPrice = 0;

		if (productList == null) {
			productList = new ArrayList<>();
		}
		if (muserList == null) {
			muserList = new ArrayList<>();
		}
		for (Product p : productList) {
			switch (p.getProductType()) {
			case 1:
				tbSingleproductPrice = p.getProductPrice();
				break;
			case 2:
				tbDetaillistPrice = p.getProductPrice();
				break;
			case 3:
				tbCardPrice = p.getProductPrice();
				break;
			case 4:
				tmSingleproductPrice = p.getProductPrice();
				break;
			case 5:
				tmDetaillistPrice = p.getProductPrice();
				break;
			case 6:
				tmCardPrice = p.getProductPrice();
				break;
			default:
				break;
			}
		}
		map.addAttribute("tbSingleproductPrice", tbSingleproductPrice);
		map.addAttribute("tbDetaillistPrice", tbDetaillistPrice);
		map.addAttribute("tbCardPrice", tbCardPrice);
		map.addAttribute("tmSingleproductPrice", tmSingleproductPrice);
		map.addAttribute("tmDetaillistPrice", tmDetaillistPrice);
		map.addAttribute("tmCardPrice", tmCardPrice);
		map.addAttribute("bean", o);
		map.addAttribute("muserList", muserList);
		return "work/record/update";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo update(WorkRecord pt, HttpServletRequest request, ModelMap map) {
		try {
			workRecordService.updateWorkRecord(pt);
		} catch (BizException e) {
			return new ResultVo(AppConstant.RESULT_STATUS_ERROR, e.getMessage(), null);
		}
		return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, null);
	}

	@RequestMapping("delete")
	public String delete(@RequestParam("id") int id, HttpServletRequest request, ModelMap map) {
		try {
			workRecordService.deleteWorkRecord(id);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return "redirect:/work/record/list.htm";
	}

	@RequestMapping(value = "editStar", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo editStar(@RequestParam("id") int id, @RequestParam("level") int level, HttpServletRequest request, ModelMap map) {
		try {
			WorkRecord o = workRecordService.queryWorkRecord(id);
			o.setLevel(level);
			o.setUpdateTime(new Date());
			workRecordService.updateWorkRecord(o);
		} catch (BizException e) {
			return new ResultVo(AppConstant.RESULT_STATUS_ERROR, e.getMessage(), null);
		}
		return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, null);
	}

	@RequestMapping(value = "viewremark", method = RequestMethod.POST)
	@ResponseBody
	public ResultVo viewremark(@RequestParam("id") int id, HttpServletRequest request, ModelMap map) {
		WorkRecord o = null;
		try {
			o = workRecordService.queryWorkRecord(id);
		} catch (BizException e) {
			return new ResultVo(AppConstant.RESULT_STATUS_ERROR, e.getMessage(), null);
		}
		if (o == null) {
			o = new WorkRecord();
		}
		return new ResultVo(AppConstant.RESULT_STATUS_SUCCESS, null, o.getRemark());
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
}
