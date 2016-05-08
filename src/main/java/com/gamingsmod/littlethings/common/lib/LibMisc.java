package com.gamingsmod.littlethings.common.lib;

public class LibMisc
{
    public static final String MOD_ID = "LittleThings";
    public static final String MOD_NAME = "Little Things";
    public static final String BUILD = "GRADLE:BUILD";
    public static final String VERSION = "GRADLE:VERSION-" + BUILD;
    public static final String DEPENDENCIES = "required-after:Forge@[12.16.0.1846,);";
    public static final String PREFIX_MOD = MOD_ID.toLowerCase() + ":";

    public static final String CLIENT_PROXY = "com.gamingsmod.littlethings.client.ClientProxy";
    public static final String SERVER_PROXY = "com.gamingsmod.littlethings.common.CommonProxy";
}
