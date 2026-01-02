package com.rhombus.concrete.service;

import com.rhombus.concrete.entity.Site;
import com.rhombus.concrete.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SiteService {
    @Autowired
    private SiteRepository siteRepository;

    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    public Optional<Site> getSiteById(Long id) {
        return siteRepository.findById(id);
    }

    public Site createSite(Site site) {
        return siteRepository.save(site);
    }

    public boolean deleteSite(Long id) {
        if (siteRepository.existsById(id)) {
            siteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}



