package com.peng.plant.wattstore.manager;

import java.util.regex.Pattern;

public class AppVersionCompare {

    public AppVersionCompare()
    {
        super();
    }

    public int compare(String v1, String v2)
    {
        String s1 = normalisedVersion(v1);
        String s2 = normalisedVersion(v2);
//        Log.d("AppversionCompare" , "Noah compare: " + s1 + " / " + s2);
        return Integer.parseInt(s2) - Integer.parseInt(s1);//s1.compareTo(s2);
    }

    private String normalisedVersion(String version)
    {
        return normalisedVersion(version, ".", 1);
    }

    private String normalisedVersion(String version, String sep, int maxWidth)
    {
        String[] split = Pattern.compile(sep, Pattern.LITERAL).split(version);
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(String.format("%" + maxWidth + 's', s));
        }
        return sb.toString();
    }
}
