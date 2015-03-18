package com.maqs.moneytracker.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maqs.moneytracker.common.service.exception.ServiceException;
import com.maqs.moneytracker.dto.DomainSearchDto;
import com.maqs.moneytracker.model.Account;
import com.maqs.moneytracker.model.Category;
import com.maqs.moneytracker.services.DomainService;
import com.maqs.moneytracker.types.Period;
import com.wordnik.swagger.annotations.Api;

/**
 * The REST webservices are exposed to the external world. 
 * 
 * @author maqbool.ahmed
 */
@Controller
@RequestMapping("/api/domain")
@Api(value = "domain", description = "Domain API")
@Secured("ROLE_USER")
public class DomainController {

	@Autowired
	private DomainService domainService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Lists the Categories by given search criteria.
	 * 
	 * @param page
	 *            current page
	 * @return List of Project Dtos
	 * @throws ServiceException
	 */
	@RequestMapping(value="/categories", method = RequestMethod.POST)
	public @ResponseBody
	List<Category> listCategories(@RequestBody DomainSearchDto dto)
			throws ServiceException {
		if (logger.isDebugEnabled())
			logger.debug("listCategories method is been called");
		
		List<Category> categories = domainService.listCategories(dto);
		if (logger.isInfoEnabled())
			logger.info("listCategories() has listed "
					+ (categories == null ? 0 : categories.size()) + " records");

		return categories;
	}
	
	/**
	 * Lists the Accounts by given search criteria.
	 * 
	 * @param page
	 *            current page
	 * @return List of Project Dtos
	 * @throws ServiceException
	 */
	@RequestMapping(value="/accounts", method = RequestMethod.POST)
	public @ResponseBody
	List<Account> listAccounts(@RequestBody DomainSearchDto dto)
			throws ServiceException {
		if (logger.isDebugEnabled())
			logger.debug("listAccounts method is been called");
		
		List<Account> accounts = domainService.listAccounts(dto);
		if (logger.isInfoEnabled())
			logger.info("listAccounts() has listed "
					+ (accounts == null ? 0 : accounts.size()) + " records");

		return accounts;
	}
	
	/**
	 * Lists the Categories by given search criteria.
	 * 
	 * @param page
	 *            current page
	 * @return List of Project Dtos
	 * @throws ServiceException
	 */
	@RequestMapping(value="/categorytree", method = RequestMethod.POST)
	public @ResponseBody
	List<Category> listCategoryTree(@RequestBody DomainSearchDto dto)
			throws ServiceException {
		if (logger.isDebugEnabled())
			logger.debug("listCategoryTree method is been called");
		
		List<Category> categories = domainService.listCategoryTree(dto);
		if (logger.isInfoEnabled())
			logger.info("listCategoryTree() has listed "
					+ (categories == null ? 0 : categories.size()) + " records");

		return categories;
	}
	
	@RequestMapping(value="/periods", method = RequestMethod.GET)
	public @ResponseBody
	List<Period> listPeriod()
			throws ServiceException {
		if (logger.isDebugEnabled())
			logger.debug("listPeriod method is been called");
		
		List<Period> periods = new ArrayList<Period>(Period.values());
		return periods;
	}
}
