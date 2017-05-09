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
public class Settings {
    private String updateServerIp;
    private String updateServerPort;
    private String updateApi;
    private String versionCheckApi;
    private Double lastVersion;
    private boolean reciveExperimentalUpdates;
    private int metaJviewerSingleInstancePort;
    
    public Settings() {
    }

    public Settings(String updateServerIp, String updateServerPort, String updateApi, String versionCheckApi, Double lastVersion, boolean reciveExperimentalUpdates, int metaJviewerSingleInstancePort) {
        this.updateServerIp = updateServerIp;
        this.updateServerPort = updateServerPort;
        this.updateApi = updateApi;
        this.versionCheckApi = versionCheckApi;
        this.lastVersion = lastVersion;
        this.reciveExperimentalUpdates = reciveExperimentalUpdates;
        this.metaJviewerSingleInstancePort = metaJviewerSingleInstancePort;
    }

    public String getUpdateServerIp() {
        return updateServerIp;
    }

    public void setUpdateServerIp(String updateServerIp) {
        this.updateServerIp = updateServerIp;
    }

    public String getUpdateServerPort() {
        return updateServerPort;
    }

    public void setUpdateServerPort(String updateServerPort) {
        this.updateServerPort = updateServerPort;
    }

    public String getUpdateApi() {
        return updateApi;
    }

    public void setUpdateApi(String updateApi) {
        this.updateApi = updateApi;
    }

    public String getVersionCheckApi() {
        return versionCheckApi;
    }

    public void setVersionCheckApi(String versionCheckApi) {
        this.versionCheckApi = versionCheckApi;
    }

    public Double getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(Double lastVersion) {
        this.lastVersion = lastVersion;
    }

    public boolean isReciveExperimentalUpdatesOn() {
        return reciveExperimentalUpdates;
    }

    public void setReciveExperimentalUpdates(boolean reciveExperimentalUpdates) {
        this.reciveExperimentalUpdates = reciveExperimentalUpdates;
    }

    public int getMetaJviewerSingleInstancePort() {
        return metaJviewerSingleInstancePort;
    }

    public void setMetaJviewerSingleInstancePort(int metaJviewerSingleInstancePort) {
        this.metaJviewerSingleInstancePort = metaJviewerSingleInstancePort;
    }
    
}
