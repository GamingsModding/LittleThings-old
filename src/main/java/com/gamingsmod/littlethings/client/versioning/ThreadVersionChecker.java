package com.gamingsmod.littlethings.client.versioning;

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        VersionChecker.doneChecking = true;
    }
}
