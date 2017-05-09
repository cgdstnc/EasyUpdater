/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metajviewerupdater;

import common.Common;
import common.Updater;
import java.io.File;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import view.VersionDialog;

/**
 *
 * @author Administrator
 */
public class MetaJviewerUpdater {

    public static void checkAndShowDialog() {
        VersionDialog dialog = new VersionDialog();
        if (Updater.newerVersionAvailable()) {
            dialog.setVisible(true);
        }
    }

    
    public static void main(String[] args) throws Exception {
        //checkAndShowDialog();
        System.out.println(Common.getTxtRecord("idealupdatemtjv.metasoft.teknopark").replaceAll("\\\\",""));
    }
    
}
