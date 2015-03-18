package com.maqs.moneytracker.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.maqs.moneytracker.common.paging.Page;
import com.maqs.moneytracker.common.paging.spec.Operation;
import com.maqs.moneytracker.common.paging.spec.PropertySpec;
import com.maqs.moneytracker.common.paging.spec.QuerySpec;
import com.maqs.moneytracker.common.service.exception.ServiceException;
import com.maqs.moneytracker.common.transferobjects.Entity;
import com.maqs.moneytracker.common.util.CollectionsUtil;
import com.maqs.moneytracker.common.util.Util;
import com.maqs.moneytracker.dto.DomainSearchDto;
import com.maqs.moneytracker.model.Account;
import com.maqs.moneytracker.model.Category;
import com.maqs.moneytracker.server.core.dao.IDao;
import com.maqs.moneytracker.server.core.exception.DataAccessException;
import com.maqs.moneytracker.types.AccountType;
import com.maqs.moneytracker.types.TransactionType;

@Service
@Transactional(value="transactionManager", readOnly = true)
public class DomainServiceImpl implements DomainService {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private IDao dao = null;

	private QuerySpec accountsQuery = new QuerySpec(Account.class.getName());
	
	private Map<Long, Category> categoriesCacheMap;
	
	private Map<Long, Account> accountsCacheMap;
	
	public DomainServiceImpl() {
		this(null);
	}

	public DomainServiceImpl(IDao dao) {
		setDao(dao);
		// cache purpose
	}

	public void setDao(IDao categoryDao) {
		this.dao = categoryDao;
	}

	public IDao getDao() {
		return dao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Category> listByType(TransactionType transactionType, Page page)
			throws ServiceException {
		List<Category> categories = null;
		QuerySpec querySpec = new QuerySpec(Category.class.getName());
		querySpec.addPropertySpec(new PropertySpec(Category.TRAN_TYPE,
				Operation.EQ, transactionType.getCode()));
		try {
			categories = (List<Category>) dao.listAll(querySpec, page);
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
		return categories;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public List<Category> store(List<Category> categories)
			throws ServiceException {
		try {
			dao.saveAll(categories);
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
		return categories;
	}

	@Override
	public Category getCategoryByName(String categoryName)
			throws ServiceException {
		try {
			Category c = (Category) dao.getEntity(Category.class, 
					Category.NAME, categoryName);
			return c;
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Account> listAccounts(Page page) throws ServiceException {
		List<Account> accounts = null;
		QuerySpec querySpec = new QuerySpec(Account.class.getName());
		try {
			accounts = (List<Account>) dao.listAll(querySpec, page);
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
		return accounts;
	}

	@Override
	public Account getAccountByName(String accountName) throws ServiceException {
		try {
			Account a = (Account) dao.getEntity(Account.class, 
					Account.NAME, accountName);
			return a;
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Category> listCategories(boolean parentsOnly, Page page) throws ServiceException {
		List<Category> categories = null;
		QuerySpec querySpec = new QuerySpec(Category.class.getName());
		
		try {
			categories = (List<Category>) dao.listAll(querySpec, page);
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
		return categories;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Account> listAccountsByType(AccountType accountType, Page page)
			throws ServiceException {
		if (accountType == null) {
			throw new IllegalArgumentException("given accountType is null");
		}
		List<Account> accounts = null;
		QuerySpec querySpec = new QuerySpec(Account.class.getName());
		querySpec.addPropertySpec(new PropertySpec(Account.ACCT_TYPE,
				Operation.EQ, accountType.getCode()));
		try {
			accounts = (List<Account>) dao.listAll(querySpec, page);
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
		return accounts;
	}

	@Override
	public List<Category> listCategories(DomainSearchDto dto)
			throws ServiceException {
		if (dto == null) {
			throw new IllegalArgumentException("dto is null...");
		}
		QuerySpec querySpec = new QuerySpec(Category.class.getName());
		List<Long> categoryIds = dto.getCategoryIds();
		if (CollectionsUtil.isNonEmpty(categoryIds)) {
			querySpec.addPropertySpec(new PropertySpec(Category.ID, Operation.IN, categoryIds));
		}
		
		String tranType = dto.getType();
		if (tranType != null) {
			querySpec.addPropertySpec(new PropertySpec(Category.TRAN_TYPE, tranType));
		}
		Page page = dto.getPage();
		List<? extends Entity> entities = list(querySpec, page);
		updateParentCategory(entities);
		List<Category> categories = (List<Category>) entities;
		return categories;
	}

	private List<? extends Entity> list(QuerySpec querySpec, Page page) throws ServiceException {
		List<? extends Entity> entities = null;
		try {
			entities = dao.listAll(querySpec, page);	
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
		return entities;
	}

	private void updateParentCategory(List<? extends Entity> categories) {
		if (CollectionsUtil.isNonEmpty(categories)) {
			Map<Long, Entity> map = Util.getMap(categories);
			for (Entity entity : categories) {
				Category c = (Category) entity;
				Long parentId = c.getParentCategoryId();
				if (parentId != null) {
					Category parent = (Category) map.get(parentId);
					c.setParent(parent);
				}
			}
		}
	}

	@Override
	public List<Account> listAccounts(DomainSearchDto dto)
			throws ServiceException {
		if (dto == null) {
			throw new IllegalArgumentException("dto is null...");
		}
		QuerySpec querySpec = new QuerySpec(Account.class.getName());
		List<Long> accountIds = dto.getAccountIds();
		if (CollectionsUtil.isNonEmpty(accountIds)) {
			querySpec.addPropertySpec(new PropertySpec(Account.ID, Operation.IN, accountIds));
		}
		
		String acctType = dto.getType();
		if (acctType != null) {
			querySpec.addPropertySpec(new PropertySpec(Account.ACCT_TYPE, acctType));
		}
		Page page = dto.getPage();
		return (List<Account>) list(querySpec, page);
	}

	@Override
	public Map<Long, Category> categoriesCacheMap() throws ServiceException {
		if (categoriesCacheMap == null) {
			this.categoriesCacheMap = new HashMap<Long, Category>();
		}
		List<Category> list = listCategories(new DomainSearchDto());
		Map<Long, Entity> map = Util.getMap(list);
		for (Long id : map.keySet()) {
			Category c = (Category) map.get(id);
			categoriesCacheMap.put(id, c);
		}
		return categoriesCacheMap;
	}

	@Override
	public Map<Long, Account> accountsCacheMap() throws ServiceException {
		if (accountsCacheMap == null) {
			this.accountsCacheMap = new HashMap<Long, Account>();
		}
		List<Account> list = listAccounts(new DomainSearchDto());
		Map<Long, Entity> map = Util.getMap(list);
		for (Long id : map.keySet()) {
			Account a = (Account) map.get(id);
			accountsCacheMap.put(id, a);
		}
		return accountsCacheMap;
	}
	
	@Override
	public Account getAccountById(Long id) throws ServiceException {
		try {
			Account a = (Account) dao.getEntity(Account.class, id);
			return a;
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Category getCategoryById(Long catId) throws ServiceException {
		try {
			Category c = (Category) dao.getEntity(Category.class, catId);
			return c;
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<Category> listCategoryTree(DomainSearchDto dto)
			throws ServiceException {
		if (dto == null) {
			throw new IllegalArgumentException("dto is null...");
		}
		QuerySpec querySpec = new QuerySpec(Category.class.getName());
		List<Long> categoryIds = dto.getCategoryIds();
		if (CollectionsUtil.isNonEmpty(categoryIds)) {
			querySpec.addPropertySpec(new PropertySpec(Category.ID, Operation.IN, categoryIds));
		}
		
		String tranType = dto.getType();
		if (tranType != null) {
			querySpec.addPropertySpec(new PropertySpec(Category.TRAN_TYPE, tranType));
		}
		Page page = dto.getPage();
		List<? extends Entity> entities = list(querySpec, page);
		List<Category> categories = (List<Category>) entities;
		return prepareCategoryTree(categories);
	}

	private List<Category> prepareCategoryTree(List<Category> categories) {
		if (CollectionsUtil.isNonEmpty(categories)) {
			Map<Long, Entity> map = Util.getMap(categories);
			List<Category> categoryTree = new ArrayList<Category>(); 
			for (Category c : categories) {
				Long parentId = c.getParentCategoryId();
				if (parentId == null) {
					categoryTree.add(c);
				} else {
					Category parent = (Category) map.get(parentId);
					parent.addChild(c);
				}
			}
			return categoryTree;
		}
		return categories;
	}
	
	@Override
	public List<Category> listParentCategories(DomainSearchDto dto)
			throws ServiceException {
		if (dto == null) {
			throw new IllegalArgumentException("dto is null...");
		}
		QuerySpec querySpec = new QuerySpec(Category.class.getName());
		querySpec.addPropertySpec(new PropertySpec(Category.PARENT_ID, Operation.ISNULL, null));
		Page page = dto.getPage();
		List<? extends Entity> entities = list(querySpec, page);
		List<Category> categories = (List<Category>) entities;
		return categories;
	}
	
	@Override
	public List<Category> listChildCategories(DomainSearchDto dto)
			throws ServiceException {
		if (dto == null) {
			throw new IllegalArgumentException("dto is null...");
		}
		List<Long> parentCategoryIds = dto.getCategoryIds();
		if (! CollectionsUtil.isNonEmpty(parentCategoryIds)) {
			throw new IllegalArgumentException("given parentIds collection is null or empty...");
		}
		QuerySpec querySpec = new QuerySpec(Category.class.getName());
		querySpec.addPropertySpec(new PropertySpec(Category.PARENT_ID, Operation.IN, parentCategoryIds));
		Page page = dto.getPage();
		List<? extends Entity> entities = list(querySpec, page);
		List<Category> categories = (List<Category>) entities;
		return categories;
	}
}
