package com.astryxion.chaospersists.integration.jei;

import java.util.Collection;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * Hides {@code chaospersists} stacks in JEI that do not appear on the mod's creative tabs.
 * Relies on {@code ChaosPersists.load} having already run {@code applyChaosCreativeTabs()}.
 * <p>
 * Compares against {@link IIngredientRegistry#getAllIngredients} so we blacklist the same stacks JEI
 * actually indexes (subtypes, NBT), not only what {@link Item#getSubItems(CreativeTabs, NonNullList)}
 * returns for {@link CreativeTabs#SEARCH}.
 */
@JEIPlugin
public class JeiChaosPlugin implements IModPlugin {

  @Override
  public void register(IModRegistry registry) {
    // Creative tabs are applied in ChaosPersists.load — do not call applyChaosCreativeTabs() here:
    // a second pass saw tabChaosTools/tabChaosWeapons as "not TOOLS/COMBAT" and moved tools/weapons to Chaos Items.
    IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
    IIngredientRegistry ingredients = registry.getIngredientRegistry();

    NonNullList<ItemStack> allowed = NonNullList.create();
    for (Item item : ForgeRegistries.ITEMS) {
      if (item == null) {
        continue;
      }
      ResourceLocation rl = item.getRegistryName();
      if (rl == null || !"chaospersists".equals(rl.getNamespace())) {
        continue;
      }
      CreativeTabs tab = item.getCreativeTab();
      if (tab == null) {
        continue;
      }
      item.getSubItems(tab, allowed);
    }

    Collection<ItemStack> allItemIngredients = ingredients.getAllIngredients(VanillaTypes.ITEM);
    for (ItemStack stack : allItemIngredients) {
      if (stack == null || stack.isEmpty()) {
        continue;
      }
      Item item = stack.getItem();
      ResourceLocation rl = item.getRegistryName();
      if (rl == null || !"chaospersists".equals(rl.getNamespace())) {
        continue;
      }
      if (item.getCreativeTab() == null) {
        blacklist.addIngredientToBlacklist(stack.copy());
        continue;
      }
      if (!stackListContains(allowed, stack)) {
        blacklist.addIngredientToBlacklist(stack.copy());
      }
    }
  }

  private static boolean stackListContains(NonNullList<ItemStack> list, ItemStack candidate) {
    if (candidate == null || candidate.isEmpty()) {
      return false;
    }
    for (ItemStack s : list) {
      if (s.isEmpty()) {
        continue;
      }
      if (ItemStack.areItemsEqual(s, candidate) && ItemStack.areItemStackTagsEqual(s, candidate)) {
        return true;
      }
    }
    return false;
  }
}
