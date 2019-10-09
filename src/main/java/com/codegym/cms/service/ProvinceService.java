package com.codegym.cms.service;

import com.codegym.cms.model.Customer;
import com.codegym.cms.model.Province;

public interface ProvinceService {
    Province findById(Long id);
    void save(Province province);
    void remove(Long id);
    Iterable<Province> findAll();
}