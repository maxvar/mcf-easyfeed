package ru.maxvar.mcf.easyfeed;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class Mod implements ModInitializer {
    static final String MOD_ID = "mcf-easyfeed";
    static final Logger LOGGER = LogManager.getFormatterLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info(MOD_ID + " loaded");
    }
}
