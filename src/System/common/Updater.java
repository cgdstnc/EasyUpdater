/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System.common;

import Model.ModuleVersion;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import Model.Settings;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import System.network.Request;
import System.network.Response;
import System.network.Web;
import net.lingala.zip4j.exception.ZipException;

/**
 *
 * @author Administrator
 */
public class Updater {

    public static void update(String proje, Integer portBindingToForceKillProcess) throws IOException, ZipException, Exception {
        Settings s = SettingFunctions.getSettings();
        ModuleVersion mv = getCurrentVersions(proje);
        Integer v = s.isReciveExperimentalUpdatesOn() ? mv.getNightlyBuild() : mv.getStableVersion();

        if (newerVersionAvailable(mv)) {
            if (proje == null || v == null) {
                throw new Exception("Hatali Parametre Updater.update()");
            }
            Web.saveFile(v + ".zip", s.getUpdateServerIp() + ":" + s.getUpdateServerPort() + s.getUpdateApi() + "?p=" + proje + "&v=" + v);
            //close process using this port 
            if (portBindingToForceKillProcess != null) {
                Common.terminateProcess(String.valueOf(portBindingToForceKillProcess), false);
                //Thread.sleep(2500);//Common.terminateProcess() icindeki taskill.process.wait e bakilacak
            }

            //unzip
            File f = new File(v + ".zip");
            ZipFile zipFile = new ZipFile(v + ".zip");
            for (int i = 0; i < zipFile.getFileHeaders().size(); i++) {
                FileHeader get = (FileHeader) zipFile.getFileHeaders().get(i);
                if (!get.getFileName().equalsIgnoreCase("properties")) {//parametre olarak alinabilir ileride su isimdekileri degistirme seklinde
                    zipFile.extractFile(get.getFileName(), f.getAbsoluteFile().getParentFile().getAbsolutePath());
                }
            }
            f.deleteOnExit();
            s.setLastVersion(Double.valueOf(v));
            SettingFunctions.setSettings(s);
        }
    }

    public static boolean newerVersionAvailable(ModuleVersion mv) {
        Settings s = SettingFunctions.getSettings();
        int availableVersion = s.isReciveExperimentalUpdatesOn() ? mv.getNightlyBuild() : mv.getStableVersion();

        return (s.getLastVersion() != availableVersion);
    }

    public static ModuleVersion getCurrentVersions(String proje) {
        Settings s = SettingFunctions.getSettings();

        Request req = new Request();
        req.setApiId(-1);
        req.setApiName("lastVersion");
        req.setRequestMethod("get");
        req.setRequestURL(s.getUpdateServerIp() + ":" + s.getUpdateServerPort() + s.getVersionCheckApi() + "?p=" + proje);
        Response resp = Web.send(req);

        ModuleVersion moduleVersion = new Gson().fromJson(resp.getResponseRaw(), ModuleVersion.class);
        return moduleVersion;
    }

}
