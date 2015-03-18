package com.maqs.moneytracker.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.maqs.moneytracker.common.paging.Page;
import com.maqs.moneytracker.common.paging.spec.QuerySpec;
import com.maqs.moneytracker.common.service.exception.ServiceException;
import com.maqs.moneytracker.model.Setting;
import com.maqs.moneytracker.server.core.dao.IDao;
import com.maqs.moneytracker.server.core.exception.DataAccessException;

@Service
@Transactional(value="transactionManager", readOnly = true)
public class ApplicationServiceImpl implements ApplicationService {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private IDao dao;

	private QuerySpec settingQuery = new QuerySpec(Setting.class.getName());
	
	public ApplicationServiceImpl() {

	}

	public ApplicationServiceImpl(IDao dao) {
		setDao(dao);
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
	public List<Setting> listAll(Page page) throws ServiceException {
		List<Setting> settings = null;
		try {
			settings = (List<Setting>) dao.listAll(settingQuery, page);
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
		return settings;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
	public List<Setting> saveAll(List<Setting> changedList)
			throws ServiceException {
		try {
			dao.saveAll(changedList);
		} catch (DataAccessException e) {
			throw new ServiceException(e);
		}
		return changedList;
	}
}
