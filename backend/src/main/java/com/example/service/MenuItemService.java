package com.example.back.service;

import com.example.back.entities.MenuItems;
import com.example.back.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<MenuItems> findAll() {
        return menuItemRepository.findAll();
    }

    public Optional<MenuItems> findById(Integer id) {
        return menuItemRepository.findById(id);
    }

    public MenuItems save(MenuItems menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public void deleteById(Integer id) {
        menuItemRepository.deleteById(id);
    }
}

