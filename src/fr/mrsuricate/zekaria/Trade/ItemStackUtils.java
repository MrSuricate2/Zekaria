package fr.mrsuricate.zekaria.Trade;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;





public class ItemStackUtils
{
    private static Method asNMSCopy;
    private static Method asBukkitCopy;
    private static Method getTag;
    private static Method setTag;
    private static Method getString;
    private static Method mojangsonParse;
    private static Class<?> craftItemStack;
    private static Class<?> nmsItemStack;
    private static Class<?> nbtTagCompound;
    private static Class<?> mojangsonParser;
    private static int versionId = Integer.parseInt(Bukkit.getBukkitVersion().split("-")[0].replace(".", "#").split("#")[1]);



    public static void loadUtils() {
        try {
            nbtTagCompound = Reflection.getNMSClass("NBTTagCompound");
            mojangsonParser = Reflection.getNMSClass("MojangsonParser");
            nmsItemStack = Reflection.getNMSClass("ItemStack");
            craftItemStack = Reflection.getClass("org.bukkit.craftbukkit." + Reflection.getVersion() + ".inventory.CraftItemStack");

            mojangsonParse = mojangsonParser.getDeclaredMethod("parse", new Class[] { String.class });

            asNMSCopy = craftItemStack.getDeclaredMethod("asNMSCopy", new Class[] { ItemStack.class });
            asBukkitCopy = craftItemStack.getDeclaredMethod("asBukkitCopy", new Class[] { nmsItemStack });

            getTag = nmsItemStack.getDeclaredMethod("getTag", new Class[0]);
            setTag = nmsItemStack.getDeclaredMethod("setTag", new Class[] { nbtTagCompound });

            getString = nbtTagCompound.getDeclaredMethod("getString", new Class[] { String.class });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static String getNBTString(ItemStack itemStack, String path) {
        String message = null;


        try {
            Object nmsCopy = asNMSCopy.invoke(null, new Object[] { itemStack });

            Object tag = getTag.invoke(nmsCopy, new Object[0]);

            String value = (String)getString.invoke(tag, new Object[] { path });

            if (!value.equals("")) {
                message = value;
            }
        }
        catch (Exception e) {
            return null;
        }

        return message;
    }


    public static String getNBTTags(ItemStack itemStack) {
        String message = "";


        try {
            Object nmsCopy = asNMSCopy.invoke(null, new Object[] { itemStack });

            Object tag = getTag.invoke(nmsCopy, new Object[0]);

            if (tag != null) {
                message = tag.toString();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }



    public static ItemStack applyNBTTags(ItemStack itemStack, String nbtTag) {
        if (nbtTag == null) {
            return itemStack;
        }

        if (nbtTag.equals("")) {
            return itemStack;
        }

        ItemStack finalItemStack = itemStack;


        try {
            Object nmsCopy = asNMSCopy.invoke(null, new Object[] { itemStack });

            Object nbtTagCompound = mojangsonParse.invoke(null, new Object[] { nbtTag });

            setTag.invoke(nmsCopy, new Object[] { nbtTagCompound });

            finalItemStack = (ItemStack)asBukkitCopy.invoke(null, new Object[] { nmsCopy });
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return finalItemStack;
    }



    public static ItemStack getTexturedNBTItem(int amount, String displayName, String texture, String nbtTag) {
        ItemStack itemStack = getItem(Material.valueOf((versionId >= 13) ? "LEGACY_SKULL_ITEM" : "SKULL_ITEM"), amount, 3, displayName, new String[0]);

        itemStack = applyNBTTags(itemStack, nbtTag);

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemStack = addTexture(itemStack, itemMeta, texture);

        itemMeta.setDisplayName(displayName);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }



    public static ItemStack getTexturedNBTItem(int amount, String displayName, List<String> lores, String texture, String nbtTag) {
        ItemStack itemStack = getItem(Material.valueOf((versionId >= 13) ? "LEGACY_SKULL_ITEM" : "SKULL_ITEM"), amount, 3, displayName, new String[0]);

        itemStack = applyNBTTags(itemStack, nbtTag);

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemStack = addTexture(itemStack, itemMeta, texture);

        itemMeta.setDisplayName(displayName);

        itemMeta.setLore(lores);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }



    public static ItemStack getTexturedItem(int amount, String displayName, String texture) {
        ItemStack itemStack = getItem(Material.valueOf((versionId >= 13) ? "LEGACY_SKULL_ITEM" : "SKULL_ITEM"), amount, 3, displayName, new String[0]);

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemStack = addTexture(itemStack, itemMeta, texture);

        itemMeta.setDisplayName(displayName);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }



    public static ItemStack getTexturedItem(int amount, String displayName, List<String> lores, String texture) {
        ItemStack itemStack = getItem(Material.valueOf((versionId >= 13) ? "LEGACY_SKULL_ITEM" : "SKULL_ITEM"), amount, 3, displayName, new String[0]);

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemStack = addTexture(itemStack, itemMeta, texture);

        itemMeta.setDisplayName(displayName);

        itemMeta.setLore(lores);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }



    public static ItemStack addTexture(ItemStack itemStack, ItemMeta meta, String texture) {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));


        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return itemStack;
    }


    public static String getTexture(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();


        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            GameProfile gameProfile = (GameProfile)profileField.get(meta);

            if (gameProfile == null) {
                return "";
            }

            return ((Property)gameProfile.getProperties().get("textures").iterator().next()).getValue();
        }
        catch (Exception e) {
            e.printStackTrace();


            return "";
        }
    }


    public static ItemStack getItem(Material material, int amount, int data, String title, String... lores) {
        ItemStack itemStack = new ItemStack(material, amount, (short)(byte)data);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(title);
        meta.setLore(Arrays.asList(lores));
        itemStack.setItemMeta(meta);

        return itemStack;
    }




    public static ItemStack getItem(Material material, int amount, int data, String title, List<String> lores) {
        ItemStack itemStack = new ItemStack(material, amount, (short)(byte)data);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(title);
        meta.setLore(lores);
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
