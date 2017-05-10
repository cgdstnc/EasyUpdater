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
public class MetaJviewerUpdater {

    public static void checkAndShowDialog(String proje, Integer portBindingToForceKillProcess) {
        if (Updater.newerVersionAvailable(Updater.getCurrentVersions(proje))) {
            VersionDialog dialog = new VersionDialog(proje, portBindingToForceKillProcess);
            dialog.setVisible(true);
        }
    }

    public static void main(String[] args) throws Exception {
        String proje = null;
        Integer portToKill = null;

        try {
            proje = args[0];
            portToKill = (Integer.valueOf(args[1]) == null) ? null : Integer.valueOf(args[1]);
        } catch (Exception e) {
        }

//        //debug 
//        proje = "mjv";
//        portToKill = 1809;
        checkAndShowDialog(proje, portToKill);
    }

}
