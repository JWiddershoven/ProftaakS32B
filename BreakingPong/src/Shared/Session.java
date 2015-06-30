/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import Interfaces.IServer;

/**
 *
 * @author Mnesymne
 */
public class Session {

    /**
     * Username of the user wich the session belongs to
     */
    private final String username;
    /**
     * Server on wich the session is taking place
     */
    private final IServer server;

    /**
     *
     * @param username name for the session of the user
     * @param server server on wich the session is taking place
     */
    public Session(String username, IServer server) {
        this.username = username;
        this.server = server;
    }

    /**
     * Get the username of the session
     *
     * @return username of session as String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get the server on wich the session is taking place
     *
     * @return Server of session as IServer
     */
    public IServer getServer() {
        return this.server;
    }
}
