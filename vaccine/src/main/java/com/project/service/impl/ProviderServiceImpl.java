package com.project.service.impl;

import com.project.entity.Provider;
import com.project.repository.ProviderRepository;
import com.project.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    ProviderRepository providerRepository;


    /**
     * @return
     */
    @Override
    public void createProvider(String name) {
        providerRepository.createProvider(name);
    }


    /**
     * @return
     */
    @Override
    public Provider searchNameProvider(String name) {
        return providerRepository.searchNameProvider(name);
    }
}
