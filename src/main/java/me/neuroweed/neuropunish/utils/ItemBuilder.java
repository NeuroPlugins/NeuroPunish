package me.neuroweed.neuropunish.utils;

import me.neuroweed.neuropunish.managers.MessagesManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private ItemStack stack;

    public ItemBuilder(Material material, String name, int amount){
        ItemStack stack = new ItemStack(material, amount);
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(MessagesManager.c(name));
        stack.setItemMeta(meta);
        this.stack = stack;
    }

    public ItemBuilder(ItemStack stack){
        this.stack = stack;
    }

    public ItemBuilder resetLore(){
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.setLore(new ArrayList<>());
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addLine(String line){
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();
        if(meta.getLore() == null){
            lore = new ArrayList<>();
        }
        lore.add(MessagesManager.c(line));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeLine(int index){
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();
        if(meta.getLore() == null){
            lore = new ArrayList<>();
        }
        try {
            lore.remove(index);
        }catch (IndexOutOfBoundsException e){
            Bukkit.getConsoleSender().sendMessage("Line " + index + " not found in this item. ITEM -->" + stack.getData().toString());
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder editLine(int index, String newLine){
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();
        if(meta.getLore() == null){
            lore = new ArrayList<>();
        }
        try {
            lore.set(index, newLine);
        }catch (IndexOutOfBoundsException e){
            Bukkit.getConsoleSender().sendMessage("Line " + index + " not found in this item. ITEM -->" + stack.getData().toString());
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.addEnchant(enchantment, level, true);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag){
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.addItemFlags(flag);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemStack getRaw(){
        return this.stack;
    }

}