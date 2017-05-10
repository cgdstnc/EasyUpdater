/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Administrator
 */
public class ModuleVersion {

    private Integer nightlyBuild;
    private Integer stableVersion;

    public ModuleVersion() {
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
