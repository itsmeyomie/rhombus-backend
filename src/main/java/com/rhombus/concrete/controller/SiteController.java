package com.rhombus.concrete.controller;

import com.rhombus.concrete.entity.Site;
import com.rhombus.concrete.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sites")
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.1.2:4200"})
public class SiteController {
    @Autowired
    private SiteService siteService;

    @GetMapping
    public ResponseEntity<List<Site>> getAllSites() {
        return ResponseEntity.ok(siteService.getAllSites());
    }

    @PostMapping
    public ResponseEntity<Site> createSite(@RequestBody Site site) {
        return ResponseEntity.ok(siteService.createSite(site));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        siteService.deleteSite(id);
        return ResponseEntity.ok().build();
    }
}



