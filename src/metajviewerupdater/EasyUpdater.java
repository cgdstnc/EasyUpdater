/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metajviewerupdater;

import System.common.Updater;
import View.VersionDialog;

/**
 *
 * @author Administrator
 */
public class EasyUpdater {

    public static void checkAndShowDialog(String proje, Integer portBindingToForceKillProcess) {
        if (Updater.newerVersionAvailable(Updater.getCurrentVersions(proje))) {
            VersionDialog dialog = new VersionDialog(proje, portBindingToForceKillProcess);
            dialog.setVisible(true);
        }
    }

    public static void forceUpdate(String proje, Integer portBindingToForceKillProcess) {
        if (Updater.newerVersionAvailable(Updater.getCurrentVersions(proje))) {
            try {
                Updater.update(proje, portBindingToForceKillProcess);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String proje = null;
        Integer portToKill = null;

        try {
            proje = args[0];
            portToKill = (Integer.valueOf(args[1]) == null) ? null : Integer.valueOf(args[1]);
            //checkAndShowDialog(proje, portToKill);
            Updater.update(proje, portToKill);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ornek kullanim java -jar EasyUpdater.jar {projeAdi} {(int)projeninTuttuguPortYoksaKullanilmayan4-5hanelibirseysalla}");
        }

//        //debug 
//        proje = "mjv";
//        portToKill = 1809;
//checkAndShowDialog(proje, portToKill);
    }

}
