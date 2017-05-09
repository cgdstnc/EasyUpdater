/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import static jdk.nashorn.internal.objects.NativeRegExp.source;
import model.PacsViewerVersion;
import model.Settings;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import network.Request;
import network.Response;
import network.Web;

/**
 *
 * @author Administrator
 */
public class Updater {
    /**
     * metaJviewer'ın singleInstance icin kullandıgı default port 1809 guncellemeden once onu kapatacak.
     */
    public static String forceUpdate() throws IOException {
        Settings s = SettingFunctions.getSettings();
        Request req = new Request();
        req.setApiId(-1);
        req.setApiName("versionCheck");
        req.setRequestMethod("get");
        req.setRequestURL(s.getUpdateServerIp() + ":" + s.getUpdateServerPort() + s.getVersionCheckApi());
        
        try {
            Response resp = Web.send(req);
            PacsViewerVersion pacsViewerVersion = new Gson().fromJson(resp.getResponseRaw(), PacsViewerVersion.class);

            int availableVersion = s.isReciveExperimentalUpdatesOn() ? pacsViewerVersion.getNightlyBuild() : pacsViewerVersion.getStableVersion();
            if (s.getLastVersion() != availableVersion) {// < || > değil tam istenilen versionda çalışsın
                Web.saveFile(availableVersion + ".zip", s.getUpdateServerIp() + ":" + s.getUpdateServerPort() + s.getUpdateApi() + "?v=" + availableVersion);

                File f = new File(availableVersion + ".zip");
                try {
                    System.out.println(String.valueOf(s.getMetaJviewerSingleInstancePort()));
                    Common.terminateProcess(String.valueOf(s.getMetaJviewerSingleInstancePort()));
                    Thread.sleep(2500);//donen process i waitfor yapilmali ama daha once bir sebepten oturu yapmadim suan hatirlamiyorum. 2.5 sn cok degil beklesin.
                } catch (Exception e) {
                    return "Hata\n" + e.getLocalizedMessage();
                }
                try {
                    ZipFile zipFile = new ZipFile(availableVersion + ".zip");
                    for (int i = 0; i < zipFile.getFileHeaders().size(); i++) {
                        FileHeader get = (FileHeader) zipFile.getFileHeaders().get(i);
                        if (!get.getFileName().equalsIgnoreCase("properties")) {
                            zipFile.extractFile(get.getFileName(), f.getAbsoluteFile().getParentFile().getAbsolutePath());
                        }
                    }
                    try {
                        f.deleteOnExit();
                    } catch (Exception e) {
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                    return "Hata\n" + e.getLocalizedMessage();
                }
                return "";
                
            }
        } catch (Exception e) {
            return "Hata\n" + e.getLocalizedMessage();
        }

        return "-1";
    }

    public static boolean newerVersionAvailable() {
        Settings s = SettingFunctions.getSettings();
        Request req = new Request();
        req.setApiId(-1);
        req.setApiName("versionCheck");
        req.setRequestMethod("get");
        req.setRequestURL(s.getUpdateServerIp() + ":" + s.getUpdateServerPort() + s.getVersionCheckApi());
        Response resp = Web.send(req);

        PacsViewerVersion pacsViewerVersion = new Gson().fromJson(resp.getResponseRaw(), PacsViewerVersion.class);

        int availableVersion = s.isReciveExperimentalUpdatesOn() ? pacsViewerVersion.getNightlyBuild() : pacsViewerVersion.getStableVersion();

        return (s.getLastVersion() != availableVersion);
    }
}
