package ru.maxvar.mcf.easyfeed;

import net.fabricmc.api.ModInitializer;

@SuppressWarnings("unused")
public class Mod implements ModInitializer {
    static final String MOD_ID = "mcf-easyfeed";

    @Override
    public void onInitialize() {
        System.out.println(MOD_ID + " init!");
    }
}
