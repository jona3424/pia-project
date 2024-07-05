package com.example.back.controller;

import com.example.back.entities.MenuItems;
import com.example.back.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @GetMapping
    public List<MenuItems> getAllMenuItems() {
        return menuItemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItems> getMenuItemById(@PathVariable Integer id) {
        Optional<MenuItems> MenuItems = menuItemService.findById(id);
        return MenuItems.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public MenuItems createMenuItem(@RequestBody MenuItems MenuItems) {
        return menuItemService.save(MenuItems);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItems> updateMenuItem(@PathVariable Integer id, @RequestBody MenuItems menuItemDetails) {
        Optional<MenuItems> MenuItems = menuItemService.findById(id);
        if (MenuItems.isPresent()) {
            MenuItems updatedMenuItem = MenuItems.get();
            updatedMenuItem.setName(menuItemDetails.getName());
            updatedMenuItem.setDescription(menuItemDetails.getDescription());
            updatedMenuItem.setPrice(menuItemDetails.getPrice());
            updatedMenuItem.setImage(menuItemDetails.getImage());
            return ResponseEntity.ok(menuItemService.save(updatedMenuItem));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Integer id) {
        menuItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

