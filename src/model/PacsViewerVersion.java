/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Administrator
 */
public class PacsViewerVersion {

    private Integer nightlyBuild;
    private Integer stableVersion;

    public PacsViewerVersion() {
    }

    public Integer getNightlyBuild() {
        return nightlyBuild;
    }

    public void setNightlyBuild(Integer nightlyBuild) {
        this.nightlyBuild = nightlyBuild;
    }

    public Integer getStableVersion() {
        return stableVersion;
    }

    public void setStableVersion(Integer stableVersion) {
        this.stableVersion = stableVersion;
    }

    
}
