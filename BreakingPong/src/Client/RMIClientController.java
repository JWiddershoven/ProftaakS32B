/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Helpers.StaticConstants;
import Interfaces.IClient;
import Interfaces.IServer;
import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;
import java.beans.PropertyChangeEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author Jordi
 */
public class RMIClientController extends UnicastRemoteObject implements RemotePropertyListener, IClient {

    public static RemotePublisher services;
    private Registry reg;
    private ClientGUI client;
    private GameLobbyFXController gamelobby;
    private CreateLobbyFXController create;
    private LobbySelectFXController lobby;
    private long timeOut;
    private static final ArrayList<Integer> subscribedGames = new ArrayList<>();

    public RMIClientController(ClientGUI client) throws RemoteException {
        this.client = client;
        this.start();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {

//        if (evt.getPropertyName().equals("getPlayers")) {
//            lobby.addUserToList((ObservableList<RMILobby>) evt);
//            lobby.fillListViews();
//            System.out.println("GETPLAYERS DONE !@#$");
//        }
        System.out.print("RMIClientController property change: " + evt.getNewValue().toString());
    }

    /**
     * services.addListener(rpl, "getChat" + Integer.toString(lobbyId));
     * services.addListener(rpl, "getLobbyPlayers" + Integer.toString(lobbyId));
     *
     * @param rpl THIS
     * @param lobbyId
     */
    public static void subscribeToLobby(RemotePropertyListener rpl, int lobbyId) {
        try {
            services.addListener(rpl, "getChat" + Integer.toString(lobbyId));
            services.addListener(rpl, "getLobbyPlayers" + Integer.toString(lobbyId));
            services.addListener(rpl, "getLobbyStarted" + Integer.toString(lobbyId));
            if (!subscribedGames.contains(lobbyId)) {
                subscribedGames.add(lobbyId);
            }
        }
        catch (RemoteException ex) {
            Logger.getLogger(RMIClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * services.removeListener(rpl, "getChat" + Integer.toString(lobbyId));
     * services.removeListener(rpl, "getLobbyPlayers" +
     * Integer.toString(lobbyId));
     *
     * @param rpl THIS
     * @param lobbyId
     */
    public static void unsubscribeFromLobby(RemotePropertyListener rpl, int lobbyId) {
        try {
            services.removeListener(rpl, "getChat" + Integer.toString(lobbyId));
            services.removeListener(rpl, "getLobbyPlayers" + Integer.toString(lobbyId));
            services.removeListener(rpl, "getLobbyStarted" + Integer.toString(lobbyId));
            if (subscribedGames.contains(lobbyId)) {
                subscribedGames.remove(lobbyId);
            }
        }
        catch (RemoteException ex) {
            Logger.getLogger(RMIClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void start() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Platform.isImplicitExit()) {
                    super.cancel();
                }

                if (System.currentTimeMillis() - timeOut > 10 * 1000 || services == null) {
                    System.out.println("Attempting to setup a connection");
                    try {
                        connect();
                        if (reg != null) {
                            System.out.println("Connected!");
                        }
                    }
                    catch (MalformedURLException ex) {
                        Logger.getLogger(RMIClientController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (NotBoundException ex) {
                        Logger.getLogger(RMIClientController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }, 0, 2500);
    }

    private void connect() throws MalformedURLException, NotBoundException {
        try {
            this.reg = LocateRegistry.getRegistry(StaticConstants.SERVER_IP_ADDRESS, StaticConstants.SERVER_PORT);
            this.services = (IServer) this.reg.lookup("gameServer");

            this.services.addListener(this, "getPlayers");
            this.services.addListener(this, "getLobbys");
            this.services.addListener(this, "lobbyselectChat");
//            for (int i = 0; i < 100; i++) {
//                this.services.addListener(this, "getChat" + Integer.toString(i));
//                this.services.addListener(this,"getLobbyPlayers" + Integer.toString(i));
//            }

        }
        catch (RemoteException | NotBoundException ex) {
            System.out.println("Could not connect to server");
        }
        finally {
            this.timeOut = System.currentTimeMillis();
        }
    }

    public void stop() {
        try {
            if (this.services != null) {
                this.services.removeListener(this, "getPlayers");
                this.services.removeListener(this, "getLobbys");
                this.services.removeListener(this, "lobbyselectChat");
                for (int i : subscribedGames) {
                    this.services.removeListener(gamelobby, "getChat" + Integer.toString(i));
                    this.services.removeListener(gamelobby, "getLobbyPlayers" + Integer.toString(i));
                    this.services.removeListener(gamelobby, "getLobbyStarted" + Integer.toString(i));
                }
            }
            UnicastRemoteObject.unexportObject(this, true);
        }
        catch (RemoteException ex) {
            Logger.getLogger(RMIClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void returnToLobby() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setLobbyController(LobbySelectFXController lobbycontroller) {
        this.lobby = lobbycontroller;
    }

    public void setGameController(GameLobbyFXController gameLobby) {
        this.gamelobby = gameLobby;
    }

    public void setCreateController(CreateLobbyFXController createLobby) {
        this.create = createLobby;
    }

    public LobbySelectFXController getLobbyController() {
        return this.lobby;
    }

    public GameLobbyFXController getGameLobbyFXController() {
        return this.gamelobby;
    }

    public CreateLobbyFXController getCreateLobbyFXController() {
        return this.create;
    }
}
