package com.pinuspintar.bootcamp.controller;

import com.pinuspintar.bootcamp.model.Item;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController  // Menandakan ini adalah controller untuk REST API
@RequestMapping("/items")  // Menambahkan path dasar "/items" untuk semua endpoint di controller ini
public class Pertemuan2 {
    private List<Item> items = new ArrayList<>();
    private int nextId = 1;

    // Endpoint untuk mendapatkan semua item
    @GetMapping
    public List<Item> getAllItems() {
        return items;
    }

    // Endpoint untuk membuat item baru
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        item.setId(nextId++);
        items.add(item);
        return item;
    }

    // Endpoint untuk mendapatkan item berdasarkan ID
    @GetMapping("/{id}")
    public Item getItem(@PathVariable int id) {
        return items.stream()
                .filter(item -> item.getId() == id)  // Cari item dengan ID yang sesuai
                .findFirst()
                .orElse(null);  // Kembalikan null jika tidak ditemukan
    }

    // Endpoint untuk memperbarui item berdasarkan ID
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable int id, @RequestBody Item updateItem) {
        // Cari item yang ingin diupdate
        Item item = items.stream()
                .filter(existingItem -> existingItem.getId() == id)  // Filter berdasarkan ID
                .findFirst()
                .orElse(null);  // Kembalikan null jika item tidak ditemukan

        if (item != null) {
            item.setName(updateItem.getName());  // Update nama item
        }
        return item;  // Kembalikan item yang telah diupdate
    }

    // Endpoint untuk menghapus item berdasarkan ID
    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable int id) {
        boolean removed = items.removeIf(item -> item.getId() == id);  // Hapus item jika ditemukan
        if (removed) {
            return "Item dengan id = " + id + " telah dihapus";  // Return success message
        } else {
            return "Item dengan id = " + id + " tidak ditemukan";  // Return error message jika item tidak ditemukan
        }
    }
}
