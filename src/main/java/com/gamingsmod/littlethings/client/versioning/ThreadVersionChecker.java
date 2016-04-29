package com.gamingsmod.littlethings.client.versioning;

import com.gamingsmod.littlethings.common.helper.PlayerHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ThreadVersionChecker extends Thread
{
    public ThreadVersionChecker()
    {
        setName("LittleThings Version Checker");
        setDaemon(true);
        start();
    }

    public void run()
    {
        try {
            URL url = new URL("https://raw.githubusercontent.com/GamingsModding/LittleThings/master/version/mc" + MinecraftForge.MC_VERSION + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            VersionChecker.version = reader.readLine();
            reader.close();
            if (PlayerHelper.isAlphaTester(Minecraft.getMinecraft().thePlayer)) {
                URL urla = new URL("https://raw.githubusercontent.com/GamingsModding/LittleThings/master/version/mc" + MinecraftForge.MC_VERSION + "a.txt");
                BufferedReader readera = new BufferedReader(new InputStreamReader(urla.openStream()));
                VersionChecker.alpha_version = readera.readLine();
                readera.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        VersionChecker.doneChecking = true;
    }
}
