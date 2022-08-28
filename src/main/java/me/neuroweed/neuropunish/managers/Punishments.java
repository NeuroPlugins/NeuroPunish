package me.neuroweed.neuropunish.managers;

import org.bukkit.Material;

public class Punishments {

    public enum PunishType{
        KICK, BAN, CRASH
    }

    /**
     * 1:
     *     SLOT_GUI: 1
     *     PUNISH_TYPE: KICK
     *     PUNISH_MATERIAL: FEATHER
     *     PUNISH_TITLE: "&7Kick"
     *     PUNISH_REASON: "Abusing"
     */

    private String key;

    private int slot;
    private PunishType type;
    private Material material;
    private String title;
    private String reason;
    private String duration;

    public Punishments() {

    }

    public int getSlot() {
        return slot;
    }

    public PunishType getType() {
        return type;
    }

    public Material getMaterial() {
        return material;
    }

    public String getTitle() {
        return title;
    }

    public String getReason() {
        return reason;
    }

    public String getDuration() {
        return duration;
    }

    public String getKey() {
        return key;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setType(PunishType type) {
        this.type = type;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
