/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import model.Settings;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author Administrator
 */
public class SettingFunctions {

    private static Settings settings;

    private static String getDNSServer() {
        File saved = new File("dns");
        try {
            if (saved.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(saved));
                return br.readLine();
            } else {
                FileWriter fileWriter = new FileWriter(saved);
                fileWriter.write("idealupdatemtjv.metasoft.teknopark");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Settings getSettings() {
        String lg = "";
        try {
            if (settings == null) {
                String dns = getDNSServer();
                String dnsTXT=Common.getTxtRecord(dns);
                System.out.println(dnsTXT);
                String json = dnsTXT.replaceAll("\\\\", "");
                json = "{" + json.substring(0, json.length() - 1) + "}";
                settings = new Gson().fromJson(json, Settings.class);
                setSettings(settings);
                lg = "DNS";
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DNS'den ayarlar alinamadi. Dosyaya Bakiliyor.");
            File saved = new File("updater");
            if (!saved.exists()) {
                settings = getDefaultSettings();
                setSettings(settings);

            } else {
                try {
                    settings = new Gson().fromJson(new BufferedReader(new FileReader(saved)), Settings.class);
                    lg = "File";
                } catch (Exception ex) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(lg);
        return settings;

//        if (settings == null) {
//            File saved = new File("updater");
//            if (!saved.exists()) {
//                settings = getDefaultSettings();
//                setSettings(settings);
//
//            } else {
//                try {
//                    settings = new Gson().fromJson(new BufferedReader(new FileReader(saved)), Settings.class);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return settings;
    }

    private static Settings getDefaultSettings() {
        Settings settings = new Settings();
        settings.setLastVersion(-1d);
        settings.setUpdateServerIp("http://192.168.12.128");
        settings.setUpdateServerPort("1893");
        settings.setVersionCheckApi("/lastVersion");
        settings.setUpdateApi("/update");
        settings.setReciveExperimentalUpdates(false);
        settings.setMetaJviewerSingleInstancePort(1809);
        return settings;
    }

    public static void setSettings(Settings s) {
        settings = s;
        try {
            File saved = new File("updater");
            String tmp = new Gson().toJson(settings, settings.getClass());
            tmp = tmp.replaceAll(",", ",\n");
            try {
                FileWriter fileWriter = new FileWriter(saved);
                fileWriter.write(tmp);
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
