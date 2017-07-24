package com.bhg.pipeServer.utils;

/**
 * 远程SSH操作参数
 * @author llq
 *
 */
public class SshRemoteParams {
    private String sship;
    private int sshPort;
    private String sshUser;
    private String sshPassword;
    
    public String getSship() {
        return sship;
    }
    public void setSship(String sship) {
        this.sship = sship;
    }
    public int getSshPort() {
        return sshPort;
    }
    public void setSshPort(int sshPort) {
        this.sshPort = sshPort;
    }
    public String getSshUser() {
        return sshUser;
    }
    public void setSshUser(String sshUser) {
        this.sshUser = sshUser;
    }
    public String getSshPassword() {
        return sshPassword;
    }
    public void setSshPassword(String sshPassword) {
        this.sshPassword = sshPassword;
    }
    
    
}
