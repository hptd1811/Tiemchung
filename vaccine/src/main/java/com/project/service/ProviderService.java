package com.project.service;

import com.project.entity.Provider;

public interface ProviderService {

    /**
     * @return
     */
    void createProvider(String name);

    /**
     * @return
     */
    Provider searchNameProvider(String name);
}

