package com.maqs.moneytracker.services;

import java.util.List;

import com.maqs.moneytracker.common.paging.Page;
import com.maqs.moneytracker.common.service.exception.ServiceException;
import com.maqs.moneytracker.model.Setting;

public interface ApplicationService {

	public List<Setting> listAll(Page page)
			throws ServiceException;

	public List<Setting> saveAll(List<Setting> changedList) throws ServiceException;

}
