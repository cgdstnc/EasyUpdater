/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.common;

import Model.Settings;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author Administrator
 */
public class SettingFunctions {

    private static Settings settings;

//    public static Settings getSettings() {
//        try {
//            if (settings == null) {
//
//                setSettings(settings);
//                System.out.println("Ayarlar Suradan Aliniyor: DNS");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("DNS'den ayarlar alinamadi. Dosyaya Bakiliyor.");
//            File saved = new File("easyupdate");
//            if (!saved.exists()) {
//                settings = getDefaultSettings();
//                setSettings(settings);
//
//            } else {
//                try {
//                    settings = new Gson().fromJson(new BufferedReader(new FileReader(saved)), Settings.class);
//                    System.out.println("Ayarlar Suradan Aliniyor: File");
//                } catch (Exception ex) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return settings;
//    }
//    
//    
    public static Settings getSettings() {
        if (settings == null) {
            Settings dnsSettings = readSettingsFromDNS();
            Settings fileSettings = readSettingsFromFile();

            if (dnsSettings == null) {
                return fileSettings;//dosyayakaydetmeye gerek yok zaten dosyadaki
            }
            if (fileSettings == null) {
                settings = dnsSettings;
                setSettings(settings);//dns den ayar okuduk dosyada yok dosyaya kaydet bu ayarları oldugu gibi
            } else {//dns den okuduk dosyaya baktık o da var o zaman merge et ayarları sonra dosyaya kaydet
                dnsSettings.setLastVersion(fileSettings.getLastVersion());
                dnsSettings.setReciveExperimentalUpdates(fileSettings.isReciveExperimentalUpdatesOn());
                settings = dnsSettings;
                setSettings(settings);
            }

        }
        return settings;
    }

    private static Settings getDefaultSettings() {
        Settings settings = new Settings();
        settings.setLastVersion(-1d);
        settings.setUpdateServerIp("http://192.168.12.128");
        settings.setUpdateServerPort("1893");
        settings.setVersionCheckApi("/lastVersion");
        settings.setUpdateApi("/update");
        settings.setReciveExperimentalUpdates(false);
        return settings;
    }

    private static Settings readSettingsFromFile() {
        try {
            File saved = new File("easyupdate");
            return new Gson().fromJson(new BufferedReader(new FileReader(saved)), Settings.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Settings readSettingsFromDNS() {
        try {
            String TXT = Common.getTxtRecord(getDNSServer());
            String json = TXT.replaceAll("\\\\", "");
            json = "{" + json.substring(0, json.length() - 1) + "}";
            return new Gson().fromJson(json, Settings.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

    public static void setSettings(Settings s) {
        settings = s;
        try {
            File saved = new File("easyupdate");
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
